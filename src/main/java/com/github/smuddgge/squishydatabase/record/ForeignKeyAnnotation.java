package com.github.smuddgge.squishydatabase.record;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Used to state the reference for
 * a foreign key.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ForeignKeyAnnotation {

    /**
     * @return The name of the table the foreign
     * key is from.
     */
    String table();

    /**
     * @return The name of the field in the
     * reference table.
     */
    String field();
}
