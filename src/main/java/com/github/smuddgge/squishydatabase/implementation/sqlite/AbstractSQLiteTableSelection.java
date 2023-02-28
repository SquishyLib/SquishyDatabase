package com.github.smuddgge.squishydatabase.implementation.sqlite;

import com.github.smuddgge.squishydatabase.Query;
import com.github.smuddgge.squishydatabase.console.Console;
import com.github.smuddgge.squishydatabase.interfaces.TableSelection;
import com.github.smuddgge.squishydatabase.record.Record;
import com.github.smuddgge.squishydatabase.record.RecordField;
import com.github.smuddgge.squishydatabase.record.RecordFieldType;
import org.jetbrains.annotations.NotNull;
import org.sqlite.SQLiteErrorCode;
import org.sqlite.SQLiteException;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

/**
 * Represents methods not defined in the
 * {@link TableSelection} implementation.
 * However, these methods are specific to
 * the sqlite implementation.
 */
public abstract class AbstractSQLiteTableSelection<R extends Record>
        extends TableSelection<R, SQLiteDatabase> {

    /**
     * <h1>Used to build the where statement</h1>
     * This will use the query to insert the correct
     * amount of wildcards.
     * <p>Example output</p>
     * <li>? = ? AND ? = ?</li>
     *
     * @param query The instance of the query.
     * @return The instance of the statement.
     * The statement will not contain a semicolon.
     */
    protected @NotNull String buildWhereStatement(@NotNull Query query) {
        StringBuilder statement = new StringBuilder();

        for (Map.Entry<String, Object> map : query.get().entrySet()) {
            statement.append(map.getKey()).append(" = ? AND ");
        }

        // Delete the last ' AND'
        statement.replace(statement.length() - 5, statement.length(), "");

        return statement.toString();
    }

    /**
     * Used to append the query to a statement.
     *
     * @param statement The instance of the statement.
     * @param query     The query to append.
     * @return The instance of the prepared statement.
     * @throws SQLException Represents a sql error.
     */
    protected @NotNull PreparedStatement appendQuery(String statement, Query query) throws SQLException {
        assert this.getDatabase() != null;
        assert this.getDatabase().getConnection() != null;

        // Prepare the statement.
        try {
            PreparedStatement preparedStatement = this.getDatabase()
                    .getConnection().prepareStatement(statement);

            int index = 1;
            for (Map.Entry<String, Object> map : query.get().entrySet()) {
                preparedStatement.setObject(index, map.getValue());
                index++;
            }

            return preparedStatement;

        } catch (Exception exception) {
            Console.error("Statement : " + statement);
            exception.printStackTrace();
            throw new SQLiteException(statement, SQLiteErrorCode.SQLITE_ABORT);
        }
    }

    /**
     * Used to add a new record to the database.
     *
     * @param record The instance of the record.
     * @return True if the record was added successfully.
     * @throws SQLException If unsuccessful.
     */
    @SuppressWarnings("Duplicates")
    public boolean addRecord(@NotNull Record record) throws SQLException {
        assert this.getDatabase() != null;
        assert this.getDatabase().getConnection() != null;

        // Create the statement.
        StringBuilder statementBuilder = new StringBuilder();
        statementBuilder.append("INSERT INTO `").append(this.getName()).append("` (");

        StringBuilder valueBuilder = new StringBuilder();
        for (RecordField recordField : record.getFieldList()) {
            if (!recordField.hasValue()) continue;

            statementBuilder.append(recordField.getKey()).append(", ");
            valueBuilder.append("?, ");
        }

        // Delete the last ', '
        statementBuilder.replace(statementBuilder.length() - 2, statementBuilder.length(), "");
        valueBuilder.replace(valueBuilder.length() - 2, valueBuilder.length(), "");

        // Combine the builders
        statementBuilder.append(") VALUES (").append(valueBuilder).append(");");

        // Build prepared statement
        PreparedStatement preparedStatement = this.getDatabase().getConnection()
                .prepareStatement(statementBuilder.toString());

        int index = 1;
        for (RecordField recordField : record.getFieldList()) {
            if (!recordField.hasValue()) continue;
            preparedStatement.setObject(index, recordField.getValue());
            index++;
        }

        // Execute the statement.
        return this.getDatabase().executeStatement(preparedStatement);
    }

    /**
     * Used to update an existing record in the database.
     *
     * @param record The instance of the record.
     * @return True if the record was updated successfully.
     */
    @SuppressWarnings("Duplicates")
    public boolean updateRecord(@NotNull Record record) throws SQLException {
        assert this.getDatabase() != null;
        assert this.getDatabase().getConnection() != null;

        // Create the statement.
        StringBuilder statementBuilder = new StringBuilder();
        statementBuilder.append("UPDATE `").append(this.getName()).append("` SET ");

        for (RecordField recordField : record.getFieldList()) {
            if (!recordField.hasValue()) continue;
            statementBuilder.append(recordField.getKey()).append(" = ?, ");
        }

        // Delete the last ', '
        statementBuilder.replace(statementBuilder.length() - 2, statementBuilder.length(), "");

        // Combine the builders
        statementBuilder.append(" WHERE").append(" ").append(record.getPrimaryKey().getKey()).append(" = ?;");

        // Build prepared statement
        PreparedStatement preparedStatement = this.getDatabase().getConnection()
                .prepareStatement(statementBuilder.toString());

        int index = 1;
        for (RecordField recordField : record.getFieldList()) {
            if (!recordField.hasValue()) continue;
            preparedStatement.setObject(index, recordField.getValue());
            index++;
        }

        // Set the primary key.
        RecordField primaryKey1 = record.getFieldList(RecordFieldType.PRIMARY).get(0);
        preparedStatement.setObject(index, primaryKey1.getValue());

        // Execute the statement.
        return this.getDatabase().executeStatement(preparedStatement);
    }
}
