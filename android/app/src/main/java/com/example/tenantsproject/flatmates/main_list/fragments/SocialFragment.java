package com.example.tenantsproject.flatmates.main_list.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.tenantsproject.flatmates.R;
import com.example.tenantsproject.flatmates.main_list.list.MyList;
import com.example.tenantsproject.flatmates.main_list.list.RowAdapter;
<<<<<<< HEAD
import com.example.tenantsproject.flatmates.main_list.list.RowAdapterMyList;
=======
>>>>>>> e1a3dda09abc260a54f0847185cef34790a24c01
import com.example.tenantsproject.flatmates.model.data.Product;
import com.example.tenantsproject.flatmates.model.rest.Response;
import com.example.tenantsproject.flatmates.model.service.ProductService;
import com.example.tenantsproject.flatmates.model.service.UserService;
import com.example.tenantsproject.flatmates.security.Authenticator;

import java.util.ArrayList;

/**
 * Created by Ratan on 7/29/2015.
 */
public class SocialFragment extends ListFragment implements Updateable {
    // my list
    int page = 1;
    boolean flag_loading;
    SwipeRefreshLayout swipeContainer;
    Response r3 = new Response();
    ArrayList<Product> products = new ArrayList<>();
    ArrayList<Product> RowBean_data = new ArrayList<>();
    ProductService productService = new ProductService();
<<<<<<< HEAD
    RowAdapterMyList adapterMain;
=======
    RowAdapter adapterMain;
>>>>>>> e1a3dda09abc260a54f0847185cef34790a24c01


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_messages, container,
                false);
<<<<<<< HEAD
        adapterMain = new RowAdapterMyList(getActivity(),
=======
        adapterMain = new RowAdapter(getActivity(),
>>>>>>> e1a3dda09abc260a54f0847185cef34790a24c01
                R.layout.custom_row, RowBean_data);
        setListAdapter(adapterMain);
        swipeContainer = (SwipeRefreshLayout) rootView.findViewById(R.id.swiperefresh);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onUpdate();
            }
        });
        onUpdate();
        return rootView;
    }

    public void onActivityCreated(Bundle savedState) {
        super.onActivityCreated(savedState);

        getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int arg2, long arg3) {
                Intent i = new Intent(getActivity(), MyList.class);
                startActivity(i);
                return true;
            }
        });
        getListView().setOnScrollListener(new AbsListView.OnScrollListener() {

            public void onScrollStateChanged(AbsListView view, int scrollState) {


            }

            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {

                if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount != 0) {
                    if (flag_loading == false) {
                        flag_loading = true;
                        uploadInBacground();
                    }
                }
            }
        });
    }

    public void additems() {
        Response r5;
        r5 = productService.getFlatProducts(getActivity(), 2, getUserId(), ProductService.FILTER_ALL, ++page);
        products = (ArrayList<Product>) r5.getObject();
        if (!products.isEmpty()) {

            for (int i = 0; i < products.size(); i++) {
                RowBean_data.add(products.get(i));

            }
            adapterMain.notifyDataSetChanged();
            flag_loading = false;
        }
        flag_loading = false;
    }

    public int getUserId() {
        final Authenticator aut = new Authenticator();
        final UserService userService = new UserService();
        Response res;
        res = userService.getUserID(getContext(), aut.getLoggedInUserName(getContext()));
        int id = (int) res.getObject();
        return id;
    }

    public int getMyActualFlat() {
        int actualFlatnumber;
        Response response;
        UserService userService = new UserService();
        response = userService.getUserFlats(getContext(), getUserId());
        ArrayList<Integer> pa;
        pa = (ArrayList<Integer>) response.getObject();
        actualFlatnumber = pa.get(0);
        return actualFlatnumber;
    }
    public void uploadInBacground() {
        getListView().post(new Runnable() {
            @Override
            public void run() {
                additems();
            }
        });
    }

    public void onUpdate() {
        Response r4;
        r4 = productService.getFlatProducts(getActivity(), getMyActualFlat(), getUserId(), ProductService.FILTER_ALL, 1);
        switch (r4.getMessageCode()) {
            case Response.MESSAGE_OK:
                page = 2;
                RowBean_data.clear();
                products.clear();
                products = (ArrayList<Product>) r4.getObject();
                for (int i = 0; i < products.size(); i++) {
                    RowBean_data.add(products.get(i));
                }
                r4 = productService.getFlatProducts(getActivity(), getMyActualFlat(), getUserId(), ProductService.FILTER_ALL, 2);
                if (!products.isEmpty()) {
                    products = (ArrayList<Product>) r4.getObject();
                    for (int i = 0; i < products.size(); i++) {
                        RowBean_data.add(products.get(i));
                    }
                }
                setHasOptionsMenu(true);
                adapterMain.notifyDataSetChanged();
                swipeContainer.setRefreshing(false);
                break;
            default:
                Toast.makeText(getActivity(), "ERROR, Please check your internet connection", Toast.LENGTH_LONG).show();
                swipeContainer.setRefreshing(false);
        }
    }
}
