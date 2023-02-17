```yaml
status: In Development
```

Example of the capability's!

```java
// Create a database factory of your choice.
// Change the type of database anytime!
DatabaseFactory databaseFactory=DatabaseFactory.SQLITE;

// Make new databases using the factory.
Database database=databaseFactory.create(new DatabaseCredentials().setPath("src/test/resources/database.sqlite3"));
database.setDebugMode(true);

// Create a new table
 CustomerTable customerTable=new CustomerTable();
database.createTable(customerTable);

// Get the table class back from the database!
CustomerTable resultTable=database.getTable(CustomerTable.class);
```
