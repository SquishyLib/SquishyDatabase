package com.github.smuddgge.tables;

import com.github.smuddgge.interfaces.TableAdapter;
import com.github.smuddgge.records.Customer;
import org.jetbrains.annotations.NotNull;

/**
 * Represents the customer table.
 */
public class CustomerTable extends TableAdapter<Customer> {

    @Override
    public @NotNull String getName() {
        return "Customer";
    }
}
