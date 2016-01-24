package com.tenantsproject.flatmates.todolist;

/**
 * Created by JaCoB6 on 2016-01-20.
 */

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tenantsproject.flatmates.R;
import com.tenantsproject.flatmates.utils.JSONFileHandler;

import java.util.Calendar;

public class OnNotificationClick extends TodoList {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.on_notification_click);
        String toShow = getIntent().getStringExtra("toShow");
        TextView textV = (TextView)findViewById(R.id.textView2);
        String mytext = toShow.toString();
        textV.setText(mytext);
    }

    public void tenminutes(View w){
        String toShow = getIntent().getStringExtra("toShow");
        String mytext = toShow.toString();
        Calendar current = Calendar.getInstance();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, 10);
        if(cal.compareTo(current) <= 0){
            Toast.makeText(getApplicationContext(),
                    "Nieprawidłowa data!",
                    Toast.LENGTH_LONG).show();
        }else{
            setAlarm(cal,mytext);
            Intent intent = new Intent(getApplicationContext(),TodoList.class);
            startActivity(intent);
        }

    }

    public void twohours(View w){
        String toShow = getIntent().getStringExtra("toShow");
        String mytext = toShow.toString();
        Calendar current = Calendar.getInstance();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR, 2);
        if(cal.compareTo(current) <= 0){
            Toast.makeText(getApplicationContext(),
                    "Nieprawidłowa data!",
                    Toast.LENGTH_LONG).show();
        }else{
            setAlarm(cal,mytext);
            Intent intent = new Intent(getApplicationContext(),TodoList.class);
            startActivity(intent);
        }

    }

    public void eighthours(View w){
        String toShow = getIntent().getStringExtra("toShow");
        String mytext = toShow.toString();
        Calendar current = Calendar.getInstance();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR, 8);
        if(cal.compareTo(current) <= 0){
            Toast.makeText(getApplicationContext(),
                    "Nieprawidłowa data!",
                    Toast.LENGTH_LONG).show();
        }else{
            setAlarm(cal,mytext);
            Intent intent = new Intent(getApplicationContext(),TodoList.class);
            startActivity(intent);
        }

    }

    public void twantyfourhours(View w){
        String toShow = getIntent().getStringExtra("toShow");
        String mytext = toShow.toString();
        Calendar current = Calendar.getInstance();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR, 24);
        if(cal.compareTo(current) <= 0){
            Toast.makeText(getApplicationContext(),
                    "Nieprawidłowa data!",
                    Toast.LENGTH_LONG).show();
        }else{
            setAlarm(cal,mytext);
            Intent intent = new Intent(getApplicationContext(),TodoList.class);
            startActivity(intent);
        }

    }

    public void done(View w){
        String toShow = getIntent().getStringExtra("toShow");
        String mytext = toShow.toString();
        remove(mytext);
        Intent intent = new Intent(getApplicationContext(),TodoList.class);
        startActivity(intent);

    }
}

