package com.example.johnsupermarket.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DealerItemTest {

    @Test
    void getItemName() {
        DealerItem item = new DealerItem("Soap", "Lux", 75.00, 20);
        assertEquals("Soap", item.getItemName());
    }

    @Test
    void setItemName() {
        DealerItem item = new DealerItem("Soap", "Lux", 75.00, 20);
        item.setItemName("Shampoo");
        assertEquals("Shampoo", item.getItemName());
    }

    @Test
    void getBrand() {
        DealerItem item = new DealerItem("Soap", "Lux", 75.00, 20);
        assertEquals("Lux", item.getBrand());
    }

    @Test
    void setBrand() {
        DealerItem item = new DealerItem("Soap", "Lux", 75.00, 20);
        item.setBrand("Sunsilk");
        assertEquals("Sunsilk", item.getBrand());
    }

    @Test
    void getPrice() {
        DealerItem item = new DealerItem("Soap", "Lux", 75.00, 20);
        assertEquals(75.00, item.getPrice());
    }

    @Test
    void setPrice() {
        DealerItem item = new DealerItem("Soap", "Lux", 75.00, 20);
        item.setPrice(100.00);
        assertEquals(100.00, item.getPrice());
    }

    @Test
    void getQuantity() {
        DealerItem item = new DealerItem("Soap", "Lux", 75.00, 20);
        assertEquals(20, item.getQuantity());
    }

    @Test
    void setQuantity() {
        DealerItem item = new DealerItem("Soap", "Lux", 75.00, 20);
        item.setQuantity(50);
        assertEquals(50, item.getQuantity());
    }
}
