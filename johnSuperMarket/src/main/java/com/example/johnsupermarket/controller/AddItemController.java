package com.example.johnsupermarket.controller;

import com.example.johnsupermarket.validator.InputValidator;
import com.example.johnsupermarket.loader.FileManager;
import com.example.johnsupermarket.model.Item;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

public class AddItemController {
    // FXML Bindings to Input Fields
    @FXML private TextField codeField;
    @FXML private TextField nameField;
    @FXML private TextField brandField;
    @FXML private TextField priceField;
    @FXML private TextField quantityField;
    @FXML private TextField categoryField;
    @FXML private TextField dateField;
    @FXML private TextField thresholdField;
    @FXML private TextField imageField;

    // FXML Bindings to Error Labels for validation feedback
    @FXML private Label codeErrorLabel;
    @FXML private Label nameErrorLabel;
    @FXML private Label brandErrorLabel;
    @FXML private Label priceErrorLabel;
    @FXML private Label quantityErrorLabel;
    @FXML private Label categoryErrorLabel;
    @FXML private Label dateErrorLabel;
    @FXML private Label thresholdErrorLabel;
    @FXML private Label imageErrorLabel;

    private List<Item> items; // Reference to the current list of items

    // Sets the item list
    public void setItems(List<Item> items) {
        this.items = items;
    }

    @FXML
    public void initialize() {
        // Live validation listeners using InputValidator
        codeField.focusedProperty().addListener((obs, oldVal, newVal) -> { if (!newVal) validateCode(); });
        nameField.focusedProperty().addListener((obs, oldVal, newVal) -> { if (!newVal) validateName(); });
        brandField.focusedProperty().addListener((obs, oldVal, newVal) -> { if (!newVal) validateBrand(); });
        priceField.focusedProperty().addListener((obs, oldVal, newVal) -> { if (!newVal) validatePrice(); });
        quantityField.focusedProperty().addListener((obs, oldVal, newVal) -> { if (!newVal) validateQuantity(); });
        categoryField.focusedProperty().addListener((obs, oldVal, newVal) -> { if (!newVal) validateCategory(); });
        dateField.focusedProperty().addListener((obs, oldVal, newVal) -> { if (!newVal) validateDate(); });
        thresholdField.focusedProperty().addListener((obs, oldVal, newVal) -> { if (!newVal) validateThreshold(); });
    }

    // Input validation methods

    // Validates item code
    private boolean validateCode() {
        String error = InputValidator.validateCode(codeField.getText().trim(), items, true);
        codeErrorLabel.setText(error);
        return error.isEmpty();
    }

    // Validates name
    private boolean validateName() {
        String error = InputValidator.validateName(nameField.getText().trim());
        nameErrorLabel.setText(error);
        return error.isEmpty();
    }

    // Validates brand
    private boolean validateBrand() {
        String error = InputValidator.validateBrand(brandField.getText().trim());
        brandErrorLabel.setText(error);
        return error.isEmpty();
    }

    // Validates price
    private boolean validatePrice() {
        String error = InputValidator.validatePrice(priceField.getText().trim());
        priceErrorLabel.setText(error);
        return error.isEmpty();
    }

    // Validates quantity
    private boolean validateQuantity() {
        String error = InputValidator.validateQuantity(quantityField.getText().trim());
        quantityErrorLabel.setText(error);
        return error.isEmpty();
    }

    // Validates category
    private boolean validateCategory() {
        String error = InputValidator.validateCategory(categoryField.getText().trim());
        categoryErrorLabel.setText(error);
        return error.isEmpty();
    }

    // Validates date
    private boolean validateDate() {
        String error = InputValidator.validateDate(dateField.getText().trim());
        dateErrorLabel.setText(error);
        return error.isEmpty();
    }

    // Validates low stock threshold
    private boolean validateThreshold() {
        String error = InputValidator.validateThreshold(thresholdField.getText().trim());
        thresholdErrorLabel.setText(error);
        return error.isEmpty();
    }

    // Event handlers for buttons

    // Called when the 'Browse' button is clicked to select an image file
    @FXML
    private void handleBrowseImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.jpeg", "*.png", "*.gif")
        );
        File selectedFile = fileChooser.showOpenDialog(codeField.getScene().getWindow());
        if (selectedFile != null) {
            imageField.setText(selectedFile.getName());
            imageErrorLabel.setText("");
        }
    }

    // Called when the 'OK' button is clicked
    @FXML
    private void handleOk() {
        // Validate all fields and only continue if all are valid
        boolean valid = validateCode() & validateName() & validateBrand() &
                validatePrice() & validateQuantity() & validateCategory() &
                validateDate() & validateThreshold();
        if (!valid) return;

        // Gather all user input
        String code = codeField.getText().trim().toUpperCase();
        String name = nameField.getText().trim();
        String brand = brandField.getText().trim();
        double price = Double.parseDouble(priceField.getText().trim());
        int quantity = Integer.parseInt(quantityField.getText().trim());
        String category = categoryField.getText().trim();
        LocalDate date = LocalDate.parse(dateField.getText().trim());
        int threshold = Integer.parseInt(thresholdField.getText().trim());
        String imageName = imageField.getText().trim();
        if (imageName.isEmpty()) imageName = "default.png";

        // Create new Item object and add to the items list
        Item newItem = new Item(code, name, brand, price, quantity, category, date, threshold, imageName);
        items.add(newItem);
        FileManager.saveItems(items, "items.txt"); // Save the updated list

        // Show a success alert to the user
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText("Item added successfully!");
        alert.showAndWait();

        // Close the Add Item dialog
        ((Stage) codeField.getScene().getWindow()).close();
    }

    // Called when the 'Clear' button is clicked ant this clears all the fields and error messages
    @FXML
    private void handleClear() {
        codeField.clear(); codeErrorLabel.setText("");
        nameField.clear(); nameErrorLabel.setText("");
        brandField.clear(); brandErrorLabel.setText("");
        priceField.clear(); priceErrorLabel.setText("");
        quantityField.clear(); quantityErrorLabel.setText("");
        categoryField.clear(); categoryErrorLabel.setText("");
        dateField.clear(); dateErrorLabel.setText("");
        thresholdField.clear(); thresholdErrorLabel.setText("");
        imageField.clear(); imageErrorLabel.setText("");
    }

    // Called when the 'Cancel' button is clicked and it closes the dialog without saving
    @FXML
    private void handleCancel() {
        ((Stage) codeField.getScene().getWindow()).close();
    }
}
