package com.example.tenantsproject.flatmates.main_list.list;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tenantsproject.flatmates.R;
import com.example.tenantsproject.flatmates.main_list.fragments.TabFragment;
import com.example.tenantsproject.flatmates.model.data.Product;
import com.example.tenantsproject.flatmates.model.rest.Response;
import com.example.tenantsproject.flatmates.model.service.ProductService;
import com.example.tenantsproject.flatmates.model.service.UserService;
import com.example.tenantsproject.flatmates.security.Authenticator;

import java.util.ArrayList;

public class Add extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_window);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;


        getWindow().setLayout((int) (width * .4), (int) (height * .2));
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

    public void addProduct(View view) {
        Response add = new Response();
        ProductService productService = new ProductService();
        String des;
        EditText name = (EditText) findViewById(R.id.editText);
        des = name.getText().toString();
        Product added = new Product(des, getMyActualFlat(), getUserId());
        add = productService.addProduct(this, added);
        switch (add.getMessageCode()) {
            case Response.MESSAGE_OK:
                Toast.makeText(this, "Added: " + des, Toast.LENGTH_LONG).show();
                break;
            default:
                Toast toast2 = Toast.makeText(this, "ERROR, Please try again", Toast.LENGTH_LONG);
                toast2.show();

        }
        TabFragment.viewPager.getAdapter().notifyDataSetChanged();
        finish();
    }

    public int getMyActualFlat() {
        int actualFlatnumber;
        Response response = new Response();
        UserService userService = new UserService();
        response = userService.getUserFlats(this, getUserId());
        ArrayList<Integer> pa = new ArrayList<Integer>();
        pa = (ArrayList<Integer>) response.getObject();
        actualFlatnumber = pa.get(0);
        return actualFlatnumber;
    }

    public int getUserId() {
        final Authenticator aut = new Authenticator();
        final UserService userService = new UserService();
        Response res = new Response();
        res = userService.getUserID(this, aut.getLoggedInUserName(this));
        int id = (int) res.getObject();
        return id;
    }
}
