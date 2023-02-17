package com.github.smuddgge.records;

import com.github.smuddgge.record.Record;
import com.github.smuddgge.record.RecordFieldAnnotation;
import com.github.smuddgge.record.RecordFieldIgnoreAnnotation;
import com.github.smuddgge.record.RecordFieldType;

/**
 * <h1>Represents a customer</h1>
 */
public class Customer extends Record {

    @RecordFieldAnnotation(type = RecordFieldType.PRIMARY)
    public String identifier;

    public String name;

    @RecordFieldIgnoreAnnotation
    public Object dataStore;
}
