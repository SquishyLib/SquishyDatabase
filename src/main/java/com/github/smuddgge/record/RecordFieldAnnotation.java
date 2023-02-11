package com.github.smuddgge.record;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * <h1>Represents a record field annotation</h1>
 * If this annotation is not used in a record it will
 * default to a {@link RecordFieldType#FIELD}.
 * Not setting the annotation will also default
 * to {@link RecordFieldType#FIELD}.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface RecordFieldAnnotation {

    /**
     * Used to set what type of field the field is.
     * This defaults to {@link RecordFieldType#FIELD}
     * @return The field type.
     */
    RecordFieldType type() default RecordFieldType.FIELD;
}
