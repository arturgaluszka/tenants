package com.tenantsproject.flatmates.model.data;

public class Product {
    private int id;
    private int done;
    private double price;
    private String description;
    private int flat;
    private int user;
    private int creator;
    private Long modificationDate;

    public Product() {
    }

    public Product(String description, int flat, int creator) {
        this.description = description;
        this.flat = flat;
        this.creator = creator;
    }

    public Product(double price, String description, int done, int flat, int user) {
        this.price = price;
        this.description = description;
        this.done = done;
        this.flat = flat;
        this.user = user;
    }

    public Product(int id, double price, String description, int done, int flat, int user) {
        this.id = id;
        this.price = price;
        this.description = description;
        this.done = done;
        this.flat = flat;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getDone() {
        return done;
    }

    public void setDone(int done) {
        this.done = done;
    }

    public int getFlat() {
        return flat;
    }

    public void setFlat(int flat) {
        this.flat = flat;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public int getCreator() {
        return creator;
    }

    public void setCreator(int creator) {
        this.creator = creator;
    }

    public Long getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(Long modificationDate) {
        this.modificationDate = modificationDate;
    }
}
