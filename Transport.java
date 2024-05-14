package com.example.javafxx;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Transport extends Application {
    private SightseeingOptionsService service = new SightseeingOptionsService();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Transport and Sightseeing Booking");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(20));
        grid.setVgap(10);
        grid.setHgap(10);

        Label heading = new Label("Book Transport");
        heading.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        grid.add(heading, 0, 0, 2, 1);

        // Transport to the destination
        Label toDestinationLabel = new Label("To Destination:");
        ComboBox<String> toDestinationComboBox = new ComboBox<>();
        toDestinationComboBox.getItems().addAll("Bus", "Train", "Flight");
        toDestinationComboBox.setValue("Bus"); // default value

        // Transport within the destination
        Label withinDestinationLabel = new Label("Within Destination:");
        ComboBox<String> withinDestinationComboBox = new ComboBox<>();
        withinDestinationComboBox.getItems().addAll("Car", "Private Vehicle", "Public Transport");
        withinDestinationComboBox.setValue("Car"); // default value

        Button bookButton = new Button("Book Transport");
        grid.add(bookButton, 1, 3);

        Label bookingStatus = new Label();
        grid.add(bookingStatus, 0, 4, 2, 1);  // Positioned under the button for visual clarity

        bookButton.setOnAction(e -> {
            String toDestination = toDestinationComboBox.getValue();
            String withinDestination = withinDestinationComboBox.getValue();
            if (toDestination != null && withinDestination != null) {
                // Assuming destination ID and details are fetched from the context
                service.bookOption("Transport", toDestination, 1, "Details about reaching the destination");
                service.bookOption("Transport", withinDestination, 1, "Details about transport within the destination");
                bookingStatus.setText(toDestination + " transport confirmed to reach the destination and " + withinDestination + " to travel within the destination.");
            } else {
                bookingStatus.setText("Please select options for both fields.");
            }
        });

        Scene scene = new Scene(grid, 400, 250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}