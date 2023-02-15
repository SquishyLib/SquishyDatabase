package com.github.smuddgge.implementation.sqlite;

import com.github.smuddgge.DatabaseCredentials;
import com.github.smuddgge.errors.DatabaseCredentialsException;
import com.github.smuddgge.interfaces.Database;
import com.github.smuddgge.interfaces.TableAdapter;
import com.github.smuddgge.interfaces.TableSelection;
import com.github.smuddgge.record.Record;
import org.jetbrains.annotations.NotNull;

import java.io.File;

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
    public boolean createTable(@NotNull TableAdapter<?> table) {
        // Link the table.
        this.linkTable(table);

        // Create the statement.
        String statement = this.getCreateTableStatement(table);

        // Execute the statement.
        return this.executeStatement(statement);
    }

    @Override
    @SuppressWarnings("unchecked")
    public @NotNull <R extends Record, D extends Database> TableSelection<R, D> getTableSelection(@NotNull String name) {
        return new SqliteTableSelection<R, D>(name).link((D) this);
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
        throw new DatabaseCredentialsException();
    }
}
