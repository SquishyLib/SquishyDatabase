package com.github.smuddgge.squishydatabase.implementation.mysql;

import com.github.smuddgge.squishydatabase.console.Console;
import com.github.smuddgge.squishydatabase.console.ConsoleColour;
import com.github.smuddgge.squishydatabase.implementation.sqlite.SQLiteDatabase;
import org.jetbrains.annotations.NotNull;

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
    protected String username;
    protected String password;

    /**
     * Used to create an abstract my sql database.
     *
     * @param url The instance of the url.
     */
    public AbstractMySQLDatabase(@NotNull String url) {
        this.url = url;
    }

    /**
     * Used to create an abstract my sql database.
     *
     * @param url      The instance of the url string.
     * @param username The username to log into.
     * @param password The password to log into.
     */
    public AbstractMySQLDatabase(@NotNull String url, @NotNull String username, @NotNull String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    /**
     * Used to attempt to create a connection with the database.
     */
    public void createConnection() {
        try {
            if (this.username == null) {
                // Create the connection.
                this.connection = DriverManager.getConnection(this.url);
            } else {
                this.connection = DriverManager.getConnection(this.url, this.username, this.password);
            }

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
