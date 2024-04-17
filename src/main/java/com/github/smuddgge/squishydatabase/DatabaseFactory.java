package com.github.smuddgge.squishydatabase;

import com.github.smuddgge.squishyconfiguration.interfaces.ConfigurationSection;
import com.github.smuddgge.squishydatabase.implementation.mongo.MongoDatabase;
import com.github.smuddgge.squishydatabase.implementation.mysql.MySQLDatabase;
import com.github.smuddgge.squishydatabase.implementation.sqlite.SQLiteDatabase;
import com.github.smuddgge.squishydatabase.interfaces.Database;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a database factory.
 * Used to create database instances.
 */
public enum DatabaseFactory {
    SQLITE {
        @Override
        public @NotNull Database create(@NotNull DatabaseCredentials databaseCredentials) {
            return SQLiteDatabase.extract(databaseCredentials).setup();
        }
    }, MYSQL {
        @Override
        public @NotNull Database create(@NotNull DatabaseCredentials databaseCredentials) {
            return new MySQLDatabase(databaseCredentials.getConnectionString()).setup();
        }
    }, MONGO {
        @Override
        public @NotNull Database create(@NotNull DatabaseCredentials databaseCredentials) {
            return new MongoDatabase(databaseCredentials.getConnectionString(), databaseCredentials.getDatabaseName()).setup();
        }
    };

    /**
     * Used to create a database.
     *
     * @return The instance of a new database.
     */
    public abstract @NotNull Database create(@NotNull DatabaseCredentials databaseCredentials);
}
