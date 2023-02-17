package com.github.smuddgge.implementation.sqlite;

import com.github.smuddgge.DatabaseCredentials;
import com.github.smuddgge.errors.DatabaseCredentialsException;
import com.github.smuddgge.interfaces.Database;
import com.github.smuddgge.interfaces.TableAdapter;
import com.github.smuddgge.interfaces.TableSelection;
import com.github.smuddgge.record.Record;
import com.github.smuddgge.utility.console.ConsoleColour;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;

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
