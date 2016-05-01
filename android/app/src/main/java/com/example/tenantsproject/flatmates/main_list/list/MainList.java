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

import com.example.tenantsproject.flatmates.R;
import com.example.tenantsproject.flatmates.model.data.Product;

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
        startActivity(i);
        finish();

    }

    public void info(View view) {
        startActivity(new Intent(this, Information.class));
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
}