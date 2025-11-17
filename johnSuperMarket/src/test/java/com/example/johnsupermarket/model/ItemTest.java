package com.example.johnsupermarket.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ItemTest {

    @Test
    void createSeparator() {
        Item sep = Item.createSeparator("Grocery");
        assertTrue(sep.isSeparator());
        assertEquals("Grocery", sep.getSeparatorCategory());
    }

    @Test
    void isSeparator() {
        Item sep = Item.createSeparator("Bakery");
        assertTrue(sep.isSeparator());
        Item item = new Item("A01", "Milk", "Anchor", 200.00, 10, "Dairy", LocalDate.of(2025, 7, 18), 5, "milk.png");
        assertFalse(item.isSeparator());
    }

    @Test
    void getSeparatorCategory() {
        Item sep = Item.createSeparator("Frozen");
        assertEquals("Frozen", sep.getSeparatorCategory());
    }

    @Test
    void getItemCode() {
        Item item = new Item("C13", "Soap", "Lux", 80.00, 20, "Cosmetics", LocalDate.of(2025, 7, 18), 5, "soap.png");
        assertEquals("C13", item.getItemCode());
    }

    @Test
    void setItemCode() {
        Item item = new Item("C13", "Soap", "Lux", 80.00, 20, "Cosmetics", LocalDate.of(2025, 7, 18), 5, "soap.png");
        item.setItemCode("D20");
        assertEquals("D20", item.getItemCode());
    }

    @Test
    void getItemName() {
        Item item = new Item("C13", "Soap", "Lux", 80.00, 20, "Cosmetics", LocalDate.of(2025, 7, 18), 5, "soap.png");
        assertEquals("Soap", item.getItemName());
    }

    @Test
    void setItemName() {
        Item item = new Item("C13", "Soap", "Lux", 80.00, 20, "Cosmetics", LocalDate.of(2025, 7, 18), 5, "soap.png");
        item.setItemName("Handwash");
        assertEquals("Handwash", item.getItemName());
    }

    @Test
    void getItemBrand() {
        Item item = new Item("C13", "Soap", "Lux", 80.00, 20, "Cosmetics", LocalDate.of(2025, 7, 18), 5, "soap.png");
        assertEquals("Lux", item.getItemBrand());
    }

    @Test
    void setItemBrand() {
        Item item = new Item("C13", "Soap", "Lux", 80.00, 20, "Cosmetics", LocalDate.of(2025, 7, 18), 5, "soap.png");
        item.setItemBrand("Pears");
        assertEquals("Pears", item.getItemBrand());
    }

    @Test
    void getItemPrice() {
        Item item = new Item("C13", "Soap", "Lux", 80.00, 20, "Cosmetics", LocalDate.of(2025, 7, 18), 5, "soap.png");
        assertEquals(80.00, item.getItemPrice());
    }

    @Test
    void setItemPrice() {
        Item item = new Item("C13", "Soap", "Lux", 80.00, 20, "Cosmetics", LocalDate.of(2025, 7, 18), 5, "soap.png");
        item.setItemPrice(95.00);
        assertEquals(95.00, item.getItemPrice());
    }

    @Test
    void getQuantity() {
        Item item = new Item("C13", "Soap", "Lux", 80.00, 20, "Cosmetics", LocalDate.of(2025, 7, 18), 5, "soap.png");
        assertEquals(20, item.getQuantity());
    }

    @Test
    void setQuantity() {
        Item item = new Item("C13", "Soap", "Lux", 80.00, 20, "Cosmetics", LocalDate.of(2025, 7, 18), 5, "soap.png");
        item.setQuantity(40);
        assertEquals(40, item.getQuantity());
    }

    @Test
    void getCategory() {
        Item item = new Item("C13", "Soap", "Lux", 80.00, 20, "Cosmetics", LocalDate.of(2025, 7, 18), 5, "soap.png");
        assertEquals("Cosmetics", item.getCategory());
    }

    @Test
    void setCategory() {
        Item item = new Item("C13", "Soap", "Lux", 80.00, 20, "Cosmetics", LocalDate.of(2025, 7, 18), 5, "soap.png");
        item.setCategory("Toiletries");
        assertEquals("Toiletries", item.getCategory());
    }

    @Test
    void getPurchasedDate() {
        LocalDate date = LocalDate.of(2025, 7, 18);
        Item item = new Item("C13", "Soap", "Lux", 80.00, 20, "Cosmetics", date, 5, "soap.png");
        assertEquals(date, item.getPurchasedDate());
    }

    @Test
    void setPurchasedDate() {
        Item item = new Item("C13", "Soap", "Lux", 80.00, 20, "Cosmetics", LocalDate.of(2025,7,18), 5, "soap.png");
        LocalDate newDate = LocalDate.of(2025, 7, 22);
        item.setPurchasedDate(newDate);
        assertEquals(newDate, item.getPurchasedDate());
    }

    @Test
    void getLowStockThreshold() {
        Item item = new Item("C13", "Soap", "Lux", 80.00, 20, "Cosmetics", LocalDate.of(2025,7,18), 5, "soap.png");
        assertEquals(5, item.getLowStockThreshold());
    }

    @Test
    void setLowStockThreshold() {
        Item item = new Item("C13", "Soap", "Lux", 80.00, 20, "Cosmetics", LocalDate.of(2025,7,18), 5, "soap.png");
        item.setLowStockThreshold(8);
        assertEquals(8, item.getLowStockThreshold());
    }

    @Test
    void getImageName() {
        Item item = new Item("C13", "Soap", "Lux", 80.00, 20, "Cosmetics", LocalDate.of(2025,7,18), 5, "soap.png");
        assertEquals("soap.png", item.getImageName());
    }

    @Test
    void setImageName() {
        Item item = new Item("C13", "Soap", "Lux", 80.00, 20, "Cosmetics", LocalDate.of(2025,7,18), 5, "soap.png");
        item.setImageName("handwash.png");
        assertEquals("handwash.png", item.getImageName());
    }

    @Test
    void isLowStock() {
        Item low = new Item("A01", "Milk", "Anchor", 200.00, 2, "Dairy", LocalDate.of(2025,7,18), 5, "milk.png");
        Item normal = new Item("A02", "Cheese", "Anchor", 200.00, 7, "Dairy", LocalDate.of(2025,7,18), 5, "cheese.png");
        assertTrue(low.isLowStock());
        assertFalse(normal.isLowStock());
    }
}
