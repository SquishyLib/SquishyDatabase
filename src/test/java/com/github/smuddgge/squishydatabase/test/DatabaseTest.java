package com.github.smuddgge.squishydatabase.test;

import com.github.smuddgge.squishydatabase.DatabaseFactory;
import com.github.smuddgge.squishydatabase.interfaces.Database;
import com.github.smuddgge.squishydatabase.tester.DatabaseTester;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class DatabaseTest {

    @Test
    public void testSqlite() {
        Database database = DatabaseFactory.SQLITE("src/test/resources/database.sqlite3").setDebugMode(true);
        DatabaseTester databaseTester = new DatabaseTester(database);
        databaseTester.runTests();
    }
}
