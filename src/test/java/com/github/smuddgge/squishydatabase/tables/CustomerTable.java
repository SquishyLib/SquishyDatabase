package com.github.smuddgge.squishydatabase.tables;

import com.github.smuddgge.squishydatabase.interfaces.TableAdapter;
import com.github.smuddgge.squishydatabase.records.Customer;
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
