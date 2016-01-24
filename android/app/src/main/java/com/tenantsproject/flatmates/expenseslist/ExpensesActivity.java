package com.tenantsproject.flatmates.expenseslist;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.tenantsproject.flatmates.R;
import com.tenantsproject.flatmates.model.data.Expense;
import com.tenantsproject.flatmates.model.rest.Response;
import com.tenantsproject.flatmates.model.service.ExpenseService;
import com.tenantsproject.flatmates.user.UserActivity;
import com.tenantsproject.flatmates.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;

public class ExpensesActivity extends AppCompatActivity {
    private ExpenseService expenseService;
    private List<Expense> data;
    private ExpenseArrayAdapter adapter;

    private EditText descriptionEdit;
    private EditText priceEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses);
        init();
    }

    private void init() {
        expenseService = new ExpenseService();
        this.data = loadData();

        ListView listView = (ListView) findViewById(R.id.expenseListView);
        this.adapter = new ExpenseArrayAdapter(this, R.layout.expense_list_item, data, listView);
        listView.setAdapter(adapter);

        this.descriptionEdit = (EditText) findViewById(R.id.expenseslist_adddescription);
        this.priceEdit = (EditText) findViewById(R.id.expenseslist_addprice);

        createAddButton();
        createRefreshButton();
    }

    private List<Expense> loadData() {
        Response response = expenseService.getAllExpenses(getFlat());
        List<Expense> data;
        switch (response.getMessageCode()) {
            case Response.MESSAGE_OK:
                data = (List<Expense>) response.getObject();
                break;
            default:
                data = new ArrayList<>();
                Log.e("expense load", "Can't load expense list");
        }
        return data;
    }

    private void refreshData() {
        Response response = expenseService.getAllExpenses(getFlat());
        switch (response.getMessageCode()) {
            case Response.MESSAGE_OK:
                this.data = (List<Expense>) response.getObject();
                adapter.updateData(this.data);
                break;
            default:
                Log.e("expense load", "Can't load expense list");
        }
    }

    private void createRefreshButton() {
        Button refreshButton = (Button) findViewById(R.id.expenselist_refreshbutton);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshData();
            }
        });
    }

    private void createAddButton() {
        Button addButton = (Button) findViewById(R.id.expenselist_addbutton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String description = descriptionEdit.getText().toString();
                double price = Double.parseDouble(priceEdit.getText().toString());
                if (price > 0 && !description.equals("")) {
                    Expense expense = new Expense(price, description, 0, getFlat(), getUser());
                    //handle server response
                    Response response = expenseService.newExpense(expense);
                    if (response.getMessageCode() == Response.MESSAGE_OK) {
                        expense = (Expense) response.getObject();
                        adapter.addElement(expense);
                    }
                    //TODO: else handle errors
                }
                //TODO: else notify user


            }
        });
    }

    public void showPopupWindow(final Expense expense) {
        LayoutInflater layoutInflater
                = (LayoutInflater) getBaseContext()
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = layoutInflater.inflate(R.layout.expenselist_detailspopup, null);

        boolean error = false;
        Expense detailedExpense = null;
        int id = expense.getId();
        Response response = expenseService.get(id);
        final EditText editDescription = (EditText) popupView.findViewById(R.id.expenseslits_details_description);
        final EditText editPrice = (EditText) popupView.findViewById(R.id.expenseslits_details_price);
        final TextView editError = (TextView) popupView.findViewById(R.id.expenseslits_details_error);
        final TextView editUser = (TextView) popupView.findViewById(R.id.expenseslits_details_user);
        final TextView editModDate = (TextView) popupView.findViewById(R.id.expenseslits_details_moddate);
        //make fields not editable
        editUser.setKeyListener(null);
        editModDate.setKeyListener(null);
        //load data and handle get(id) errors
        switch (response.getMessageCode()) {
            case Response.MESSAGE_OK:
                detailedExpense = (Expense) response.getObject();
                editDescription.setText(detailedExpense.getDescription());
                editPrice.setText(String.valueOf(detailedExpense.getPrice()));
                editModDate.setText(TimeUtils.displayDate(detailedExpense.getModificationDate()));
                editUser.setText(detailedExpense.getUser());
                break;
            case Response.MESSAGE_NOT_FOUND:
                error = true;
                editError.setText("This expense doesn't exist. Please refresh.");
                break;
            default:
                error = true;
                editError.setText("Unknown error. Please refresh.");
                break;
        }

        final boolean finalError = error;
        final Expense finalExpense = detailedExpense;
        final AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setView(popupView)
                .setCancelable(false)
                .setPositiveButton("OK", null)
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                .setNeutralButton("DELETE", null)
                .create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button okButton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                okButton.setOnClickListener(getExpenseEditConfirmationAction(finalError, editDescription,
                        editPrice, finalExpense, editError, expense, alertDialog));
                Button deleteButton = alertDialog.getButton(DialogInterface.BUTTON_NEUTRAL);

                deleteButton.setOnClickListener(getExpenseDeleteAction(finalExpense, alertDialog, editError));
            }
        });
        // show it
        alertDialog.show();
    }

    @NonNull
    private View.OnClickListener getExpenseDeleteAction(final Expense finalExpense, final AlertDialog alertDialog, final TextView editError) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (finalExpense.getDone() == 0) {
                    Response response = expenseService.delete(finalExpense);
                    switch (response.getMessageCode()) {
                        case Response.MESSAGE_OK:
                            adapter.deleteElement(finalExpense);
                            alertDialog.dismiss();
                            break;
                        case Response.MESSAGE_NOT_FOUND:
                            editError.setText("Already deleted Please refresh");
                            break;
                        default:
                            editError.setText("Unknown error. Please refresh");
                            break;
                    }
                } else {
                    editError.setText("Can't delete done expenses");
                }

            }
        };
    }

    @NonNull
    private View.OnClickListener getExpenseEditConfirmationAction(final boolean finalError, final EditText editDescription, final EditText editPrice, final Expense finalExpense, final TextView editError, final Expense expense, final AlertDialog alertDialog) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!finalError) {
                    String description = editDescription.getText().toString();
                    double price = Double.valueOf(editPrice.getText().toString());

                    boolean changed = false;
                    boolean done = false;
                    if (!description.equals(finalExpense.getDescription())
                            || price != finalExpense.getPrice()) {
                        changed = true;
                    }
                    if (changed && finalExpense.getDone() != 0) {
                        editError.setText("Already done, can't update");
                        done = true;
                    }
                    if (changed && !done && price > 0 && !description.equals("")) {
                        finalExpense.setDescription(description);
                        finalExpense.setPrice(price);
                        finalExpense.setUser(getUser());
                        //update and handle errors
                        Response response = expenseService.update(finalExpense);
                        switch (response.getMessageCode()) {
                            case Response.MESSAGE_OK:
                                //continue closing
                                expense.setDescription(description);
                                expense.setPrice(price);
                                expense.setUser(getUser());
                                adapter.notifyDataSetChanged();
                                alertDialog.dismiss();
                                break;
                            case Response.MESSAGE_NOT_FOUND:
                                editError.setText("Expense not found. Please refresh.");
                                break;
                            case Response.MESSAGE_CONFLICT:
                                editError.setText("Somebody already updated. Please refresh.");
                                break;
                            default:
                                editError.setText("Error while updating. Please refresh.");
                                break;
                        }
                    } else {
                        if (!done && changed) {
                            editError.setText("Enter correct data");
                        }
                        if (!changed) {
                            alertDialog.dismiss();
                        }
                    }
                }
            }
        };
    }

    //TODO: improve and/or delete (shared pref user info)
    private int getFlat() {
        SharedPreferences sP = getSharedPreferences(UserActivity.USER_PREF_NAME, Context.MODE_PRIVATE);
        return sP.getInt(UserActivity.USER_PREF_FLAT, 0);
    }

    //TODO: improve and/or delete (shared pref user info)
    private String getUser() {
        SharedPreferences sP = getSharedPreferences(UserActivity.USER_PREF_NAME, Context.MODE_PRIVATE);
        return sP.getString(UserActivity.USER_PREF_USER, "default");
    }
}
