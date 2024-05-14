package com.example.javafxx;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;

public class FeedbackPage extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Feedback Page");

        // Creating a VBox layout
        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));
        vbox.setStyle("-fx-background-color: #f0f0f0;"); // Set background color

        // Adding heading label
        Label headingLabel = new Label("Feedback Submission");
        headingLabel.setFont(Font.font("Arial", 24));
        headingLabel.setTextFill(Color.DARKBLUE);
        vbox.getChildren().add(headingLabel);

        // Adding feedback choice label and choice box
        Label feedbackChoiceLabel = new Label("Would you like to submit feedback for your booking experience?");
        vbox.getChildren().add(feedbackChoiceLabel);

        ChoiceBox<String> feedbackChoiceBox = new ChoiceBox<>();
        feedbackChoiceBox.getItems().addAll("Yes", "No");
        feedbackChoiceBox.setValue("Yes");
        vbox.getChildren().add(feedbackChoiceBox);

        // Adding rating and comment controls inside an HBox
        HBox ratingAndCommentBox = new HBox(10);
        ratingAndCommentBox.setAlignment(Pos.CENTER);
        ratingAndCommentBox.setVisible(false);

        // Adding rating label and rating control
        Label ratingLabel = new Label("Rating:");
        ComboBox<Integer> ratingComboBox = new ComboBox<>();
        ratingComboBox.getItems().addAll(1, 2, 3, 4, 5);
        HBox ratingBox = new HBox(10, ratingLabel, ratingComboBox);
        ratingAndCommentBox.getChildren().add(ratingBox);

        // Adding comment label and text area
        Label commentLabel = new Label("Comments:");
        TextArea commentTextArea = new TextArea();
        commentTextArea.setPrefRowCount(5);
        VBox commentBox = new VBox(10, commentLabel, commentTextArea);
        ratingAndCommentBox.getChildren().add(commentBox);

        // Adding submit button for feedback choice
        Button submitFeedbackChoiceButton = new Button("OK");
        submitFeedbackChoiceButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;"); // Set button style
        submitFeedbackChoiceButton.setOnAction(event -> {
            String feedbackChoice = feedbackChoiceBox.getValue();
            if ("Yes".equals(feedbackChoice)) {
                ratingAndCommentBox.setVisible(true);
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Thank you!");
                alert.setHeaderText("Your feedback is appreciated.");
                alert.setContentText("Thank you for your time.");
                alert.showAndWait();
                primaryStage.close();
            }
        });
        vbox.getChildren().add(submitFeedbackChoiceButton);

        // Adding submit button for ratings and comments
        Button submitRatingAndCommentButton = new Button("Submit Feedback");
        submitRatingAndCommentButton.setStyle("-fx-background-color: #008CBA; -fx-text-fill: white;"); // Set button style
        submitRatingAndCommentButton.setOnAction(event -> {
            int rating = ratingComboBox.getValue();
            String comment = commentTextArea.getText();

            // Display a message or perform any other action as desired
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Feedback Submitted");
            alert.setHeaderText("Thank you for your feedback!");
            alert.setContentText("Your feedback has been submitted successfully.");
            alert.showAndWait();

            primaryStage.close();
        });
        ratingAndCommentBox.getChildren().add(submitRatingAndCommentButton);

        vbox.getChildren().add(ratingAndCommentBox);

        // Set scene
        Scene scene = new Scene(vbox, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
