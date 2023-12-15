package com.github.smuddgge.squishydatabase.records;

import com.github.smuddgge.squishydatabase.record.ForeignField;
import com.github.smuddgge.squishydatabase.record.Record;
import com.github.smuddgge.squishydatabase.record.Field;
import com.github.smuddgge.squishydatabase.record.RecordFieldType;

/**
 * <h1>Represents a purchase</h1>
 */
public class Purchase extends Record {

    @Field(type = RecordFieldType.PRIMARY)
    public String uuid;

    @Field(type = RecordFieldType.FOREIGN)
    @ForeignField(table = "Customer", field = "uuid")
    public String customer;
}
