package com.example.jacob6.layouts;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by JaCoB6 on 2016-03-01.
 */
public class Mylist extends Activity{
    ListView Mylist;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mylist);
        RowBean RowBean_data[] = new RowBean[] {

                new RowBean(R.drawable.pawel, "Kupić masło!"),
                new RowBean(R.drawable.pawel, "Ketchup"),
                new RowBean(R.drawable.pawel, "Parówki"),

        };
        RowAdapter adapterMain = new RowAdapter(this,
                R.layout.custom_row, RowBean_data);
        Mylist = (ListView)findViewById(R.id.listView3);
        Mylist.setAdapter(adapterMain);
        Mylist.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {
                Intent nextActivity2 = new Intent(Mylist.this, Popchoose.class);
                startActivity(nextActivity2);


                return true;
            }
        });
    }
    public void previous(View view){
        Intent nextActivity = new Intent(this,Drawer.class);
        startActivity(nextActivity);

        overridePendingTransition(R.layout.slide_in_left, R.layout.slide_out_right);
    }
    public void next(View view){
        Intent nextActivity2 = new Intent(this,MyArchiwum.class);
        startActivity(nextActivity2);

        overridePendingTransition(R.layout.slide_in_right, R.layout.slide_out_left);
    }



}
