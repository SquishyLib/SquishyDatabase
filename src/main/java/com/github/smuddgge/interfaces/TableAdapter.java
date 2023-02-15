package com.github.smuddgge.interfaces;

import com.github.smuddgge.record.Record;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Represents a database table.
 *
 * @param <R> The instance of the record.
 */
public abstract class TableAdapter<R extends Record> extends TableSelection<R, Database> {

    @Override
    public R getFirstRecord(@NotNull Query query) {
        TableSelection<R, Database> tableSelection = this.getTableSelection();
        if (tableSelection == null) return null;

        return tableSelection.getFirstRecord(query);
    }

    @Override
    public List<R> getRecordList(@NotNull Query query) {
        TableSelection<R, Database> tableSelection = this.getTableSelection();
        if (tableSelection == null) return null;

        return tableSelection.getRecordList(query);
    }

    @Override
    public int getAmountOfRecords() {
        TableSelection<R, Database> tableSelection = this.getTableSelection();
        if (tableSelection == null) return -1;

        return tableSelection.getAmountOfRecords();
    }

    @Override
    public int getAmountOfRecords(@NotNull Query query) {
        TableSelection<R, Database> tableSelection = this.getTableSelection();
        if (tableSelection == null) return -1;

        return tableSelection.getAmountOfRecords(query);
    }

    @Override
    public boolean insertRecord(@NotNull R record) {
        TableSelection<R, Database> tableSelection = this.getTableSelection();
        if (tableSelection == null) return false;

        return tableSelection.insertRecord(record);
    }

    @Override
    public boolean removeRecord(@NotNull Record record) {
        TableSelection<R, Database> tableSelection = this.getTableSelection();
        if (tableSelection == null) return false;

        return tableSelection.removeRecord(record);
    }

    @Override
    public boolean removeAllRecords(@NotNull Query query) {
        TableSelection<R, Database> tableSelection = this.getTableSelection();
        if (tableSelection == null) return false;

        return tableSelection.removeAllRecords(query);
    }

    /**
     * Used to get the instance of the database selection
     * in the database.
     *
     * @return The database selection.
     */
    protected @Nullable TableSelection<R, Database> getTableSelection() {
        if (this.getDatabase() == null) return null;
        return this.getDatabase().getTableSelection(this.getName());
    }
}
