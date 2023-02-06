package com.github.smuddgge.implementation.sqlite;

import com.github.smuddgge.DatabaseCredentials;
import com.github.smuddgge.interfaces.Database;
import com.github.smuddgge.interfaces.Record;
import com.github.smuddgge.interfaces.Table;
import com.github.smuddgge.interfaces.TableSelection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SqliteDatabase extends AbstractSqliteDatabase {

    @Override
    public @NotNull Database setup(@NotNull DatabaseCredentials databaseCredentials) {
        return this;
    }

    @Override
    public boolean createTable(@NotNull Table<?> table) {
        return false;
    }

    @Override
    public <T, R extends Record> Table<R> getTable(@NotNull Class<T> instance) {
        return null;
    }

    @Override
    public @Nullable <R extends Record> TableSelection<R> getTable(@NotNull String name) {
        return null;
    }

    @Override
    public @NotNull List<TableSelection<?>> getTableList() {
        return null;
    }

    @Override
    public @NotNull List<String> getTableNameList() {
        return null;
    }

    @Override
    public boolean isDisabled() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
