package com.github.smuddgge.implementation.sqlite;

import com.github.smuddgge.interfaces.TableSelection;
import com.github.smuddgge.record.Record;
import com.github.smuddgge.utility.Query;
import org.jetbrains.annotations.NotNull;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

/**
 * Represents methods not defined in the
 * {@link TableSelection} implementation.
 * However, these methods are specific to
 * the sqlite implementation.
 */
public abstract class AbstractSQLiteTableSelection<R extends Record>
        extends TableSelection<R, SQLiteDatabase> {

    /**
     * Used to turn a query into a safe prepared statement.
     *
     * @param query The instance of the query.
     * @return The requested statement to execute.
     */
    protected @NotNull PreparedStatement getStatement(@NotNull Query query) throws SQLException {
        assert this.getDatabase() != null;

        // Build the statement.
        String statement = this.buildStatement(query) + " LIMIT 1;";

        // Prepare the statement.
        PreparedStatement preparedStatement = this.getDatabase()
                .getConnection().prepareStatement(statement);

        int index = 0;
        for (Map.Entry<String, Object> map : query.get().entrySet()) {
            preparedStatement.setObject(index, map.getKey());
            preparedStatement.setObject((index + 1), map.getValue());
            index += 2;
        }

        return preparedStatement;
    }

    /**
     * Used to build the basic statement.
     * This will use the query to insert the correct
     * amount of wildcards.
     *
     * @param query The instance of the query.
     * @return The instance of the statement.
     * The statement will not contain a semicolon.
     */
    private @NotNull String buildStatement(@NotNull Query query) {
        StringBuilder statement = new StringBuilder();

        statement.append("SELECT * FROM `").append(this.getName()).append("` WHERE ");

        for (Map.Entry<String, Object> map : query.get().entrySet()) {
            // Check if the object is a string.
            if (map.getValue() instanceof String) {
                statement.append("? = '?' AND");
                continue;
            }

            statement.append("? = ? AND");
        }

        // Delete the last ' AND'
        statement.replace(statement.length() - 4, statement.length() - 1, "");

        return statement.toString();
    }
}
