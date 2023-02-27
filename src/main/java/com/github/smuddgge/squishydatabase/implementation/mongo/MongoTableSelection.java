package com.github.smuddgge.squishydatabase.implementation.mongo;

import com.github.smuddgge.squishydatabase.Query;
import com.github.smuddgge.squishydatabase.implementation.sqlite.SQLiteDatabase;
import com.github.smuddgge.squishydatabase.implementation.sqlite.SQLiteTableSelection;
import com.github.smuddgge.squishydatabase.interfaces.TableSelection;
import com.github.smuddgge.squishydatabase.record.Record;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MongoTableSelection<R extends Record> extends TableSelection<R, MongoDatabase> {

    /**
     * The tables name.
     */
    private final @NotNull String name;

    /**
     * Used to initiate a sqlite table selection.
     *
     * @param name The name of the record.
     */
    public MongoTableSelection(@NotNull String name, @NotNull MongoDatabase database) {
        this.name = name;
        this.link(database);
    }

    @Override
    public @NotNull String getName() {
        return this.name;
    }

    @Override
    public @Nullable R getFirstRecord(@NotNull Query query) {
        return null;
    }

    @Override
    public @NotNull List<R> getRecordList() {
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
    public boolean insertRecord(@NotNull R record) {
        return false;
    }

    @Override
    public boolean removeRecord(@NotNull Record record) {
        return false;
    }

    @Override
    public boolean removeAllRecords(@NotNull Query query) {
        return false;
    }
}
