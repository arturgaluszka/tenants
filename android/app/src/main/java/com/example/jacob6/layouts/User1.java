package com.example.jacob6.layouts;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class User1 extends AppCompatActivity {
    ListView ArchiveList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user1);
        RowBean RowBean_data[] = new RowBean[] {


                new RowBean(R.drawable.pawel, "Cheese"),
                new RowBean(R.drawable.pawel, "Milk"),
                new RowBean(R.drawable.pawel, "Ketchup"),
                new RowBean(R.drawable.pawel, "Bread"),
        };

        RowAdapter adapterMain = new RowAdapter(this,
                R.layout.custom_row, RowBean_data);
        ArchiveList = (ListView)findViewById(R.id.listView5);
        ArchiveList.setAdapter(adapterMain);

        ArchiveList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent intent = new Intent(User1.this, Describtion.class);
                startActivity(intent);
            }
        });
    }


}
