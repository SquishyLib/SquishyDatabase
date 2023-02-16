package com.github.smuddgge;

import com.github.smuddgge.Records.Customer;
import com.github.smuddgge.Tables.CustomerTable;
import com.github.smuddgge.interfaces.Database;
import com.github.smuddgge.utility.Console;
import com.github.smuddgge.utility.Query;

import java.util.UUID;

public class MainTesting {

    public static void main(String[] args) {

        // Create a database
        DatabaseFactory databaseFactory = DatabaseFactory.SQLITE;
        Database database = databaseFactory.create(new DatabaseCredentials().setPath("src/test/resources/database.sqlite3"));
        database.setDebugMode(true);

        // Create a new table.
        CustomerTable customerTable = new CustomerTable();
        database.createTable(customerTable);

        // Create a new record.
        Customer customer = new Customer();
        customer.identifier = UUID.randomUUID().toString();
        customer.name = "Smudge";

        // Insert into the table.
        customerTable.insertRecord(customer);

        // Get the instance of the table stored in the database.
        CustomerTable resultTable = database.getTable(CustomerTable.class);

        // Get the record.
        Customer resultCustomer = resultTable.getFirstRecord(new Query()
                .match("identifier", customer.identifier));

        assert resultCustomer != null;

        Console.log(resultCustomer.identifier);
        Console.log(resultCustomer.name);
    }
}
