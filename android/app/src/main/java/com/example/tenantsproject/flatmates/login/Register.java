package com.example.tenantsproject.flatmates.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tenantsproject.flatmates.R;
import com.example.tenantsproject.flatmates.model.rest.Response;
import com.example.tenantsproject.flatmates.model.service.UserService;


public class Register extends AppCompatActivity {
    String mLogin,mPassword,mConfirmPassword = "";
    EditText login,password,confirmPassword;
    TextView err;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void login(View v) {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    public void save(View v) {
        UserService userService = new UserService();
        login = (EditText) findViewById(R.id.editText3);
        password = (EditText) findViewById(R.id.editText4);
        confirmPassword = (EditText) findViewById(R.id.editText5);
        err = (TextView) findViewById(R.id.textView15);
        mLogin = login.getText().toString();
        mPassword = password.getText().toString();
        mConfirmPassword = confirmPassword.getText().toString();

        if(mPassword.equals(mConfirmPassword) && !mLogin.isEmpty() && !mPassword.isEmpty() && !mConfirmPassword.isEmpty()){
        Response res2 = userService.createUser(this,mLogin,mPassword);
        switch (res2.getMessageCode()) {
            case Response.MESSAGE_OK:
                Intent intent = new Intent(this, Login.class);
                startActivity(intent);
                Toast toast = Toast.makeText(this, "Register completed, please login", Toast.LENGTH_LONG);
                toast.show();
                break;
            default:
                err.setText("Incorrect login or password");
        }}else{
            err.setText("Incorrect password");
        }

    }
}
