package com.github.smuddgge.interfaces;

import com.github.smuddgge.record.Record;
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
    private boolean isDisabled;

    /**
     * Used to set up the database.
     *
     * @return The instance of the database.
     */
    public abstract @NotNull Database setup();

    /**
     * Used to create a table in the database.
     * This also links the table to the database which
     * lets you get the table from the database.
     *
     * @param table The instance of the table.
     * @return True if the table was successfully added.
     */
    public abstract boolean createTable(@NotNull TableAdapter<?> table);

    /**
     * Used to get a table section from the database.
     *
     * @param name The name of the table.
     * @return The instance of the requested table selection.
     */
    public abstract @NotNull <R extends Record, D extends Database> TableSelection<R, D> getTableSelection(@NotNull String name);

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
    public @Nullable <T extends TableAdapter<R>, R extends Record> T getTable(Class<T> clazz) {
        for (TableAdapter<?> table : this.getTableList()) {
            if (clazz.isAssignableFrom(table.getClass())) return (T) table;
        }
        return null;
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
    public void setDisable() {
        this.isDisabled = false;
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
