package com.github.smuddgge.implementation.sqlite;

import com.github.smuddgge.interfaces.TableSelection;
import com.github.smuddgge.record.Record;
import com.github.smuddgge.utility.Query;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Represents the sqlite implementation
 * of a {@link TableSelection}.
 *
 * @param <R> The type of record used.
 */
public class SQLiteTableSelection<R extends Record>
        extends AbstractSQLiteTableSelection<R> {

    /**
     * The tables name.
     */
    private final String name;

    /**
     * Used to initiate a sqlite table selection.
     *
     * @param name The name of the record.
     */
    public SQLiteTableSelection(@NotNull String name, @NotNull SQLiteDatabase database) {
        this.name = name;
        this.link(database);
    }

    @Override
    public @NotNull String getName() {
        return this.name;
    }

    @Override
    @SuppressWarnings("unchecked")
    public @Nullable R getFirstRecord(@NotNull Query query) {
        assert this.getDatabase() != null;

        try {
            // Prepare the statement
            PreparedStatement preparedStatement = this.getStatement(query);

            // Get the results.
            ResultSet results = preparedStatement.executeQuery();
            if (results == null) return null;

            // Create a new record.
            R record = this.createRecord();

            // If the result exists append the results.
            if (results.first()) return (R) record.append(results);

        } catch (SQLException exception) {
            exception.printStackTrace();
            this.getDatabase().setDisable();
        }

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
