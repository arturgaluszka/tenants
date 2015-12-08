package com.tenantsproject.flatmates.expenseslist;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.ScrollView;

import com.tenantsproject.flatmates.R;
import com.tenantsproject.flatmates.utils.JSONFileHandler;

import java.util.Map;
import java.util.TreeMap;

public class ExpensesListActivity extends AppCompatActivity {

    private final static String EXPENSELIST_FILE_NAME = "expenselistfile";
    private Button addButton;
    private Map<Integer, CheckBox> expense;
    private ExpenseList expenseList;
    private LinearLayout scrollContent;
    private EditText addValueDescription;
    private EditText addValueNumber;
    private PopupWindow popupWindow;
    private JSONFileHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses_list);

        initObjects();
        /*# loading old data #*/
        this.handler = new JSONFileHandler(EXPENSELIST_FILE_NAME, this);
        expenseList = (ExpenseList) handler.load(ExpenseList.class);
        if (this.expenseList == null) {
            this.expenseList = new ExpenseList();
        }
        reloadView();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

    }

    public void reloadView() {
        for (Expense expense : this.expenseList.getAll()) {
            addToView(expense, false);
        }
    }

    private void addToView(final Expense expense, boolean isNew) {
        final CheckBox checkBox = new CheckBox(ExpensesListActivity.this);
        checkBox.setChecked(expense.isDone());
        checkBox.setText(expense.getDescription() + " [" + expense.getPrice() + " zł]");
        checkBox.setOnCheckedChangeListener(null);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBox.setChecked(!checkBox.isChecked());
                if (ExpensesListActivity.this.popupWindow != null
                        && ExpensesListActivity.this.popupWindow.isShowing()) {
                    ExpensesListActivity.this.popupWindow.dismiss();
                }
                LayoutInflater layoutInflater
                        = (LayoutInflater) getBaseContext()
                        .getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = layoutInflater.inflate(R.layout.activity_expenses_list_set_popup, null);
                ExpensesListActivity.this.popupWindow = new PopupWindow(
                        popupView,
                        LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT);

                Button btnCheck = (Button) popupView.findViewById(R.id.activity_expenses_list_set_pop_checkbutton);
                btnCheck.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkBox.setChecked(!checkBox.isChecked());
                        expense.setDone(!expense.isDone());
                        handler.save(ExpensesListActivity.this.expenseList);
                        ExpensesListActivity.this.popupWindow.dismiss();
                    }
                });
                Button btnDelete = (Button) popupView.findViewById(R.id.activity_expenses_list_set_pop_deletebutton);
                btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ExpensesListActivity.this.expenseList.remove(expense);
                        ViewGroup layout = (ViewGroup) checkBox.getParent();
                        if (null != layout) //for safety only  as you are doing onClick
                            layout.removeView(checkBox);
                        ExpensesListActivity.this.expense.remove(expense.getId());
                        handler.save(ExpensesListActivity.this.expenseList);
                        ExpensesListActivity.this.popupWindow.dismiss();
                    }
                });
                ExpensesListActivity.this.popupWindow.showAsDropDown(ExpensesListActivity.this.addButton, 50, -10);
            }
        });
        this.expense.put(expense.getId(), checkBox);
        if (isNew) {
            this.expenseList.add(expense);
        }
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        scrollContent.addView(checkBox, lp);
    }

    private void initObjects() {
        this.expense = new TreeMap<>();
        this.expenseList = new ExpenseList();
        ScrollView scrollView = (ScrollView) findViewById(R.id.expensesListScrollView);
        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (ExpensesListActivity.this.popupWindow != null) {
                    ExpensesListActivity.this.popupWindow.dismiss();
                }
                return false;
            }
        });
        this.scrollContent = (LinearLayout) findViewById(R.id.expenseactivityfillable);
        this.addValueDescription = (EditText) findViewById(R.id.expensesListAddDescription);
        this.addValueDescription.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (ExpensesListActivity.this.popupWindow != null) {
                    ExpensesListActivity.this.popupWindow.dismiss();
                }
                return false;
            }
        });
        this.addValueNumber = (EditText) findViewById(R.id.expensesListAddValue);
        this.addValueNumber.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (ExpensesListActivity.this.popupWindow != null) {
                    ExpensesListActivity.this.popupWindow.dismiss();
                }
                return false;
            }
        });
        this.addButton = (Button) findViewById(R.id.expensesListAddButton);
        this.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ExpensesListActivity.this.popupWindow != null) {
                    ExpensesListActivity.this.popupWindow.dismiss();
                }
                Double cost = Double.parseDouble(ExpensesListActivity.this.addValueNumber.getText().toString());
                String description = ExpensesListActivity.this.addValueDescription.getText().toString();
                if (cost >= 0 && !description.equals("")) {
                    Expense newExpense = new Expense(cost, description);
                    addToView(newExpense, true);
                    handler.save(ExpensesListActivity.this.expenseList);
                }
            }
        });
    }
}

