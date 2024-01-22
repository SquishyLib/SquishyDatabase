package com.github.smuddgge.squishydatabase.implementation.mysql;

import com.github.smuddgge.squishydatabase.console.Console;
import com.github.smuddgge.squishydatabase.console.ConsoleColour;
import com.github.smuddgge.squishydatabase.implementation.sqlite.SQLiteDatabase;
import org.jetbrains.annotations.NotNull;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Represents an abstract sql database.
 * Contains methods used in the implementation.
 * This database uses the sqlite database implementation
 * for its functionality.
 */
public class AbstractMySQLDatabase extends SQLiteDatabase {

    protected final @NotNull String url;

    /**
     * Used to create an abstract sql database.
     *
     * @param url      The instance of the url.
     */
    public AbstractMySQLDatabase(@NotNull String url) {
        this.url = url;
    }

    /**
     * Used to attempt to create a connection with the database.
     */
    public void createConnection() {
        try {
            // Create the connection.
            this.connection = DriverManager.getConnection(this.url);

            // Check if the connection is null.
            if (this.connection == null) {
                this.setDisable();
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
            this.setDisable();
            return;
        }

        Console.log(this.getPrefix() + ConsoleColour.YELLOW + "Connected to the database successfully.");
    }
}
