package com.example.orderapp;

public class Product {
    private int id;
    private String title;
    private double price;
    private String date;
    private String status;

    public Product(int id, String title, double price, String date, String status) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.date = date;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return title;
    }

    public double getPrice() {
        return price;
    }

    public String getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }
}
