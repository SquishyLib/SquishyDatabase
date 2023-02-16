package com.github.smuddgge.utility;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a database query.
 */
public class Query {

    private final Map<String, Object> map = new HashMap<>();

    /**
     * Used to get the map of matches.
     *
     * @return The instance of the map.
     */
    public @NotNull Map<String, Object> get() {
        return this.map;
    }


    /**
     * Used to get the size of the query.
     *
     * @return The size of the query map.
     */
    public int getSize() {
        return this.map.size();
    }

    /**
     * Used to add a match for a key and value.
     *
     * @param key   The name of the key.
     * @param value The instance of the value.
     */
    public @NotNull Query match(@NotNull String key, @NotNull Object value) {
        this.map.put(key, value);
        return this;
    }
}
