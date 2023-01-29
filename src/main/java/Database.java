import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * <h1>Represents a database</h1>
 * Contains methods used to interact with the
 * database.
 */
public interface Database {

    /**
     * Used to create a table in the database.
     *
     * @param table The instance of the table.
     * @return True if the table was successfully added.
     */
    boolean createTable(@NotNull Table table);

    /**
     * Used to get a table from the database.
     *
     * @param instance Class instance of a table.
     * @return The instance of the requested table
     * if it exists.
     */
    <T> Table getTable(@NotNull Class<T> instance);

    /**
     * Used to get a table from the database.
     *
     * @param name The name of the table.
     * @return The instance of the requested table
     * if it exists.
     */
    Table getTable(@NotNull String name);

    /**
     * Used to get the list of tables in the database.
     *
     * @return The requested list of tables.
     */
    @NotNull List<Table> getTableList();

    /**
     * Used to get the list of table names in the database.
     *
     * @return The requested list of table names.
     */
    @NotNull List<String> getTableNameList();
}
