package com.example.jacob6.layouts;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Statistics extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
    }

    public void archive(View v){
        Intent intent = new Intent(this, Archive.class);
        startActivity(intent);
    }

    public void back(View v){
        finish();
    }
}
