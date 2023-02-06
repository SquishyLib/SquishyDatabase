package com.github.smuddgge.implementation.sqlite;

import com.github.smuddgge.interfaces.AbstractDatabase;
import com.github.smuddgge.interfaces.Database;

import java.io.File;
import java.sql.Connection;

/**
 * Represents methods not defined in the
 * {@link Database} implementation.
 */
public abstract class AbstractSqliteDatabase extends AbstractDatabase {

    /**
     * The instance of the file.
     */
    protected File file;

    /**
     * The connection to the sqlite database.
     */
    protected Connection connection;

    /**
     * Used to initiate the database driver class.
     */
    protected void initiateDrivers() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException exception) {
            exception.printStackTrace();
            this.setDisable();
        }
    }
}
