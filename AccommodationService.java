package com.example.javafxx;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AccommodationService {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/TravelIndia";
    private static final String USER = "root";
    private static final String PASS = "sit@123";

    // Method to fetch accommodations from the database
    public List<Accommodation> getAccommodationsForDestination(int destinationId) {
        List<Accommodation> accommodations = new ArrayList<>();
        String sql = "SELECT accommodation_id, name, price, type FROM accommodations WHERE destination_id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, destinationId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int accommodationId = rs.getInt("accommodation_id");
                    String name = rs.getString("name");
                    double price = rs.getDouble("price");
                    String type = rs.getString("type");
                    accommodations.add(new Accommodation(accommodationId, name, price, type,destinationId));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error accessing the database: " + e.getMessage());
            e.printStackTrace();
        }
        return accommodations;
    }


    public class Accommodation {
        private int accommodationId;
        private String name;
        private double price;
        private String type;
        private int destinationId; // Field to store the destination ID

        // Constructor
        public Accommodation(int accommodationId, String name, double price, String type, int destinationId) {
            this.accommodationId = accommodationId;
            this.name = name;
            this.price = price;
            this.type = type;
            this.destinationId = destinationId; // Initialize the destination ID
        }

        // Getters
        public int getAccommodationId() {
            return accommodationId;
        }

        public String getName() {
            return name;
        }

        public double getPrice() {
            return price;
        }

        public String getType() {
            return type;
        }

        public int getDestinationId() { // Getter for the destination ID
            return destinationId;
        }
    }

}
