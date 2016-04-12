package com.example.tenantsproject.flatmates.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.tenantsproject.flatmates.R;
import com.example.tenantsproject.flatmates.login.Login;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void login(View v) {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }
}
