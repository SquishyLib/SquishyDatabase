package com.github.smuddgge.record;

/**
 * <h1>Represents a fields type</h1>
 * This is used in a {@link RecordField} to determine
 * what the value represents in the database.
 * <li>
 *     A {@link RecordFieldType#FIELD} represents the
 *     default value.
 * </li>
 */
public enum RecordFieldType {
    FIELD, PRIMARY, FOREIGN
}
