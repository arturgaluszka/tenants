package com.example.tenantsproject.flatmates.main_list.list;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.widget.TextView;

import com.example.tenantsproject.flatmates.R;
import com.example.tenantsproject.flatmates.model.data.Product;
import com.example.tenantsproject.flatmates.model.rest.Response;
import com.example.tenantsproject.flatmates.model.service.UserService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Information extends Activity {

    Product prod;
    Intent in;
    TextView txt1;
    TextView txt2;
    TextView txt3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informations);

        txt1 = (TextView) findViewById(R.id.textView28);
        txt2 = (TextView) findViewById(R.id.textView29);
        txt3 = (TextView) findViewById(R.id.textView30);

        in = getIntent();
        prod = (Product) in.getExtras().getSerializable("Object");
        long data = prod.getModificationDate();

        txt1.setText(getNameById(prod.getCreator()));
        txt2.setText(prod.getDescription());
        txt3.setText(String.valueOf(new Date(data)));

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * .7), (int) (height * .35));
    }

    public String getNameById(int id){
        Response rs;
        UserService nf = new UserService();
        rs = nf.getUser(this, id);
        return String.valueOf(rs.getObject());
    }

    public boolean onTouchEvent ( MotionEvent event ) {
        // I only care if the event is an UP action
        if ( event.getAction () == MotionEvent.ACTION_UP ) {
            // create a rect for storing the window rect
            Rect r = new Rect ( 0, 0, 0, 0 );
            // retrieve the windows rect
            this.getWindow ().getDecorView ().getHitRect ( r );
            // check if the event position is inside the window rect
            boolean intersects = r.contains ( (int) event.getX (), (int) event.getY () );
            // if the event is not inside then we can close the activity
            if ( !intersects ) {
                // close the activity
                this.finish ();
                // notify that we consumed this event
                return true;
            }
        }
        // let the system handle the event
        return super.onTouchEvent ( event );
    }
}
