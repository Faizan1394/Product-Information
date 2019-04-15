package com.example.faizan.assignment2;


/**
 * Created by faizan on 10/11/17.
 */

public class Product {

    private int productID;
    private String name;
    private String description;
    private double price;


    public Product(String name, String description, double price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public int getProductID() {
        return productID;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {

        return description;
    }

    public double getPrice() {
        return price;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }
}
