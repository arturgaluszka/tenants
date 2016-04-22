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

import com.example.tenantsproject.flatmates.R;

public class Flat extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flat);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * .7), (int) (height * .5));
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
        if (item.getTitle() == "Delete") {
            Intent i = new Intent(this, DeleteFlat.class);
            startActivity(i);
        }
        return super.onContextItemSelected(item);
    }

    public void addFlat(View v){
        Intent i = new Intent(this, AddFlat.class);
        startActivity(i);
    }

    public void deleteFlat(View v){
        Intent i = new Intent(this, DeleteFlat.class);
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
