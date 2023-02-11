package com.github.smuddgge.record;

import java.lang.reflect.Field;

/**
 * <h1>Represents a field in a record</h1>
 * A field can be used to set and get values.
 */
public record RecordField(Record record, Field field) {

    /**
     * Used to get the key of the field.
     * This is otherwise known as the name or identifier.
     *
     * @return The key of the field.
     */
    public String getKey() {
        return this.field.getName();
    }

    /**
     * Used to get the value of the field.
     *
     * @return The fields value.
     * @throws IllegalAccessException The exception thrown in the
     *                                field or record does not exist.
     */
    public Object getValue() throws IllegalAccessException {
        return this.field.get(this.record);
    }

    /**
     * Used to get the value type as a string.
     *
     * @return The value type as a string.
     */
    public Class<?> getValueType() {
        return this.field.getType();
    }

    /**
     * Used to get the type of field.
     * This will default to {@link RecordFieldType#FIELD}.
     *
     * @return The type of field.
     */
    public RecordFieldType getFieldType() {
        if (!this.hasFieldAnnotation()) return RecordFieldType.FIELD;
        return this.field.getAnnotation(RecordFieldAnnotation.class).type();
    }

    /**
     * Used to set the field value.
     *
     * @param value The value to set the field to.
     * @return True if the value was set successfully.
     */
    public boolean setValue(Object value) {
        try {
            this.field.set(this.record, value);
            return true;
        } catch (IllegalAccessException exception) {
            exception.printStackTrace();
            return false;
        }
    }

    /**
     * Used to check if the field has a field annotation.
     *
     * @return True if the field has {@link RecordFieldAnnotation}
     */
    public boolean hasFieldAnnotation() {
        return this.field.isAnnotationPresent(RecordFieldAnnotation.class);
    }

    /**
     * Used to check if the field should be ignored.
     * @return True if the field has {@link RecordFieldIgnoreAnnotation}
     */
    public boolean isIgnored() {
        return this.field.isAnnotationPresent(RecordFieldIgnoreAnnotation.class);
    }
}
