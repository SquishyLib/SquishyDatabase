package com.github.smuddgge.interfaces;

import com.github.smuddgge.record.Record;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * <h1>Represents a database</h1>
 * Contains methods used to interact with the
 * database.
 */
public interface Database {

    /**
     * Used to set up the database.
     *
     * @return The instance of the database.
     */
    @NotNull Database setup();

    /**
     * Used to create a table in the database.
     *
     * @param table The instance of the table.
     * @return True if the table was successfully added.
     */
    boolean createTable(@NotNull Table<?> table);

    /**
     * Used to get a table from the database.
     *
     * @param instance Class instance of a table.
     *                 <p>
     *                 For example, TableName.class
     *                 </p>
     * @return The instance of the requested table
     * if it exists.
     */
    <T, R extends Record> Table<R> getTable(@NotNull Class<T> instance);

    /**
     * Used to get a table section from the database.
     *
     * @param name The name of the table.
     * @return The instance of the requested table
     * if it exists.
     */
    @Nullable <R extends Record> TableSelection<R> getTable(@NotNull String name);

    /**
     * Used to get the list of tables in the database.
     *
     * @return The requested list of tables.
     */
    @NotNull List<TableSelection<?>> getTableList();

    /**
     * Used to get the list of table names in the database.
     *
     * @return The requested list of table names.
     */
    @NotNull List<String> getTableNameList();

    /**
     * Used to disable the database.
     */
    void setDisable();

    /**
     * Used to check if the database is disabled.
     *
     * @return True if the database is disabled.
     */
    boolean isDisabled();

    /**
     * Used to check if the database is empty.
     *
     * @return True if the database is empty.
     */
    boolean isEmpty();

    /**
     * Used to check if the database is enabled.
     *
     * @return True if the database is enabled.
     */
    default boolean isEnabled() {
        return !this.isDisabled();
    }
}
