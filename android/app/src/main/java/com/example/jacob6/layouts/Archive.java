package com.example.jacob6.layouts;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class Archive extends AppCompatActivity {

    ListView MylistArchiveUsers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archive);
        RowBean RowBean_data[] = new RowBean[] {


                new RowBean(R.drawable.pawel, "User1"),
                new RowBean(R.drawable.pawel, "User2"),
                new RowBean(R.drawable.pawel, "User3"),
                new RowBean(R.drawable.pawel, "User4"),
    };
        RowAdapter adapterMain = new RowAdapter(this,
                R.layout.custom_row, RowBean_data);
        MylistArchiveUsers = (ListView)findViewById(R.id.listView);
        MylistArchiveUsers.setAdapter(adapterMain);

        MylistArchiveUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent intent = new Intent(Archive.this, User1.class);
                startActivity(intent);
            }
        });
    }}
