package com.tenantsproject.flatmates.model.service;

import com.tenantsproject.flatmates.model.data.Expense;
import com.tenantsproject.flatmates.model.rest.ExpenseREST;
import com.tenantsproject.flatmates.model.rest.Response;

public class ExpenseService {
    private ExpenseREST expenseREST;

    public ExpenseService(){
        this.expenseREST = new ExpenseREST();
    }

    public Response getAllExpenses(int id){
        return expenseREST.getAllExpenses(id);
    }
    public Response update(Expense expense){
        return expenseREST.update(expense);
    }
    public Response newExpense(Expense expense){
        return expenseREST.newExpense(expense);
    }
    public Response get(int id){
        return expenseREST.get(id);
    }
    public Response delete(Expense expense){
        return expenseREST.delete(expense);
    }
}
