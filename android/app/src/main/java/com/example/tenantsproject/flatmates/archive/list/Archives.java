package com.example.tenantsproject.flatmates.archive.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.tenantsproject.flatmates.R;
import com.example.tenantsproject.flatmates.archive.fragments.TabFragment_archives;
import com.example.tenantsproject.flatmates.archive.fragments.TabFragment_users;
import com.example.tenantsproject.flatmates.flat.Flat;
import com.example.tenantsproject.flatmates.main_list.fragments.TabFragment;
import com.example.tenantsproject.flatmates.main_list.list.Add;
import com.example.tenantsproject.flatmates.main_list.list.MainActivity;

public class Archives extends AppCompatActivity {
    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.archives);


        //   ActionBar actionBar = getSupportActionBar();
        //   actionBar.hide();


        /**
         *Setup the DrawerLayout and NavigationView
         */

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mNavigationView = (NavigationView) findViewById(R.id.shitstuff);

        /**
         * Lets inflate the very first fragment
         * Here , we are inflating the TabFragment as the first Fragment
         */

        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.containerView, new TabFragment_archives()).commit();
        /**
         * Setup click events on the Navigation View Items.
         */

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                mDrawerLayout.closeDrawers();





                return false;
            }

        });

        /**
         * Setup Drawer Toggle of the Toolbar
         */

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.app_name,
                R.string.app_name);

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerToggle.syncState();

    }

    public void check(View view) {
        startActivity(new Intent(this, Add.class));
    }
    public void flat(View view) {

        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }

    public void archives(View view) {
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.containerView, new TabFragment_archives()).commit();
        // startActivity(new Intent(getApplicationContext(), Archives.class));
        //  finish();
    }
    public void options(View view) {
        //Creating the instance of PopupMenu

        PopupMenu popup = new PopupMenu(this, view);
        //Inflating the Popup using xml file
        popup.getMenuInflater().inflate(R.layout.options_popup_menu, popup.getMenu());

        //registering popup with OnMenuItemClickListener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.flat) {
                    startActivity(new Intent(getApplicationContext(), Flat.class));
                }
                if (item.getItemId() == R.id.account) {

                }
                Toast.makeText(Archives.this, "You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show();

                return true;
            }
        });
        popup.show();
    }
    public void search(View view) {
        //Creating the instance of PopupMenu

        PopupMenu popup = new PopupMenu(this, view);
        //Inflating the Popup using xml file
        popup.getMenuInflater().inflate(R.layout.search_popup_menu_archive, popup.getMenu());

        //registering popup with OnMenuItemClickListener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {

                Toast.makeText(Archives.this, "You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.containerView, new TabFragment_users()).commit();
                return true;
            }
        });
        popup.show();
    }
}
