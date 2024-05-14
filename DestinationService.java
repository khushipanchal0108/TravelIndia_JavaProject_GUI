package com.example.javafxx;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;
import java.util.Arrays;

public class DestinationService {

    // Connection details
    private static final String DB_URL = "jdbc:mysql://localhost:3306/TravelIndia";
    private static final String USER = "root";
    private static final String PASSWORD = "sit@123"; // Change this to your database password

    public DestinationService() {
        // Initialize database connection
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Destination searchDestination(String query) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            String sql = "SELECT * FROM destination WHERE name = ?";
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, query);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        int destinationID = resultSet.getInt("destinationID");
                        String name = resultSet.getString("name");
                        double estimatedCost = resultSet.getDouble("estimatedCost");
                        String attractions = resultSet.getString("attractions");
                        return new Destination(destinationID, name, attractions, estimatedCost);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Set<String> getAttractions(String destinationName) {
        Set<String> attractions = new HashSet<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            String sql = "SELECT attractions FROM destination WHERE name = ?";
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, destinationName);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        String attractionsString = resultSet.getString("attractions");
                        String[] attractionArray = attractionsString.split(", ");
                        attractions.addAll(Arrays.asList(attractionArray));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return attractions;
    }


    public void updateSelectedAttraction(int destinationID, String selectedAttractions) {
        String sql = "UPDATE destination SET attractions = CONCAT(attractions, ?) WHERE destinationID = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, "," + selectedAttractions);
            statement.setInt(2, destinationID);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void storeSelectedAttractions(int destinationID, String selectedAttractions) {
        String sql = "INSERT INTO selected_attractions (destinationID, attractions) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, destinationID);
            statement.setString(2, selectedAttractions);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static class Destination {
        private int destinationID;
        private String name;
        private String attractions;
        private double estimatedCost;

        public Destination(int destinationID, String name, String attractions, double estimatedCost) {
            this.destinationID = destinationID;
            this.name = name;
            this.attractions = attractions;
            this.estimatedCost = estimatedCost;
        }

        // Getters and setters
        public int getDestinationID() {
            return destinationID;
        }

        public String getName() {
            return name;
        }

        public String getAttractions() {
            return attractions;
        }

        public double getEstimatedCost() {
            return estimatedCost;
        }
    }
}