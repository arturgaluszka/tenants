package com.example.tenantsproject.flatmates.archive.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tenantsproject.flatmates.R;
import com.example.tenantsproject.flatmates.main_list.fragments.PrimaryFragment;
import com.example.tenantsproject.flatmates.main_list.list.MainActivity;
import com.example.tenantsproject.flatmates.main_list.list.RowAdapter;
import com.example.tenantsproject.flatmates.model.data.Product;
import com.example.tenantsproject.flatmates.model.data.Statistics;
import com.example.tenantsproject.flatmates.model.rest.Response;
import com.example.tenantsproject.flatmates.model.service.ProductService;
import com.example.tenantsproject.flatmates.model.service.StatsService;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainArchiveList extends ListFragment {
    int page = 1;
    ListView mainArchiveList;
    SwipeRefreshLayout swipeContainer;
    ArrayList<Product> products = new ArrayList<>();
   // PrimaryFragment.RowBean_data;
    StatsService stServ = new StatsService();
    ArrayList<Product> RowBean_data = new ArrayList<>();
    RowAdapterArchive adapterMain;
    TextView txt1;
    Statistics stat = new Statistics();
    boolean flag_loading;


    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.archives_amount, container,
                false);
        setHasOptionsMenu(true);
        Response rs1 = stServ.getStats(getActivity(), 2,2);
        txt1 = (TextView) rootView.findViewById(R.id.textView11);
        switch (rs1.getMessageCode()){
            case Response.MESSAGE_OK:
                //stat.setSum((Double) rs1.getObject());
                txt1.setText(String.valueOf(stat.getSum()));
                break;
            default:
                txt1.setText("sum");
        }




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

    @Override

    public void onActivityCreated(Bundle savedState) {
        super.onActivityCreated(savedState);


        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Response re;
                StatsService stsServ = new StatsService();
                //TODO for Arur check reserver Product don't working
                re = stsServ.undoBuy(getContext(), RowBean_data.get(position));
                switch (re.getMessageCode()) {
                    case Response.MESSAGE_OK:
                        Toast.makeText(getActivity(), "Added to my list", Toast.LENGTH_LONG).show();
                        onUpdate();
                        break;
                    default:
                        Toast.makeText(getActivity(), "ERROR, Please check your internet connection", Toast.LENGTH_LONG).show();
                }
            }

        });

    }

    public void additems() {
        Response r5;
        r5 = stServ.getArchivalProducts(getActivity(), 2, 2, StatsService.FILTER_ALL, ++page);
        Log.d(r5.toString(), r5.toString());
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

    public void onUpdate(){
        Response r1;
        r1 = stServ.getArchivalProducts(getActivity(), 2, 2, StatsService.FILTER_ALL, 1);
        switch (r1.getMessageCode()) {
            case Response.MESSAGE_OK:
                page = 2;
                RowBean_data.clear();
                products.clear();
                products = (ArrayList<Product>) r1.getObject();
                for (int i = 0; i < products.size(); i++) {
                    RowBean_data.add(products.get(i));
                }
                r1 = stServ.getArchivalProducts(getActivity(), 2, 2, StatsService.FILTER_ALL, 2);
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
                Toast.makeText(getActivity(), "ERROR, Please check your internet connection", Toast.LENGTH_LONG).show();
               // swipeContainer.setRefreshing(false);

        }
    }

}
