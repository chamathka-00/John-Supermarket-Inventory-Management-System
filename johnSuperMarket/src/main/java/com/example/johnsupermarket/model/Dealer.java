package com.example.johnsupermarket.model;

import java.util.List;

// Dealer class models a dealer for the supermarket
public class Dealer {
    private String name;
    private String contact;
    private String location;
    private List<DealerItem> items;

    // Constructing a Dealer object
    public Dealer(String name, String contact, String location, List<DealerItem> items) {
        this.name = name;
        this.contact = contact;
        this.location = location;
        this.items = items;
    }

    // Getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public List<DealerItem> getItems() { return items; }
    public void setItems(List<DealerItem> items) { this.items = items; }
}
