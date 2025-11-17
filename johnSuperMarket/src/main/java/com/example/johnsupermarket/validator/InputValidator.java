package com.example.johnsupermarket.validator;

import java.time.LocalDate;
import java.util.List;
import com.example.johnsupermarket.model.Item;

public class InputValidator {
    // Validates the item code
    public static String validateCode(String code, List<Item> items, boolean checkDuplicate) {
        if (code == null || code.trim().isEmpty())
            return "Required"; // Must not be blank
        if (code.length() != 3)
            return "Must be 3 characters"; // Must be exactly 3 characters
        if (checkDuplicate && items != null && items.stream().anyMatch(item -> item.getItemCode().equals(code)))
            return "Already exists"; // Must be unique
        return "";
    }

    // Validates that the name is not empty
    public static String validateName(String name) {
        if (name == null || name.trim().isEmpty())
            return "Required";
        return "";
    }

    // Validates that the brand is not empty
    public static String validateBrand(String brand) {
        if (brand == null || brand.trim().isEmpty())
            return "Required";
        return "";
    }

    // Validates the price input
    public static String validatePrice(String priceText) {
        if (priceText == null || priceText.trim().isEmpty())
            return "Required";
        try {
            double price = Double.parseDouble(priceText);
            if (price <= 0)
                return "Must be positive";
        } catch (NumberFormatException e) {
            return "Invalid number";
        }
        return "";
    }

    // Validates the quantity input
    public static String validateQuantity(String quantityText) {
        if (quantityText == null || quantityText.trim().isEmpty())
            return "Required";
        try {
            int quantity = Integer.parseInt(quantityText);
            if (quantity < 0)
                return "Must be positive";
        } catch (NumberFormatException e) {
            return "Invalid number";
        }
        return "";
    }

    // Validates the category is not empty
    public static String validateCategory(String category) {
        if (category == null || category.trim().isEmpty())
            return "Required";
        return "";
    }

    // Validates the date input
    public static String validateDate(String dateText) {
        if (dateText == null || dateText.trim().isEmpty())
            return "Required";
        try {
            LocalDate.parse(dateText); // Must be in YYYY-MM-DD format
        } catch (Exception e) {
            return "YYYY-MM-DD";
        }
        return "";
    }

    // Validates the threshold input
    public static String validateThreshold(String thresholdText) {
        if (thresholdText == null || thresholdText.trim().isEmpty())
            return "Required";
        try {
            int threshold = Integer.parseInt(thresholdText);
            if (threshold <= 0)
                return "Must be positive";
        } catch (NumberFormatException e) {
            return "Invalid number";
        }
        return "";
    }
}
