package com.tenantsproject.flatmates.expenseslist;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.tenantsproject.flatmates.R;
import com.tenantsproject.flatmates.model.data.Expense;
import com.tenantsproject.flatmates.model.rest.Response;
import com.tenantsproject.flatmates.model.service.ExpenseService;

import java.util.List;

public class ExpenseArrayAdapter extends ArrayAdapter<Expense> {

    private final Context mContext;
    private List<Expense> data;
    private int layoutResourceId;

    private ExpenseService expenseService;

    public ExpenseArrayAdapter(Context mContext, int layoutResourceId, List<Expense> data, ListView v) {
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
        final Expense objectItem = data.get(position);

        final TextView descriptionView = (TextView) convertView.findViewById(R.id.expense_textview1);
        final TextView priceView = (TextView) convertView.findViewById(R.id.expense_textview2);
        CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.expense_checkbox);
        Button moreButton = (Button) convertView.findViewById(R.id.expense_morebutton);

        descriptionView.setText(objectItem.getDescription());
        priceView.setText(String.valueOf(objectItem.getPrice()));
        checkBox.setChecked(objectItem.getDone() != 0);
        checkBox.setOnClickListener(getCheckChangeAction(objectItem));
        moreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ExpensesActivity)mContext).showPopupWindow(objectItem);
            }
        });

        return convertView;
    }

    @NonNull
    private View.OnClickListener getCheckChangeAction(final Expense objectItem) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked = ((CheckBox) v).isChecked();
                objectItem.setDone(isChecked ? 1 : 0);
                Response response = expenseService.update(objectItem);
                //TODO: show errors to user , force refresh
                switch(response.getMessageCode()){
                    case Response.MESSAGE_OK:
                        break;
                    case Response.MESSAGE_CONFLICT:
                        objectItem.setDone(objectItem.getDone()!=0?0:1);
                        ((CheckBox)v).setChecked(!isChecked);
                        break;
                    case Response.MESSAGE_NOT_FOUND:
                        objectItem.setDone(objectItem.getDone()!=0?0:1);
                        ((CheckBox)v).setChecked(!isChecked);
                        break;
                    default:
                        objectItem.setDone(objectItem.getDone()!=0?0:1);
                        ((CheckBox)v).setChecked(!isChecked);
                        break;
                }
                // if no errors
                ((CheckBox) v).setChecked(isChecked);
            }
        };
    }

    @Override
    public int getCount() {
        return data.size();
    }

    public void addElement(Expense expense){
        data.add(expense);
        this.notifyDataSetChanged();
    }
    public void deleteElement(Expense expense){
        for(Expense expenseFromData:data){
            if(expenseFromData.getId()==expense.getId()){
                data.remove(expenseFromData);
                this.notifyDataSetChanged();
                break;
            }
        }
    }
    public void updateData(List<Expense> data){
        this.data=data;
        notifyDataSetChanged();
    }

}
