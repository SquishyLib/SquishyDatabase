package com.github.smuddgge.squishydatabase.implementation.mongo;

import com.github.smuddgge.squishydatabase.interfaces.Database;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;

/**
 * Represents the abstract mongo database.
 * Contains methods not override.
 */
public abstract class AbstractMongoDatabase extends Database {

    protected MongoClient client;
    protected com.mongodb.client.MongoDatabase database;

    /**
     * Used to get the instance of the client.
     *
     * @return The client.
     */
    public MongoClient getClient() {
        return client;
    }

    /**
     * Used to get the instance of the database.
     *
     * @return The mongo database.
     */
    public MongoDatabase getDatabase() {
        return database;
    }
}
