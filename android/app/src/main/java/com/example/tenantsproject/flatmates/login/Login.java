package com.example.tenantsproject.flatmates.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.tenantsproject.flatmates.R;
import com.example.tenantsproject.flatmates.main_list.list.MainActivity;
import com.example.tenantsproject.flatmates.model.rest.Response;
import com.example.tenantsproject.flatmates.security.Authenticator;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }

    public void login(View v) {
        Authenticator authenticator = new Authenticator();
        //TODO: change this !
        Response res1 = authenticator.login(this, "Strajk", "test");
       switch (res1.getMessageCode()){
           case Response.MESSAGE_OK:
               Intent intent = new Intent(this, MainActivity.class);
               startActivity(intent);
               break;
           default:
               Log.e("LOGIN","Cant log in");
       }

    }

    public void register(View v) {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }
}
