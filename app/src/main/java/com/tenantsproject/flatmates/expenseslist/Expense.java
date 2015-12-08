package com.tenantsproject.flatmates.expenseslist;

public class Expense {
    private int id;
    private boolean done;
    private double price;
    private String description;

    public Expense(int id, double price, String description, boolean done) {
        this.id = id;
        this.price = price;
        this.description = description;
        this.done = done;
    }

    public Expense(double price, String description) {
        this.price = price;
        this.description = description;
        this.done = false;
    }

    public String toString() {
        return "id=" + id + ", description=" + description;
    }

    public boolean isDone() {
        return done;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public double getPrice() {
        return this.price;
    }
}
