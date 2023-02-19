package com.github.smuddgge.squishydatabase.tables;

import com.github.smuddgge.squishydatabase.interfaces.TableAdapter;
import com.github.smuddgge.squishydatabase.records.Purchase;
import org.jetbrains.annotations.NotNull;

public class PurchaseTable extends TableAdapter<Purchase> {

    @Override
    public @NotNull String getName() {
        return "Purchase";
    }
}
