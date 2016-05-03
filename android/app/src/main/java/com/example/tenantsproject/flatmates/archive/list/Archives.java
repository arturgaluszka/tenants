package com.example.tenantsproject.flatmates.archive.list;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tenantsproject.flatmates.R;
import com.example.tenantsproject.flatmates.archive.fragments.TabFragment_archives;
import com.example.tenantsproject.flatmates.archive.fragments.TabFragment_users;
import com.example.tenantsproject.flatmates.flat.Flat;
import com.example.tenantsproject.flatmates.login.Login;
import com.example.tenantsproject.flatmates.main_list.fragments.TabFragment;
import com.example.tenantsproject.flatmates.main_list.list.Add;
import com.example.tenantsproject.flatmates.main_list.list.MainActivity;
import com.example.tenantsproject.flatmates.model.data.Product;
import com.example.tenantsproject.flatmates.model.rest.Response;
import com.example.tenantsproject.flatmates.model.service.FlatService;
import com.example.tenantsproject.flatmates.model.service.UserService;
import com.example.tenantsproject.flatmates.security.Authenticator;
import com.example.tenantsproject.flatmates.settings.user.Account;

import java.util.ArrayList;

public class Archives extends AppCompatActivity {
    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    FragmentManager mFragmentManager;
    FragmentManager mFragmentManager2;
    FragmentTransaction mFragmentTransaction;
    FragmentTransaction mFragmentTransaction2;
    ArrayList<Integer> usersID = new ArrayList<>();
    ArrayList<String> users = new ArrayList<>();
    public int idUser;
    TextView txt1;
    private UserArchiveList userList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.archives);
        usersFlatID();
        getUserName();
        txt1 = (TextView) findViewById(R.id.textView9);
        txt1.setText(getMyFlatName());

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
        mFragmentManager2 = getSupportFragmentManager();
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

    public void getUserName(){
    UserService usrServ = new UserService();

                //users = (ArrayList<Object>) rs.getObject();
                if (!usersID.isEmpty()) {
                    for (int i = 0; i < usersID.size(); i++) {
                        Response rs = usrServ.getUser(this, usersID.get(i));
                        switch(rs.getMessageCode()){
                            case Response.MESSAGE_OK:
                                users.add(String.valueOf(rs.getObject()));
                                break;
                            default:
                                Log.d("fsdafa", "Fsafsa");
                    }
                }
        }

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
                    startActivity(new Intent(getApplicationContext(), Account.class));
                }
                if (item.getItemId() == R.id.logout){
                    Authenticator at = new Authenticator();
                    at.logOut(Archives.this);
                    finish();
                    startActivity(new Intent(getApplicationContext(), Login.class));
                }
                Toast.makeText(Archives.this, "You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show();

                return true;
            }
        });
        popup.show();
    }

    void usersFlatID(){
        FlatService fServ = new FlatService();
        Response rs = fServ.getFlatMembers(this, 2);
        switch(rs.getMessageCode()){
            case Response.MESSAGE_OK:
                usersID = (ArrayList<Integer>) rs.getObject();
                if (!usersID.isEmpty()) {
                    for (int i = 0; i < usersID.size(); i++) {
                        Log.d("LOL", String.valueOf(usersID.get(i)));
                    }
                }
                break;
            default:
                Log.d("fsdafa", "Fsafsa");

        }
    }

    public int getUserArchiveId(String name){
        UserService uServ = new UserService();
        Response rs;
        rs = uServ.getUserID(this, name);
        return (int) rs.getObject();
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
        for(int i = 0; i<users.size(); i++){
            popup.getMenu().add(users.get(i));
        }
        popup.show();
        //popup.getMenuInflater().inflate(R.layout.search_popup_menu_archive, popup.getMenu());

        //registering popup with OnMenuItemClickListener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                Toast.makeText(Archives.this, "You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show();

                idUser = getUserArchiveId((String) item.getTitle());
                //userList.refresh();


                Log.d("ID mieszkanca", String.valueOf(idUser));

                mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.containerView, new UserArchiveList()).commit();


                return true;
            }
        });
        popup.show();
    }
}
