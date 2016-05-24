package com.example.tenantsproject.flatmates.flat;

import android.app.Activity;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tenantsproject.flatmates.R;
import com.example.tenantsproject.flatmates.model.rest.Response;
import com.example.tenantsproject.flatmates.model.service.FlatService;
import com.example.tenantsproject.flatmates.model.service.UserService;
import com.example.tenantsproject.flatmates.security.Authenticator;

public class SignToFlat extends Activity {

    EditText login,pass;
    String log,pas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_to_flat);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * .4), (int) (height * .36));
    }

    public void loginToFlat(View view){
        FlatService flatService = new FlatService();
        UserService uS = new UserService();
        int flat;
        login = (EditText) findViewById(R.id.editText22);
        pass = (EditText) findViewById(R.id.editText9);
        log = login.getText().toString();
        pas = pass.getText().toString();
        Response r;
        r = flatService.getFlatID(this, log);
        flat = (int)r.getObject();
        switch (r.getMessageCode()) {
            case Response.MESSAGE_OK:
                r = uS.signToFlat(this,getUserId(),flat,pas);
                switch(r.getMessageCode()){
                    case Response.MESSAGE_OK:
                        this.finish();
                        break;
                    default:
                        Toast.makeText(this, "Incorrect login or password", Toast.LENGTH_LONG).show();

                }
                break;
            default:
                Toast.makeText(this, "Check your internet connection", Toast.LENGTH_LONG).show();

        }}

    public int getUserId() {
        final Authenticator aut = new Authenticator();
        final UserService userService = new UserService();
        Response res;
        res = userService.getUserID(this, aut.getLoggedInUserName(this));
        int id = (int) res.getObject();
        return id;
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

