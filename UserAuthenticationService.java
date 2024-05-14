package com.example.javafxx;

import java.sql.*;

public class UserAuthenticationService {
    // Database connection parameters
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/TravelIndia";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "sit@123";

    // Constructor
    public UserAuthenticationService() {
        // Initialize database connection
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Method to create a predefined user for simulation
    public static void createPredefinedUser() {
        String predefinedEmail = "khushi@gmail.com";
        String predefinedPassword = "abcde";
        String predefinedName = "Khushi Panchal";
        register(predefinedEmail, predefinedPassword, predefinedName);
    }

    // Method to check if a user with the given email exists in the database
    private static boolean userExists(String email) {
        String query = "SELECT * FROM users WHERE email = ?";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to register a new user
    public static boolean register(String email, String password, String name) {
        if (userExists(email)) {
            System.out.println("Email already registered. Please log in.\n");
            return false;
        }
        String query = "INSERT INTO users (email, password, name) VALUES (?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, name);
            int rowsInserted = preparedStatement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to authenticate a user
    public static boolean login(String email, String password) {
        String query = "SELECT * FROM users WHERE email = ? AND password = ?";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}