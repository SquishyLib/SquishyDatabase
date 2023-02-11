package com.github.smuddgge.record;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * <h1>Represents a field ignore annotation</h1>
 * Used to state which field should be ignored by the database.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface RecordFieldIgnoreAnnotation {
}
