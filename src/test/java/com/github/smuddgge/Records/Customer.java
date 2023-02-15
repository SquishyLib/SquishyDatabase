package com.github.smuddgge.Records;

import com.github.smuddgge.record.*;
import com.github.smuddgge.record.Record;

/**
 * <h1>Represents a customer</h1>
 */
public class Customer extends Record {

    @RecordFieldAnnotation(type = RecordFieldType.PRIMARY)
    public String identifier;

    @RecordFieldIgnoreAnnotation
    public Object dataStore;
}
