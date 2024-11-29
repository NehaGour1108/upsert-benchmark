Flickr's ID generation strategy is based on creating **unique identifiers** that are both **globally unique** and **time-ordered**. The method uses a **64-bit** integer, which allows for a huge number of unique IDs that can be generated without collision.

<img width="703" alt="Screenshot 2024-11-29 at 11 56 34 PM" src="https://github.com/user-attachments/assets/3d054631-574a-4547-9301-3763be54df9b">

# H2 Upsert Benchmark

## Overview

This project benchmarks the performance of **upsert** operations (insert or update) using **H2 in-memory database**. An **upsert** is a database operation that inserts a record if it doesn’t exist, or updates the record if it does. The project simulates performing **1 million upserts** on a table, measures the time taken for these operations, and prints the result.

The **H2 in-memory database** is used for this benchmark to avoid disk IO overhead and focus solely on the database's performance in handling upsert operations.

## Features

- **H2 In-Memory Database**: The database runs in memory, providing faster data access without writing to disk.
- **Upsert (MERGE)**: Efficiently inserts or updates records using the SQL **MERGE** statement.
- **Batch Processing**: Inserts/updates are batched into a single operation to minimize execution overhead.
- **Benchmarking**: Measures and reports the time taken to perform **1 million upsert operations**.

## Technologies Used

- **H2 In-Memory Database**: A lightweight relational database that stores data in memory.
- **Java JDBC**: Java Database Connectivity to interact with the H2 database.
- **SQL MERGE Statement**: Handles both insert and update in a single operation.
- **Batch Processing**: Groups multiple SQL operations into a batch for more efficient execution.

## Setup Instructions

### Prerequisites

- **Java**: You need to have Java 8 or higher installed.
- **Maven**: Maven is used to manage the project dependencies.

### 1. Clone the repository

```bash
git clone https://github.com/your-username/h2-upsert-benchmark.git
cd h2-upsert-benchmark
```

### 2. Compile and Run the Project

To build and run the project, execute the following command:

```bash
mvn clean install
mvn exec:java
```

This will start the program, connect to an H2 in-memory database, and execute the benchmarking logic.

## Code Explanation

1. **H2 In-Memory Database**:
   - A connection is established to an in-memory H2 database using JDBC.
   - The connection URL is `jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1`, which keeps the database alive while the JVM is running.

2. **Table Creation**:
   - The `users` table is created with two columns: `ID` (the primary key) and `Age`.

3. **Upsert Operation**:
   - The program uses the SQL **MERGE** statement to either insert or update a record in the `users` table.
   - It batches 1 million upsert operations into a single transaction to minimize execution overhead.

4. **Benchmarking**:
   - The time taken to perform the 1 million upserts is measured in nanoseconds and then converted to milliseconds.
   - The final result is printed to the console.

## Expected Output

After running the program, you should see output similar to the following:

```
Time taken for 1 million upserts: 500 ms
```

This shows the total time it took to perform **1 million upserts** in the H2 in-memory database.

## Use Cases

- **Performance Benchmarking**: This project provides a benchmark for upsert operations, helping developers evaluate the speed of this operation in an in-memory database.
- **Learning SQL MERGE**: The project demonstrates how to use the **MERGE** statement in SQL for efficiently inserting or updating records.
- **In-Memory Database Usage**: The project shows how to use an in-memory database for fast, temporary data storage and processing.
