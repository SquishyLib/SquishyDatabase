package com.github.smuddgge.squishydatabase.implementation.sqlite;

import com.github.smuddgge.squishydatabase.interfaces.TableSelection;
import com.github.smuddgge.squishydatabase.record.Record;
import com.github.smuddgge.squishydatabase.Query;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
    private final @NotNull String name;

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

            // Build the statement
            String statement = "SELECT * FROM " + this.getName() + " WHERE " +
                    this.buildWhereStatement(query) + " LIMIT 1;";

            // Prepare the statement
            PreparedStatement preparedStatement = this.appendQuery(statement, query);

            // Get the results.
            ResultSet results = this.getDatabase().executeQuery(preparedStatement);
            if (results == null) return null;

            // Create a new record.
            R record = this.createRecord();

            // If the result exists append the results.
            if (results.next()) return (R) record.append(results);

        } catch (SQLException exception) {
            exception.printStackTrace();
            this.getDatabase().setDisable();
        }

        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public @NotNull List<R> getRecordList() {
        assert this.getDatabase() != null;

        try {

            // Build the statement.
            String statement = "SELECT * FROM " + this.getName() + ";";

            // Get the results.
            ResultSet results = this.getDatabase().executeQuery(statement);
            if (results == null) return new ArrayList<>();

            // Create a list of records from the result.
            List<R> recordList = new ArrayList<>();

            while (results.next()) {
                recordList.add((R) this.createRecord().append(results));
            }
            return recordList;

        } catch (SQLException exception) {
            exception.printStackTrace();
            this.getDatabase().setDisable();
            return new ArrayList<>();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<R> getRecordList(@NotNull Query query) {
        assert this.getDatabase() != null;

        try {

            // Build the statement.
            String statement = "SELECT * FROM " + this.getName() + " WHERE " +
                    this.buildWhereStatement(query) + ";";

            // Prepare the statement.
            PreparedStatement preparedStatement = this.appendQuery(statement, query);

            // Get the results.
            ResultSet results = preparedStatement.executeQuery();
            if (results == null) return null;

            // Create a list of records from the result.
            List<R> recordList = new ArrayList<>();

            while (results.next()) {
                recordList.add((R) this.createRecord().append(results));
            }
            return recordList;

        } catch (SQLException exception) {
            exception.printStackTrace();
            this.getDatabase().setDisable();
            return null;
        }
    }

    @Override
    public int getAmountOfRecords() {
        assert this.getDatabase() != null;

        try {

            // Build statement.
            String statement = "SELECT COUNT(*) AS amount FROM " + this.getName() + ";";

            // Execute query.
            ResultSet resultSet = this.getDatabase().executeQuery(statement);

            if (resultSet == null) return 0;

            return resultSet.getInt("amount");

        } catch (SQLException exception) {
            exception.printStackTrace();
            this.getDatabase().setDisable();
            return 0;
        }
    }

    @Override
    public int getAmountOfRecords(@NotNull Query query) {
        assert this.getDatabase() != null;

        try {

            // Build the statement
            String statement = "SELECT COUNT(*) AS amount FROM " + this.getName() + " WHERE " +
                    this.buildWhereStatement(query) + ";";

            // Prepare the statement.
            PreparedStatement preparedStatement = this.appendQuery(statement, query);

            // Execute query.
            ResultSet resultSet = this.getDatabase().executeQuery(preparedStatement);

            if (resultSet == null) return 0;

            return resultSet.getInt("amount");

        } catch (SQLException exception) {
            exception.printStackTrace();
            this.getDatabase().setDisable();
            return 0;
        }
    }

    @Override
    public boolean removeRecord(@NotNull Record record) {
        assert this.getDatabase() != null;
        assert this.getDatabase().getConnection() != null;

        try {

            // Create statement.
            String statement = "DELETE FROM " + this.getName() + " WHERE " +
                    record.getPrimaryKey().getKey() + " = ?;";

            // Prepare the statement.
            PreparedStatement preparedStatement = this.getDatabase()
                    .getConnection().prepareStatement(statement);

            preparedStatement.setObject(1, record.getPrimaryKey().getValue());

            // Execute the statement.
            return this.getDatabase().executeStatement(preparedStatement);

        } catch (SQLException exception) {
            exception.printStackTrace();
            this.getDatabase().setDisable();
            return false;
        }
    }

    @Override
    public boolean removeAllRecords(@NotNull Query query) {
        assert this.getDatabase() != null;
        assert this.getDatabase().getConnection() != null;

        try {

            // Create statement.
            String statement = "DELETE FROM " + this.getName() + " WHERE " +
                    this.buildWhereStatement(query) + ";";

            // Prepare the statement.
            PreparedStatement preparedStatement = this.appendQuery(statement, query);

            // Execute the statement.
            return this.getDatabase().executeStatement(preparedStatement);

        } catch (SQLException exception) {
            exception.printStackTrace();
            this.getDatabase().setDisable();
            return false;
        }
    }

    @Override
    public boolean insertRecord(@NotNull Record record) {
        assert this.getDatabase() != null;
        assert record.getPrimaryKey().getValue() != null;

        try {

            // Check if the record already exists.
            Record result = this.getFirstRecord(new Query()
                    .match(record.getPrimaryKey().getKey(), record.getPrimaryKey().getValue()));

            // If the result is null add the new record.
            if (result == null) {
                return this.addRecord(record);
            }

            // Otherwise, update the existing record.
            return this.updateRecord(record);

        } catch (SQLException exception) {
            exception.printStackTrace();
            this.getDatabase().setDisable();
            return false;
        }
    }
}
