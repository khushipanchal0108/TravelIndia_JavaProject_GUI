package com.example.javafxx;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Feedback {
    private int feedbackID;
    private int userID;
    private int rating;
    private String destination;
    private int destinationID;
    private String comment;
    private String transport_mode;

    public Feedback(int feedbackID, int userID, int rating, String destination, int destinationID, String comment, String transport_mode) {
        this.feedbackID = feedbackID;
        this.userID = userID;
        this.rating = rating;
        this.destination = destination;
        this.destinationID = destinationID;
        this.comment = comment;
        this.transport_mode = transport_mode;
    }

    public static void submitFeedback(Feedback feedback) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaDB", "root", "sit@123");
            PreparedStatement statement = connection.prepareStatement("INSERT INTO feedback (userID, rating, destination, destinationID, comment, transport_mode) VALUES (?, ?, ?, ?, ?, ?)");
            statement.setInt(1, feedback.getUserID());
            statement.setInt(2, feedback.getRating());
            statement.setString(3, feedback.getDestination());
            statement.setInt(4, feedback.getDestinationID());
            statement.setString(5, feedback.getComment());
            statement.setString(6, feedback.getTransport_mode());
            statement.executeUpdate();
            statement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public int getFeedbackID() {
        return feedbackID;
    }

    public void setFeedbackID(int feedbackID) {
        this.feedbackID = feedbackID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getDestinationID() {
        return destinationID;
    }

    public void setDestinationID(int destinationID) {
        this.destinationID = destinationID;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTransport_mode() {
        return transport_mode;
    }

    public void setTransport_mode(String transport_mode) {
        this.transport_mode = transport_mode;
    }
}
