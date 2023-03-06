package com.github.smuddgge.squishydatabase.implementation.mongo;

import com.github.smuddgge.squishydatabase.DatabaseFactory;
import com.github.smuddgge.squishydatabase.interfaces.Database;
import com.github.smuddgge.squishydatabase.interfaces.TableAdapter;
import com.github.smuddgge.squishydatabase.interfaces.TableSelection;
import com.github.smuddgge.squishydatabase.record.Record;
import com.mongodb.MongoClient;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * <h1>Represents a mongo database</h1>
 */
public class MongoDatabase extends Database {

    private MongoClient mongoClient;

    @Override
    public @NotNull Database setup() {
        return null;
    }

    @Override
    public boolean createTable(@NotNull TableAdapter<?> table) {
        return false;
    }

    @Override
    public String getPrefix() {
        return null;
    }

    @Override
    public DatabaseFactory getType() {
        return null;
    }

    @Override
    public @Nullable <R extends Record, D extends Database> TableSelection<R, D> getTableSelection(@NotNull String name, TableAdapter<R> tableAdapter) {
        return null;
    }
}
