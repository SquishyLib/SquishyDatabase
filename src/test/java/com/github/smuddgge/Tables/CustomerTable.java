package com.github.smuddgge.Tables;

import com.github.smuddgge.Records.Customer;
import com.github.smuddgge.interfaces.Table;
import org.jetbrains.annotations.NotNull;

/**
 * Represents the customer table.
 */
public class CustomerTable extends Table<Customer> {

    @Override
    public @NotNull String getName() {
        return "Customer";
    }
}
