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
import com.example.tenantsproject.flatmates.archive.list.MyArchiveList;
import com.example.tenantsproject.flatmates.archive.list.RowAdapterArchive;
import com.example.tenantsproject.flatmates.model.data.Product;
import com.example.tenantsproject.flatmates.model.rest.Response;
import com.example.tenantsproject.flatmates.model.service.ProductService;
import com.example.tenantsproject.flatmates.model.service.StatsService;
import com.example.tenantsproject.flatmates.model.service.UserService;
import com.example.tenantsproject.flatmates.security.Authenticator;

import java.util.ArrayList;

public class UpdatesFragment extends ListFragment {

    ListView Mylist;
    int page = 1;
    StatsService stServ = new StatsService();
    ArrayList<Product> RowBean_data = new ArrayList<>();
    RowAdapterArchive adapterMain;
    ArrayList<Product> products = new ArrayList<>();
    ListView mainArchiveList;
    SwipeRefreshLayout swipeContainer;
// my archive
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.archives_amount, container,
                false);

        swipeContainer = (SwipeRefreshLayout) rootView.findViewById(R.id.swiperefresh);
        adapterMain = new RowAdapterArchive(getActivity(), R.layout.custom_row_archive, RowBean_data);
        /*RowAdapterArchive adapterMain = new RowAdapterArchive(getActivity(),
                R.layout.custom_row_archive, RowBean_data); */
        mainArchiveList = (ListView) rootView.findViewById(R.id.MainArchiveList);
        setListAdapter(adapterMain);
        /*swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onUpdate();
            }
        }); */
        onUpdate();
        return rootView;
    }

    public void onActivityCreated(Bundle savedState) {
        super.onActivityCreated(savedState);

        getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int arg2, long arg3) {
                startActivity(new Intent(getActivity(), MyArchiveList.class));
                return true;
            }
        });
    }

    public void onUpdate(){
        Response r1;
        r1 = stServ.getArchivalProducts(getActivity(), getMyActualFlat(), getUserId(), StatsService.FILTER_ALL, 1);
        switch (r1.getMessageCode()) {
            case Response.MESSAGE_OK:
                page = 2;
                RowBean_data.clear();
                products.clear();
                products = (ArrayList<Product>) r1.getObject();
                for (int i = 0; i < products.size(); i++) {
                    RowBean_data.add(products.get(i));
                }
                r1 = stServ.getArchivalProducts(getActivity(), getMyActualFlat(), getUserId(), StatsService.FILTER_ALL, 2);
                products = (ArrayList<Product>) r1.getObject();
                if (!products.isEmpty()) {
                    for (int i = 0; i < products.size(); i++) {
                        RowBean_data.add(products.get(i));
                    }
                }
                adapterMain.notifyDataSetChanged();
                setHasOptionsMenu(true);
                // swipeContainer.setRefreshing(false);
                break;
            default:
                Toast.makeText(getActivity(), getString(R.string.error2), Toast.LENGTH_LONG).show();
                // swipeContainer.setRefreshing(false);

        }
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

    public int getUserId() {
        final Authenticator aut = new Authenticator();
        final UserService userService = new UserService();
        Response res;
        res = userService.getUserID(getContext(), aut.getLoggedInUserName(getContext()));
        int id = (int) res.getObject();
        return id;
    }
}

