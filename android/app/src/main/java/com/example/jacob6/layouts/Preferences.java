package com.example.jacob6.layouts;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Preferences extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);
    }

    public void user(View v){
        Intent intent = new Intent(this, User.class);
        startActivity(intent);
    }

    public void notification(View v){
        Intent intent = new Intent(this, Notification.class);
        startActivity(intent);
    }

    public void report(View v){
        Intent intent = new Intent(this, Report.class);
        startActivity(intent);
    }

    public void back(View v){
        finish();
    }
}

