package com.github.smuddgge.squishydatabase.errors;

import org.jetbrains.annotations.NotNull;

/**
 * <h1>Represents a foreign key error</h1>
 * This error occurs when the foreign key reference is incorrect or does not exist.
 */
public class ForeignKeyReferenceException extends RuntimeException {

    public ForeignKeyReferenceException(@NotNull String tableName, @NotNull String fieldName) {
        super("Incorrect foreign key at {table}.{field}"
                .replace("{table}", tableName)
                .replace("{field}", fieldName)
        );
    }
}
