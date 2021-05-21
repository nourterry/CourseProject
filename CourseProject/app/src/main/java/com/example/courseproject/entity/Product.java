package com.example.courseproject.entity;

public class Product {
   private int id;
    private String name;
    private  String description;
    private  String price;
    private String isCash;
    private String image;

    public Product() {
    }

    public Product(String name, String description, String price, String isCash, String image) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.isCash = isCash;
        this.image = image;
    }

    public Product(int id, String name, String description, String price, String isCash, String image) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.isCash = isCash;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getIsCash() {
        return isCash;
    }

    public void setIsCash(String isCash) {
        this.isCash = isCash;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
