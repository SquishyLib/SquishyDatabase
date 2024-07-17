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

    protected final @NotNull String address;
    protected final @NotNull String databaseName;
    protected final @NotNull String username;
    protected final @NotNull String password;

    /**
     * Used to create an abstract my sql database.
     */
    public AbstractMySQLDatabase(@NotNull String address, @NotNull String databaseName, @NotNull String username, @NotNull String password) {
        this.address = address;
        this.databaseName = databaseName;
        this.username = username;
        this.password = password;
    }

    /**
     * Used to attempt to create a connection with the database.
     */
    public void createConnection() {
        try {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

            // Connect to all databases.
            this.connection = DriverManager.getConnection("jdbc:mysql://{address}/?user={username}&password={password}?autoReconnect=true"
                    .replace("{address}", this.address)
                    .replace("{username}", this.username)
                    .replace("{password}", this.password)
            );

            // Ensure database is created.
            this.connection.createStatement().executeUpdate("CREATE DATABASE IF NOT EXISTS {name}".replace("{name}", this.databaseName));

            // Connect to specific database.
            this.connection = DriverManager.getConnection("jdbc:mysql://{address}/{database}"
                            .replace("{address}", this.address)
                            .replace("{database}", this.databaseName),
                    this.username,
                    this.password
            );

        } catch (SQLException exception) {
            exception.printStackTrace();
            this.setDisable();
            return;
        }

        Console.log(this.getPrefix() + ConsoleColour.YELLOW + "Connected to the database successfully.");
    }
}
