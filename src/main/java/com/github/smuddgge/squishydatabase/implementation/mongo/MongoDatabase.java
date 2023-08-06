package com.github.smuddgge.squishydatabase.implementation.mongo;

import com.github.smuddgge.squishydatabase.DatabaseFactory;
import com.github.smuddgge.squishydatabase.console.Console;
import com.github.smuddgge.squishydatabase.console.ConsoleColour;
import com.github.smuddgge.squishydatabase.interfaces.Database;
import com.github.smuddgge.squishydatabase.interfaces.TableAdapter;
import com.github.smuddgge.squishydatabase.interfaces.TableSelection;
import com.github.smuddgge.squishydatabase.record.Record;
import com.mongodb.client.MongoClients;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

/**
 * <h1>Represents a mongo database</h1>
 */
public class MongoDatabase extends AbstractMongoDatabase {

    private final @NotNull String connectionString;
    private final @NotNull String databaseName;

    /**
     * Used to create a mongo database.
     *
     * @param connectionString The connection string.
     * @param databaseName     The databases name.
     */
    public MongoDatabase(@NotNull String connectionString, @NotNull String databaseName) {
        this.connectionString = connectionString;
        this.databaseName = databaseName;
    }

    @Override
    public @NotNull Database setup() {
        // Create client.
        this.client = MongoClients.create(this.connectionString);

        // Create database.
        this.database = client.getDatabase(this.databaseName);

        // Log.
        Console.log(this.getPrefix() + ConsoleColour.YELLOW + "Connected to the database successfully.");
        return this;
    }

    @Override
    public boolean createTable(@NotNull TableAdapter<?> table) {
        if (this.isDisabled()) return false;

        try {
            // Create a collection if it doesn't exist.
            if (!this.database.listCollectionNames().into(new ArrayList<>())
                    .contains(table.getName())) {

                // Create table.
                this.database.createCollection(table.getName());
            }

            // Link the table.
            this.linkTable(table);
            return true;

        } catch (Exception exception) {
            exception.printStackTrace();
            this.setDisable();
            return false;
        }
    }

    @Override
    public String getPrefix() {
        return ConsoleColour.GRAY + "[MongoDatabase] " + ConsoleColour.GREEN;
    }

    @Override
    public DatabaseFactory getType() {
        return DatabaseFactory.MONGO;
    }

    @Override
    @SuppressWarnings("unchecked")
    public @Nullable <R extends Record, D extends Database> TableSelection<R, D>
    getTableSelection(@NotNull String name, TableAdapter<R> tableAdapter) {

        if (this.isDisabled()) return null;

        // Return MongoTable and use the table adapter to create records.
        return (TableSelection<R, D>) new MongoTableSelection<R>(name, this) {
            @Override
            public @NotNull R createRecord() {
                return tableAdapter.createRecord();
            }
        };
    }
}
