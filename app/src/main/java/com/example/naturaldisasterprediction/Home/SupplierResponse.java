package com.example.naturaldisasterprediction.Home;

import com.example.naturaldisasterprediction.Service.ItemModel;

import java.util.List;

public class SupplierResponse {
    public List<ItemModel> getFood() {
        return food;
    }

    public void setFood(List<ItemModel> food) {
        this.food = food;
    }

    private List<ItemModel> food;

    public List<ItemModel> getClothing() {
        return clothing;
    }

    public void setClothing(List<ItemModel> clothing) {
        this.clothing = clothing;
    }

    private List<ItemModel> clothing;

    public List<ItemModel> getOther_supplies() {
        return other_supplies;
    }

    public void setOther_supplies(List<ItemModel> other_supplies) {
        this.other_supplies = other_supplies;
    }

    private List<ItemModel> other_supplies;


}
