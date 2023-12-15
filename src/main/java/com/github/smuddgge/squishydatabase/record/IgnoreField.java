package com.github.smuddgge.squishydatabase.record;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * <h1>Represents a field ignore annotation</h1>
 * Used to state which field should be ignored by the database.
 * The value will not be saved in the database.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface IgnoreField {
}
