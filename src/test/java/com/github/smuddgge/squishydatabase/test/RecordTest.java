package com.github.smuddgge.squishydatabase.test;

import com.github.smuddgge.squishydatabase.record.Record;
import com.github.smuddgge.squishydatabase.results.ResultChecker;
import org.junit.jupiter.api.Test;

public class RecordTest {

    @Test
    public void testGetOpposite() {
        new ResultChecker()
                .expect((Boolean) Record.getOpposite(false))
                .expect(Record.getOpposite("true"), "false")
                .expect(Record.getOpposite("false"), "true")
                .expect(Record.getOpposite(1), 0);
    }
}
