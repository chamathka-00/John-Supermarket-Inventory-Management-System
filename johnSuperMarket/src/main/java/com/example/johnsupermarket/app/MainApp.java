package com.example.johnsupermarket.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class MainApp extends Application {
    // Main method
    public static void main(String[] args) {
        launch(args); // Starts JavaFX runtime
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            // Load the main view layout from the FXML resource file
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/johnsupermarket/MainView.fxml"));

            // Set the title of the application window
            primaryStage.setTitle("John's Supermarket Inventory");

            // Set the main scene
            primaryStage.setScene(new Scene(root, 1000, 650));

            // Set up a custom close request handler for the window
            primaryStage.setOnCloseRequest(event -> {
                // Creating a confirmation dialog
                Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                        "Are you sure you want to quit?",
                        ButtonType.YES, ButtonType.NO);

                confirm.setTitle("Confirm Exit");
                confirm.setHeaderText(null);

                // Show the dialog and wait for user response
                confirm.showAndWait().ifPresent(response -> {
                    if (response != ButtonType.YES) {
                        event.consume(); // If the user choose NO, keep the app open
                    }
                });
            });

            // Sow the primary window to the user
            primaryStage.show();

        } catch (Exception e) {
            // If any error happens, print the error details
            e.printStackTrace();
        }
    }
}
