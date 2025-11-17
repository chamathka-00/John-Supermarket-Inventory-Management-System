package com.example.johnsupermarket.controller;

import com.example.johnsupermarket.loader.FileManager;
import com.example.johnsupermarket.model.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;
import java.util.stream.Collectors;

public class MainController {
    // FXML Bindings to Table and Table Columns
    @FXML private TableView<Item> itemTable;
    @FXML private TableColumn<Item, String> codeColumn;
    @FXML private TableColumn<Item, String> nameColumn;
    @FXML private TableColumn<Item, String> brandColumn;
    @FXML private TableColumn<Item, Double> priceColumn;
    @FXML private TableColumn<Item, Integer> quantityColumn;
    @FXML private TableColumn<Item, String> categoryColumn;
    @FXML private TableColumn<Item, String> dateColumn;
    @FXML private TableColumn<Item, Integer> thresholdColumn;
    @FXML private TableColumn<Item, String> imageColumn;

    @FXML
    public void initialize() {
        // Bind each table column to the corresponding Item property
        codeColumn.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        brandColumn.setCellValueFactory(new PropertyValueFactory<>("itemBrand"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("itemPrice"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("purchasedDate"));
        thresholdColumn.setCellValueFactory(new PropertyValueFactory<>("lowStockThreshold"));
        imageColumn.setCellValueFactory(new PropertyValueFactory<>("imageName"));
        imageColumn.setPrefWidth(90); // Sets column width for images

        // Responsive column widths for all columns
        double total = 1 + 1.5 + 1 + 1 + 1 + 1 + 1.3 + 0.8 + 1.2;
        // Make columns auto-resize to always fill the table width
        codeColumn.prefWidthProperty().bind(itemTable.widthProperty().multiply(1.0 / total));
        nameColumn.prefWidthProperty().bind(itemTable.widthProperty().multiply(1.5 / total));
        brandColumn.prefWidthProperty().bind(itemTable.widthProperty().multiply(1.0 / total));
        priceColumn.prefWidthProperty().bind(itemTable.widthProperty().multiply(1.0 / total));
        quantityColumn.prefWidthProperty().bind(itemTable.widthProperty().multiply(1.0 / total));
        categoryColumn.prefWidthProperty().bind(itemTable.widthProperty().multiply(1.0 / total));
        dateColumn.prefWidthProperty().bind(itemTable.widthProperty().multiply(1.3 / total));
        thresholdColumn.prefWidthProperty().bind(itemTable.widthProperty().multiply(0.8 / total));
        imageColumn.prefWidthProperty().bind(itemTable.widthProperty().multiply(1.2 / total));
        // Set the TableView resize policy
        itemTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Setting up the image column to display images
        imageColumn.setCellFactory(tc -> new TableCell<Item, String>() {
            private final ImageView imageView = new ImageView();

            {
                imageView.setFitWidth(80);
                imageView.setFitHeight(80);
                imageView.setPreserveRatio(false);
                imageView.setSmooth(true);
                imageView.setCache(true);
            }

            @Override
            protected void updateItem(String imageName, boolean empty) {
                super.updateItem(imageName, empty);
                Item item = getTableRow() == null ? null : getTableRow().getItem();
                if (empty || item == null || item.isSeparator() || imageName == null || imageName.isEmpty()) {
                    setGraphic(null); // No image for separator rows
                } else {
                    Image image = null;
                    try {
                        image = new Image("file:images/" + imageName, 80, 80, false, true, true);
                        if (image.isError()) throw new Exception();
                    } catch (Exception e) {
                        image = new Image("file:images/default.png", 80, 80, false, true, true);
                    }
                    imageView.setImage(image);
                    setGraphic(imageView);
                }
                setText(null); // No text in image cell
            }
        });

        // Format category names
        categoryColumn.setCellFactory(tc -> new TableCell<Item, String>() {
            @Override
            protected void updateItem(String value, boolean empty) {
                super.updateItem(value, empty);
                if (empty || value == null || value.isEmpty()) {
                    setText(null);
                } else if (value.length() == 1) {
                    setText(value.toUpperCase());
                } else {
                    setText(value.substring(0, 1).toUpperCase() + value.substring(1).toLowerCase());
                }
            }
        });

        // Format price as two decimal places
        priceColumn.setCellFactory(tc -> new TableCell<Item, Double>() {
            @Override
            protected void updateItem(Double price, boolean empty) {
                super.updateItem(price, empty);
                if (empty || price == null) {
                    setText(null);
                } else {
                    setText(String.format("%.2f", price));
                }
            }
        });


        // Load all items from file, filter to show only low stock items, and display in the table
        List<Item> allItems = FileManager.readItems("items.txt");
        List<Item> lowStockItems = allItems.stream()
                .filter(Item::isLowStock)
                .collect(Collectors.toList());

        ObservableList<Item> observableList = FXCollections.observableArrayList(lowStockItems);
        itemTable.setItems(observableList);
    }

    // Reference to the 'Add Item' button
    @FXML private Button addItemButton;

    // Handler for the 'Add Item' button
    @FXML
    private void handleAddItem() {
        try {
            // Load the Add Item dialog layout
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/johnsupermarket/AddItemDialog.fxml"));
            Parent root = loader.load();

            // Pass the items list to the AddItemController
            AddItemController addItemController = loader.getController();
            List<Item> items = FileManager.readItems("items.txt");
            addItemController.setItems(items);

            // Create and show the Add Item dialog
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Add Item");
            dialogStage.setScene(new Scene(root));
            dialogStage.initOwner(addItemButton.getScene().getWindow());
            dialogStage.showAndWait();

            // After closing, reload and update the low stock items in the main table
            List<Item> updatedItems = FileManager.readItems("items.txt");
            List<Item> lowStockItems = updatedItems.stream().filter(Item::isLowStock).collect(Collectors.toList());
            itemTable.setItems(FXCollections.observableArrayList(lowStockItems));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Handler for the 'Update Item' button
    @FXML
    private void handleUpdateItem() {
        // Show a dialog to ask for the item code
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Update Item");
        dialog.setHeaderText("Update Item By Code");
        dialog.setContentText("Enter Item Code:");

        dialog.showAndWait().ifPresent(code -> {
            if (code == null || code.trim().isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Please enter a code.");
                return;
            }
            // Find the item by code
            List<Item> itemList = FileManager.readItems("items.txt");
            Item itemToUpdate = null;
            for (Item it : itemList) {
                if (it.getItemCode().equalsIgnoreCase(code.trim())) {
                    itemToUpdate = it;
                    break;
                }
            }
            if (itemToUpdate == null) {
                showAlert(Alert.AlertType.ERROR, "Item not found with code: " + code);
                return;
            }

            try {
                // Load the Update Item dialog layout
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/johnsupermarket/UpdateItemDialog.fxml"));
                Parent root = loader.load();

                // Pass the item and items list to the UpdateItemController
                UpdateItemController updateItemController = loader.getController();
                updateItemController.setItem(itemToUpdate);
                updateItemController.setItems(itemList);

                // Create and show the Update Item dialog
                Stage dialogStage = new Stage();
                dialogStage.setTitle("Update Item");
                dialogStage.setScene(new Scene(root));
                dialogStage.initOwner(addItemButton.getScene().getWindow());
                dialogStage.showAndWait();

                // After update, reload and update the main table
                List<Item> refreshed = FileManager.readItems("items.txt");
                List<Item> lowStockItems = refreshed.stream().filter(Item::isLowStock).toList();
                itemTable.setItems(FXCollections.observableArrayList(lowStockItems));

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    // Helper method to show an alert dialog with a given message
    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Handler for the 'View Items' button
    @FXML
    private void handleViewItems() {
        try {
            // Load and show the View Items dialog
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/johnsupermarket/ViewItemDialog.fxml"));
            Parent root = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("View Items by Category");
            dialogStage.setScene(new Scene(root, 1000, 650));
            dialogStage.initOwner(itemTable.getScene().getWindow());
            dialogStage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Handler for the 'Delete Item' button
    @FXML
    private void handleDeleteItem() {
        // If a row is selected in the table, prompt for confirmation and delete
        Item selected = itemTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                    "Are you sure you want to delete the item:\n" +
                            selected.getItemName() + " (Code: " + selected.getItemCode() + ")?",
                    ButtonType.YES, ButtonType.NO);
            confirm.setTitle("Confirm Deletion");
            confirm.setHeaderText(null);
            confirm.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES) {
                    List<Item> items = FileManager.readItems("items.txt");
                    boolean removed = items.removeIf(it -> it.getItemCode().equals(selected.getItemCode()));
                    if (removed) {
                        FileManager.saveItems(items, "items.txt");
                        showAlert(Alert.AlertType.INFORMATION, "Item deleted successfully!");
                    } else {
                        showAlert(Alert.AlertType.ERROR, "Failed to delete item. Item may have already been deleted.");
                    }
                    refreshTable();
                }
            });
            return;
        }

        // If nothing is selected, prompt the user to enter an item code
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Delete Item");
        dialog.setHeaderText("Delete Item By Code");
        dialog.setContentText("Enter Item Code to Delete:");

        dialog.showAndWait().ifPresent(code -> {
            if (code == null || code.trim().isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Please enter an item code.");
                return;
            }
            List<Item> items = FileManager.readItems("items.txt");
            Item toDelete = null;
            for (Item it : items) {
                if (it.getItemCode().equalsIgnoreCase(code.trim())) {
                    toDelete = it;
                    break;
                }
            }
            if (toDelete == null) {
                showAlert(Alert.AlertType.ERROR, "No item found with code: " + code);
                return;
            }
            final Item itemToRemove = toDelete;
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                    "Are you sure you want to delete the item:\n" +
                            itemToRemove.getItemName() + " (Code: " + itemToRemove.getItemCode() + ")?",
                    ButtonType.YES, ButtonType.NO);
            confirm.setTitle("Confirm Deletion");
            confirm.setHeaderText(null);
            confirm.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES) {
                    items.remove(itemToRemove);
                    FileManager.saveItems(items, "items.txt");
                    showAlert(Alert.AlertType.INFORMATION, "Item deleted successfully!");
                    refreshTable();
                }
            });
        });
    }

    // Helper method to reload the item table with updated data from file
    private void refreshTable() {
        List<Item> refreshed = FileManager.readItems("items.txt");
        List<Item> lowStockItems = refreshed.stream().filter(Item::isLowStock).toList();
        itemTable.setItems(FXCollections.observableArrayList(lowStockItems));
    }

    // Handler for the 'Refresh' button
    @FXML
    private void handleRefresh() {
        refreshTable();
    }

    // Handler for the 'Draw Random Dealers' button
    @FXML
    private void handleDrawDealers() {
        try {
            // Load and show the Radom Dealer dialog
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/johnsupermarket/RandomDealerDialog.fxml"));
            Parent root = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Random Dealer Draw");
            dialogStage.setScene(new Scene(root, 640, 500));
            dialogStage.initOwner(itemTable.getScene().getWindow());
            dialogStage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

