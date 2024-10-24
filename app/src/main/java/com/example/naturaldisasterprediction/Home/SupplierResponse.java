package com.example.naturaldisasterprediction.Home;

import java.util.List;

public class SupplierResponse {
    private List<FoodItem> food;
    private List<ClothingItem> clothing;
    private List<OtherSupplyItem> other_supplies;

    // Getters and Setters
    public List<FoodItem> getFood() {
        return food;
    }

    public void setFood(List<FoodItem> food) {
        this.food = food;
    }

    public List<ClothingItem> getClothing() {
        return clothing;
    }

    public void setClothing(List<ClothingItem> clothing) {
        this.clothing = clothing;
    }

    public List<OtherSupplyItem> getOtherSupplies() {
        return other_supplies;
    }

    public void setOtherSupplies(List<OtherSupplyItem> other_supplies) {
        this.other_supplies = other_supplies;
    }

    // Inner classes for the items
    public static class FoodItem {
        private String item;
        private String quantity;
        private String description;

        // Getters and Setters
        public String getItem() {
            return item;
        }

        public void setItem(String item) {
            this.item = item;
        }

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
        @Override
        public String toString() {
            return "FoodItem{" +
                    "item='" + item + '\'' +
                    ", quantity='" + quantity + '\'' +
                    ", description='" + description + '\'' +
                    '}';
        }

    }

    public static class ClothingItem {
        private String item;
        private String quantity;
        private String description;

        // Getters and Setters
        public String getItem() {
            return item;
        }

        public void setItem(String item) {
            this.item = item;
        }

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        @Override
        public String toString() {
            return "ClothingItem{" +
                    "item='" + item + '\'' +
                    ", quantity='" + quantity + '\'' +
                    ", description='" + description + '\'' +
                    '}';
        }
    }

    public static class OtherSupplyItem {
        private String item;
        private String quantity;
        private String description;

        // Getters and Setters
        public String getItem() {
            return item;
        }

        public void setItem(String item) {
            this.item = item;
        }

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
        @Override
        public String toString() {
            return "SupplierItem{" +
                    "item='" + item + '\'' +
                    ", quantity='" + quantity + '\'' +
                    ", description='" + description + '\'' +
                    '}';
        }

    }
}
