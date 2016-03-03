package com.example.jacob6.layouts;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);}

    public void register(View view){
        Intent intent = new Intent(getApplicationContext(), Register_activity.class);
        startActivity(intent);
    }
    public void chooseFlat(View view){
        Intent intent = new Intent(getApplicationContext(), Flat_activity.class);
        startActivity(intent);
    }
    public void enter(View view){
        Intent intent = new Intent(getApplicationContext(), Drawer.class);
        startActivity(intent);
    }


}
