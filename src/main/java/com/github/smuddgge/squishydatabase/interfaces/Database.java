package com.github.smuddgge.squishydatabase.interfaces;

import com.github.smuddgge.squishydatabase.DatabaseFactory;
import com.github.smuddgge.squishydatabase.record.Record;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>Represents a database</h1>
 * Contains methods used to interact with the
 * database.
 */
public abstract class Database {

    /**
     * Represents the list of linked tables.
     */
    private final List<TableAdapter<?>> tableList = new ArrayList<>();

    /**
     * Represents the status of the database.
     */
    private boolean isDisabled = false;

    /**
     * Represents if the database is in debug mode.
     */
    private boolean isDebugMode = false;

    /**
     * Used to set up the database.
     *
     * @return The instance of the database.
     */
    public abstract @NotNull Database setup();

    /**
     * Used to create a table in the database
     * if it doesn't exists.
     * This also links the table to the database which
     * lets you get the table from the database.
     *
     * @param table The instance of the table.
     * @return True if the table was successfully added.
     */
    @SuppressWarnings("UnusedReturnValue")
    public abstract boolean createTable(@NotNull TableAdapter<?> table);

    /**
     * <h1>Used to get the prefix of the database</h1>
     * The prefix is shown in console outputs.
     *
     * @return The console prefix.
     */
    public abstract String getPrefix();

    /**
     * Used to get the database factory type.
     *
     * @return The database factory type.
     */
    public abstract DatabaseFactory getType();

    /**
     * Used to get a table section from the database.
     *
     * @param name         The name of the table.
     * @param tableAdapter Instance of a table adapter
     * @return The instance of the requested table selection.
     */
    public abstract @Nullable <R extends Record, D extends Database> TableSelection<R, D>
    getTableSelection(@NotNull String name, TableAdapter<R> tableAdapter);

    /**
     * Used to get the list of tables in the database.
     *
     * @return The requested list of tables.
     */
    public @NotNull List<TableAdapter<?>> getTableList() {
        return this.tableList;
    }

    /**
     * Used to get a table from the database.
     *
     * @param clazz Class instance of a table.
     *              <p>For example, TableName.class</p>
     * @return The instance of the requested table
     * if it exists.
     */
    @SuppressWarnings("unchecked")
    public @NotNull <T extends TableAdapter<R>, R extends Record> T getTable(@NotNull Class<T> clazz) {
        for (TableAdapter<?> table : this.getTableList()) {
            if (clazz.isAssignableFrom(table.getClass())) return (T) table;
        }

        throw new NullPointerException("Table {table} does not exist in the database."
                .replace("{table}", clazz.getName()));
    }

    /**
     * Used to get the list of table names in the database.
     *
     * @return The requested list of table names.
     */
    public @NotNull List<String> getTableNameList() {
        List<String> tableNameList = new ArrayList<>();

        for (TableAdapter<?> table : this.getTableList()) {
            tableNameList.add(table.getName());
        }

        return tableNameList;
    }

    /**
     * Used to disable the database.
     */
    public Database setDisable() {
        this.isDisabled = false;
        return this;
    }

    /**
     * Used to set if the database should
     * be in debug mode.
     *
     * @param debugMode True if the database should
     *                  be set to debug mode.
     * @return This instance of the database.
     */
    public Database setDebugMode(boolean debugMode) {
        this.isDebugMode = debugMode;
        return this;
    }

    /**
     * Used to check if the database is disabled.
     *
     * @return True if the database is disabled.
     */
    public boolean isDisabled() {
        return this.isDisabled;
    }

    /**
     * Used to check if the database is enabled.
     *
     * @return True if the database is enabled.
     */
    public boolean isEnabled() {
        return !this.isDisabled();
    }

    /**
     * Used to check if the database is in debug mode.
     *
     * @return True if in debug mode.
     */
    public boolean isDebugMode() {
        return this.isDebugMode;
    }

    /**
     * Used to link a table to this database.
     * It also links the table to this database.
     *
     * @param tableAdapter The instance of the table adapter
     */
    public void linkTable(@NotNull TableAdapter<?> tableAdapter) {
        tableAdapter.link(this);
        this.tableList.add(tableAdapter);
    }
}
