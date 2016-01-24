package com.tenantsproject.flatmates.user;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.tenantsproject.flatmates.R;

public class UserActivity extends AppCompatActivity {
    public final static String USER_PREF_NAME = "userpreferences";
    public final static String USER_PREF_USER = "userpreferencesuser";
    public final static String USER_PREF_FLAT = "userpreferencesflat";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        EditText editFlat = (EditText) findViewById(R.id.user_flat);
        EditText editUser = (EditText) findViewById(R.id.user_user);
        //load data
        SharedPreferences preferences = getSharedPreferences(USER_PREF_NAME, Context.MODE_PRIVATE);
        int flat = preferences.getInt(USER_PREF_FLAT, 0);
        String user = preferences.getString(USER_PREF_USER,"default");
        editFlat.setText(String.valueOf(flat));
        editUser.setText(user);
        Button buttonUpdate = (Button) findViewById(R.id.user_update);
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editFlat = (EditText) findViewById(R.id.user_flat);
                EditText editUser = (EditText) findViewById(R.id.user_user);
                //TODO: validation
                int flat = Integer.valueOf(editFlat.getText().toString());
                String user = editUser.getText().toString();
                save(flat,user);
            }
        });
    }

    private void save(int flat, String name){
        SharedPreferences preferences = getSharedPreferences(USER_PREF_NAME, Context.MODE_PRIVATE);
        preferences.edit()
                .putInt(USER_PREF_FLAT,flat)
                .putString(USER_PREF_USER,name)
                .commit();
    }
}
