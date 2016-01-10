package com.tenantsproject.flatmates.wallet;

public class Wallet {
    private double current;

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

    public double getCurrent() {
        return this.current;
    }
}
