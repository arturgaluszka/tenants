package com.tenantsproject.flatmates;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.tenantsproject.flatmates.expenseslist.ExpensesActivity;
import com.tenantsproject.flatmates.todolist.TodoList;
import com.tenantsproject.flatmates.user.UserActivity;
import com.tenantsproject.flatmates.wallet.WalletActivity;

public class MainActivity extends AppCompatActivity {

    Button b;
    Button b2;
    Button b3;
    Button b4;
    Button b5;
    Button b6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b = (Button) findViewById(R.id.button);
        b2 = (Button) findViewById(R.id.button2);
        b3 = (Button) findViewById(R.id.button3);
        b4 = (Button) findViewById(R.id.button4);
        b5 = (Button) findViewById(R.id.button5);
        b6 = (Button) findViewById(R.id.button6);
    }

    public void Action(View v){
        Intent i = new Intent(this, TodoList.class);
        startActivity(i);
    }

    public void startWallet(View v) {
        Intent intent = new Intent(this, WalletActivity.class);
        startActivity(intent);
    }

    public void startExpenseList(View v) {
        Intent intent = new Intent(this, ExpensesActivity.class);
        startActivity(intent);
    }

    public void startUser(View v){
        Intent intent = new Intent(this, UserActivity.class);
        startActivity(intent);
    }

    public void Exit(View V){
        System.exit(0);
    }
}
