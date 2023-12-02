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
     * Used to create an abstract sql database.
     *
     * @param url      The instance of the url.
     * @param user     The user that will be used to log into
     *                 the database.
     * @param password The user's password.
     */
    public MySQLDatabase(@NotNull String url, @NotNull String user, @NotNull String password) {
        super(url, user, password);
    }

    @Override
    public @NotNull Database setup() {

        // Attempt to create a connection to the database.
        this.createConnection();
        return this;
    }

    @Override
    public String getPrefix() {
        return ConsoleColour.GRAY + "[MySql] " + ConsoleColour.GREEN;
    }

    @Override
    public DatabaseFactory getType() {
        return DatabaseFactory.MYSQL;
    }
}
