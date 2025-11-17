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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ViewItemController {
    // FXML Bindings to TableView and columns
    @FXML private TableView<Item> viewItemTable;
    @FXML private TableColumn<Item, String> codeColumn;
    @FXML private TableColumn<Item, String> nameColumn;
    @FXML private TableColumn<Item, String> brandColumn;
    @FXML private TableColumn<Item, Double> priceColumn;
    @FXML private TableColumn<Item, Integer> quantityColumn;
    @FXML private TableColumn<Item, String> categoryColumn;
    @FXML private TableColumn<Item, LocalDate> dateColumn;
    @FXML private TableColumn<Item, Integer> thresholdColumn;
    @FXML private TableColumn<Item, String> imageColumn;
    @FXML private Label countLabel; // For total item count
    @FXML private Label valueLabel; // For total value

    @FXML
    public void initialize() {
        // Bind each table column to the corresponding Item property
        codeColumn.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        brandColumn.setCellValueFactory(new PropertyValueFactory<>("itemBrand"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("itemPrice"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        //categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("purchasedDate"));
        thresholdColumn.setCellValueFactory(new PropertyValueFactory<>("lowStockThreshold"));
        imageColumn.setCellValueFactory(new PropertyValueFactory<>("imageName"));
        imageColumn.setPrefWidth(90); // Size for image display

        // Responsive column widths for all columns
        double total = 1 + 1.5 + 1 + 1 + 1 + 1 + 1.3 + 0.8 + 1.2;
        // Make columns auto-resize to always fill the table width
        codeColumn.prefWidthProperty().bind(viewItemTable.widthProperty().multiply(1.0 / total));
        nameColumn.prefWidthProperty().bind(viewItemTable.widthProperty().multiply(1.5 / total));
        brandColumn.prefWidthProperty().bind(viewItemTable.widthProperty().multiply(1.0 / total));
        priceColumn.prefWidthProperty().bind(viewItemTable.widthProperty().multiply(1.0 / total));
        quantityColumn.prefWidthProperty().bind(viewItemTable.widthProperty().multiply(1.0 / total));
        //categoryColumn.prefWidthProperty().bind(viewItemTable.widthProperty().multiply(1.0 / total));
        dateColumn.prefWidthProperty().bind(viewItemTable.widthProperty().multiply(1.3 / total));
        thresholdColumn.prefWidthProperty().bind(viewItemTable.widthProperty().multiply(0.8 / total));
        imageColumn.prefWidthProperty().bind(viewItemTable.widthProperty().multiply(1.2 / total));
        // Set the TableView resize policy
        viewItemTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);


        // Custom cell factories for coloring separator rows and images
        setupCellFactoryForSeparators();

        // Format price cells
        priceColumn.setCellFactory(tc -> new TableCell<Item, Double>() {
            @Override
            protected void updateItem(Double price, boolean empty) {
                super.updateItem(price, empty);
                Item item = getTableRow() == null ? null : getTableRow().getItem();
                if (empty || price == null || (item != null && item.isSeparator())) {
                    setText(null);
                } else {
                    setText(String.format("%.2f", price));
                }
            }
        });

        // Row factory
        viewItemTable.setRowFactory(tv -> new TableRow<Item>() {
            @Override
            protected void updateItem(Item item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setStyle("");
                } else if (item.isSeparator()) {
                    setStyle("-fx-background-color: #e1e5f2; -fx-font-weight: bold;");
                } else {
                    setStyle("");
                }
            }
        });

        // Load items from file, group by category, and display in table
        List<Item> allItems = FileManager.readItems("items.txt");
        ObservableList<Item> sortedList = FXCollections.observableArrayList(customSortedItems(allItems));
        viewItemTable.setItems(sortedList);

        // Show item count and total value
        updateSummaryLabels(sortedList);
    }

    // Configures custom cell factories for each column to handle category separator rows and formatting
    private void setupCellFactoryForSeparators() {
        // Code column shows category name for separator rows, otherwise code
        codeColumn.setCellFactory(tc -> new TableCell<Item, String>() {
            @Override
            protected void updateItem(String value, boolean empty) {
                super.updateItem(value, empty);
                Item item = getTableRow() == null ? null : getTableRow().getItem();
                if (empty || item == null) {
                    setText(null);
                } else if (item.isSeparator()) {
                    setText("   " + item.getSeparatorCategory().toUpperCase());
                    setStyle("-fx-font-weight: bold; -fx-background-color: #e1e5f2;");
                } else {
                    setText(value);
                    setStyle("");
                }
            }
        });

        // For other columns, hide content for separator rows
        nameColumn.setCellFactory(tc -> new TableCell<Item, String>() {
            @Override
            protected void updateItem(String value, boolean empty) {
                super.updateItem(value, empty);
                Item item = getTableRow() == null ? null : getTableRow().getItem();
                if (empty || item == null || item.isSeparator()) {
                    setText(null);
                } else {
                    setText(value);
                }
            }
        });

        brandColumn.setCellFactory(tc -> new TableCell<Item, String>() {
            @Override
            protected void updateItem(String value, boolean empty) {
                super.updateItem(value, empty);
                Item item = getTableRow() == null ? null : getTableRow().getItem();
                if (empty || item == null || item.isSeparator()) {
                    setText(null);
                } else {
                    setText(value);
                }
            }
        });

        priceColumn.setCellFactory(tc -> new TableCell<Item, Double>() {
            @Override
            protected void updateItem(Double price, boolean empty) {
                super.updateItem(price, empty);
                Item item = getTableRow() == null ? null : getTableRow().getItem();
                if (empty || price == null || (item != null && item.isSeparator())) {
                    setText(null);
                } else {
                    setText(String.format("%.2f", price));
                }
            }
        });

        quantityColumn.setCellFactory(tc -> new TableCell<Item, Integer>() {
            @Override
            protected void updateItem(Integer value, boolean empty) {
                super.updateItem(value, empty);
                Item item = getTableRow() == null ? null : getTableRow().getItem();
                if (empty || item == null || item.isSeparator()) {
                    setText(null);
                } else {
                    setText(value == null ? null : value.toString());
                }
            }
        });

        dateColumn.setCellFactory(tc -> new TableCell<Item, LocalDate>() {
            @Override
            protected void updateItem(LocalDate value, boolean empty) {
                super.updateItem(value, empty);
                Item item = getTableRow() == null ? null : getTableRow().getItem();
                if (empty || item == null || item.isSeparator()) {
                    setText(null);
                } else if (value != null) {
                    setText(value.toString());
                } else {
                    setText("");
                }
            }
        });

        thresholdColumn.setCellFactory(tc -> new TableCell<Item, Integer>() {
            @Override
            protected void updateItem(Integer value, boolean empty) {
                super.updateItem(value, empty);
                Item item = getTableRow() == null ? null : getTableRow().getItem();
                if (empty || item == null || item.isSeparator()) {
                    setText(null);
                } else {
                    setText(value == null ? null : value.toString());
                }
            }
        });

        // Image column shows an image for each item except for the separator rows
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
                    setGraphic(null);
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
                setText(null);
            }
        });
    }

    // Sorts and groups items by category, and inserts a separator row for each category (separator row is only for visual grouping)
    private List<Item> customSortedItems(List<Item> items) {
        List<String> categories = new ArrayList<>();
        for (Item item : items) {
            // Build a list of unique categories
            boolean found = false;
            for (String cat : categories) {
                if (cat.equalsIgnoreCase(item.getCategory())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                categories.add(item.getCategory());
            }
        }

        // Bubble sort for categories
        for (int i = 0; i < categories.size() - 1; i++) {
            for (int j = 0; j < categories.size() - i - 1; j++) {
                if (categories.get(j).compareToIgnoreCase(categories.get(j + 1)) > 0) {
                    // Swap
                    String temp = categories.get(j);
                    categories.set(j, categories.get(j + 1));
                    categories.set(j + 1, temp);
                }
            }
        }

        List<Item> sorted = new ArrayList<>();
        for (String category : categories) {
            // Insert a separator row for display
            sorted.add(Item.createSeparator(category));
            List<Item> catItems = new ArrayList<>();
            for (Item item : items)
                if (item.getCategory().equalsIgnoreCase(category))
                    catItems.add(item);

            // Sort items in this category by code
            // Bubble sort for items by item code
            for (int i = 0; i < catItems.size() - 1; i++) {
                for (int j = 0; j < catItems.size() - i - 1; j++) {
                    if (catItems.get(j).getItemCode().compareTo(catItems.get(j + 1).getItemCode()) > 0) {
                        // Swap
                        Item temp = catItems.get(j);
                        catItems.set(j, catItems.get(j + 1));
                        catItems.set(j + 1, temp);
                    }
                }
            }

            sorted.addAll(catItems);
        }
        return sorted;
    }

    // Event handlers

    // Handler for 'Add Item' button
    @FXML
    private void handleAddItem() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/johnsupermarket/AddItemDialog.fxml"));
            Parent root = loader.load();

            AddItemController addItemController = loader.getController();
            List<Item> items = FileManager.readItems("items.txt");
            addItemController.setItems(items);

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Add Item");
            dialogStage.setScene(new Scene(root));
            dialogStage.initOwner(viewItemTable.getScene().getWindow());
            dialogStage.showAndWait();

            // After closing dialog, refresh the main table
            List<Item> updatedItems = FileManager.readItems("items.txt");
            ObservableList<Item> sortedList = FXCollections.observableArrayList(customSortedItems(updatedItems));
            viewItemTable.setItems(sortedList);

            updateSummaryLabels(sortedList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Handler for 'Update Item' button
    @FXML
    private void handleUpdateItem() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Update Item");
        dialog.setHeaderText("Update Item By Code");
        dialog.setContentText("Enter Item Code:");

        dialog.showAndWait().ifPresent(code -> {
            if (code == null || code.trim().isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Please enter a code.");
                return;
            }
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
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/johnsupermarket/UpdateItemDialog.fxml"));
                Parent root = loader.load();

                UpdateItemController updateItemController = loader.getController();
                updateItemController.setItem(itemToUpdate);
                updateItemController.setItems(itemList);

                Stage dialogStage = new Stage();
                dialogStage.setTitle("Update Item");
                dialogStage.setScene(new Scene(root));
                dialogStage.initOwner(viewItemTable.getScene().getWindow());
                dialogStage.showAndWait();

                // Refresh table and summary after update
                List<Item> refreshed = FileManager.readItems("items.txt");
                ObservableList<Item> sortedRefreshed = FXCollections.observableArrayList(customSortedItems(refreshed));
                viewItemTable.setItems(sortedRefreshed);
                updateSummaryLabels(sortedRefreshed);
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

    // Closes the View Item dialog
    @FXML
    private void handleClose() {
        ((Stage) viewItemTable.getScene().getWindow()).close();
    }

    // Helper method to update the summary labels
    private void updateSummaryLabels(ObservableList<Item> items) {
        // Exclude separators from the count and total
        int realCount = 0;
        double totalValue = 0;
        for (Item item : items) {
            if (!item.isSeparator()) {
                realCount++;
                totalValue += item.getItemPrice() * item.getQuantity();
            }
        }
        countLabel.setText("Total Items: " + realCount);
        valueLabel.setText(String.format("Total Inventory Value: Rs. %.2f", totalValue));
    }

    // Handler for the 'Delete Item' button
    @FXML
    private void handleDeleteItem() {
        // If a row is selected in the table, prompt for confirmation and delete
        Item selected = viewItemTable.getSelectionModel().getSelectedItem();
        if (selected != null && !selected.isSeparator()) {
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

        // If nothing selected, prompt for code
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
        List<Item> updated = FileManager.readItems("items.txt"); // Always reload from file
        ObservableList<Item> sortedList = FXCollections.observableArrayList(customSortedItems(updated)); // Include separator rows
        viewItemTable.setItems(sortedList);
        updateSummaryLabels(sortedList);
        viewItemTable.getSelectionModel().clearSelection();
        viewItemTable.refresh();
    }

    // Handler for the 'Refresh' button
    @FXML
    private void handleRefresh() {
        refreshTable();
    }


}
