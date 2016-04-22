package com.example.tenantsproject.flatmates.settings.others;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.tenantsproject.flatmates.R;

public class Settings extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * .7), (int) (height * .4));
    }

    final int CONTEXT_MENU_NICK = 1;
    final int CONTEXT_MENU_PASS = 2;
    final int CONTEXT_MENU_NUMBER = 3;
    final int CONTEXT_MENU_MEIL = 4;
    final int CONTEXT_MENU_AVATAR = 5;

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        //Context menu
        menu.setHeaderTitle("Account Settings");
        menu.add(Menu.NONE, CONTEXT_MENU_NICK, Menu.NONE, "Nickname");
        menu.add(Menu.NONE, CONTEXT_MENU_AVATAR, Menu.NONE, "Avatar");
        menu.add(Menu.NONE, CONTEXT_MENU_PASS, Menu.NONE, "Password");
        menu.add(Menu.NONE, CONTEXT_MENU_NUMBER, Menu.NONE, "Phone number");
        menu.add(Menu.NONE, CONTEXT_MENU_MEIL, Menu.NONE, "E-mail");
    }

    public void user(View v) {
        registerForContextMenu(v);
        openContextMenu(v);
    }

    public boolean onContextItemSelected(MenuItem item) {
        return super.onContextItemSelected(item);
    }


}
