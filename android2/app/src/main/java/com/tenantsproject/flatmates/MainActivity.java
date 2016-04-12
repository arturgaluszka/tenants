package com.tenantsproject.flatmates;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.tenantsproject.flatmates.model.data.Product;
import com.tenantsproject.flatmates.model.rest.Response;
import com.tenantsproject.flatmates.model.service.ProductService;
import com.tenantsproject.flatmates.model.service.FlatService;
import com.tenantsproject.flatmates.model.service.UserService;
import com.tenantsproject.flatmates.security.Authenticator;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        test();
    }

    public void test() {
        Button button = (Button) findViewById(R.id.button);
        final ProductService productService = new ProductService();
        UserService userService = new UserService();
        FlatService flatService = new FlatService();
        Authenticator authenticator = new Authenticator();
        Response res1 = authenticator.login(this, "Strajk", "test");
//        Response res2 = userService.getUserID(this, "Strajk");
//        Response res4 = flatService.getFlatMembers(this, 2);
//        Toast.makeText(this, Arrays.toString(((List<Integer>)res4.getObject()).toArray()),Toast.LENGTH_LONG);
//        Response res5 = userService.getUserID(this,"NOVA");
//        Toast.makeText(this, res5.getObject().toString(),Toast.LENGTH_LONG).show();
//        authenticator.logOut(this);
//        Response res6 = userService.getUserID(this,"NOVA");
//        int a = 1;
//        Product product =  new Product(2.0,"test1",0,2,2);
//        product.setCreator(2);
//        product.setId(4);
//        Response res7 = productService.removeFromMainList(this,product);
        Response res8 = productService.getFlatProducts(this, 2, 0, 1, 1);

//        Response res9 = flatService.changePassword(this, 2, "test", "asdf");
        final Product product2 = ((ArrayList<Product>) res8.getObject()).get(0);
        product2.setPrice(40);
        final Response[] res10 = new Response[1];
        final Response[] res11 = new Response[1];
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                res10[0] = productService.updateProduct(MainActivity.this, product2);
                res11[0] = productService.unreserveProduct(MainActivity.this,product2);
                int c = 2;
            }
        });
        int b = 1;
    }
}
