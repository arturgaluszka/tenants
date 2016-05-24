package com.example.tenantsproject.flatmates.archive.list;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.tenantsproject.flatmates.R;
import com.example.tenantsproject.flatmates.main_list.list.ArchInformation;
import com.example.tenantsproject.flatmates.model.data.Product;
import com.example.tenantsproject.flatmates.model.rest.Response;
import com.example.tenantsproject.flatmates.model.service.StatsService;
import com.example.tenantsproject.flatmates.model.service.UserService;
import com.example.tenantsproject.flatmates.security.Authenticator;

public class MyArchiveList extends Activity {

    Product prod;
    Intent in;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_archive_list);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * .7), (int) (height * .23));
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

    public void info(View view) {
        in = getIntent();
        prod = (Product) in.getExtras().getSerializable("Object");
        Intent i = new Intent(this, ArchInformation.class);
        i.putExtra("Object", prod);
        startActivity(i);
        finish();
    }

    public void undo(View view) {
        Response re;
        in = getIntent();
        prod = (Product) in.getExtras().getSerializable("Object");
        StatsService stsServ = new StatsService();
        //TODO for Arur check reserver Product don't working
        if(getUserName(prod.getUser()).equals(getUserName(getUserId()))){
        re = stsServ.undoBuy(this, prod);
        Intent i = new Intent(this, Archives.class);
        Toast.makeText(this, getString(R.string.undo), Toast.LENGTH_SHORT).show();
        startActivity(i);
        finish();}
        else{
            Toast.makeText(this, getString(R.string.error7), Toast.LENGTH_SHORT).show();
        }
    }

    public String getUserName(int id){
        Response rs;
        UserService nf = new UserService();
        rs = nf.getUser(this, id);
        return String.valueOf(rs.getObject());
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
