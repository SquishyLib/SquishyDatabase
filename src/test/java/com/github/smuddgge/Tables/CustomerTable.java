package com.github.smuddgge.Tables;

import com.github.smuddgge.interfaces.Record;
import com.github.smuddgge.interfaces.Table;
import org.jetbrains.annotations.NotNull;

/**
 * Represents the customer table.
 * @param <R> The instance of the record.
 */
public class CustomerTable<R extends Record> extends Table<R> {

    @Override
    public @NotNull String getName() {
        return "Customer";
    }
}
