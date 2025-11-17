package com.example.johnsupermarket.validator;

import com.example.johnsupermarket.model.Item;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class InputValidatorTest {

    @Test
    void validateCode() {
        assertEquals("Required", InputValidator.validateCode("", null, true));
        assertEquals("Must be 3 characters", InputValidator.validateCode("A1", null, true));
        assertEquals("Must be 3 characters", InputValidator.validateCode("A123", null, true));
        Item existing = new Item("ABC", "Apple", "BrandX", 50.00, 10, "Fruit", LocalDate.of(2025, 7, 18), 5, "apple.png");
        assertEquals("Already exists", InputValidator.validateCode("ABC", List.of(existing), true));
        assertEquals("", InputValidator.validateCode("DEF", List.of(existing), true));
    }

    @Test
    void validateName() {
        assertEquals("Required", InputValidator.validateName(""));
        assertEquals("", InputValidator.validateName("Rice"));
    }

    @Test
    void validateBrand() {
        assertEquals("Required", InputValidator.validateBrand(""));
        assertEquals("", InputValidator.validateBrand("Keells"));
    }

    @Test
    void validatePrice() {
        assertEquals("Required", InputValidator.validatePrice(""));
        assertEquals("Must be positive", InputValidator.validatePrice("-10"));
        assertEquals("Invalid number", InputValidator.validatePrice("Abc"));
        assertEquals("", InputValidator.validatePrice("35.75"));
    }

    @Test
    void validateQuantity() {
        assertEquals("Required", InputValidator.validateQuantity(""));
        assertEquals("Must be positive", InputValidator.validateQuantity("-10"));
        assertEquals("Invalid number", InputValidator.validateQuantity("Abc"));
        assertEquals("", InputValidator.validateQuantity("25"));
    }

    @Test
    void validateCategory() {
        assertEquals("Required", InputValidator.validateCategory(""));
        assertEquals("", InputValidator.validateCategory("Fruit"));
    }

    @Test
    void validateDate() {
        assertEquals("Required", InputValidator.validateDate(""));
        assertEquals("YYYY-MM-DD", InputValidator.validateDate("2025/07/18"));
        assertEquals("", InputValidator.validateDate("2025-07-18"));
    }

    @Test
    void validateThreshold() {
        assertEquals("Required", InputValidator.validateThreshold(""));
        assertEquals("Must be positive", InputValidator.validateThreshold("-3"));
        assertEquals("Invalid number", InputValidator.validateThreshold("xyz"));
        assertEquals("", InputValidator.validateThreshold("4"));
    }
}
