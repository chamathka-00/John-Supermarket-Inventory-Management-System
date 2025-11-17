package com.example.johnsupermarket.model;

// DealerItem class models a single item supplied by a dealer
public class DealerItem {
    private String itemName;
    private String brand;
    private double price;
    private int quantity;

    // Constructing a DealerItem object
    public DealerItem(String itemName, String brand, double price, int quantity) {
        this.itemName = itemName;
        this.brand = brand;
        this.price = price;
        this.quantity = quantity;
    }

    // Getters and setters
    public String getItemName() { return itemName; }
    public void setItemName(String itemName) { this.itemName = itemName; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}
