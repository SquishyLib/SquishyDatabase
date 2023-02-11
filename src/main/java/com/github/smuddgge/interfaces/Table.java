package com.github.smuddgge.interfaces;

import com.github.smuddgge.record.Record;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.function.Supplier;

/**
 * Represents a database table.
 *
 * @param <R> The instance of the record.
 */
public abstract class Table<R extends Record> {

    /**
     * <h1>The linked database instance.</h1>
     * This database instance will be used to
     * get the table selection instance.
     */
    private Database database;

    /**
     * Used to get the name of the table.
     */
    public abstract @NotNull String getName();

    @SuppressWarnings("unchecked")
    public R createRecord() {
        try {
            ParameterizedType parameterizedType = (ParameterizedType) this.getClass().getGenericSuperclass();
            Class<R> clazz = (Class<R>) parameterizedType.getActualTypeArguments()[0];
            return clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    /**
     * Used to get the table selection.
     *
     * @return The table selection.
     */
    public @Nullable TableSelection<R> getSelection() {
        return this.database.getTable(this.getName());
    }

    /**
     * Used to link the table to a database.
     *
     * @param database The database to link to.
     */
    public void link(Database database) {
        this.database = database;
    }

    /**
     * Used to check if the table is linked to a database.
     *
     * @return True if the table is linked.
     */
    public boolean isLinked() {
        return this.database != null;
    }
}
