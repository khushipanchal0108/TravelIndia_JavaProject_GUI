package com.example.javafxx;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SightseeingOptionsService {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/TravelIndia";
    private static final String USER = "root";
    private static final String PASS = "sit@123";

    public List<String> getSightseeingOptionsForDestination(int destinationId) {
        List<String> options = new ArrayList<>();
        String sql = "SELECT option_name FROM sightseeing_options WHERE destination_id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, destinationId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    options.add(rs.getString("option_name"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error accessing the database: " + e.getMessage());
            e.printStackTrace();
        }
        return options;
    }

    // Additional method to book transport or sightseeing based on the user's choice
    public void bookOption(String type, String option, int destinationId, String details) {
        String sql = "INSERT INTO bookings (type, option, destination_id, details) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, type);
            pstmt.setString(2, option);
            pstmt.setInt(3, destinationId);
            pstmt.setString(4, details);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println(type + " " + option + " booked successfully!");
            } else {
                System.out.println("Failed to book " + type + " " + option);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

