package com.github.smuddgge.squishydatabase.implementation.mysql;

import com.github.smuddgge.squishydatabase.DatabaseFactory;
import com.github.smuddgge.squishydatabase.console.ConsoleColour;
import com.github.smuddgge.squishydatabase.interfaces.Database;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a MySql database.
 * This database uses the sqlite implementation
 * for its functionality.
 */
public class MySQLDatabase extends AbstractMySQLDatabase {

    /**
     * Used to create a mysql database.
     *
     * @param url The connection string.
     * @param username The username to log in with.
     * @param password The password to log in with.
     */
    public MySQLDatabase(@NotNull String url, @NotNull String databaseName, @NotNull String username, @NotNull String password) {
        super(url, databaseName, username, password);
    }

    @Override
    public @NotNull Database setup() {

        // Attempt to create a connection to the database.
        this.createConnection();
        return this;
    }

    @Override
    public String getPrefix() {
        return ConsoleColour.GRAY + "[MySQL] " + ConsoleColour.GREEN;
    }

    @Override
    public DatabaseFactory getType() {
        return DatabaseFactory.MYSQL;
    }
}
