package com.example.javafxx;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;


public class LoginPage extends Application {

    private UserAuthenticationService authenticationService = new UserAuthenticationService();
    private BookingService bookingService = new BookingService();
    private SightseeingOptionsService sightseeingService = new SightseeingOptionsService(); // Assuming you have this service
    private DestinationService destinationService = new DestinationService();


    private Label messageLabel; // Now a class-level variable

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Travel India - User Authentication");
        GridPane grid = setupLoginGrid(primaryStage); // Pass primaryStage
        StackPane root = new StackPane(grid);
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private GridPane setupLoginGrid(Stage primaryStage) {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(40));
        grid.setVgap(20);
        grid.setHgap(20);
        Image backgroundImage = new Image("file:C:/Users/sunil/Downloads/AdobeStock_130860698_Preview.jpeg");
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(1.0, 1.0, true, true, false, false));
        grid.setBackground(new Background(background));

        Label headingLabel = new Label("Namaste!\nWelcome To Travel India!");
        headingLabel.setFont(Font.font("Arial", FontWeight.BOLD, 36));
        headingLabel.setTextFill(Color.DARKBLUE);
        grid.add(headingLabel, 0, 0, 2, 1);

        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        choiceBox.getItems().addAll("Sign In", "Register");
        choiceBox.setValue("Sign In");
        choiceBox.setStyle("-fx-background-color: #FFD700; -fx-text-fill: #800080;");
        grid.add(choiceBox, 0, 1);

        Label emailLabel = new Label("Email:");
        emailLabel.setTextFill(Color.BLACK);
        grid.add(emailLabel, 0, 2);

        TextField emailInput = new TextField();
        emailInput.setPromptText("Enter your email");
        grid.add(emailInput, 1, 2);

        Label passwordLabel = new Label("Password:");
        passwordLabel.setTextFill(Color.BLACK);
        grid.add(passwordLabel, 0, 3);

        PasswordField passwordInput = new PasswordField();
        passwordInput.setPromptText("Enter your password");
        grid.add(passwordInput, 1, 3);

        Button actionButton = new Button("Sign In");
        actionButton.setStyle("-fx-background-color: #ADD8E6; -fx-text-fill: #0000FF;");
        grid.add(actionButton, 1, 4);

        messageLabel = new Label();
        messageLabel.setTextFill(Color.RED);
        grid.add(messageLabel, 1, 5);

        actionButton.setOnAction(e -> {
            String choice = choiceBox.getValue();
            String email = emailInput.getText();
            String password = passwordInput.getText();
            if ("Sign In".equals(choice)) {
                handleLogin(email, password, primaryStage);
            } else {
                handleRegister(email, password, primaryStage);
            }
        });

        return grid;
    }

    private void handleLogin(String email, String password, Stage primaryStage) {
        if (authenticationService.login(email, password)) {
            messageLabel.setText("Login successful.");
            DestinationService.Destination destination = destinationService.searchDestination(email);
            if (destination != null) {
                openSightseeingOptions(primaryStage, destination.getDestinationID());
            }
            new DestinationSearchPage().start(new Stage());
            primaryStage.close();
        } else {
            messageLabel.setText("Login failed. Please check your credentials.");
        }
    }

    private void handleRegister(String email, String password, Stage primaryStage) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Registration");
        dialog.setHeaderText("Registration");
        dialog.setContentText("Please enter your name:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> {
            if (authenticationService.register(email, password, name)) {
                messageLabel.setText("Registration successful.");
                DestinationService.Destination destination = destinationService.searchDestination(email);
                if (destination != null) {
                    openSightseeingOptions(primaryStage, destination.getDestinationID());
                }
                new DestinationSearchPage().start(new Stage());
                primaryStage.close();
            } else {
                messageLabel.setText("Registration failed.");
            }
        });
    }

    private void openSightseeingOptions(Stage primaryStage, int destinationId) {
        List<String> options = sightseeingService.getSightseeingOptionsForDestination(destinationId);
        if (!options.isEmpty()) {
            Stage newStage = new Stage();
            VBox layout = new VBox(10);
            layout.setPadding(new Insets(20));
            options.forEach(option -> layout.getChildren().add(new CheckBox(option)));
            Scene scene = new Scene(layout, 300, 400);
            newStage.setTitle("Sightseeing Options");
            newStage.setScene(scene);
            newStage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "No sightseeing options available.");
            alert.showAndWait();
        }
    }

    public static void main(String[] args)
    {
        launch(args);
    }

}