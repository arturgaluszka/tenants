package com.example.tenantsproject.flatmates.settings.user;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tenantsproject.flatmates.R;
import com.example.tenantsproject.flatmates.main_list.list.MainActivity;
import com.example.tenantsproject.flatmates.model.rest.Response;
import com.example.tenantsproject.flatmates.model.service.UserService;

import java.util.Locale;

public class Account extends Activity implements AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {

    Spinner spinner;
    ArrayAdapter adapter;
    EditText edText1;
    EditText edText2;
    String oldPass;
    String newPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        spinner = (Spinner) findViewById(R.id.spinner);
        adapter = ArrayAdapter.createFromResource(this, R.array.language, android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        TextView myText = (TextView) view;
        if (position == 1) {
            Locale locale = new Locale("pl");
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            getResources().updateConfiguration(config,getResources().getDisplayMetrics());
            this.finish();
            Intent intent = new Intent(this, Account.class);
            startActivity(intent);
        }

        if(position == 2){
            Locale locale = new Locale("en");
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            getResources().updateConfiguration(config, getResources().getDisplayMetrics());
            this.finish();
            Intent intent = new Intent(this, Account.class);
            startActivity(intent);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void changePass(View v){
        UserService us = new UserService();
        //Response id = us.getUserID(this, "strajk");
        edText1 = (EditText) findViewById(R.id.editText11);
        edText2 = (EditText) findViewById(R.id.editText12);
        newPass = edText1.getText().toString();
        oldPass = edText2.getText().toString();

        Response res = us.changePassword(this, 2, oldPass, newPass);
        switch (res.getMessageCode()) {
            case Response.MESSAGE_OK:
                Toast.makeText(this, "Hasło zmienione" , Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(this, getString(R.string.error) , Toast.LENGTH_SHORT).show();
        }

    }

    public void back(View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
