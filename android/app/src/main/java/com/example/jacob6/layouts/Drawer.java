package com.example.jacob6.layouts;


import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;

import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by JaCoB6 on 2016-03-01.
 */
public class Drawer extends FragmentActivity {
    private static String TAG = Drawer.class.getSimpleName();
    ListView mDrawerList;
    ListView MainList;
    RelativeLayout mDrawerPane;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private PagerAdapter mPagerAdapter;



    ArrayList<NavItem> mNavItems = new ArrayList<NavItem>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sidebar);

        RowBean RowBean_data[] = new RowBean[] {

                new RowBean(R.drawable.pawel, "Kupić masło!"),
                new RowBean(R.drawable.pawel, "Chleb"),
                new RowBean(R.drawable.pawel, "Papier toaletowy"),
                new RowBean(R.drawable.pawel, "Ketchup"),
                new RowBean(R.drawable.pawel, "Parówki"),

        };


        RowAdapter adapterMain = new RowAdapter(this,
                R.layout.custom_row, RowBean_data);

        MainList = (ListView)findViewById(R.id.listViewM);

        MainList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {
                Intent nextActivity2 = new Intent(Drawer.this,Popchoose.class);
                startActivity(nextActivity2);

              

                return true;
            }
        });

        MainList.setAdapter(adapterMain);
        mNavItems.add(new NavItem("Flat", "Choose your flat", R.drawable.home));
        mNavItems.add(new NavItem("Statistics", "Show statistics", R.drawable.statistics));
        mNavItems.add(new NavItem("Bin", "All removed things", R.drawable.bin));
        mNavItems.add(new NavItem("Preferences", "Change your preferences", R.drawable.preferences));
        mNavItems.add(new NavItem("About", "Get to know about us", R.drawable.about));


        // DrawerLayout
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        // Populate the Navigtion Drawer with options
        mDrawerPane = (RelativeLayout) findViewById(R.id.drawerPane);
        mDrawerList = (ListView) findViewById(R.id.navList);
        DrawerListAdapter adapter = new DrawerListAdapter(this, mNavItems);
        mDrawerList.setAdapter(adapter);

        // Drawer Item click listeners
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItemFromDrawer(position);
            }
        });
    }
    private void selectItemFromDrawer(int position) {
        Fragment fragment = new PreferencesFragment();

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.mainContent, fragment)
                .commit();

        mDrawerList.setItemChecked(position, true);
        setTitle(mNavItems.get(position).mTitle);

        // Close the drawer
        mDrawerLayout.closeDrawer(mDrawerPane);

        switch (position){
            case 1:
                Intent intent = new Intent(Drawer.this, Statistics.class);
                startActivity(intent);
                break;
            case 3:
                Intent intent2 = new Intent(Drawer.this, Preferences.class);
                startActivity(intent2);
                break;
        }
    }
    public void next(View view){
        Intent nextActivity = new Intent(this,Mylist.class);
        startActivity(nextActivity);

        overridePendingTransition(R.layout.slide_in_right, R.layout.slide_out_left);
    }

}





