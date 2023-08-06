[![](https://jitpack.io/v/Smuddgge/SquishyDatabase.svg)](https://jitpack.io/#Smuddgge/SquishyDatabase)
[![CodeFactor](https://www.codefactor.io/repository/github/smuddgge/squishydatabase/badge)](https://www.codefactor.io/repository/github/smuddgge/squishydatabase)

<div align=center>
    <a href="https://smuddgge.gitbook.io/squishy-database/"><img src="./graphics/wiki.png" width="512"></a>
</div>

**Example**
```java
// Create a database factory of your choice.
DatabaseFactory databaseFactory=DatabaseFactory.SQLITE;

// Make new databases using the factory.
Database database = databaseFactory.create(
    DatabaseCredentials().SQLITE("src/test/resources/database.sqlite3")
).setDebugMode(true);

// Create a new table.
CustomerTable customerTable = new CustomerTable();
database.createTable(customerTable);

// Create a record.
Customer customer = new Customer();
customer.identifier = UUID.randomUUID().toString();

// Insert the record.
customerTable.insertRecord(customer);
```

**Maven**
```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```
```xml
<dependency>
    <groupId>com.github.smuddgge</groupId>
    <artifactId>SquishyDatabase</artifactId>
    <version>Tag</version>
</dependency>
```

**Gradle**
```gradle
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```
```gradle
dependencies {
    implementation 'com.github.smuddgge:SquishyDatabase:Tag'
}
```
