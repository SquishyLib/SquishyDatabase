package com.github.smuddgge.implementation.sqlite;

import com.github.smuddgge.interfaces.Database;
import com.github.smuddgge.interfaces.Query;
import com.github.smuddgge.interfaces.TableSelection;
import com.github.smuddgge.record.Record;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Represents the sqlite implementation
 * of a {@link TableSelection}.
 *
 * @param <R> The type of record used.
 * @param <D> The type of database used.
 */
public class SqliteTableSelection<R extends Record, D extends Database> extends TableSelection<R, D> {

    /**
     * The tables name.
     */
    private final String name;

    /**
     * Used to initiate a sqlite table selection.
     *
     * @param name The name of the record.
     */
    public SqliteTableSelection(String name) {
        this.name = name;
    }

    @Override
    public @NotNull String getName() {
        return this.name;
    }

    @Override
    public R getFirstRecord(@NotNull Query query) {
        return null;
    }

    @Override
    public List<R> getRecordList(@NotNull Query query) {
        return null;
    }

    @Override
    public int getAmountOfRecords() {
        return 0;
    }

    @Override
    public int getAmountOfRecords(@NotNull Query query) {
        return 0;
    }

    @Override
    public boolean removeRecord(@NotNull Record record) {
        return false;
    }

    @Override
    public boolean removeAllRecords(@NotNull Query query) {
        return false;
    }

    @Override
    public boolean insertRecord(@NotNull Record record) {
        return false;
    }
}
