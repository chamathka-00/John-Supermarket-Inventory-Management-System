package com.example.johnsupermarket.loader;

import com.example.johnsupermarket.model.Item;
import com.example.johnsupermarket.model.Dealer;
import com.example.johnsupermarket.model.DealerItem;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    // Reads all items from the specified text file and returns as a List<Item>
    public static List<Item> readItems(String filename) {
        List<Item> items = new ArrayList<>(); // Creates an empty list for items
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) { // Read each line
                String[] fields = line.split(",");  // Split by comma
                if (fields.length == 9) { // Expecting 9 fields per item
                    String code = fields[0];
                    String name = fields[1];
                    String brand = fields[2];
                    double price = Double.parseDouble(fields[3]);
                    int quantity = Integer.parseInt(fields[4]);
                    String category = fields[5];
                    LocalDate purchasedDate = LocalDate.parse(fields[6]);
                    int lowStockThreshold = Integer.parseInt(fields[7]);
                    String imageName = fields[8];
                    if (imageName == null || imageName.trim().isEmpty()) imageName = "default.png"; // Set default image if blank

                    // Create new Item object and add to list
                    items.add(new Item(code, name, brand, price, quantity, category, purchasedDate, lowStockThreshold, imageName));
                } else {
                    // Skip lines that do not have the expected number of fields
                }
            }
        } catch (FileNotFoundException e) {
            // If file not found, create an empty file
            try {
                new File(filename).createNewFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace(); // Print any other errors
        }
        return items; // Return the list
    }

    // Saves the given list of items to the items.txt file
    public static void saveItems(List<Item> items, String filename) { // Open file for writing (overwrite)
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            for (Item item : items) {
                // Write each item as a comma separated line
                bw.write(item.getItemCode() + "," +
                        item.getItemName() + "," +
                        item.getItemBrand() + "," +
                        item.getItemPrice() + "," +
                        item.getQuantity() + "," +
                        item.getCategory() + "," +
                        item.getPurchasedDate() + "," +
                        item.getLowStockThreshold() + "," +
                        item.getImageName());
                bw.newLine(); // Go to the next line
            }
        } catch (IOException e) {
            e.printStackTrace(); // Print any write errors
        }
    }

    // Read all dealers from the dealers.txt file
    public static List<Dealer> readDealers(String filename) {
        List<Dealer> dealers = new ArrayList<>(); // List to hold all dealers
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            List<String> dealerBlock = new ArrayList<>(); // Temp list for one dealer's line block
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    // Blank line indicates end of dealer block
                    if (!dealerBlock.isEmpty()) {
                        dealers.add(parseDealerBlock(dealerBlock)); // Parse and add dealer
                        dealerBlock.clear(); // Reset for next dealer
                    }
                } else {
                    dealerBlock.add(line); // Add line to current dealer block
                }
            }
            // After reading all lines, checking for remaining dealer block
            if (!dealerBlock.isEmpty()) {
                dealers.add(parseDealerBlock(dealerBlock));
            }
        } catch (FileNotFoundException e) {
            // GUI Alert
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dealers;
    }

    // Helper method to convert a dealer block into Dealer object
    private static Dealer parseDealerBlock(List<String> block) {
        String name = block.get(0);
        String contact = block.get(1);
        String location = block.get(2);
        List<DealerItem> items = new ArrayList<>();

        // From the 4th line onward, each line is an item
        for (int i = 3; i < block.size(); i++) {
            String[] itemFields = block.get(i).split(",");
            if (itemFields.length == 4) {
                String itemName = itemFields[0];
                String brand = itemFields[1];
                double price = Double.parseDouble(itemFields[2]);
                int quantity = Integer.parseInt(itemFields[3]);
                // Add the dealer's item to the list
                items.add(new DealerItem(itemName, brand, price, quantity));
            } else {
                // Skip invalid item lines
            }
        }
        // Create and return a Dealer object
        return new Dealer(name, contact, location, items);
    }
}
