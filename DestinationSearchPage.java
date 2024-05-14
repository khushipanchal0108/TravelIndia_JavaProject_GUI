package com.example.javafxx;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.List;

public class DestinationSearchPage extends Application {
    private DestinationService destinationService = new DestinationService();
    private AccommodationService accommodationService = new AccommodationService();
    private BookingService bookingService = new BookingService();
    private SightseeingOptionsService sightseeingService = new SightseeingOptionsService(); // Ensure this is initialized

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Destination Search");

        VBox vbox = new VBox(20);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(40));

        Label headingLabel = new Label("Search for Your Destination");
        headingLabel.setFont(new javafx.scene.text.Font("Arial", 24));
        vbox.getChildren().add(headingLabel);

        TextField searchField = new TextField();
        searchField.setPromptText("Enter your destination");
        vbox.getChildren().add(searchField);

        Button searchButton = new Button("Search");
        vbox.getChildren().add(searchButton);

        Label messageLabel = new Label();
        vbox.getChildren().add(messageLabel);

        Scene scene = new Scene(vbox, 400, 200);
        primaryStage.setScene(scene);
        primaryStage.show();

        searchButton.setOnAction(e -> {
            String query = searchField.getText().trim();
            DestinationService.Destination destination = destinationService.searchDestination(query);
            if (destination != null) {
                displayAttractions(primaryStage, destination);
            } else {
                messageLabel.setText("Destination not found. Please try again.");
            }
        });
    }

    private void displayAttractions(Stage primaryStage, DestinationService.Destination destination) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Attraction Options");
        alert.setHeaderText("Available Attractions for " + destination.getName());

        VBox alertContent = new VBox(10);
        alertContent.setAlignment(Pos.CENTER);
        for (String attraction : destination.getAttractions().split(", ")) {
            CheckBox checkBox = new CheckBox(attraction);
            alertContent.getChildren().add(checkBox);
        }

        Button continueButton = new Button("Continue");
        continueButton.setOnAction(e -> {
            alert.close();
            showAccommodations(primaryStage, destination.getDestinationID());
        });

        alertContent.getChildren().add(continueButton);
        alert.getDialogPane().setContent(alertContent);
        alert.showAndWait();
    }

    private void showAccommodations(Stage primaryStage, int destinationId) {
        List<AccommodationService.Accommodation> accommodations = accommodationService.getAccommodationsForDestination(destinationId);
        Stage stage = new Stage();
        stage.setTitle("Available Accommodations");

        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));

        for (AccommodationService.Accommodation accommodation : accommodations) {
            Label nameLabel = new Label(accommodation.getName() + " - " + accommodation.getType());
            nameLabel.setFont(new javafx.scene.text.Font("Arial", 14));
            Label priceLabel = new Label("Rs. " + accommodation.getPrice() + " per night");
            priceLabel.setFont(new javafx.scene.text.Font("Arial", 12));

            Button bookButton = new Button("Book");
            bookButton.getStyleClass().add("green-button");
            bookButton.setOnAction(e -> showBookingDetails(accommodation, primaryStage));

            VBox infoBox = new VBox(5);
            infoBox.getChildren().addAll(nameLabel, priceLabel, bookButton);
            vbox.getChildren().add(infoBox);
        }

        ScrollPane scrollPane = new ScrollPane(vbox);
        scrollPane.setFitToWidth(true);
        Scene newScene = new Scene(scrollPane, 400, 600);
        stage.setScene(newScene);
        stage.show();
    }

    private void showBookingDetails(AccommodationService.Accommodation accommodation, Stage parentStage) {
        Stage stage = new Stage();
        stage.setTitle("Booking Details");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(20));
        grid.setHgap(10);
        grid.setVgap(10);

        TextField checkInField = new TextField();
        checkInField.setPromptText("YYYY-MM-DD");
        grid.add(new Label("Check-in Date:"), 0, 0);
        grid.add(checkInField, 1, 0);

        TextField checkOutField = new TextField();
        checkOutField.setPromptText("YYYY-MM-DD");
        grid.add(new Label("Check-out Date:"), 0, 1);
        grid.add(checkOutField, 1, 1);

        TextField peopleField = new TextField();
        grid.add(new Label("Number of People:"), 0, 2);
        grid.add(peopleField, 1, 2);

        TextField roomsField = new TextField();
        grid.add(new Label("Number of Rooms:"), 0, 3);
        grid.add(roomsField, 1, 3);

        Button confirmButton = new Button("Confirm Booking");
        confirmButton.setOnAction(e -> {
            LocalDate checkIn = LocalDate.parse(checkInField.getText());
            LocalDate checkOut = LocalDate.parse(checkOutField.getText());
            int people = Integer.parseInt(peopleField.getText());
            int rooms = Integer.parseInt(roomsField.getText());
            List<String> sightseeingOptions = sightseeingService.getSightseeingOptionsForDestination(accommodation.getDestinationId());
            bookingService.confirmBooking(accommodation, checkIn, checkOut, people, rooms, sightseeingOptions);
            stage.close();  // Close the window after booking
            showSightseeingOptions(checkIn, checkOut, accommodation.getDestinationId(), parentStage); // Pass destination ID to the sightseeing options
        });
        grid.add(confirmButton, 1, 4);

        Scene scene = new Scene(grid);
        stage.setScene(scene);
        stage.initOwner(parentStage);  // Set the owner of this stage to block interaction with parent
        stage.showAndWait();  // Show and wait blocks other interactions until this window is closed
    }


    private void showSightseeingOptions(LocalDate checkIn, LocalDate checkOut, int destinationId, Stage parentStage) {
        long days = checkIn.until(checkOut).getDays() + 1; // Include both check-in and check-out days
        List<String> options = sightseeingService.getSightseeingOptionsForDestination(destinationId);

        Stage stage = new Stage();
        stage.setTitle("Sightseeing Options");
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true); // Ensures that the scroll pane uses the width of the VBox
        scrollPane.setContent(layout);

        for (int i = 0; i < days; i++) {
            LocalDate date = checkIn.plusDays(i);
            Label dateLabel = new Label("Day " + (i + 1) + ": " + date.toString());
            layout.getChildren().add(dateLabel);

            options.forEach(option -> {
                CheckBox checkBox = new CheckBox(option);
                layout.getChildren().add(checkBox);
            });
        }

        Button continueToTransportButton = new Button("Continue to Transport Booking");
        continueToTransportButton.setOnAction(e -> {
            stage.close(); // Close the sightseeing window
            showTransportOptions(parentStage); // Open the transport booking window
        });

        layout.getChildren().add(continueToTransportButton);

        Scene scene = new Scene(scrollPane, 400, 600);
        stage.setScene(scene);
        stage.initOwner(parentStage);
        stage.show();
    }

    private void showTransportOptions(Stage parentStage) {
        Stage stage = new Stage();
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));

        // Transport for reaching the destination
        Label reachDestinationLabel = new Label("Select Mode of Transport to Destination:");
        ComboBox<String> transportModes = new ComboBox<>();
        transportModes.getItems().addAll("Bus", "Train", "Flight");
        transportModes.setValue("Bus"); // Set a default value

        // Transport within the destination
        Label withinDestinationLabel = new Label("Select Mode of Transport Within Destination:");
        ComboBox<String> transportWithinModes = new ComboBox<>();
        transportWithinModes.getItems().addAll("Car", "Private Vehicle", "Public Transport");
        transportWithinModes.setValue("Car"); // Set a default value

        Button bookButton = new Button("Book Transport");
        Label bookingConfirmation = new Label();

        bookButton.setOnAction(e -> {
            bookingConfirmation.setText("Fetching recent booking details...");
            List<String> recentBooking = bookingService.getRecentBookingFromDatabase();
            if (!recentBooking.isEmpty()) {
                showRecentBookingDetails(stage, recentBooking);
            } else {
                bookingConfirmation.setText("No recent booking found.");
            }
        });

        layout.getChildren().addAll(
                reachDestinationLabel, transportModes,
                withinDestinationLabel, transportWithinModes,
                bookButton,
                bookingConfirmation // Add the booking confirmation label to the layout
        );

        stage.setScene(new Scene(layout, 350, 300)); // Adjusted for better layout spacing
        stage.setTitle("Transport Booking");
        stage.show();
    }

    private void showRecentBookingDetails(Stage parentStage, List<String> recentBooking) {
        Stage stage = new Stage();
        stage.setTitle("Recent Booking Details");

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));

        // Adding booking details to the VBox as labels
        for (String bookingDetail : recentBooking) {
            String[] details = bookingDetail.split(", "); // Splitting the booking details
            for (String detail : details) {
                String[] keyValue = detail.split(": "); // Splitting each detail into key-value pair
                Label label = new Label(keyValue[0] + ": " + keyValue[1]); // Creating label
                vbox.getChildren().add(label); // Adding label to the VBox
            }
            Separator separator = new Separator(Orientation.HORIZONTAL); // Adding horizontal separator between bookings
            vbox.getChildren().add(separator);
        }

        // Create a button to proceed to the feedback page
        Button feedbackButton = new Button("Provide Feedback");
        feedbackButton.setOnAction(e -> {
            stage.close(); // Close the recent booking details window
            openFeedbackPage(parentStage); // Open the feedback page
        });
        vbox.getChildren().add(feedbackButton); // Add the feedback button to the VBox

        Scene scene = new Scene(vbox, 400, 600);
        stage.setScene(scene);
        stage.initOwner(parentStage);
        stage.show();
    }

    // Method to open the FeedbackPage UI
    private void openFeedbackPage(Stage parentStage) {
        FeedbackPage feedbackPage = new FeedbackPage();
        feedbackPage.start(new Stage()); // Open the FeedbackPage UI in a new stage
        parentStage.close(); // Close the parent stage (Recent Booking Details window)
    }


    public static void main(String[] args) {
        launch(args);
    }
}