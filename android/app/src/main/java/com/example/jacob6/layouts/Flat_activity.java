package com.example.jacob6.layouts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by JaCoB6 on 2016-03-01.
 */
public class Flat_activity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flat);
    }
    public void newFlat(View view){
        Intent intent2 = new Intent(getApplicationContext(), newFlat_acitivity.class);
        startActivity(intent2);
    }
    public void enter(View view){
        Intent intent2 = new Intent(getApplicationContext(), Drawer.class);
        startActivity(intent2);
    }
}

