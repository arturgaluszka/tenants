package com.tenantsproject.flatmates.expenseslist;

import java.util.ArrayList;
import java.util.List;

public class ExpenseList {
    private List<Expense> expenses;
    private int nextId;

    public ExpenseList() {
        expenses = new ArrayList<>();
        nextId = 0;
    }

    public void add(Expense expense) {
        expense.setId(nextId++);
        this.expenses.add(expense);
    }

    public List<Expense> getAll() {
        return this.expenses;
    }

    public void remove(Expense expense) {
        this.expenses.remove(expense);
    }
}
