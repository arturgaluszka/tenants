package com.example.tenantsproject.flatmates.flat;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.tenantsproject.flatmates.R;
import com.example.tenantsproject.flatmates.main_list.fragments.PrimaryFragment;
import com.example.tenantsproject.flatmates.main_list.list.MainActivity;
import com.example.tenantsproject.flatmates.model.rest.Response;
import com.example.tenantsproject.flatmates.model.service.FlatService;
import com.example.tenantsproject.flatmates.model.service.UserService;
import com.example.tenantsproject.flatmates.security.Authenticator;

import java.util.ArrayList;

public class ViewFlats extends Activity {

    ArrayList<Integer> flatsID = new ArrayList<>();
    ArrayList<String> flats = new ArrayList<>();
    ListView listFlats;
    String flat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_flats);
        usersFlatID();
        usersFlatName();
        listFlats = (ListView) findViewById(R.id.listView);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.listview_row, flats);
        listFlats.setAdapter(arrayAdapter);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * .4), (int) (height * .2));

        listFlats.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                for (int i = 0; i < flatsID.size(); i++) {
                    if (position == i) {
                        Intent a = new Intent(getBaseContext(), MainActivity.class);
                        flat = flats.get(i);
                        Log.d("Nazwa", flat);
                        Log.d("Position", String.valueOf(position));
                        a.putExtra("Name", flat);
                        a.putExtra("Position", String.valueOf(position));
                       /* Bundle bundle = new Bundle();
                        bundle.putString("Position", String.valueOf(position));
                        android.app.Fragment prim2 = new android.app.Fragment();
                        prim2.setArguments(bundle);*/
                        startActivity(a);

                    }
                }
            }
        });
    }


    void usersFlatID() {
        UserService uServ = new UserService();
        Response rs = uServ.getUserFlats(this, getUserId());
        switch (rs.getMessageCode()) {
            case Response.MESSAGE_OK:
                flatsID = (ArrayList<Integer>) rs.getObject();
                if (!flatsID.isEmpty()) {
                    for (int i = 0; i < flatsID.size(); i++) {
                        Log.d("LOL", String.valueOf(flatsID.get(i)));
                    }
                }
                break;
            default:
                Log.d("fsdafa", "Fsafsa");

        }
    }

    public void usersFlatName() {
        FlatService fServ = new FlatService();
        Response rs;
        //Response rs =
        if (!flatsID.isEmpty()) {
            for (int i = 0; i < flatsID.size(); i++) {
                rs = fServ.getFlat(this, flatsID.get(i));
                flats.add((String) rs.getObject());
                Log.d("LOL", String.valueOf(flats.get(i)));
            }
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
