package com.example.tenantsproject.flatmates.flat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tenantsproject.flatmates.R;
import com.example.tenantsproject.flatmates.main_list.list.MainActivity;
import com.example.tenantsproject.flatmates.model.rest.Response;
import com.example.tenantsproject.flatmates.model.service.FlatService;

public class EditFlat extends AppCompatActivity {

    EditText txt1;
    EditText txt2;
    EditText txt3;
    String login = "";
    String password = "";
    String confirm = "";
    FlatService fltServ = new FlatService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_flat);

    }

    final int CONTEXT_MENU_USER1 = 1;
    final int CONTEXT_MENU_USER2 = 2;
    final int CONTEXT_MENU_USER3 = 3;

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        //Context menu
        menu.setHeaderTitle("Users requesting access");
        menu.add(Menu.NONE, CONTEXT_MENU_USER1, Menu.NONE, "USER1");
        menu.add(Menu.NONE, CONTEXT_MENU_USER2, Menu.NONE, "USER2");
        menu.add(Menu.NONE, CONTEXT_MENU_USER3, Menu.NONE, "USER3");
    }

    public void onHelp(View v) {
        registerForContextMenu(v);
        openContextMenu(v);
    }

    public void changePass(View v){
        txt3 = (EditText) findViewById(R.id.editText10);
        txt1 = (EditText) findViewById(R.id.editText11);
        txt2 = (EditText) findViewById(R.id.editText12);
        login = txt1.getText().toString();
        password = txt2.getText().toString();
        confirm = txt3.getText().toString();

        if(password.equals(confirm)){
        Response res1 = fltServ.changePassword(this, 3,login, password);
        switch (res1.getMessageCode()) {
            case Response.MESSAGE_OK:
                Toast.makeText(this, getString(R.string.change2), Toast.LENGTH_LONG).show();
                break;
            default:
                Toast.makeText(this, getString(R.string.error4), Toast.LENGTH_LONG).show();

        }}
        else
        {
            Toast.makeText(this, getString(R.string.error), Toast.LENGTH_LONG).show();
        }




    }

    public void back(View v){
        this.finish();
    }

}
