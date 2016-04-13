package com.example.tenantsproject.flatmates.main_list.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.tenantsproject.flatmates.R;
import com.example.tenantsproject.flatmates.main_list.list.MyList;
import com.example.tenantsproject.flatmates.main_list.list.RowAdapter;
import com.example.tenantsproject.flatmates.main_list.list.RowBean;
import com.example.tenantsproject.flatmates.model.data.Product;
import com.example.tenantsproject.flatmates.model.rest.Response;
import com.example.tenantsproject.flatmates.model.service.ProductService;

import java.util.ArrayList;

/**
 * Created by Ratan on 7/29/2015.
 */
public class SocialFragment extends ListFragment {
    // my list
    ListView Mainlist;
    SwipeRefreshLayout swipeContainer;
    Response r3 = new Response();
    ArrayList<Product> products = new ArrayList<Product>();
    ProductService productService = new ProductService();
    ListView social;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_messages, container,
                false);
        swipeContainer = (SwipeRefreshLayout) rootView.findViewById(R.id.swiperefresh);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast toast = Toast.makeText(getActivity(), "Refreshed", Toast.LENGTH_LONG);
                //TODO change parameters
                r3 = productService.getFlatProducts(getActivity(), 2, 2, ProductService.FILTER_ALL, 1);
                switch (r3.getMessageCode()) {
                    case Response.MESSAGE_OK:
                        products = (ArrayList<Product>) r3.getObject();
                        break;
                    case Response.MESSAGE_FORBIDDEN:
                        toast = Toast.makeText(getActivity(), "ERROR, Please check your internet connection", Toast.LENGTH_LONG);
                        toast.show();
                        break;
                    case Response.MESSAGE_UNAUTHORIZED:
                        Toast toast1 = Toast.makeText(getActivity(), "ERROR, Please check your internet connection", Toast.LENGTH_LONG);
                        toast1.show();
                        break;
                    default:
                        Toast toast2 = Toast.makeText(getActivity(), "ERROR, Please check your internet connection", Toast.LENGTH_LONG);
                        toast2.show();

                }
                RowBean RowBean_data[] = new RowBean[products.size()];
                for (int i = 0; i < products.size(); i++) {
                    RowBean_data[i] = new RowBean(R.drawable.avatar3, products.get(i).getDescription());

                }
                RowAdapter adapterMain = new RowAdapter(getActivity(),
                        R.layout.custom_row, RowBean_data);
                Mainlist = (ListView) rootView.findViewById(R.id.Mainlist);
                setListAdapter(adapterMain);
                toast.show();
                swipeContainer.setRefreshing(false);
            }

        });
        //TODO change parameters
        r3 = productService.getFlatProducts(getActivity(), 2, 2, ProductService.FILTER_ALL, 1);
        switch (r3.getMessageCode()) {
            case Response.MESSAGE_OK:
                products = (ArrayList<Product>) r3.getObject();
                break;
            case Response.MESSAGE_FORBIDDEN:
                Toast toast = Toast.makeText(getActivity(), "ERROR, Please check your internet connection", Toast.LENGTH_LONG);
                toast.show();
                break;
            case Response.MESSAGE_UNAUTHORIZED:
                Toast toast1 = Toast.makeText(getActivity(), "ERROR, Please check your internet connection", Toast.LENGTH_LONG);
                toast1.show();
                break;
            default:
                Toast toast2 = Toast.makeText(getActivity(), "ERROR, Please check your internet connection", Toast.LENGTH_LONG);
                toast2.show();
        }
        RowBean RowBean_data[] = new RowBean[products.size()];
        for (int i = 0; i < products.size(); i++) {
            RowBean_data[i] = new RowBean(R.drawable.avatar3, products.get(i).getDescription());

        }
        RowAdapter adapterMain = new RowAdapter(getActivity(),
                R.layout.custom_row, RowBean_data);
        super.onActivityCreated(savedInstanceState);
        Mainlist = (ListView) rootView.findViewById(R.id.social);
        setListAdapter(adapterMain);

        return rootView;
    }

    public void onActivityCreated(Bundle savedState) {
        super.onActivityCreated(savedState);

        getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int arg2, long arg3) {
                startActivity(new Intent(getActivity(), MyList.class));
                return true;
            }
        });

    }
}
