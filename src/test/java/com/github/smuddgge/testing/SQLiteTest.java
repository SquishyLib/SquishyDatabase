package com.github.smuddgge.testing;

import com.github.smuddgge.DatabaseCredentials;
import com.github.smuddgge.DatabaseFactory;
import com.github.smuddgge.console.Console;
import com.github.smuddgge.interfaces.Database;
import com.github.smuddgge.records.Customer;
import com.github.smuddgge.tables.CustomerTable;
import com.github.smuddgge.Query;
import com.github.smuddgge.results.ResultChecker;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

/**
 * <h1>Represents the sqlite tests</h1>
 */
public class SQLiteTest {

    /**
     * Create a database factory.
     */
    public static DatabaseFactory databaseFactory = DatabaseFactory.SQLITE;

    /**
     * Create database.
     */
    public static Database database = databaseFactory.create(
            new DatabaseCredentials().setPath("src/test/resources/database.sqlite3")
    ).setDebugMode(true);

    @Test
    public void testGetFirstRecord() {
        // Create table.
        CustomerTable customerTable = new CustomerTable();
        database.createTable(customerTable);

        // Create a new record.
        Customer customer = new Customer();
        customer.identifier = UUID.randomUUID().toString();
        customer.name = "Smudge";

        // Insert the record.
        customerTable.insertRecord(customer);

        // Get the record back from the table.
        Customer result = customerTable.getFirstRecord(
                new Query().match("identifier", customer.identifier)
        );

        assert result != null;

        // Check results.
        new ResultChecker()
                .expect(result.identifier, customer.identifier)
                .expect(result.name, customer.name);
    }

    @Test
    public void testGetRecordList() {
        // Create table.
        CustomerTable customerTable = new CustomerTable();
        database.createTable(customerTable);

        // Create a new record.
        Customer customer1 = new Customer();
        customer1.identifier = UUID.randomUUID().toString();
        customer1.name = UUID.randomUUID().toString();

        Customer customer2 = new Customer();
        customer2.identifier = UUID.randomUUID().toString();
        customer2.name = customer1.name;

        // Insert the record.
        customerTable.insertRecord(customer1);
        customerTable.insertRecord(customer2);

        // Get the record back from the table.
        List<Customer> result = customerTable.getRecordList(
                new Query().match("name", customer2.name)
        );

        assert result != null;

        // Check results.
        new ResultChecker()
                .expect(result.get(0).identifier, customer1.identifier)
                .expect(result.get(0).name, customer1.name)
                .expect(result.get(1).identifier, customer2.identifier)
                .expect(result.get(1).name, customer2.name);
    }

    @Test
    public void testGetAmountOfRecords() {
        // Create table.
        CustomerTable customerTable = new CustomerTable();
        database.createTable(customerTable);

        // Create a new record.
        Customer customer1 = new Customer();
        customer1.identifier = UUID.randomUUID().toString();
        customer1.name = UUID.randomUUID().toString();

        // Insert the record.
        customerTable.insertRecord(customer1);

        // Get the record back from the table.
        int amount = customerTable.getAmountOfRecords();

        Console.log("Total amount of records : " + amount);

        // Check results.
        new ResultChecker().expect(amount > 0);
    }

    @Test
    public void testQueryGetAmountOfRecords() {
        // Create table.
        CustomerTable customerTable = new CustomerTable();
        database.createTable(customerTable);

        // Create a new record.
        Customer customer1 = new Customer();
        customer1.identifier = UUID.randomUUID().toString();
        customer1.name = UUID.randomUUID().toString();

        // Insert the record.
        customerTable.insertRecord(customer1);

        // Get the record back from the table.
        int amount = customerTable.getAmountOfRecords(
                new Query().match("name", customer1.name)
        );

        // Check results.
        new ResultChecker().expect(amount > 0);
    }

    @Test
    public void testRemoveRecord() {
        // Create table.
        CustomerTable customerTable = new CustomerTable();
        database.createTable(customerTable);

        // Create a new record.
        Customer customer1 = new Customer();
        customer1.identifier = UUID.randomUUID().toString();
        customer1.name = UUID.randomUUID().toString();

        // Insert the record.
        customerTable.insertRecord(customer1);

        // Remove the record.
        customerTable.removeRecord(customer1);

        // Get the record back from the table.
        int amount = customerTable.getAmountOfRecords(
                new Query().match("identifier", customer1.identifier)
        );

        // Check results.
        new ResultChecker().expect(amount, 0);
    }

    @Test
    public void testQueryRemoveRecord() {
        // Create table.
        CustomerTable customerTable = new CustomerTable();
        database.createTable(customerTable);

        // Create a new record.
        Customer customer1 = new Customer();
        customer1.identifier = UUID.randomUUID().toString();
        customer1.name = UUID.randomUUID().toString();

        Customer customer2 = new Customer();
        customer2.identifier = UUID.randomUUID().toString();
        customer2.name = customer1.name;

        // Insert the record.
        customerTable.insertRecord(customer1);
        customerTable.insertRecord(customer2);

        customerTable.removeAllRecords(
                new Query().match("name", customer2.name)
        );

        // Get the record back from the table.
        int amount = customerTable.getAmountOfRecords(
                new Query().match("identifier", customer1.identifier)
        );

        // Check results.
        new ResultChecker().expect(amount, 0);
    }
}
