package com.tenantsproject.flatmates.expenseslist;

import android.app.Activity;
import android.content.Context;
import android.text.method.CharacterPickerDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import com.tenantsproject.flatmates.R;
import com.tenantsproject.flatmates.model.data.ExpensePOJO;
import com.tenantsproject.flatmates.model.rest.Response;
import com.tenantsproject.flatmates.model.service.ExpenseService;

import java.util.List;

public class ExpenseArrayAdapter extends ArrayAdapter<ExpensePOJO> {

    private final Context mContext;
    private List<ExpensePOJO> data;
    private int layoutResourceId;

    private ExpenseService expenseService;

    public ExpenseArrayAdapter(Context mContext, int layoutResourceId, List<ExpensePOJO> data, ListView v) {
        super(mContext, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.data = data;

        this.expenseService = new ExpenseService();
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        if (convertView == null) {
            // inflate the layout
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(layoutResourceId, parent, false);
        }
        final ExpensePOJO objectItem = data.get(position);

        final TextView descriptionView = (TextView) convertView.findViewById(R.id.expense_textview1);
        final TextView priceView = (TextView) convertView.findViewById(R.id.expense_textview2);
        CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.expense_checkbox);
        Button moreButton = (Button) convertView.findViewById(R.id.expense_morebutton);

        descriptionView.setText(objectItem.getDescription());
        priceView.setText(String.valueOf(objectItem.getPrice()));
        checkBox.setChecked(objectItem.getDone() != 0);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked = ((CheckBox) v).isChecked();
                objectItem.setDone(isChecked ? 1 : 0);
                //TODO: handle errors
                expenseService.update(objectItem);
                // if no errors
                ((CheckBox) v).setChecked(isChecked);
            }
        });
        moreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ExpensesActivity)mContext).showPopupWindow(objectItem);
            }
        });

        return convertView;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    public void addElement(ExpensePOJO expensePOJO){
        data.add(expensePOJO);
        this.notifyDataSetChanged();
    }
    public void deleteElement(ExpensePOJO expensePOJO){
        for(ExpensePOJO expense:data){
            if(expense.getId()==expensePOJO.getId()){
                data.remove(expense);
                this.notifyDataSetChanged();
            }
        }
    }
    public void updateData(List<ExpensePOJO> data){
        this.data=data;
        notifyDataSetChanged();
    }

}
