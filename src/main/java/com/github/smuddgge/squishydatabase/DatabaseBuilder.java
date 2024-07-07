package com.github.smuddgge.squishydatabase;

import com.github.smuddgge.squishyconfiguration.interfaces.ConfigurationSection;
import com.github.smuddgge.squishydatabase.implementation.mongo.MongoDatabase;
import com.github.smuddgge.squishydatabase.implementation.mysql.MySQLDatabase;
import com.github.smuddgge.squishydatabase.implementation.sqlite.SQLiteDatabase;
import com.github.smuddgge.squishydatabase.interfaces.Database;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;

/**
 * Represents the database builder.
 * <p>
 * A better way of connecting to a database.
 * <p>
 * This is used to build a database connection.
 * <p>
 * After obtaining an instance of the database,
 * the method {@link Database#setup()} can be called
 * to attempt to connect.
 */
public class DatabaseBuilder {

    private ConfigurationSection section;

    private String type;
    private String path;

    private ConfigurationSection sqliteConfiguration;
    private ConfigurationSection mysqlConfiguration;
    private ConfigurationSection mongoConfiguration;

    public DatabaseBuilder() {
    }

    /**
     * Used to create a database builder.
     *
     * @param section The configuration section that contains
     *                the database connection infomation.
     * @param path    The path that should be used for the sqlite
     *                database connection.
     */
    public DatabaseBuilder(@NotNull ConfigurationSection section, @NotNull String path) {
        this.section = section;
        this.type = section.getString("type");
        this.path = path;

        this.sqliteConfiguration = section.getSection("sqlite");
        this.mysqlConfiguration = section.getSection("mysql");
        this.mongoConfiguration = section.getSection("mongodb");
    }

    /**
     * Used to get the type of database as an
     * uppercase string.
     * This will only return the type if the type identifier
     * is used in the configuration.
     *
     * @return The type of database.
     */
    public @Nullable String getType() {
        return type;
    }

    public @NotNull ConfigurationSection getConfigurationSection() {
        if (this.section.getKeys().contains("type")) return this.section;
        if (this.sqliteConfiguration.getBoolean("enable")) return this.sqliteConfiguration;
        if (this.mysqlConfiguration.getBoolean("enable")) return this.mysqlConfiguration;
        if (this.mongoConfiguration.getBoolean("enable")) return this.mongoConfiguration;

        throw new RuntimeException(
                "Incorrect database configuration. " +
                        "Ether no database sections are enabled or the type does not exist: " + this.getType() + "."
        );
    }

    /**
     * Used to get the path that's used to connect to
     * the sqlite database.
     *
     * @return The path to the sqlite database.
     */
    public @Nullable String getPath() {
        return this.path;
    }

    /**
     * Used to get the connection string.
     * <p>
     * This is the url used to connect to a database
     * such as Mongo or MySql.
     *
     * @return The connection string.
     */
    public @Nullable String getConnectionString() {
        return this.getConfigurationSection().getString("connection_string");
    }

    /**
     * Used to get the database's name.
     * <p>
     * This is used when connecting to a mongo database.
     *
     * @return The database's name.
     */
    public @Nullable String getDatabaseName() {
        return this.getConfigurationSection().getString("database_name");
    }

    /**
     * Used to get the username used to log into the database.
     * <p>
     * This can be used when connecting to a mysql database.
     * However, if this is set the password should also be set.
     *
     * @return The database username.
     */
    public @Nullable String getUsername() {
        return this.getConfigurationSection().getString("username");
    }

    /**
     * Used to get the password used to log into the database.
     * <p>
     * This can be used when connecting to a mysql database.
     * However, if the username is not set it will try to just
     * use the connection string.
     *
     * @return The database password.
     */
    public @Nullable String getPassword() {
        return this.getConfigurationSection().getString("password");
    }

    /**
     * Used to get the instance of the database factory
     * that corresponds to the database type specified
     * in the configuration.
     *
     * @return The instance of the database factory.
     */
    public @NotNull DatabaseFactory getFactory() {
        if (this.getType() == null) {
            if (this.sqliteConfiguration.getBoolean("enable")) return DatabaseFactory.SQLITE;
            if (this.mysqlConfiguration.getBoolean("enable")) return DatabaseFactory.MYSQL;
            if (this.mongoConfiguration.getBoolean("enable")) return DatabaseFactory.MONGO;
        } else {
            return DatabaseFactory.valueOf(this.getType().toUpperCase());
        }

        throw new RuntimeException(
                "Incorrect database configuration. " +
                        "Ether no database sections are enabled or the type does not exist: " + this.getType() + "."
        );
    }

    /**
     * Used to set the type of database to build.
     * <p>
     * You can provide a lowercase string, as this method
     * will also make it uppercase.
     *
     * @param type The type of database to build.
     * @return This instance.
     */
    public @NotNull DatabaseBuilder setType(@NotNull String type) {
        this.type = type.toUpperCase();
        return this;
    }

    /**
     * Used to set the path of the database.
     * <p>
     * This is used when creating a sqlite database.
     *
     * @param path The path to the database file.
     * @return This instance.
     */
    public @NotNull DatabaseBuilder setPath(@NotNull String path) {
        this.path = path;
        return this;
    }

    /**
     * Used to set the connection string.
     * <p>
     * This is used in most database connections to log into the database.
     *
     * @param connectionString The instance of the connection string.
     * @return This instance.
     */
    public @NotNull DatabaseBuilder setConnectionString(@NotNull String connectionString) {
        this.section.set("connection_string", connectionString);
        return this;
    }

    /**
     * Used to set the database name.
     * <p>
     * This will be used in a mongo database connection.
     *
     * @param databaseName The name of the database.
     * @return This instance.
     */
    public @NotNull DatabaseBuilder setDatabaseName(@NotNull String databaseName) {
        this.section.set("database_name", databaseName);
        return this;
    }

    /**
     * Used to set the username to log in with.
     * <p>
     * This can be used in a mysql connection.
     * However, if the password is not set it will fail.
     *
     * @param username The username used to log into the database.
     * @return This instance.
     */
    public @NotNull DatabaseBuilder setUsername(@NotNull String username) {
        this.section.set("username", username);
        return this;
    }

    /**
     * Used to set the password to log in with.
     * <p>
     * This can be used in a mysql connection
     * However, if the username is not set it will
     * just try to use the connection string.
     *
     * @param password The password used to log into the database.
     * @return This instance.
     */
    public @NotNull DatabaseBuilder setPassword(@NotNull String password) {
        this.section.set("password", password);
        return this;
    }

    /**
     * Used to build a sqlite database.
     *
     * @param path The path to the sqlite file.
     * @return This instance.
     */
    public @NotNull DatabaseBuilder setSqlite(@NotNull String path) {
        this.type = "SQLITE";
        this.path = path;
        return this;
    }

    /**
     * Used to build a mysql database.
     *
     * @param connectionString The connection string.
     * @return This instance.
     */
    public @NotNull DatabaseBuilder setMySql(@NotNull String connectionString) {
        this.type = "MYSQL";
        this.section.set("connection_string", connectionString);
        return this;
    }

    /**
     * Used to build a mysql database with username and password separate.
     *
     * @param connectionString The connection string to use.
     * @param username         The username to use.
     * @param password         The password to use.
     * @return This instance.
     */
    public @NotNull DatabaseBuilder setMySql(@NotNull String connectionString, @NotNull String username, @NotNull String password) {
        this.type = "MYSQL";
        this.section.set("connection_string", connectionString);
        this.section.set("username", username);
        this.section.set("password", password);
        return this;
    }

    /**
     * Used to build a mongo database.
     *
     * @param connectionString The connection string to use.
     * @param databaseName     The database name that will be logged into.
     * @return This instance.
     */
    public @NotNull DatabaseBuilder setMongo(@NotNull String connectionString, @NotNull String databaseName) {
        this.type = "MONGO";
        this.section.set("connection_string", connectionString);
        this.section.set("database_name", databaseName);
        return this;
    }

    /**
     * Used to build a connection to a database and connect.
     *
     * @return The instance of the database.
     */
    public @NotNull Database build() {

        // Check if the database type is MYSQL.
        if (this.getFactory().equals(DatabaseFactory.MYSQL)) {
            if (this.getConnectionString() == null) throw new NullPointerException(
                    "You must specify a connection string for a MySQL database."
            );
            if (this.getDatabaseName() == null) throw new NullPointerException(
                    "You must specify a database name for a MySQL database."
            );
            if (this.getUsername() == null) throw new NullPointerException(
                    "You must specify a username for a MySQL database."
            );
            if (this.getPassword() == null) throw new NullPointerException(
                    "You must specify a password for a MySQL database."
            );
            return new MySQLDatabase(this.getConnectionString(), this.getDatabaseName(), this.getUsername(), this.getPassword()).setup();
        }

        // Check if the database type is MONGO.
        if (this.getFactory().equals(DatabaseFactory.MONGO)) {
            if (this.getConnectionString() == null) throw new NullPointerException(
                    "You must specify a connection string for a Mongo database."
            );
            if (this.getDatabaseName() == null) throw new NullPointerException(
                    "You must specify a database name for a Mongo database."
            );
            return new MongoDatabase(this.getConnectionString(), this.getDatabaseName()).setup();
        }

        // Check if the database type is SQLITE.
        if (this.getFactory().equals(DatabaseFactory.SQLITE)) {
            if (this.getPath() == null) throw new NullPointerException(
                    "You must specify a path for a SQLite database."
            );
            return new SQLiteDatabase(new File(this.getPath())).setup();
        }

        // Otherwise, the database type is invalid.
        throw new NullPointerException("Unknown database type: " + this.getType());
    }
}