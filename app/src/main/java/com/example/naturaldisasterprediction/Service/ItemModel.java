package com.example.naturaldisasterprediction.Service;

public class ItemModel {
        private String item;
        private String quantity;
        private String description;

    public ItemModel(String item, String quantity, String description) {
        this.item = item;
        this.quantity = quantity;
        this.description = description;
    }

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
        return "ItemModel{" +
                "item='" + item + '\'' +
                ", quantity='" + quantity + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
