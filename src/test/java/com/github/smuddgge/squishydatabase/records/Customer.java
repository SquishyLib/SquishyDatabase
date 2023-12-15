package com.github.smuddgge.squishydatabase.records;

import com.github.smuddgge.squishydatabase.record.Record;
import com.github.smuddgge.squishydatabase.record.Field;
import com.github.smuddgge.squishydatabase.record.IgnoreField;
import com.github.smuddgge.squishydatabase.record.RecordFieldType;

/**
 * <h1>Represents a customer</h1>
 */
public class Customer extends Record {

    @Field(type = RecordFieldType.PRIMARY)
    public String uuid;

    public String name;

    @IgnoreField
    public Object dataStore;
}
