package com.example.tenantsproject.flatmates.flat;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.widget.ListView;

import com.example.tenantsproject.flatmates.R;
import com.example.tenantsproject.flatmates.archive.list.RowBeanArchive;
import com.example.tenantsproject.flatmates.archive.list.RowAdapterArchive;

public class ViewFlat extends Activity {
    ListView userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_flat);

        RowBeanArchive RowBean_data[] = new RowBeanArchive[]{

                new RowBeanArchive(R.drawable.avatar, "User1", "0"),
                new RowBeanArchive(R.drawable.avatar2, "User2", "0"),
                new RowBeanArchive(R.drawable.avatar3, "User3", "0"),


        };

        RowAdapterArchive adapterMain = new RowAdapterArchive(this,
                R.layout.custom_row_archive, RowBean_data);
        userList = (ListView) findViewById(R.id.listView);
        userList.setAdapter(adapterMain);

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
}
