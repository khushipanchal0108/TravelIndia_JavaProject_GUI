package com.example.javafxx;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookingService {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/TravelIndia";
    private static final String USER = "root";
    private static final String PASS = "sit@123";

    public void confirmBooking(AccommodationService.Accommodation accommodation, LocalDate checkIn, LocalDate checkOut, int numberOfPeople, int numberOfRooms, List<String> sightseeingOptions) {
        double totalAmount = numberOfRooms * accommodation.getPrice() * checkIn.until(checkOut).getDays();
        System.out.println("Booking confirmed for " + accommodation.getName() + " Total amount: Rs." + totalAmount);

        // Database operation
        String sql = "INSERT INTO user_bookings (user_id, accommodation_id, accommodation_name, check_in_date, check_out_date, number_of_people, number_of_rooms, total_amount, status, sightseeing_option) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, 1); // Assuming user ID is hardcoded for now
            pstmt.setInt(2, accommodation.getAccommodationId());
            pstmt.setString(3, accommodation.getName()); // Set hotel name
            pstmt.setDate(4, java.sql.Date.valueOf(checkIn));
            pstmt.setDate(5, java.sql.Date.valueOf(checkOut));
            pstmt.setInt(6, numberOfPeople);
            pstmt.setInt(7, numberOfRooms);
            pstmt.setDouble(8, totalAmount);
            pstmt.setString(9, "Confirmed");
            pstmt.setString(10, sightseeingOptions != null ? String.join(",", sightseeingOptions) : null); // Concatenate sightseeing options into a comma-separated string

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Booking saved to database successfully.");
                ResultSet generatedKeys = pstmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int bookingId = generatedKeys.getInt(1);
                    System.out.println("Generated booking ID: " + bookingId);
                }
            } else {
                System.out.println("Failed to save booking to database.");
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to fetch user bookings from the database
    // Method to fetch the most recent booking entry from the database
    public List<String> getRecentBookingFromDatabase() {
        List<String> recentBooking = new ArrayList<>();
        String sql = "SELECT * FROM user_bookings ORDER BY booking_id DESC LIMIT 1";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                int bookingId = rs.getInt("booking_id");
                int userId = rs.getInt("user_id");
                int accommodationId = rs.getInt("accommodation_id");
                String checkInDate = rs.getDate("check_in_date").toString();
                String checkOutDate = rs.getDate("check_out_date").toString();
                int numberOfPeople = rs.getInt("number_of_people");
                int numberOfRooms = rs.getInt("number_of_rooms");
                double totalAmount = rs.getDouble("total_amount");
                String status = rs.getString("status");
                String sightseeingOption = rs.getString("sightseeing_option");
                String accommodationName = rs.getString("accommodation_name");

                String bookingInfo = "Booking ID: " + bookingId +
                        ", User ID: " + userId +
                        ", Accommodation ID: " + accommodationId +
                        ", Check-in Date: " + checkInDate +
                        ", Check-out Date: " + checkOutDate +
                        ", Number of People: " + numberOfPeople +
                        ", Number of Rooms: " + numberOfRooms +
                        ", Total Amount: Rs." + totalAmount +
                        ", Status: " + status +
                        ", Sightseeing Option: " + sightseeingOption +
                        ", Accommodation Name: " + accommodationName;
                recentBooking.add(bookingInfo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return recentBooking;
    }

}
