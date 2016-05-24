package com.example.tenantsproject.flatmates.flat;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.tenantsproject.flatmates.R;
import com.example.tenantsproject.flatmates.login.Login_flat;
import com.example.tenantsproject.flatmates.model.rest.Response;
import com.example.tenantsproject.flatmates.model.service.UserService;
import com.example.tenantsproject.flatmates.security.Authenticator;

import java.util.ArrayList;

public class Flat extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flat);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * .7), (int) (height * .62));
    }

    final int CONTEXT_MENU_ADD = 1;
    final int CONTEXT_MENU_EDIT = 2;
    final int CONTEXT_MENU_DELETE = 3;

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        //Context menu
        menu.setHeaderTitle("Edit Flat");
        menu.add(Menu.NONE, CONTEXT_MENU_ADD, Menu.NONE, "Add");
        menu.add(Menu.NONE, CONTEXT_MENU_EDIT, Menu.NONE, "Edit");
        menu.add(Menu.NONE, CONTEXT_MENU_DELETE, Menu.NONE, "Delete");
    }

    public boolean onContextItemSelected(MenuItem item) {

        if (item.getTitle() == "Add") {
            Intent i = new Intent(this, AddFlat.class);
            startActivity(i);
        }
        if (item.getTitle() == "Edit") {
            Intent i = new Intent(this, EditFlat.class);
            startActivity(i);
        }
        return super.onContextItemSelected(item);
    }

    public void signToFlat(View v) {
        Intent i = new Intent(this, SignToFlat.class);
        startActivity(i);
    }

    public void addFlat(View v) {
        Intent i = new Intent(this, AddFlat.class);
        startActivity(i);
    }


    public void onHelp(View v) {
        Intent i = new Intent(this, EditFlat.class);
        startActivity(i);
    }

    public void viewFlat(View v) {
        Intent i = new Intent(this, ViewFlat.class);
        startActivity(i);
    }

    public void back(View v) {
        this.finish();
    }

    public void sgnOut(View v){
        UserService uServ = new UserService();
        Response r;
        r = uServ.signOut(this, getUserId(), getMyActualFlat());
        switch (r.getMessageCode()) {
            case Response.MESSAGE_OK:
                switch(r.getMessageCode()){
                    case Response.MESSAGE_OK:
                        Toast.makeText(this, getString(R.string.success), Toast.LENGTH_LONG).show();
                        startActivity(new Intent(this, Login_flat.class));
                        finish();
                        break;
                    default:
                        Toast.makeText(this, getString(R.string.error5), Toast.LENGTH_LONG).show();

                }
                break;
            default:
                Toast.makeText(this, getString(R.string.error2), Toast.LENGTH_LONG).show();

        }
    }

    public int getMyActualFlat() {
        int actualFlatnumber;
        Response response;
        UserService userService = new UserService();
        response = userService.getUserFlats(this, getUserId());
        ArrayList<Integer> pa;
        pa = (ArrayList<Integer>) response.getObject();
        actualFlatnumber = pa.get(0);
        return actualFlatnumber;
    }

    public int getUserId() {
        final Authenticator aut = new Authenticator();
        final UserService userService = new UserService();
        Response res;
        res = userService.getUserID(this, aut.getLoggedInUserName(this));
        int id = (int) res.getObject();
        return id;
    }

    public void flatUsers(View v){
        startActivity(new Intent(this, ViewFlats.class));
    }

    public boolean onTouchEvent(MotionEvent event) {
        // I only care if the event is an UP action
        if (event.getAction() == MotionEvent.ACTION_UP) {
            // create a rect for storing the window rect
            Rect r = new Rect(0, 0, 0, 0);
            // retrieve the windows rect
            this.getWindow().getDecorView().getHitRect(r);
            // check if the event position is inside the window rect
            boolean intersects = r.contains((int) event.getX(), (int) event.getY());
            // if the event is not inside then we can close the activity
            if (!intersects) {
                // close the activity
                this.finish();
                // notify that we consumed this event
                return true;
            }
        }
        // let the system handle the event
        return super.onTouchEvent(event);
    }
}
