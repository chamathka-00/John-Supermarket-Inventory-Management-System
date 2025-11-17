package com.example.johnsupermarket.model;

import java.time.LocalDate;

public class Item {
    // Data fields
    private String itemCode;
    private String itemName;
    private String itemBrand;
    private double itemPrice;
    private int quantity;
    private String category;
    private LocalDate purchasedDate;
    private int lowStockThreshold;
    private String imageName;

    // Main constructor which constructs an Item object
    public Item(String itemCode, String itemName, String itemBrand, double itemPrice, int quantity,
                String category, LocalDate purchasedDate, int lowStockThreshold, String imageName) {
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.itemBrand = itemBrand;
        this.itemPrice = itemPrice;
        this.quantity = quantity;
        this.category = category;
        this.purchasedDate = purchasedDate;
        this.lowStockThreshold = lowStockThreshold;
        this.imageName = imageName;
    }

    // Fields and constructors for Separator rows

    // Marks whether the current Item object represents a Separator row
    private boolean isSeparator = false;
    // Labeling the Separator row with category name
    private String separatorCategory = null;

    // Constructor for creating a Separator row for grouping items
    public Item(boolean isSeparator, String separatorCategory) {
        this.isSeparator = isSeparator;
        this.separatorCategory = separatorCategory;
    }

    // Factory method for creating a Separator
    public static Item createSeparator(String category) {
        return new Item(true, category);
    }

    // Separator getters
    public boolean isSeparator() { return isSeparator; }
    public String getSeparatorCategory() { return separatorCategory; }


    // Normal getters and setters
    public String getItemCode() { return itemCode; }
    public void setItemCode(String itemCode) { this.itemCode = itemCode; }

    public String getItemName() { return itemName; }
    public void setItemName(String itemName) { this.itemName = itemName; }

    public String getItemBrand() { return itemBrand; }
    public void setItemBrand(String itemBrand) { this.itemBrand = itemBrand; }

    public double getItemPrice() { return itemPrice; }
    public void setItemPrice(double itemPrice) { this.itemPrice = itemPrice; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public LocalDate getPurchasedDate() { return purchasedDate; }
    public void setPurchasedDate(LocalDate purchasedDate) { this.purchasedDate = purchasedDate; }

    public int getLowStockThreshold() { return lowStockThreshold; }
    public void setLowStockThreshold(int lowStockThreshold) { this.lowStockThreshold = lowStockThreshold; }

    public String getImageName() { return imageName; }
    public void setImageName(String imageName) { this.imageName = imageName; }

    // Inventory Logic to highlight low stock items
    public boolean isLowStock() {
        return quantity < lowStockThreshold;
    }

}
