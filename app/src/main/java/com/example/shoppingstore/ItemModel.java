package com.example.shoppingstore;

public class ItemModel {
    private String name;
    private int price;
    private int quantity;
    private int image;


    public ItemModel(String name, int price, int quantity, int image) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getImage() {
        return image;
    }
}
