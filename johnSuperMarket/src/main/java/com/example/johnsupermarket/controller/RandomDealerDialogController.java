package com.example.johnsupermarket.controller;

import com.example.johnsupermarket.loader.FileManager;
import com.example.johnsupermarket.model.Dealer;
import com.example.johnsupermarket.model.DealerItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.File;
import java.util.*;

public class RandomDealerDialogController {
    // FXML Bindings to TableViews and columns for dealer and dealer items
    @FXML private TableView<Dealer> dealerTable;
    @FXML private TableColumn<Dealer, String> nameColumn;
    @FXML private TableColumn<Dealer, String> contactColumn;
    @FXML private TableColumn<Dealer, String> locationColumn;

    @FXML private TableView<DealerItem> itemTable;
    @FXML private TableColumn<DealerItem, String> itemNameColumn;
    @FXML private TableColumn<DealerItem, String> itemBrandColumn;
    @FXML private TableColumn<DealerItem, Double> itemPriceColumn;
    @FXML private TableColumn<DealerItem, Integer> itemQtyColumn;


    // Holds the list of randomly selected dealers to display
    private ObservableList<Dealer> randomDealers = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Set up cell value factories for dealer columns
        nameColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getName()));
        contactColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getContact()));
        locationColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getLocation()));

        // Set up cell value factories for dealer item columns
        itemNameColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getItemName()));
        itemBrandColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getBrand()));
        itemPriceColumn.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getPrice())
        );
        // Format price cell to show two decimals
        itemPriceColumn.setCellFactory(tc -> new TableCell<DealerItem, Double>() {
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
        itemQtyColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getQuantity()));

        // File existence check
        File file = new File("dealers.txt");
        if (!file.exists()) {
            // Show error alert if file does not exist, then close dialog
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("File Not Found");
            alert.setHeaderText(null);
            alert.setContentText("The dealer file 'dealers.txt' does not exist. Please create the file and try again.");
            if (dealerTable != null && dealerTable.getScene() != null)
                alert.initOwner(dealerTable.getScene().getWindow());
            alert.showAndWait();
            closeWindow();
            return;
        }

        // Read dealers from file
        List<Dealer> allDealers = FileManager.readDealers("dealers.txt");
        // Check if there are enough dealers for a draw
        if (allDealers.size() < 4) {
            showAlert(Alert.AlertType.ERROR, "Not enough dealers in the file.");
            closeWindow();
            return;
        }

        // Randomly pick 4 dealers
        Collections.shuffle(allDealers);
        List<Dealer> selected = new ArrayList<>(allDealers.subList(0, 4));

        // Bubble sort by location (A-Z)
        for (int i = 0; i < selected.size() - 1; i++)
            for (int j = 0; j < selected.size() - i - 1; j++)
                if (selected.get(j).getLocation().compareTo(selected.get(j+1).getLocation()) > 0) {
                    Dealer tmp = selected.get(j);
                    selected.set(j, selected.get(j+1));
                    selected.set(j+1, tmp);
                }

        // Show dealers in table
        randomDealers.setAll(selected);
        dealerTable.setItems(randomDealers);

        // Show items of selected dealer
        dealerTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                itemTable.setItems(FXCollections.observableArrayList(newSel.getItems()));
            } else {
                itemTable.setItems(FXCollections.observableArrayList());
            }
        });

        // Select first dealer by default
        if (!randomDealers.isEmpty()) {
            dealerTable.getSelectionModel().selectFirst();
        }

    }

    // Handler for 'Close' button
    @FXML
    private void handleClose() {
        closeWindow();
    }

    // Helper to close the dialog window
    private void closeWindow() {
        ((Stage) dealerTable.getScene().getWindow()).close();
    }

    // Helper to show an alert dialog with a given message
    private void showAlert(Alert.AlertType type, String msg) {
        Alert alert = new Alert(type);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
