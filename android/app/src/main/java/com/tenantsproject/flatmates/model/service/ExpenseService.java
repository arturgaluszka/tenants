package com.tenantsproject.flatmates.model.service;

import com.tenantsproject.flatmates.model.data.ExpensePOJO;
import com.tenantsproject.flatmates.model.rest.ExpenseREST;
import com.tenantsproject.flatmates.model.rest.Response;

import java.util.List;

/**
 * Created by Strajk on 10.01.2016.
 */
public class ExpenseService {
    private ExpenseREST expenseREST;

    public ExpenseService(){
        this.expenseREST = new ExpenseREST();
    }

    public Response getAllExpenses(int id){
        return expenseREST.getAllExpenses(id);
    }
    public Response update(ExpensePOJO expense){
        return expenseREST.update(expense);
    }
    public Response newExpense(ExpensePOJO expensePOJO){
        return expenseREST.newExpense(expensePOJO);
    }
    public Response get(int id){
        return expenseREST.get(id);
    }
    public Response delete(ExpensePOJO expensePOJO){
        return expenseREST.delete(expensePOJO);
    }
}
