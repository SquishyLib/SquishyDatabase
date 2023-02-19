package com.github.smuddgge.squishydatabase.records;

import com.github.smuddgge.squishydatabase.record.Record;
import com.github.smuddgge.squishydatabase.record.RecordFieldAnnotation;
import com.github.smuddgge.squishydatabase.record.RecordFieldIgnoreAnnotation;
import com.github.smuddgge.squishydatabase.record.RecordFieldType;

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
