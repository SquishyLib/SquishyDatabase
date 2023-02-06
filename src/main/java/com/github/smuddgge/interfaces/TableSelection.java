package com.github.smuddgge.interfaces;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Represents a table's selection.
 *
 * @param <R> The instance of the record the table will use.
 */
public interface TableSelection<R> {

    /**
     * Used to get the first record in the list of
     * records matching a query.
     *
     * @param query The instance of a query.
     * @return The requested record.
     */
    R getFirstRecord(@NotNull Query query);

    /**
     * Used to get the list of records matching a query.
     *
     * @param query The instance of a query.
     * @return The requested list of records.
     */
    List<R> getRecordList(@NotNull Query query);

    /**
     * Used to get the amount of records in the database.
     *
     * @return The amount of records.
     */
    int getAmountOfRecords();

    /**
     * Used to get the amount of records given a query.
     *
     * @param query The instance of a query.
     * @return The requested amount of records.
     */
    int getAmountOfRecords(@NotNull Query query);

    /**
     * Used to insert a record into the database.
     *
     * @param record The instance of the record.
     * @return True if successful.
     */
    boolean insertRecord(@NotNull R record);

    /**
     * Used to remove a record.
     *
     * @param record The instance of a record to remove
     *               from the database table.
     * @return True if successful.
     */
    boolean removeRecord(@NotNull Record record);

    /**
     * Used to remove records in the database.
     *
     * @param query The instance of a query.
     * @return True if successful.
     */
    boolean removeAllRecords(@NotNull Query query);
}
