package com.github.smuddgge.squishydatabase.interfaces;

import com.github.smuddgge.squishydatabase.Query;
import com.github.smuddgge.squishydatabase.errors.RecordCreationException;
import com.github.smuddgge.squishydatabase.record.Record;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * Represents a table's selection.
 *
 * @param <R> The instance of the record the table will use.
 */
public abstract class TableSelection<R extends Record, D extends Database> {

    /**
     * The instance of the database.
     */
    private D database;

    /**
     * <h1>Used to get the name of the table</h1>
     * This value should be constant. If the user can
     * change this value you may be venerable to sql injection.
     */
    public abstract @NotNull String getName();

    /**
     * Used to get the first record in the list of
     * records matching a query.
     *
     * @param query The instance of a query.
     * @return The requested record.
     */
    public abstract @Nullable R getFirstRecord(@NotNull Query query);

    /**
     * Used to get the list of all the records
     * in the table.
     *
     * @return The list of all the records.
     */
    public abstract @NotNull List<R> getRecordList();

    /**
     * Used to get the list of records matching a query.
     *
     * @param query The instance of a query.
     * @return The requested list of records.
     */
    public abstract List<R> getRecordList(@NotNull Query query);

    /**
     * Used to get the amount of records in the database.
     *
     * @return The amount of records.
     */
    public abstract int getAmountOfRecords();

    /**
     * Used to get the amount of records given a query.
     *
     * @param query The instance of a query.
     * @return The requested amount of records.
     */
    public abstract int getAmountOfRecords(@NotNull Query query);

    /**
     * Used to insert a record into the database.
     *
     * @param record The instance of the record.
     * @return True if successful.
     */
    public abstract boolean insertRecord(@NotNull R record);

    /**
     * Used to remove a record.
     *
     * @param record The instance of a record to remove
     *               from the database table.
     * @return True if successful.
     */
    public abstract boolean removeRecord(@NotNull Record record);

    /**
     * Used to remove records in the database.
     *
     * @param query The instance of a query.
     * @return True if successful.
     */
    public abstract boolean removeAllRecords(@NotNull Query query);

    /**
     * Used to create a new instance of the record.
     *
     * @return A new instance of a record.
     */
    @SuppressWarnings("unchecked")
    public @NotNull R createRecord() {
        try {
            ParameterizedType parameterizedType = (ParameterizedType) this.getClass().getGenericSuperclass();
            Class<R> clazz = (Class<R>) parameterizedType.getActualTypeArguments()[0];
            return clazz.newInstance();

        } catch (InstantiationException | IllegalAccessException exception) {
            exception.printStackTrace();
            throw new RecordCreationException();
        }
    }

    /**
     * Used to link the table to a database.
     *
     * @param database The database to link to.
     * @return This instance.
     */
    public TableSelection<R, D> link(@NotNull D database) {
        this.database = database;
        return this;
    }

    /**
     * Used to check if the table is linked to a database.
     *
     * @return True if the table is linked.
     */
    public boolean isLinked() {
        return this.database != null;
    }

    /**
     * Used to get the instance of the database.
     *
     * @return The requested instance of the database.
     */
    protected @Nullable D getDatabase() {
        return this.database;
    }
}
