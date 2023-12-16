package com.tms.model;

import java.sql.*;

public class DatabaseConnection {
    private static Connection connection;

    private DatabaseConnection() {
        // private constructor to prevent instantiation
    }

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            // Initialize the connection if it is not available or closed
            initializeConnection();
        }
        return connection;
    }

    private static void initializeConnection() throws SQLException {
        // Load the JDBC driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
//          System.out.println("-------------------class found");
        } catch (ClassNotFoundException e) {
            e.printStackTrace(); // Handle exceptions appropriately
            throw new SQLException("JDBC driver not found.");
        }

        // Database connection properties
        String url = "jdbc:mysql://localhost:3306/";
        String username = "root";
        String password = "";
        String databaseName = "task";

        // Establish a connection
        try (Connection tempConnection = DriverManager.getConnection(url, username, password)) {
//            System.out.println("-------------------connected to the database");

            // Check if the database exists
            if (!databaseExists(tempConnection, databaseName)) {
                // If the database doesn't exist, create it
                createDatabase(tempConnection, databaseName);
            }

            // Connect to the specified database
            url = "jdbc:mysql://localhost:3306/" + databaseName;
            connection = DriverManager.getConnection(url, username, password);

            // Create "user" and "task" tables if they don't exist
            createUserTable();
            createTaskTable();

        } catch (SQLException e) {
            // System.out.println("-------------------couldn't create the database");

            e.printStackTrace(); // Handle exceptions appropriately
            throw new SQLException("Failed to initialize the database connection.");
        }
    }

    private static void createUserTable() throws SQLException {
        String createUserTableSQL = "CREATE TABLE IF NOT EXISTS user (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "name VARCHAR(255) NOT NULL," +
                "password VARCHAR(255) NOT NULL)";
        executeUpdate(createUserTableSQL);
    }

    private static void createTaskTable() throws SQLException {
        String createTaskTableSQL = "CREATE TABLE IF NOT EXISTS task (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "title VARCHAR(255) NOT NULL," +
                "description TEXT," +
                "due_date DATE," +
                "priority ENUM('High', 'Medium', 'Low')," +
                "completed BOOLEAN)";
        executeUpdate(createTaskTableSQL);
    }

    private static void executeUpdate(String sql) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
        }
    }

    private static boolean databaseExists(Connection connection, String databaseName) throws SQLException {
        String query = "SELECT SCHEMA_NAME FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, databaseName);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    private static void createDatabase(Connection connection, String databaseName) throws SQLException {
        String query = "CREATE DATABASE " + databaseName;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        }
    }
}
