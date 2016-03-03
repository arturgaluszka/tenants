package com.tenantsproject.flatmates;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.tenantsproject.flatmates.model.rest.Response;
import com.tenantsproject.flatmates.model.service.ExpenseService;
import com.tenantsproject.flatmates.model.service.FlatService;
import com.tenantsproject.flatmates.model.service.UserService;
import com.tenantsproject.flatmates.security.Authenticator;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        test();
    }
    public void test(){
        ExpenseService expenseService = new ExpenseService();
        UserService userService = new UserService();
        FlatService flatService = new FlatService();
        Authenticator authenticator = new Authenticator();
        Response res1 = authenticator.login(this,"Strajk", "test");
//        Response res2 = userService.getUserID(this,"Strajk");
//        Response res3 = userService.signToFlat(this,(int)res2.getObject(),2,"asdf");
        Response res4 = flatService.getFlatMembers(this,2);
        int a = 1;
    }
}
