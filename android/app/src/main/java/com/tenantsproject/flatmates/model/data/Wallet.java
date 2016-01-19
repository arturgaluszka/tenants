package com.tenantsproject.flatmates.model.data;

public class Wallet {
    private int id;
    private double current;
    private String user;
    private long modificationDate;
    private int flat;

    public Wallet() {
        this.current = 0;
    }

    public void deposit(double amount) {
        this.current += amount;
    }

    public boolean withdraw(double amount) {
        if (this.current >= amount) {
            this.current -= amount;
            return true;
        } else {
            return false;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getCurrent() {
        return this.current;
    }

    public void setCurrent(double current) {
        this.current = current;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public long getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(long modificationDate) {
        this.modificationDate = modificationDate;
    }

    public int getFlat() {
        return flat;
    }

    public void setFlat(int flat) {
        this.flat = flat;
    }
}
