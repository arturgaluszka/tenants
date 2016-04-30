package com.example.tenantsproject.flatmates.flat;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tenantsproject.flatmates.R;
import com.example.tenantsproject.flatmates.login.Login_flat;
import com.example.tenantsproject.flatmates.main_list.list.MainActivity;
import com.example.tenantsproject.flatmates.model.rest.Response;
import com.example.tenantsproject.flatmates.model.service.FlatService;

public class AddFlat extends Activity {

    String pass ="";
    String name = "";
    String conf = "";
    TextView txt1;
    TextView txt2;
    TextView txt3;
    FlatService fltServ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_flat);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * .4), (int) (height * .7));
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

    public void createFlat(View v){
        fltServ = new FlatService();
        txt1 = (EditText) findViewById(R.id.editText22);
        txt2 = (EditText) findViewById(R.id.editText9);
        txt3 = (EditText) findViewById(R.id.editText7);
        name = txt1.getText().toString();
        pass = txt2.getText().toString();
        conf = txt3.getText().toString();
        if(pass.equals(conf) && !name.isEmpty() && !pass.isEmpty() && !conf.isEmpty()){
            Response res = fltServ.createFlat(this, pass, name);
            switch (res.getMessageCode()) {
                case Response.MESSAGE_OK:
                    Toast toast = Toast.makeText(this, "Flat created", Toast.LENGTH_LONG);
                    toast.show();
                    Intent intent = new Intent(this, Login_flat.class);
                    startActivity(intent);
                    this.finish();
                    break;
                default:
                    Toast toast1 = Toast.makeText(this, "Incorrect login or password", Toast.LENGTH_LONG);
                    toast1.show();
            }}else{
            Toast toast2 = Toast.makeText(this, "Incorrect login or password", Toast.LENGTH_LONG);
            toast2.show();
        }
    }
}
