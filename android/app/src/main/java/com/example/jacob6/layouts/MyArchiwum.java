package com.example.jacob6.layouts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

/**
 * Created by JaCoB6 on 2016-03-03.
 */
public class MyArchiwum extends Activity {
    ListView MylistArch;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myarchiwum);
        RowBean RowBean_data[] = new RowBean[] {


                new RowBean(R.drawable.pawel, "Chleb"),
                new RowBean(R.drawable.pawel, "Papier toaletowy"),
                new RowBean(R.drawable.pawel, "Ketchup"),
                new RowBean(R.drawable.pawel, "Parówki"),

        };
        RowAdapter adapterMain = new RowAdapter(this,
                R.layout.custom_row, RowBean_data);
        MylistArch = (ListView)findViewById(R.id.listView4);
        MylistArch.setAdapter(adapterMain);
        MylistArch.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {
                Intent nextActivity2 = new Intent(MyArchiwum.this, InformationAboutToDo.class);
                startActivity(nextActivity2);


                return true;
            }
        });
    }
    public void previous(View view){
        Intent nextActivity = new Intent(this,Mylist.class);
        startActivity(nextActivity);

        overridePendingTransition(R.layout.slide_in_left, R.layout.slide_out_right);
    }

}
