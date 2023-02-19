package com.github.smuddgge.squishydatabase.records;

import com.github.smuddgge.squishydatabase.record.ForeignKeyAnnotation;
import com.github.smuddgge.squishydatabase.record.Record;
import com.github.smuddgge.squishydatabase.record.RecordFieldAnnotation;
import com.github.smuddgge.squishydatabase.record.RecordFieldType;

public class Purchase extends Record {

    @RecordFieldAnnotation(type = RecordFieldType.PRIMARY)
    public String uuid;

    @RecordFieldAnnotation(type = RecordFieldType.FOREIGN)
    @ForeignKeyAnnotation(table = "Customer", field = "uuid")
    public String customer;
}
