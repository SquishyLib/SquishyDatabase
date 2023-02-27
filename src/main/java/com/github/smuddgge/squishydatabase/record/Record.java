package com.github.smuddgge.squishydatabase.record;

import com.github.smuddgge.squishydatabase.interfaces.Database;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a record in the {@link Database}
 */
public class Record {

    /**
     * Used to get all the record fields.
     * The {@link RecordFieldIgnoreAnnotation} fields will be left out.
     *
     * @return The full list of record fields.
     */
    public List<RecordField> getFieldList() {
        List<RecordField> recordFieldList = new ArrayList<>();

        // Loop though every field in the class.
        for (Field field : this.getClass().getFields()) {
            RecordField recordField = new RecordField(this, field);

            if (recordField.isIgnored()) continue;
            recordFieldList.add(recordField);
        }

        return recordFieldList;
    }

    /**
     * Used to get the record field list that only contains
     * one type of field.
     * The {@link RecordFieldIgnoreAnnotation} fields will be left out.
     *
     * @param type The type of field to get.
     * @return The list of fields with the specific
     * record field type.
     */
    public List<RecordField> getFieldList(RecordFieldType type) {
        List<RecordField> recordFieldList = new ArrayList<>();

        // Loop though every record field.
        for (RecordField recordField : this.getFieldList()) {

            // Check if the field is not the correct type.
            if (recordField.getFieldType() != type) continue;
            recordFieldList.add(recordField);
        }

        return recordFieldList;
    }

    /**
     * Used to get the list of field names.
     *
     * @return The names of the fields in this record.
     */
    public List<String> getFieldNameList() {
        List<String> fieldNameList = new ArrayList<>();

        for (RecordField recordField : this.getFieldList()) {
            fieldNameList.add(recordField.getKey());
        }

        return fieldNameList;
    }

    /**
     * Used to get a field from this record.
     *
     * @param name The name of the record.
     * @return The requested record field.
     */
    public @Nullable RecordField getField(@NotNull String name) {
        for (RecordField recordField : this.getFieldList()) {
            if (recordField.getKey().equals(name)) return recordField;
        }
        return null;
    }

    /**
     * Used to get the primary key.
     *
     * @return The instance of the primary key.
     */
    public @NotNull RecordField getPrimaryKey() {
        return this.getFieldList(RecordFieldType.PRIMARY).get(0);
    }

    /**
     * Used to append the result set to this record.
     *
     * @param results The instance of the results.
     * @return This instance.
     * @throws SQLException If a field does not exist in the result.
     */
    public Record append(ResultSet results) throws SQLException {
        for (RecordField recordField : this.getFieldList()) {
            recordField.setValue(results.getObject(recordField.getKey()));
        }

        return this;
    }

    /**
     * <h1>Used to toggle a boolean</h1>
     * It will set true to false.
     * The value can be a boolean, string or integer.
     *
     * @param fieldName The fields name.
     */
    public void toggleBoolean(@NotNull String fieldName) {
        RecordField recordField = this.getField(fieldName);
        assert recordField != null;

        Object value = recordField.getValue();

        // If the field is a string.
        if (value instanceof String) {
            if (((String) value).equalsIgnoreCase("true")) {
                recordField.setValue("false");
            }
            if (((String) value).equalsIgnoreCase("true")) {
                recordField.setValue("false");
            }
            return;
        }

        // If the field is a boolean
        if (value instanceof Boolean) {
            recordField.setValue(!(Boolean) value);
            return;
        }

        // If the field is an integer.
        if (value instanceof Integer) {
            if ((Integer) value == 0) recordField.setValue(1);
            if ((Integer) value == 1) recordField.setValue(0);
        }
    }
}
