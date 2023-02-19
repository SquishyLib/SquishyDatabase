package com.github.smuddgge.squishydatabase.implementation.sqlite;

import com.github.smuddgge.squishydatabase.console.Console;
import com.github.smuddgge.squishydatabase.console.ConsoleColour;
import com.github.smuddgge.squishydatabase.errors.ForeignKeyReferenceException;
import com.github.smuddgge.squishydatabase.interfaces.Database;
import com.github.smuddgge.squishydatabase.interfaces.TableAdapter;
import com.github.smuddgge.squishydatabase.record.ForeignKeyAnnotation;
import com.github.smuddgge.squishydatabase.record.Record;
import com.github.smuddgge.squishydatabase.record.RecordField;
import com.github.smuddgge.squishydatabase.record.RecordFieldType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.sql.*;

/**
 * Represents methods not defined in the
 * {@link Database} implementation.
 * However, these methods are specific to
 * the sqlite implementation.
 */
public abstract class AbstractSQLiteDatabase extends Database {

    /**
     * The instance of the file.
     */
    protected File file;

    /**
     * The connection to the sqlite database.
     */
    protected Connection connection;

    /**
     * <h1>Used to get the database's connection instance</h1>
     * A connection should be initialised if the
     * database is enabled. This can be checked
     * with {@link Database#isEnabled()}.
     *
     * @return Instance of the database connection.
     */
    public @Nullable Connection getConnection() {
        return this.connection;
    }

    /**
     * Used to initiate the database driver class.
     */
    protected void initiateDrivers() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException exception) {
            Console.error(this.getPrefix() + "org.sqlite.JDBC does not exist.");
            exception.printStackTrace();
            this.setDisable();
        }
    }

    /**
     * Used to create the parent directory where the database is located.
     */
    @SuppressWarnings("all")
    protected void createDirectory() {
        new File(this.file.getParent()).mkdir();
    }

    /**
     * Used to create a connection to the database.
     *
     * @param url The database url.
     */
    protected void createConnection(@NotNull String url) {
        try {
            // Create a connection
            this.connection = DriverManager.getConnection(url);

        } catch (SQLException exception) {
            exception.printStackTrace();
            this.setDisable();
            return;
        }

        Console.log(this.getPrefix() + ConsoleColour.YELLOW + "Connected to the database successfully.");
    }

    /**
     * Used to get a create table statement.
     *
     * @param table The table to get the statement for.
     */
    protected @NotNull String getCreateTableStatement(@NotNull TableAdapter<?> table) {
        StringBuilder stringBuilder = new StringBuilder();

        // Create the table if it does not exist.
        // Add the table name.
        stringBuilder.append("CREATE TABLE IF NOT EXISTS `").append(table.getName()).append("` (");

        // Create a new record.
        Record record = table.createRecord();

        // Loop though primary keys.
        for (RecordField recordField : record.getFieldList(RecordFieldType.PRIMARY)) {
            String type = this.parseType(recordField.getValueType());

            if (type == null) continue;

            stringBuilder.append("`{key}` {type} PRIMARY KEY,".replace("{key}", recordField.getKey()).replace("{type}", type));
        }

        // Loop though default fields.
        for (RecordField recordField : record.getFieldList(RecordFieldType.FIELD)) {
            String type = this.parseType(recordField.getValueType());

            if (type == null) continue;

            stringBuilder.append("`{key}` {type},".replace("{key}", recordField.getKey()).replace("{type}", type));
        }

        // Loop though foreign keys.
        for (RecordField recordField : record.getFieldList(RecordFieldType.FOREIGN)) {
            String type = this.parseType(recordField.getValueType());

            if (type == null) continue;

            ForeignKeyAnnotation foreignKeyAnnotation = recordField.getForeignKeyReference();
            if (foreignKeyAnnotation == null) {
                throw new ForeignKeyReferenceException(table.getName(), recordField.getKey());
            }

            stringBuilder.append("`{key}` {type} REFERENCES {reference}({reference_field}),"
                    .replace("{key}", recordField.getKey())
                    .replace("{type}", type)
                    .replace("{reference}", foreignKeyAnnotation.table())
                    .replace("{reference_field}", foreignKeyAnnotation.field()));
        }

        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        stringBuilder.append(");");

        return stringBuilder.toString();
    }

    /**
     * Used to get a string value that represents the
     * record fields type.
     *
     * @param clazz The fields value type.
     * @return The instance of the string that
     * can be used in this database.
     */
    protected @Nullable String parseType(@NotNull Class<?> clazz) {
        if (clazz.isAssignableFrom(String.class)) return "TEXT";
        if (clazz.isAssignableFrom(Integer.class)) return "INTEGER";
        return null;
    }

    /**
     * Used to execute a statement.
     *
     * @param statement The statement to execute.
     * @return True if successful.
     */
    public boolean executeStatement(@NotNull String statement) {
        if (this.isDisabled()) return false;

        try {
            // Execute the statement.
            PreparedStatement preparedStatement = this.connection.prepareStatement(statement);
            return this.executeStatement(preparedStatement);

        } catch (SQLException exception) {
            Console.error(statement);
            exception.printStackTrace();
            this.setDisable();
            return false;
        }
    }

    public boolean executeStatement(@NotNull PreparedStatement statement) {
        if (this.isDisabled()) return false;

        // Check if the database is in debug mode.
        if (this.isDebugMode()) {
            Console.log(this.getPrefix() + "Attempting to execute statement : " + ConsoleColour.PINK + statement);
        }

        try {
            // Execute the statement.
            statement.execute();

        } catch (SQLException exception) {
            Console.error(statement.toString());
            exception.printStackTrace();
            this.setDisable();
            return false;
        }

        return true;
    }

    /**
     * Used to execute a query and return the results.
     *
     * @param statement The statement to execute.
     * @return The instance of the result set.
     */
    public @Nullable ResultSet executeQuery(@NotNull String statement) {
        if (this.isDisabled()) return null;

        try {
            // Execute the statement.
            PreparedStatement preparedStatement = this.connection.prepareStatement(statement);
            return this.executeQuery(preparedStatement);

        } catch (SQLException exception) {
            Console.error(statement);
            exception.printStackTrace();
            this.setDisable();
            return null;
        }
    }

    /**
     * Used to execute a query and return the results.
     *
     * @param statement The prepared statement to execute.
     * @return The instance of the result set.
     */
    public @Nullable ResultSet executeQuery(@NotNull PreparedStatement statement) {
        if (this.isDisabled()) return null;

        // Check if the database is in debug mode.
        if (this.isDebugMode()) {
            Console.log(this.getPrefix() + "Attempting to execute query : " + ConsoleColour.PINK + statement);
        }

        try {
            // Execute the statement.
            return statement.executeQuery();

        } catch (SQLException exception) {
            Console.error(statement.toString());
            exception.printStackTrace();
            this.setDisable();
            return null;
        }
    }
}
