package com.example.tenantsproject.flatmates.main_list.list;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tenantsproject.flatmates.R;
import com.example.tenantsproject.flatmates.model.data.Product;
import com.example.tenantsproject.flatmates.model.rest.Response;
import com.example.tenantsproject.flatmates.model.service.ProductService;

public class BuyNowClick extends Activity {
    Product prod;
    Intent in;
    ProductService prdServ = new ProductService();
    EditText edtText;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buy_now);


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

    public void buy(View v){
        edtText = (EditText) findViewById(R.id.editText);
        Response buy;
        in = getIntent();
        prod = (Product) in.getExtras().getSerializable("Object");
        buy = prdServ.buyProduct(this, prod);
        switch (buy.getMessageCode()) {
            case Response.MESSAGE_OK:
                Toast.makeText(this, "You buy: " + prod.getDescription(), Toast.LENGTH_LONG).show();
                prod.setPrice(Double.parseDouble(edtText.getText().toString()));
                break;
            default:
                Toast toast2 = Toast.makeText(this, "ERROR, Please try again", Toast.LENGTH_LONG);
                toast2.show();

        }

    }
}
