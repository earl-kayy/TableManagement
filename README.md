# Table and Database Management System

## Overview
This project is a Java-based system that allows for managing tables and columns from CSV files. The system supports operations such as sorting, selecting specific rows or columns, joining tables, and retrieving specific data based on conditions. 

The main components include:
- **Table creation from CSV files**
- **Column-based operations**
- **Sorting and filtering data**
- **Join operations (cross join, inner join, outer join, full outer join)**
- **Custom predicate-based row selection**

## Features
- **CSV Table Creation**: Creates tables from CSV files, where the first row is treated as the column names and the file name as the table name.
- **Sorting**: Sorts tables by specific column indices, with options for handling `null` values and choosing ascending or descending order.
- **Column and Row Selection**: Allows selection of specific rows or columns based on indices or ranges.
- **Join Operations**: Supports various types of join operations, such as cross join, inner join, and outer join between tables.
- **Predicate-based Selection**: Supports selecting rows based on custom conditions defined using Java predicates.

## Running Tests
To run the test cases, execute the `Test.java` file located in the `test` directory. 
