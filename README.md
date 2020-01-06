# Java Database Management System
XML based DBMS that supports SQL syntax with JDBC API [JDBC Repo](https://github.com/elsayed5454/JDBC)
## Supported SQL Statements
### 1. Create
- CREATE DATABASE databaseName
- CREATE TABLE tableName (Column1 datatype, Column2 datatype, ..)
### 2. Drop
- DROP DATABASE databaseName
- DROP TABLE tableName
### 3. Select
- SELECT column FROM tableName
- SELECT * FROM tableName WHERE column1 >= value1
### 4. Insert
- INSERT INTO table_name VALUES (value1, ...)
- INSERT INTO table_name (column1, column2) VALUES (value1, value2)
### 5. Update
- UPDATE tableName SET column1 = value1, ...
- UPDATE tableName SET column1 = value1, ... WHERE column1 = value1
### 6. Delete
- DELETE FROM tableName
- DELETE FROM tableName WHERE column1 = value1
## Supported Data Types
int, varchar
## Notes
UML diagram, detailed design and screenshots of simple CLI are in the report.
