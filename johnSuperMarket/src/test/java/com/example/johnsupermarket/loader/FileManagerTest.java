package com.example.johnsupermarket.loader;

import com.example.johnsupermarket.model.Dealer;
import com.example.johnsupermarket.model.Item;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FileManagerTest {

    @Test
    void saveItems_and_readItems() {
        String testFile = "test_items.txt";
        // Prepare test data
        Item item1 = new Item("A10", "Sugar", "Lanka", 120.00, 50, "Grocery", LocalDate.of(2025, 7, 20), 5, "sugar.png");
        Item item2 = new Item("B12", "Bread", "Wonder", 180.00, 10, "Bakery", LocalDate.of(2025, 7, 10), 2, "bread.png");
        List<Item> itemsToSave = List.of(item1, item2);

        // Save to test file
        FileManager.saveItems(itemsToSave, testFile);

        // Read from test file
        List<Item> loaded = FileManager.readItems(testFile);

        assertEquals(2, loaded.size());
        assertEquals("A10", loaded.get(0).getItemCode());
        assertEquals("Bread", loaded.get(1).getItemName());

        // Clean up
        new File(testFile).delete();
    }

    @Test
    void readItems_handleErrors() throws IOException {
        String testFile = "test_items_blank.txt";
        // Test blank file
        new File(testFile).createNewFile();
        List<Item> loaded = FileManager.readItems(testFile);
        assertTrue(loaded.isEmpty());
        new File(testFile).delete();

        // Test file with an invalid line
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(testFile))) {
            bw.write("this,is,an,invalid,line,with,wrong,fields");
            bw.newLine();
        }
        loaded = FileManager.readItems(testFile);
        assertTrue(loaded.isEmpty());
        new File(testFile).delete();
    }

    @Test
    void readDealers() throws IOException {
        String testFile = "test_dealers.txt";
        // Prepare test dealer file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(testFile))) {
            bw.write("Best Dealers\n");
            bw.write("0771234567\n");
            bw.write("Colombo\n");
            bw.write("Soap,Lux,75.00,20\n");
            bw.write("Rice,Nipuna,120.00,100\n");
            bw.write("\n");
            bw.write("Fresh Traders\n");
            bw.write("0712345678\n");
            bw.write("Kandy\n");
            bw.write("Tea,Dilmah,950.00,5\n");
            bw.write("\n");
        }
        // Read dealers
        List<Dealer> dealers = FileManager.readDealers(testFile);

        assertEquals(2, dealers.size());
        Dealer d1 = dealers.get(0);
        assertEquals("Best Dealers", d1.getName());
        assertEquals(2, d1.getItems().size());
        assertEquals("Soap", d1.getItems().get(0).getItemName());
        assertEquals("Rice", d1.getItems().get(1).getItemName());

        Dealer d2 = dealers.get(1);
        assertEquals("Fresh Traders", d2.getName());
        assertEquals(1, d2.getItems().size());
        assertEquals("Tea", d2.getItems().get(0).getItemName());

        // Clean up
        new File(testFile).delete();
    }
}
