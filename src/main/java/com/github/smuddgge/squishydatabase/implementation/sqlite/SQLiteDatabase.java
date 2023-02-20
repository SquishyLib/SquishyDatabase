package com.github.smuddgge.squishydatabase.implementation.sqlite;

import com.github.smuddgge.squishydatabase.DatabaseCredentials;
import com.github.smuddgge.squishydatabase.DatabaseFactory;
import com.github.smuddgge.squishydatabase.console.ConsoleColour;
import com.github.smuddgge.squishydatabase.errors.DatabaseCredentialsException;
import com.github.smuddgge.squishydatabase.interfaces.Database;
import com.github.smuddgge.squishydatabase.interfaces.TableAdapter;
import com.github.smuddgge.squishydatabase.interfaces.TableSelection;
import com.github.smuddgge.squishydatabase.record.Record;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.List;
import java.util.Objects;

public class SQLiteDatabase extends AbstractSQLiteDatabase {

    /**
     * Used to create a sqlite database.
     *
     * @param file The instance of the file.
     */
    public SQLiteDatabase(File file) {
        this.file = file;
    }

    @Override
    public @NotNull Database setup() {
        // Initiate Drivers
        this.initiateDrivers();

        // Create the directory.
        this.createDirectory();

        // Create the url.
        String url = "jdbc:sqlite:" + this.file.getAbsolutePath();

        // Create database connection.
        this.createConnection(url);
        return this;
    }

    @Override
    public boolean createTable(@NotNull TableAdapter<?> table) {
        // Check if the table exists
        if (this.tableExists(table.getName())) {
            List<String> proposedFieldNames = table.createRecord().getFieldNameList();
            List<String> currentFieldNames = this.getColumnNames(table.getName());

            // Get the difference.
            proposedFieldNames.removeAll(currentFieldNames);

            if (proposedFieldNames.size() > 0) {
                for (String recordKey : proposedFieldNames) {
                    this.addColumn(table.getName(), Objects.requireNonNull(table.createRecord().getField(recordKey)));
                }
            }
        }

        // Link the table.
        this.linkTable(table);

        // Create the statement.
        String statement = this.getCreateTableStatement(table);

        // Execute the statement.
        return this.executeStatement(statement);
    }

    @Override
    public String getPrefix() {
        return ConsoleColour.GRAY + "[SQLiteDatabase] " + ConsoleColour.GREEN;
    }

    @Override
    public DatabaseFactory getType() {
        return DatabaseFactory.SQLITE;
    }

    @Override
    @SuppressWarnings("unchecked")
    public @Nullable <R extends Record, D extends Database> TableSelection<R, D>
    getTableSelection(@NotNull String name, TableAdapter<R> tableAdapter) {

        if (this.isDisabled()) return null;

        // Return SQLiteTable and use the table adapter to create records.
        return (TableSelection<R, D>) new SQLiteTableSelection<R>(name, this) {
            @Override
            public @NotNull R createRecord() {
                return tableAdapter.createRecord();
            }
        };
    }

    /**
     * Used to extract the credentials and create
     * the correct database instance.
     *
     * @param databaseCredentials The instance of the credentials.
     * @return The requested sqlite database.
     */
    public static @NotNull SQLiteDatabase extract(@NotNull DatabaseCredentials databaseCredentials) {

        // If a file is stated.
        if (databaseCredentials.getFile() != null) {
            return new SQLiteDatabase(databaseCredentials.getFile());
        }

        // Is a path stated.
        if (databaseCredentials.getPath() != null) {
            return new SQLiteDatabase(new File(databaseCredentials.getPath()));
        }

        // If the incorrect credentials were given
        // throw an error.
        throw new DatabaseCredentialsException();
    }
}
