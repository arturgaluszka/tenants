package com.example.tenantsproject.flatmates.flat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.View;

import com.example.tenantsproject.flatmates.R;

public class EditFlat extends AppCompatActivity {

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

}
