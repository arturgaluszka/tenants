package com.tenantsproject.flatmates.wallet;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.tenantsproject.flatmates.R;
import java.util.ArrayList;


public class HistoryActivity extends AppCompatActivity {

    private ListView list;
    ArrayList<String> dateBaseList;
    private ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        list = (ListView) findViewById(R.id.listView1);

        Intent rec = getIntent();
        String score = rec.getStringExtra("delete");
        dateBaseList = new ArrayList<String>();
        dateBaseList.add(score);

        adapter = new ArrayAdapter<String>(this, R.layout.history_element_list, databaseList());
        list.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }


    public void onClick(View v) {
        finish();
    }


}
