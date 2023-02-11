package com.github.smuddgge.record;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a record in the {@link com.github.smuddgge.interfaces.Database}
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
}
