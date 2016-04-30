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
import com.example.tenantsproject.flatmates.model.service.FlatService;

public class Register_flat extends AppCompatActivity {
    FlatService fltServ;
    EditText txt1,txt2,txt3;
    String name,pass,confirmpass;
    TextView err;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_flat);
    }

    public void toLogin(View view) {
        Intent intent = new Intent(this, Login_flat.class);
        startActivity(intent);
        finish();

    }
    public void createFlat(View view) {
        fltServ = new FlatService();
        txt1 = (EditText) findViewById(R.id.editText3);
        txt2 = (EditText) findViewById(R.id.editText4);
        txt3 = (EditText) findViewById(R.id.editText5);
        err = (TextView) findViewById(R.id.textView15);
        name = txt1.getText().toString();
        pass = txt2.getText().toString();
        confirmpass = txt3.getText().toString();

        if(pass.equals(confirmpass) && !name.isEmpty() && !pass.isEmpty() && !confirmpass.isEmpty()){
        Response res = fltServ.createFlat(this, pass, name);
        switch (res.getMessageCode()) {
            case Response.MESSAGE_OK:
                Toast toast = Toast.makeText(this, "Flat register completed, please login", Toast.LENGTH_LONG);
                toast.show();
                Intent intent = new Intent(this, Login_flat.class);
                startActivity(intent);
                this.finish();
                break;
            default:
                err.setText("Incorrect login or password");
        }}else{
            err.setText("Incorrect login or password");
        }

    }
}
