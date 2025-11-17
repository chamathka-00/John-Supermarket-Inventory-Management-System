package com.example.johnsupermarket.controller;

import com.example.johnsupermarket.validator.InputValidator;
import com.example.johnsupermarket.loader.FileManager;
import com.example.johnsupermarket.model.Item;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.List;

public class UpdateItemController {
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

    private Item item;      // Reference to the item being updated
    private List<Item> items; // Reference to the list of all items

    // Called by parent controller to specify which item to update and pre-fill all fields
    public void setItem(Item item) {
        this.item = item;
        codeField.setText(item.getItemCode());
        nameField.setText(item.getItemName());
        brandField.setText(item.getItemBrand());
        priceField.setText(String.format("%.2f", item.getItemPrice()));
        quantityField.setText(String.valueOf(item.getQuantity()));
        categoryField.setText(item.getCategory());
        dateField.setText(item.getPurchasedDate().toString());
        thresholdField.setText(String.valueOf(item.getLowStockThreshold()));
        imageField.setText(item.getImageName() == null ? "" : item.getImageName());
    }

    // Sets the item list
    public void setItems(List<Item> items) {
        this.items = items;
    }

    @FXML
    public void initialize() {
        // Live validation listeners using InputValidator
        nameField.focusedProperty().addListener((obs, oldVal, newVal) -> { if (!newVal) validateName(); });
        brandField.focusedProperty().addListener((obs, oldVal, newVal) -> { if (!newVal) validateBrand(); });
        priceField.focusedProperty().addListener((obs, oldVal, newVal) -> { if (!newVal) validatePrice(); });
        quantityField.focusedProperty().addListener((obs, oldVal, newVal) -> { if (!newVal) validateQuantity(); });
        categoryField.focusedProperty().addListener((obs, oldVal, newVal) -> { if (!newVal) validateCategory(); });
        dateField.focusedProperty().addListener((obs, oldVal, newVal) -> { if (!newVal) validateDate(); });
        thresholdField.focusedProperty().addListener((obs, oldVal, newVal) -> { if (!newVal) validateThreshold(); });
    }

    // Input validation methods

    // No code validation needed since code is not editable

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

    // Validate price
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

    // Called when the 'Browse' button is clicked to choose an image file
    @FXML
    private void handleBrowseImage() {
        javafx.stage.FileChooser fileChooser = new javafx.stage.FileChooser();
        fileChooser.setTitle("Select Image");
        fileChooser.getExtensionFilters().addAll(
                new javafx.stage.FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.jpeg", "*.png", "*.gif")
        );
        java.io.File selectedFile = fileChooser.showOpenDialog(codeField.getScene().getWindow());
        if (selectedFile != null) {
            imageField.setText(selectedFile.getName());
            imageErrorLabel.setText("");
        }
    }

    // Called when the 'Clear' button next to image is clicked
    @FXML
    private void handleClearImage() {
        imageField.clear();
        imageErrorLabel.setText("");
    }

    // Called when the 'OK' button is clicked to validate, update the item, and save
    @FXML
    private void handleOk() {
        // Validates all fields. Only proceed if all are valid
        boolean valid = validateName() & validateBrand() &
                validatePrice() & validateQuantity() & validateCategory() &
                validateDate() & validateThreshold();
        if (!valid) return;

        // Update the item's properties from user input
        item.setItemName(nameField.getText().trim());
        item.setItemBrand(brandField.getText().trim());
        item.setItemPrice(Double.parseDouble(priceField.getText().trim()));
        item.setQuantity(Integer.parseInt(quantityField.getText().trim()));
        item.setCategory(categoryField.getText().trim());
        item.setPurchasedDate(LocalDate.parse(dateField.getText().trim()));
        item.setLowStockThreshold(Integer.parseInt(thresholdField.getText().trim()));
        String imageName = imageField.getText().trim();
        if (imageName.isEmpty()) imageName = "default.png";
        item.setImageName(imageName);

        // Save the updated items list to the file
        FileManager.saveItems(items, "items.txt");

        // Show success alert
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText("Item Updated successfully!");
        alert.showAndWait();

        // Close the Update Item dialog
        ((Stage) codeField.getScene().getWindow()).close();
    }

    // Called when the 'Cancel' button is clicked and it closes the dialog without saving
    @FXML
    private void handleCancel() {
        ((Stage) codeField.getScene().getWindow()).close();
    }
}
