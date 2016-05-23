package com.example.tenantsproject.flatmates.main_list.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tenantsproject.flatmates.R;
import com.example.tenantsproject.flatmates.archive.list.Archives;
import com.example.tenantsproject.flatmates.flat.Flat;
import com.example.tenantsproject.flatmates.login.Login;
import com.example.tenantsproject.flatmates.main_list.fragments.TabFragment;
import com.example.tenantsproject.flatmates.model.rest.Response;
import com.example.tenantsproject.flatmates.model.service.FlatService;
import com.example.tenantsproject.flatmates.model.service.ProductService;
import com.example.tenantsproject.flatmates.model.service.UserService;
import com.example.tenantsproject.flatmates.security.Authenticator;
import com.example.tenantsproject.flatmates.settings.user.Account;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //DrawerLayout mDrawerLayout;
   // NavigationView mNavigationView;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    Button searchButton;
    TextView txt1;
    Intent in;
    public int pos = -1;
    String name;


    ProductService productService = new ProductService();
    public static int FILTER = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list);
        searchButton = (Button) findViewById(R.id.searchButton);
        txt1 = (TextView) findViewById(R.id.textView9);
        try{

                in = getIntent();
                pos = Integer.parseInt(in.getExtras().getString("FlatID"));
                name = in.getExtras().getString("Name");
                Log.d("Name", name);
                Log.d("FlatID", String.valueOf(pos));
                txt1.setText(name);
        }
        catch (NullPointerException e){
            txt1.setText(getMyFlatName());
        }



        Toolbar toolbar2 = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar2);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                check(view);

            }
        });
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


        /**
         *Setup the DrawerLayout and NavigationView
         */

        //mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
       // mNavigationView = (NavigationView) findViewById(R.id.shitstuff);

        /**
         * Lets inflate the very first fragment
         * Here , we are inflating the TabFragment as the first Fragment
         */

        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.containerView, new TabFragment()).commit();
        /**
         * Setup click events on the Navigation View Items.
         */

       /* mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                mDrawerLayout.closeDrawers();


                return false;
            }

        });*/


        /**
         * Setup Drawer Toggle of the Toolbar
         */

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
      /* ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.app_name,
                R.string.app_name);

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerToggle.syncState();*/


    }


    public void check(View view) {
        startActivity(new Intent(this, Add.class));
    }


    public String getMyFlatName(){
        Response response;
        String name;
        FlatService flServ = new FlatService();
        response = flServ.getFlat(this, getMyActualFlat());
        name = (String) response.getObject();
        return name;
    }

    public int getMyActualFlat() {
        int actualFlatnumber;
        Response response;
        UserService userService = new UserService();
        response = userService.getUserFlats(this, getUserId());
        ArrayList<Integer> pa;
        pa = (ArrayList<Integer>) response.getObject();
        actualFlatnumber = pa.get(0);
        return actualFlatnumber;
    }

    public int getUserId() {
        final Authenticator aut = new Authenticator();
        final UserService userService = new UserService();
        Response res;
        res = userService.getUserID(this, aut.getLoggedInUserName(this));
        int id = (int) res.getObject();
        return id;
    }

    public void search(View view) {
        //Creating the instance of PopupMenu

        PopupMenu popup = new PopupMenu(this, view);
        //Inflating the Popup using xml file
        popup.getMenuInflater().inflate(R.layout.search_popup_menu, popup.getMenu());

        //registering popup with OnMenuItemClickListener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.all) {
                    FILTER = productService.FILTER_ALL;
                    TabFragment.viewPager.getAdapter().notifyDataSetChanged();
                }
                if (item.getItemId() == R.id.active) {
                    FILTER = productService.FILTER_ACTIVE;
                    TabFragment.viewPager.getAdapter().notifyDataSetChanged();
                }
                if (item.getItemId() == R.id.reserved) {
                    FILTER = productService.FILTER_RESERVED;
                    TabFragment.viewPager.getAdapter().notifyDataSetChanged();
                }
                return true;
            }
        });
        popup.show();
    }

    public void options(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        popup.getMenuInflater().inflate(R.layout.options_popup_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.flat) {
                    startActivity(new Intent(getApplicationContext(), Flat.class));
                }
                if (item.getItemId() == R.id.account) {
                    startActivity(new Intent(getApplicationContext(), Account.class));

                }
                if (item.getItemId() == R.id.logout) {
                    Authenticator at = new Authenticator();
                    at.logOut(MainActivity.this);
                    finish();
                    startActivity(new Intent(getApplicationContext(), Login.class));

                }

                //Toast.makeText(MainActivity.this, "You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show();

                return true;
            }
        });
        popup.show();
    }

    public void flat(View view) {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }

    public void archives(View view) {
        Intent i;
        i = new Intent(getApplicationContext(), Archives.class);
        i.putExtra("Name", name);
        i.putExtra("FlatID", pos);
        startActivity(i);
        finish();
    }


}


