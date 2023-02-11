package com.github.smuddgge.implementation.sqlite;

import com.github.smuddgge.interfaces.Query;
import com.github.smuddgge.record.Record;
import com.github.smuddgge.interfaces.TableSelection;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SqliteTableSelection<R> implements TableSelection<R> {

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
