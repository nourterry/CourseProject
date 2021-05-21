package com.example.courseproject.entity;

public class Purchase {
    int id;
    String name;
    String username;
    String price;
    String timeanddate;

    public Purchase() {
    }

    public Purchase(String name, String username, String price, String timeanddate) {
        this.name = name;
        this.username = username;
        this.price = price;
        this.timeanddate = timeanddate;
    }

    public Purchase(int id, String name, String username, String price, String timeanddate) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.price = price;
        this.timeanddate = timeanddate;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTimeanddate() {
        return timeanddate;
    }

    public void setTimeanddate(String timeanddate) {
        this.timeanddate = timeanddate;
    }
}
