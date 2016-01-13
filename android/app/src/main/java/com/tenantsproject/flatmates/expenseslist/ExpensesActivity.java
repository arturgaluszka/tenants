package com.tenantsproject.flatmates.expenseslist;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
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
import com.tenantsproject.flatmates.model.data.ExpensePOJO;
import com.tenantsproject.flatmates.model.rest.Response;
import com.tenantsproject.flatmates.model.service.ExpenseService;
import com.tenantsproject.flatmates.user.UserActivity;

import java.util.ArrayList;
import java.util.List;

public class ExpensesActivity extends AppCompatActivity {
    private ExpenseService expenseService;
    private List<ExpensePOJO> data;
    private ExpenseArrayAdapter adapter;

    private EditText descriptionEdit;
    private EditText priceEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses);
        init();
    }

    private void refreshData(){
        Response response = expenseService.getAllExpenses(getFlat());
        switch (response.getMessageCode()){
            case Response.MESSAGE_OK:
                this.data = (List<ExpensePOJO>) response.getObject();
                adapter.updateData(this.data);
                break;
            default:
                Log.e("expense load", "Can't load expense list");
        }
    }

    private void init() {
        expenseService = new ExpenseService();
        Response response = expenseService.getAllExpenses(getFlat());
        switch (response.getMessageCode()){
            case Response.MESSAGE_OK:
                this.data = (List<ExpensePOJO>) response.getObject();
                break;
            default:
                this.data =new ArrayList<>();
                Log.e("expense load", "Can't load expense list");
        }
        ListView listView = (ListView) findViewById(R.id.expenseListView);
        this.adapter = new ExpenseArrayAdapter(this, R.layout.expense_list_item, data, listView);
        listView.setAdapter(adapter);

        this.descriptionEdit = (EditText) findViewById(R.id.expenseslist_adddescription);
        this.priceEdit = (EditText) findViewById(R.id.expenseslist_addprice);

        //#addbutton
        Button addButton = (Button) findViewById(R.id.expenselist_addbutton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String description = descriptionEdit.getText().toString();
                double price = Double.parseDouble(priceEdit.getText().toString());
                if (price > 0 && !description.equals("")) {
                    ExpensePOJO expensePOJO = new ExpensePOJO(price, description, 0, getFlat(), getUser());
                    //handle server response
                    Response response = expenseService.newExpense(expensePOJO);
                    if (response.getMessageCode() == Response.MESSAGE_OK) {
                        expensePOJO = (ExpensePOJO) response.getObject();
                        adapter.addElement(expensePOJO);
                    }
                    //TODO: else handle errors
                }
                //TODO: else notify user


            }
        });
        Button refreshButton = (Button) findViewById(R.id.expenselist_refreshbutton);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshData();
            }
        });

    }

    public void showPopupWindow(final ExpensePOJO expensePOJO) {
        LayoutInflater layoutInflater
                = (LayoutInflater) getBaseContext()
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = layoutInflater.inflate(R.layout.expenselist_detailspopup, null);


        boolean error = false;
        ExpensePOJO detailedExpense = null;
        int id = expensePOJO.getId();
        Response response = expenseService.get(id);
        final EditText editDescription = (EditText) popupView.findViewById(R.id.expenseslits_details_description);
        final EditText editPrice = (EditText) popupView.findViewById(R.id.expenseslits_details_price);
        final TextView editError = (TextView) popupView.findViewById(R.id.expenseslits_details_error);
        final TextView editAuthor = (TextView) popupView.findViewById(R.id.expenseslits_details_author);
        final TextView editModDate = (TextView) popupView.findViewById(R.id.expenseslits_details_moddate);
        //make fields not editable
        editAuthor.setKeyListener(null);
        editModDate.setKeyListener(null);
        //load data and handle get(id) errors
        switch (response.getMessageCode()) {
            case Response.MESSAGE_OK:
                detailedExpense = (ExpensePOJO) response.getObject();
                editDescription.setText(detailedExpense.getDescription());
                editPrice.setText(String.valueOf(detailedExpense.getPrice()));
                editAuthor.setText(detailedExpense.getUser());
                //TODO: display date in nice format
                editModDate.setText(detailedExpense.getModificationDate());
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
        final ExpensePOJO finalExpensePOJO = detailedExpense;
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
                okButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!finalError) {
                            String description = editDescription.getText().toString();
                            double price = Double.valueOf(editPrice.getText().toString());

                            boolean changed = false;
                            boolean done = false;
                            if (!description.equals(finalExpensePOJO.getDescription())
                                    || price != finalExpensePOJO.getPrice()) {
                                changed = true;
                            }
                            if (changed && finalExpensePOJO.getDone() != 0) {
                                editError.setText("Already done, can't update");
                                done = true;
                            }
                            if (changed && !done && price > 0 && !description.equals("")) {
                                finalExpensePOJO.setDescription(description);
                                finalExpensePOJO.setPrice(price);
                                //update and handle errors
                                Response response = expenseService.update(finalExpensePOJO);
                                switch (response.getMessageCode()){
                                    case Response.MESSAGE_OK:
                                        //continue closing
                                        expensePOJO.setDescription(description);
                                        expensePOJO.setPrice(price);
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
                });
                Button deleteButton = alertDialog.getButton(DialogInterface.BUTTON_NEUTRAL);

                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Response response = expenseService.delete(finalExpensePOJO);
                        switch (response.getMessageCode()) {
                            case 200:
                                adapter.deleteElement(finalExpensePOJO);
                                alertDialog.dismiss();
                                break;
                            case 404:
                                editError.setText("Can't delete. Please refresh");
                                break;
                            default:
                                editError.setText("Unknown error. Please refresh");
                                break;
                        }
                    }
                });
            }
        });
        // show it
        alertDialog.show();
    }
    //TODO: improve and/or delete (shared pref user info)
    private int getFlat(){
        SharedPreferences sP = getSharedPreferences(UserActivity.USER_PREF_NAME, Context.MODE_PRIVATE);
        return sP.getInt(UserActivity.USER_PREF_FLAT,0);
    }
    //TODO: improve and/or delete (shared pref user info)
    private String getUser(){
        SharedPreferences sP = getSharedPreferences(UserActivity.USER_PREF_NAME, Context.MODE_PRIVATE);
        return sP.getString(UserActivity.USER_PREF_USER,"default");
    }
}
