package com.github.smuddgge.squishydatabase.testing;

import com.github.smuddgge.squishydatabase.DatabaseCredentials;
import com.github.smuddgge.squishydatabase.DatabaseFactory;
import com.github.smuddgge.squishydatabase.console.Console;
import com.github.smuddgge.squishydatabase.interfaces.Database;
import com.github.smuddgge.squishydatabase.records.Customer;
import com.github.smuddgge.squishydatabase.Query;
import com.github.smuddgge.squishydatabase.results.ResultChecker;
import com.github.smuddgge.squishydatabase.tables.CustomerTable;
import com.github.smuddgge.squishydatabase.tables.PurchaseTable;
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
            new DatabaseCredentials("src/test/resources/database.sqlite3")
    ).setDebugMode(true);

    @Test
    public void testGetFirstRecord() {
        // Create table.
        CustomerTable customerTable = new CustomerTable();
        database.createTable(customerTable);

        // Create a new record.
        Customer customer = new Customer();
        customer.uuid = UUID.randomUUID().toString();
        customer.name = "Smudge";

        // Insert the record.
        customerTable.insertRecord(customer);

        // Get the record back from the table.
        Customer result = customerTable.getFirstRecord(
                new Query().match("uuid", customer.uuid)
        );

        assert result != null;

        // Check results.
        new ResultChecker()
                .expect(result.uuid, customer.uuid)
                .expect(result.name, customer.name);
    }

    @Test
    public void testGetFirstRecord2() {
        // Create table.
        CustomerTable customerTable = new CustomerTable();
        database.createTable(customerTable);

        // Create a new record.
        Customer customer = new Customer();
        customer.uuid = UUID.randomUUID().toString();
        customer.name = "Smudge";

        // Insert the record.
        customerTable.insertRecord(customer);

        // Get the record back from the table.
        Customer result = customerTable.getFirstRecord(
                new Query()
                        .match("uuid", customer.uuid)
                        .match("name", customer.name)
        );

        assert result != null;

        // Check results.
        new ResultChecker()
                .expect(result.uuid, customer.uuid)
                .expect(result.name, customer.name);
    }

    @Test
    public void testGetRecordList() {
        // Create table.
        CustomerTable customerTable = new CustomerTable();
        database.createTable(customerTable);

        // Create a new record.
        Customer customer1 = new Customer();
        customer1.uuid = UUID.randomUUID().toString();
        customer1.name = UUID.randomUUID().toString();

        Customer customer2 = new Customer();
        customer2.uuid = UUID.randomUUID().toString();
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
                .expect(result.get(0).uuid, customer1.uuid)
                .expect(result.get(0).name, customer1.name)
                .expect(result.get(1).uuid, customer2.uuid)
                .expect(result.get(1).name, customer2.name);

        // Test getting all records.
        List<Customer> allRecords = customerTable.getRecordList();

        new ResultChecker()
                .expect(allRecords.size() > 0);
    }

    @Test
    public void testGetAmountOfRecords() {
        // Create table.
        CustomerTable customerTable = new CustomerTable();
        database.createTable(customerTable);

        // Create a new record.
        Customer customer1 = new Customer();
        customer1.uuid = UUID.randomUUID().toString();
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
        customer1.uuid = UUID.randomUUID().toString();
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
        customer1.uuid = UUID.randomUUID().toString();
        customer1.name = UUID.randomUUID().toString();

        // Insert the record.
        customerTable.insertRecord(customer1);

        // Remove the record.
        customerTable.removeRecord(customer1);

        // Get the record back from the table.
        int amount = customerTable.getAmountOfRecords(
                new Query().match("uuid", customer1.uuid)
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
        customer1.uuid = UUID.randomUUID().toString();
        customer1.name = UUID.randomUUID().toString();

        Customer customer2 = new Customer();
        customer2.uuid = UUID.randomUUID().toString();
        customer2.name = customer1.name;

        // Insert the record.
        customerTable.insertRecord(customer1);
        customerTable.insertRecord(customer2);

        customerTable.removeAllRecords(
                new Query().match("name", customer2.name)
        );

        // Get the record back from the table.
        int amount = customerTable.getAmountOfRecords(
                new Query().match("uuid", customer1.uuid)
        );

        // Check results.
        new ResultChecker().expect(amount, 0);
    }

    @Test
    public void testForeignKeys() {
        new ResultChecker()
                .expect(database.createTable(new CustomerTable()))
                .expect(database.createTable(new PurchaseTable()));
    }
}
