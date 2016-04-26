package com.example.tenantsproject.flatmates.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.tenantsproject.flatmates.R;
import com.example.tenantsproject.flatmates.main_list.list.MainActivity;
import com.example.tenantsproject.flatmates.model.rest.Response;
import com.example.tenantsproject.flatmates.security.Authenticator;

public class Login extends AppCompatActivity {
    String login = "";
    String password = "";
    EditText log;
    EditText reg;
    TextView err;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }



    public void login(View v) {
        Authenticator authenticator = new Authenticator();

        log = (EditText) findViewById(R.id.editText);
        reg = (EditText) findViewById(R.id.editText2);
        err = (TextView) findViewById(R.id.textView12);
        login = log.getText().toString();
        password = reg.getText().toString();
        Response res1 = authenticator.login(this, login, password);
        switch (res1.getMessageCode()) {
            case Response.MESSAGE_OK:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            default:
                err.setText("Incorrect login or password");
        }

    }

    public void register(View v) {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }
}
