package com.example.tenantsproject.flatmates.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.tenantsproject.flatmates.R;
import com.example.tenantsproject.flatmates.main_list.list.MainActivity;
import com.example.tenantsproject.flatmates.model.rest.Response;
import com.example.tenantsproject.flatmates.model.service.FlatService;
import com.example.tenantsproject.flatmates.model.service.UserService;
import com.example.tenantsproject.flatmates.security.Authenticator;

/**
 * Created by JaCoB6 on 2016-04-26.
 */
public class Login_flat extends Activity {
    EditText login,pass;
    String log,pas;
    TextView err;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_flat);

    }

    public void register_flat(View view){
        Intent intent = new Intent(this, Register_flat.class);
        startActivity(intent);
        finish();
    }
    public void login_flat(View view){
        FlatService flatService = new FlatService();
        UserService uS = new UserService();
        int flat;
        login = (EditText) findViewById(R.id.editText);
        pass = (EditText) findViewById(R.id.editText2);
        err = (TextView) findViewById(R.id.textView12);
        log = login.getText().toString();
        pas = pass.getText().toString();
        Response r;
        r = flatService.getUserID(this, log);
        flat = (int)r.getObject();
        switch (r.getMessageCode()) {
            case Response.MESSAGE_OK:
                r = uS.signToFlat(this,getUserId(),flat,pas);
                switch(r.getMessageCode()){
                    case Response.MESSAGE_OK:
                        Intent intent = new Intent(this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    default:
                        err.setText("Incorrect login or password");

                }

                break;
            default:
                err.setText("Check your internet connection");
        }


    }

    public int getUserId() {
        final Authenticator aut = new Authenticator();
        final UserService userService = new UserService();
        Response res;
        res = userService.getUserID(this, aut.getLoggedInUserName(this));
        int id = (int) res.getObject();
        return id;
    }


}
