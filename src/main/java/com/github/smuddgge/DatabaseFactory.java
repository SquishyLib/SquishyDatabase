package com.github.smuddgge;

import com.github.smuddgge.implementation.sqlite.SQLiteDatabase;
import com.github.smuddgge.interfaces.Database;

public enum DatabaseFactory {
    SQLITE {
        @Override
        public Database create(DatabaseCredentials databaseCredentials) {
            return SQLiteDatabase.extract(databaseCredentials).setup();
        }
    };

    /**
     * Represents the types given credentials.
     */
    private DatabaseCredentials databaseCredentials;

    /**
     * Used to create a database.
     *
     * @return The instance of a new database.
     */
    public abstract Database create(DatabaseCredentials databaseCredentials);

    /**
     * Used to create a sqlite database.
     *
     * @param path The absolute path to the database.
     * @return The instance of the database type.
     */
    public static DatabaseFactory SQLITE(String path) {
        DatabaseFactory databaseType = DatabaseFactory.SQLITE;
        databaseType.databaseCredentials = new DatabaseCredentials().setPath(path);
        return databaseType;
    }
}
