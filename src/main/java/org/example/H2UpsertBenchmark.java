package org.example;

import org.h2.jdbcx.JdbcDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class H2UpsertBenchmark {
    private static JdbcDataSource dataSource;

    public static void main(String[] args) throws SQLException {
        // Initialize in-memory H2 database
        dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");

        // Establish connection
        try (Connection connection = dataSource.getConnection()) {
            // Create table
            try (Statement stmt = connection.createStatement()) {
                stmt.execute("CREATE TABLE IF NOT EXISTS users (ID INT PRIMARY KEY, Age INT)");
            }

            // Benchmark the upsert operation
            long startTime = System.nanoTime();
            benchmarkUpsert(connection);
            long endTime = System.nanoTime();
            System.out.println("Time taken for 1 million upserts: " + (endTime - startTime) / 1_000_000 + " ms");
        }
    }

    private static void benchmarkUpsert(Connection connection) throws SQLException {
        String sql = "MERGE INTO users AS target " +
                "USING (VALUES (?, ?)) AS source(ID, Age) " +
                "ON target.ID = source.ID " +
                "WHEN MATCHED THEN UPDATE SET Age = source.Age " +
                "WHEN NOT MATCHED THEN INSERT (ID, Age) VALUES (source.ID, source.Age)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            for (int i = 0; i < 1_000_000; i++) {
                stmt.setInt(1, i);       // ID
                stmt.setInt(2, 20 + (i % 50));  // Age (random between 20 and 70)
                stmt.addBatch();
            }
            stmt.executeBatch();
        }
    }
}