package com.github.smuddgge.squishydatabase;

import com.github.smuddgge.squishydatabase.implementation.sqlite.SQLiteDatabase;
import com.github.smuddgge.squishydatabase.interfaces.Database;

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
     * @return The instance of a new database.
     */
    public static Database SQLITE(String path) {
        return DatabaseFactory.SQLITE.create(
                new DatabaseCredentials().setPath(path)
        );
    }
}
