package com.example.tenantsproject.flatmates.flat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tenantsproject.flatmates.R;
import com.example.tenantsproject.flatmates.model.rest.Response;
import com.example.tenantsproject.flatmates.model.service.FlatService;
import com.example.tenantsproject.flatmates.model.service.UserService;
import com.example.tenantsproject.flatmates.security.Authenticator;

public class SignToFlat extends AppCompatActivity {

    EditText login,pass;
    String log,pas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_to_flat);
    }

    public void loginToFlat(View view){
        FlatService flatService = new FlatService();
        UserService uS = new UserService();
        int flat;
        login = (EditText) findViewById(R.id.editText22);
        pass = (EditText) findViewById(R.id.editText9);
        log = login.getText().toString();
        pas = pass.getText().toString();
        Response r;
        r = flatService.getFlatID(this, log);
        flat = (int)r.getObject();
        switch (r.getMessageCode()) {
            case Response.MESSAGE_OK:
                r = uS.signToFlat(this,getUserId(),flat,pas);
                switch(r.getMessageCode()){
                    case Response.MESSAGE_OK:
                        this.finish();
                        break;
                    default:
                        Toast.makeText(this, "Incorrect login or password", Toast.LENGTH_LONG).show();

                }
                break;
            default:
                Toast.makeText(this, "Check your internet connection", Toast.LENGTH_LONG).show();

        }}

    public int getUserId() {
        final Authenticator aut = new Authenticator();
        final UserService userService = new UserService();
        Response res;
        res = userService.getUserID(this, aut.getLoggedInUserName(this));
        int id = (int) res.getObject();
        return id;
    }


    }

