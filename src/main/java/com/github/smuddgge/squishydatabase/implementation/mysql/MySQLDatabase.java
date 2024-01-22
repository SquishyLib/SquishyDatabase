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
     */
    public MySQLDatabase(@NotNull String url) {
        super(url);
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
