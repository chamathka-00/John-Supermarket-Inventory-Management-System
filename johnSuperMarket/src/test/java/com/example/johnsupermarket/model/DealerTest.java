package com.example.johnsupermarket.model;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class DealerTest {

    @Test
    void getName() {
        Dealer dealer = new Dealer("Best Dealers", "0771234567", "Colombo", new ArrayList<>());
        assertEquals("Best Dealers", dealer.getName());
    }

    @Test
    void setName() {
        Dealer dealer = new Dealer("Best Dealers", "0771234567", "Colombo", new ArrayList<>());
        dealer.setName("Fresh Traders");
        assertEquals("Fresh Traders", dealer.getName());
    }

    @Test
    void getContact() {
        Dealer dealer = new Dealer("Best Dealers", "0771234567", "Colombo", new ArrayList<>());
        assertEquals("0771234567", dealer.getContact());
    }

    @Test
    void setContact() {
        Dealer dealer = new Dealer("Best Dealers", "0771234567", "Colombo", new ArrayList<>());
        dealer.setContact("0712345678");
        assertEquals("0712345678", dealer.getContact());
    }

    @Test
    void getLocation() {
        Dealer dealer = new Dealer("Best Dealers", "0771234567", "Colombo", new ArrayList<>());
        assertEquals("Colombo", dealer.getLocation());
    }

    @Test
    void setLocation() {
        Dealer dealer = new Dealer("Best Dealers", "0771234567", "Colombo", new ArrayList<>());
        dealer.setLocation("Kandy");
        assertEquals("Kandy", dealer.getLocation());
    }

    @Test
    void getItems() {
        List<DealerItem> items = new ArrayList<>();
        items.add(new DealerItem("Soap", "Lux", 75.00, 20));
        Dealer dealer = new Dealer("Best Dealers", "0771234567", "Colombo", items);
        assertEquals(1, dealer.getItems().size());
        assertEquals("Soap", dealer.getItems().get(0).getItemName());
    }

    @Test
    void setItems() {
        Dealer dealer = new Dealer("Best Dealers", "0771234567", "Colombo", new ArrayList<>());
        List<DealerItem> newItems = new ArrayList<>();
        newItems.add(new DealerItem("Rice", "Nipuna", 120.00, 100));
        dealer.setItems(newItems);
        assertEquals(1, dealer.getItems().size());
        assertEquals("Rice", dealer.getItems().get(0).getItemName());
    }
}
