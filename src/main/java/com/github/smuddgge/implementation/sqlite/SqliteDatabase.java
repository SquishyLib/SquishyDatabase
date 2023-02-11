package com.github.smuddgge.implementation.sqlite;

import com.github.smuddgge.DatabaseCredentials;
import com.github.smuddgge.errors.IncorrectDatabaseCredentials;
import com.github.smuddgge.interfaces.Database;
import com.github.smuddgge.record.Record;
import com.github.smuddgge.interfaces.Table;
import com.github.smuddgge.interfaces.TableSelection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.List;

public class SqliteDatabase extends AbstractSqliteDatabase {

    /**
     * Used to create a sqlite database.
     *
     * @param file The instance of the file.
     */
    public SqliteDatabase(File file) {
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
    public boolean createTable(@NotNull Table<?> table) {
        // Link the table.
        table.link(this);

        // Create the statement.
        String statement = this.getCreateTableStatement(table);

        return false;
    }

    @Override
    public <T, R extends Record> Table<R> getTable(@NotNull Class<T> instance) {
        return null;
    }

    @Override
    public @Nullable <R extends Record> TableSelection<R> getTable(@NotNull String name) {
        return null;
    }

    @Override
    public @NotNull List<TableSelection<?>> getTableList() {
        return null;
    }

    @Override
    public @NotNull List<String> getTableNameList() {
        return null;
    }

    @Override
    public boolean isDisabled() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    /**
     * Used to extract the credentials and create
     * the correct database instance.
     *
     * @param databaseCredentials The instance of the credentials.
     * @return The requested sqlite database.
     */
    public static @NotNull SqliteDatabase extract(@NotNull DatabaseCredentials databaseCredentials) {

        // If a file is stated
        if (databaseCredentials.getFile() != null) {
            return new SqliteDatabase(databaseCredentials.getFile());
        }

        if (databaseCredentials.getPath() != null) {
            return new SqliteDatabase(new File(databaseCredentials.getPath()));
        }

        // If the incorrect credentials were given
        // throw an error.
        throw new IncorrectDatabaseCredentials();
    }
}
