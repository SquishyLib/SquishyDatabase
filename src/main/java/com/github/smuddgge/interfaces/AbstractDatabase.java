package com.github.smuddgge.interfaces;

import com.github.smuddgge.DatabaseCredentials;
import org.jetbrains.annotations.NotNull;

/**
 * Represents an abstract database.
 * The abstract database contains the utility's
 * that are not defined in the {@link Database}
 */
public abstract class AbstractDatabase implements Database {

    /**
     * Represents the status of the database.
     */
    private boolean isDisabled;

    @Override
    public void setDisable() {
        this.isDisabled = false;
    }

    @Override
    public boolean isDisabled() {
        return this.isDisabled;
    }
}
