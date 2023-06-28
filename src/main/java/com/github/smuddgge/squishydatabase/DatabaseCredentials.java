package com.github.smuddgge.squishydatabase;

import org.jetbrains.annotations.Nullable;

import java.io.File;

/**
 * <h1>Represents a databases credentials</h1>
 * Contains values to connected to a database.
 */
public class DatabaseCredentials {

    private String path;
    private File file;

    private String username;
    private String password;
    private String host;
    private String port;
    private String databaseName;
    private String databaseUser;

    /**
     * Used to create database credentials.
     *
     * @param file The instance of the file.
     */
    public DatabaseCredentials(File file) {
        this.file = file;
    }

    /**
     * Used to create database credentials.
     *
     * @param path the instance of the path with the extension.
     */
    public DatabaseCredentials(String path) {
        this.path = path;
    }

    /**
     * Used to create database credentials that
     * will connect to a server.
     *
     * @param username The username.
     * @param password The password.
     * @param host The host address.
     * @param port The port number.
     * @param databaseName The databases name.
     * @param databaseUser The database name for the user documents.
     *                     This is normally called 'admin'.
     */
    public DatabaseCredentials(
            String username,
            String password,
            String host,
            String port,
            String databaseName,
            String databaseUser
    ) {
        this.username = username;
        this.password = password;
        this.host = host;
        this.port = port;
        this.databaseName = databaseName;
        this.databaseUser = databaseUser;
    }

    /**
     * Used to get the path.
     *
     * @return The requested path.
     */
    public String getPath() {
        return this.path;
    }

    /**
     * Used to get the file.
     *
     * @return The requested file.
     */
    public File getFile() {
        return this.file;
    }

    /**
     * Used to get the username to log into the database.
     *
     * @return The username.
     */
    public @Nullable String getUsername() {
        return this.username;
    }

    /**
     * Used to get the password to log into the database.
     *
     * @return The password.
     */
    public @Nullable String getPassword() {
        return this.password;
    }

    /**
     * Used to get the host address.
     *
     * @return The host address.
     */
    public @Nullable String getHost() {
        return this.host;
    }

    /**
     * Used to get the port.
     *
     * @return The port.
     */
    public String getPort() {
        return this.port;
    }

    /**
     * Used to get the databases name.
     *
     * @return The name of the database.
     */
    public String getDatabaseName() {
        return this.databaseName;
    }

    /**
     * Used to get the database where the
     * user documents are located.
     *
     * @return The user database name.
     */
    public String getDatabaseUser() {
        return this.databaseUser;
    }
}
