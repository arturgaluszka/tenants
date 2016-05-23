package com.example.tenantsproject.flatmates.main_list.list;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.tenantsproject.flatmates.R;
import com.example.tenantsproject.flatmates.main_list.fragments.PrimaryFragment;
import com.example.tenantsproject.flatmates.model.data.Product;
import com.example.tenantsproject.flatmates.model.rest.Response;
import com.example.tenantsproject.flatmates.model.service.ProductService;
<<<<<<< HEAD
import com.example.tenantsproject.flatmates.model.service.UserService;
import com.example.tenantsproject.flatmates.security.Authenticator;
=======
>>>>>>> d615d36a526aacb054df3d9149c5d63002f7be61

import java.io.Serializable;

public class MainList extends Activity{
    Product prod;
    Intent in;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_menu);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);


        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * .7), (int) (height * .5));
    }

    public void BuyNow(View view) {
        in = getIntent();
        prod = (Product) in.getExtras().getSerializable("Object");
        Intent i = new Intent(this, BuyNowClick.class);
        i.putExtra("Object", prod);
        Log.d("lol", prod.getDescription());
        if(getUserName(prod.getUser()).equals(getUserName(getUserId()))){
        startActivity(i);
        finish();}
        else{
            Toast.makeText(this, getString(R.string.cant_delete), Toast.LENGTH_LONG).show();
        }

    }

    public void deleteFromMainList(View view) {
        in = getIntent();
        prod = (Product) in.getExtras().getSerializable("Object");
        ProductService ps = new ProductService();
<<<<<<< HEAD
        UserService us = new UserService();
        Response r;
        if(getUserName(prod.getCreator()).equals(getUserName(getUserId()))){
        r = ps.removeFromMainList(this, prod);
        Toast.makeText(this, getString(R.string.deleted)+": " + prod.getDescription(), Toast.LENGTH_LONG).show();
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);}
        else{
            Toast.makeText(this, getString(R.string.cant_delete), Toast.LENGTH_LONG).show();
        }

    }

    public int getUserId() {
        final Authenticator aut = new Authenticator();
        final UserService userService = new UserService();
        Response res;
        res = userService.getUserID(this, aut.getLoggedInUserName(this));
        int id = (int) res.getObject();
        return id;
    }

    public String getUserName(int id){
        Response rs;
        UserService nf = new UserService();
        rs = nf.getUser(this, id);
        return String.valueOf(rs.getObject());
=======
        Response r;
        r = ps.removeFromMainList(this, prod);
        Toast.makeText(this, getString(R.string.deleted)+": " + prod.getDescription(), Toast.LENGTH_LONG).show();
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
>>>>>>> d615d36a526aacb054df3d9149c5d63002f7be61
    }

    public void info(View view) {
        in = getIntent();
        prod = (Product) in.getExtras().getSerializable("Object");
        Intent i = new Intent(this, Information.class);
        i.putExtra("Object", prod);
        startActivity(i);
        finish();
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
    public void addToMyList(View view){
        Response re;
        in = getIntent();
        prod = (Product) in.getExtras().getSerializable("Object");
        ProductService productService = new ProductService();
        re = productService.reserveProduct(this, prod);
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
        Toast.makeText(this, "Added to my list", Toast.LENGTH_LONG).show();
    }
}
