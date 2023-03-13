package com.github.smuddgge.squishydatabase.tester;

import com.github.smuddgge.squishydatabase.DatabaseCredentials;
import com.github.smuddgge.squishydatabase.DatabaseFactory;
import com.github.smuddgge.squishydatabase.Query;
import com.github.smuddgge.squishydatabase.console.Console;
import com.github.smuddgge.squishydatabase.interfaces.Database;
import com.github.smuddgge.squishydatabase.records.Customer;
import com.github.smuddgge.squishydatabase.results.ResultChecker;
import com.github.smuddgge.squishydatabase.tables.CustomerTable;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.platform.commons.annotation.Testable;

/**
 * <h1>Represents a database tester</h1>
 * Used to test a database type.
 */
public class DatabaseTester {

    private final Database database;

    private CustomerTable customerTable;
    private Customer customer1;
    private Customer customer2;

    /**
     * Used to create a database tester.
     * 
     * @param databaseFactory      The instance of the database factory.
     * @param databaseCredentials The instance of the database credentials.
     */
    public DatabaseTester(DatabaseFactory databaseFactory, DatabaseCredentials databaseCredentials) {
        this.database = databaseFactory.create(databaseCredentials).setDebugMode(true);
    }

    /**
     * Used to create a database tester.
     * 
     * @param database The instance of a database.
     */
    public DatabaseTester(Database database) {
        this.database = database.setDebugMode(true);
    }

    /**
     * Used to run all the database tests.
     * 
     * @return This instance of the database tester.
     */
    public DatabaseTester runTests() {
        
        // This also ensures the table has been created for other tests.
        this.testCreateTable();

        // This also ensures 2 records are inserted.
        this.testCreateRecords();

        // Other tests
        this.testGetFirstRecord();
        this.testGetFirstRecordWithTwoMatches();
        this.testGetRecordList();
        this.testGetAmountOfRecords();
        this.testGetAmountOfRecordsWithQuery();
        this.testRemoveRecord();

        return this;
    }

    /**
     * Used to test creating a table in the database.
     */
    @Tag("test")
    @Test
    public void testCreateTable() {
        this.customerTable = new CustomerTable();
        database.createTable(customerTable);
    }

    /**
     * Used to test creating records in the database.
     */
    @Test
    public void testCreateRecords() {
        // The name in both customers is the same
        // to test getting a list of records.
        this.customer1 = new Customer();
        this.customer1.uuid = UUID.randomUUID().toString();
        this.customer1.name = UUID.randomUUID().toString();

        this.customerTable.insertRecord(this.customer1);

        this.customer2 = new Customer();
        this.customer2.uuid = UUID.randomUUID().toString();
        this.customer2.name = this.customer1.name;

        this.customerTable.insertRecord(this.customer2);
    }

    /**
     * Used to test getting the first record from the database
     * after inserting a record.
     */
    @Test
    public void testGetFirstRecord() {
        // Get a record from the table.
        Customer result = this.customerTable.getFirstRecord(
                new Query().match("uuid", this.customer1.uuid)
        );

        assert result != null;

        // Check results.
        new ResultChecker()
                .expect(result.uuid, this.customer1.uuid)
                .expect(result.name, this.customer1.name);
    }

    /**
     * Used to get the first record in the database which matches two query's.
     */
    @Test
    public void testGetFirstRecordWithTwoMatches() {
        // Get a record from the table.
        Customer result = this.customerTable.getFirstRecord(
            new Query()
                .match("uuid", this.customer1.uuid)
                .match("name", this.customer1.name)
        );

        assert result != null;

        // Check results.
        new ResultChecker()
                .expect(result.uuid, this.customer1.uuid)
                .expect(result.name, this.customer1.name);

    }

    @Testable
    public void testGetRecordList() {
        // Get the records from the table.
        List<Customer> result = this.customerTable.getRecordList(
                new Query().match("name", this.customer1.name)
        );

        assert result != null;

        // Check results.
        new ResultChecker()
                .expect(result.get(0).uuid, this.customer1.uuid)
                .expect(result.get(0).name, this.customer1.name)
                .expect(result.get(1).uuid, this.customer2.uuid)
                .expect(result.get(1).name, this.customer2.name);

        // Test getting all records.
        List<Customer> allRecords = this.customerTable.getRecordList();

        new ResultChecker().expect(allRecords.size() > 0);
    }

    @Testable
    public void testGetAmountOfRecords() {
        // Get the record back from the table.
        int amount = this.customerTable.getAmountOfRecords();

        // Log the amount of records.
        Console.log("Total amount of records : " + amount);

        // Check results.
        new ResultChecker().expect(amount > 0);
    }

    @Testable
    public void testGetAmountOfRecordsWithQuery() {
        // Get the record back from the table.
        int amount = this.customerTable.getAmountOfRecords(
                new Query().match("name", this.customer1.name)
        );

        // Log the amount of records.
        Console.log("Total amount of records : " + amount);

        // Check results.
        new ResultChecker().expect(amount > 0);
    }

    /**
     * Used to test removing a record from the database.
     */
    @Testable
    public void testRemoveRecord() {
        // Create a new record.
        Customer customer3 = new Customer();
        customer3.uuid = UUID.randomUUID().toString();
        customer3.name = UUID.randomUUID().toString();

        // Insert the record.
        customerTable.insertRecord(customer3);

        // Remove the record.
        customerTable.removeRecord(customer3);

        // Get the record back from the table.
        int amount = customerTable.getAmountOfRecords(
                new Query().match("uuid", customer3.uuid)
        );

        // Check results.
        new ResultChecker().expect(amount, 0);
    }

    @Testable
    public void testRemoveRecordWithQuery() {
        // Create a new record.
        Customer customer3 = new Customer();
        customer3.uuid = UUID.randomUUID().toString();
        customer3.name = UUID.randomUUID().toString();

        // Insert the record.
        customerTable.insertRecord(customer3);

        // Remove the record.
        customerTable.removeAllRecords(
                new Query().match("uuid", customer3.uuid)
        );

        // Get the record back from the table.
        int amount = customerTable.getAmountOfRecords(
                new Query().match("uuid", customer3.uuid)
        );

        // Check results.
        new ResultChecker().expect(amount, 0);
    }
}