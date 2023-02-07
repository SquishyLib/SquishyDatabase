package com.github.smuddgge;

import com.github.smuddgge.Records.Customer;
import com.github.smuddgge.Tables.CustomerTable;
import com.github.smuddgge.interfaces.Database;

public class MainTesting {

    public static void main(String[] args) {

        // Create a database
        DatabaseFactory databaseFactory = DatabaseFactory.SQLITE;
        Database database = databaseFactory.create(new DatabaseCredentials().setPath("src/test/resources/database.sqlite3"));

        // Create a new table
        CustomerTable<Customer> customerTable = new CustomerTable<>();
        database.createTable(customerTable);
    }
}
