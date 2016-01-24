package com.tenantsproject.flatmates.model.data;

import java.io.Serializable;

public class Expense implements Serializable {
    private int id;
    private int done;
    private double price;
    private String description;
    private int flat;
    private String user;
    private Long modificationDate;

    public Expense() {
    }

    public Expense(double price, String description, int done, int flat, String user) {
        this.price = price;
        this.description = description;
        this.done = done;
        this.flat = flat;
        this.user = user;
    }

    public Expense(int id, double price, String description, int done, int flat, String user) {
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

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Long getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(Long modificationDate) {
        this.modificationDate = modificationDate;
    }
}
