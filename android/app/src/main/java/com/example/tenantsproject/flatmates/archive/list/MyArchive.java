package com.example.tenantsproject.flatmates.archive.list;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tenantsproject.flatmates.R;
import com.example.tenantsproject.flatmates.model.data.Product;
import com.example.tenantsproject.flatmates.model.data.Statistics;
import com.example.tenantsproject.flatmates.model.rest.Response;
import com.example.tenantsproject.flatmates.model.service.StatsService;
import com.example.tenantsproject.flatmates.model.service.UserService;
import com.example.tenantsproject.flatmates.security.Authenticator;

import java.util.ArrayList;

public class MyArchive extends ListFragment {
    ListView MainArchiveList;
    int page = 1;
    SwipeRefreshLayout swipeContainer;
    ArrayList<Product> products = new ArrayList<>();
    StatsService stServ = new StatsService();
    ArrayList<Product> RowBean_data = new ArrayList<>();
    RowAdapterArchive adapterMain;
    TextView txt1;
    int a = -1;
    Product prod;
    // PrimaryFragment.RowBean_data;
    ListView mainArchiveList;


    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.archives_amount, container,
                false);

        setHasOptionsMenu(true);
       /* RowAdapterArchive adapterMain = new RowAdapterArchive(getActivity(),
                R.layout.custom_row_archive, RowBean_data);*/
        super.onActivityCreated(savedInstanceState);

        swipeContainer = (SwipeRefreshLayout) rootView.findViewById(R.id.swiperefresh);
        adapterMain = new RowAdapterArchive(getActivity(), R.layout.custom_row_archive, RowBean_data);

        MainArchiveList = (ListView) rootView.findViewById(R.id.MainArchiveList);
        setListAdapter(adapterMain);
        txt1 = (TextView) rootView.findViewById(R.id.textView11);
        Response response = new StatsService().getStats(getActivity(), getUserId(), getMyActualFlat());
        Statistics stats = null;
        if(response.getMessageCode()==Response.MESSAGE_OK){
            stats = (Statistics) response.getObject();
        }
        double sum = 0;
        if(stats!=null) {
            sum = stats.getSum();
            txt1.setText(String.valueOf(sum));
        }

      //  setListAdapter(adapterMain);
        Archives activity = (Archives) getActivity();
        a = activity.flatID;

        onUpdate();
        return rootView;

    }

    @Override

    public void onActivityCreated(Bundle savedState) {
        super.onActivityCreated(savedState);

        getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int arg2, long arg3) {
                //Toast.makeText(getActivity(), "On long click listener", Toast.LENGTH_LONG).show();
                prod = RowBean_data.get(arg2);
                Intent i = new Intent(getActivity(), MyArchiveList.class);
                Bundle b = new Bundle();
                b.putSerializable("Object", prod);
                i.putExtras(b);
                startActivity(i);

                return true;
            }
        });

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

    public void onUpdate(){
        Response r1;
        r1 = stServ.getArchivalProducts(getActivity(), getUserId(), getMyActualFlat(), StatsService.FILTER_ALL, 1);
        switch (r1.getMessageCode()) {
            case Response.MESSAGE_OK:
                page = 2;
                RowBean_data.clear();
                products.clear();
                products = (ArrayList<Product>) r1.getObject();
                for (int i = 0; i < products.size(); i++) {
                    RowBean_data.add(products.get(i));
                }
                r1 = stServ.getArchivalProducts(getActivity(), getUserId(), getMyActualFlat(), StatsService.FILTER_ALL, 2);
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
                if(products.isEmpty()){
                    Toast.makeText(getActivity(), getString(R.string.error6), Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getActivity(), getString(R.string.error2), Toast.LENGTH_LONG).show();}

        }
    }
}
