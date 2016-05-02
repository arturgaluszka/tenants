package com.example.tenantsproject.flatmates.flat;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.tenantsproject.flatmates.R;
import com.example.tenantsproject.flatmates.archive.list.RowBeanArchive;
import com.example.tenantsproject.flatmates.archive.list.RowAdapterArchive;
import com.example.tenantsproject.flatmates.model.rest.Response;
import com.example.tenantsproject.flatmates.model.service.FlatService;
import com.example.tenantsproject.flatmates.model.service.UserService;

import java.util.ArrayList;

public class ViewFlat extends Activity {
    ListView userList;
    ArrayList<Integer> usersID = new ArrayList<>();
    ArrayList<String> users = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_flat);

        usersFlatID();
        getUserName();


       /* RowAdapterArchive adapterMain = new RowAdapterArchive(this,
                R.layout.custom_row_archive, RowBean_data);*/

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * .4), (int) (height * .2));

        userList = (ListView) findViewById(R.id.listView);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.listview_row, users);
        userList.setAdapter(arrayAdapter);
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

    void usersFlatID(){
        FlatService fServ = new FlatService();
        Response rs = fServ.getFlatMembers(this, 2);
        switch(rs.getMessageCode()){
            case Response.MESSAGE_OK:
                usersID = (ArrayList<Integer>) rs.getObject();
                if (!usersID.isEmpty()) {
                    for (int i = 0; i < usersID.size(); i++) {
                        Log.d("LOL", String.valueOf(usersID.get(i)));
                    }
                }
                break;
            default:
                Log.d("fsdafa", "Fsafsa");

        }
    }

    public void getUserName(){
        UserService usrServ = new UserService();

        //users = (ArrayList<Object>) rs.getObject();
        if (!usersID.isEmpty()) {
            for (int i = 0; i < usersID.size(); i++) {
                Response rs = usrServ.getUser(this, usersID.get(i));
                switch(rs.getMessageCode()){
                    case Response.MESSAGE_OK:
                        users.add(String.valueOf(rs.getObject()));
                        break;
                    default:
                        Log.d("fsdafa", "Fsafsa");
                }
            }
        }

    }
}
