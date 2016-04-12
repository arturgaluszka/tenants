package com.example.tenantsproject.flatmates.main_list.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.tenantsproject.flatmates.R;
import com.example.tenantsproject.flatmates.main_list.list.MainList;
import com.example.tenantsproject.flatmates.main_list.list.RowAdapter;
import com.example.tenantsproject.flatmates.main_list.list.RowBean;


public class PrimaryFragment extends ListFragment {
    ListView Mainlist;

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_messages, container,
                false);
        RowBean RowBean_data[] = new RowBean[]{

                new RowBean(R.drawable.avatar3, "Chleb!"),
                new RowBean(R.drawable.avatar, "Ketchup"),
                new RowBean(R.drawable.avatar2, "Parówki"),
                new RowBean(R.drawable.avatar, "Parówki"),
                new RowBean(R.drawable.avatar3, "Parówki"),
                new RowBean(R.drawable.avatar2, "Chleb!"),
                new RowBean(R.drawable.avatar, "Ketchup"),
                new RowBean(R.drawable.avatar3, "Parówki"),


        };
        setHasOptionsMenu(true);
        RowAdapter adapterMain = new RowAdapter(getActivity(),
                R.layout.custom_row, RowBean_data);
        super.onActivityCreated(savedInstanceState);
        Mainlist = (ListView) rootView.findViewById(R.id.Mainlist);

        setListAdapter(adapterMain);


        return rootView;

    }

    @Override

    public void onActivityCreated(Bundle savedState) {
        super.onActivityCreated(savedState);

        getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int arg2, long arg3) {
                Toast.makeText(getActivity(), "On long click listener", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getActivity(), MainList.class));

                return true;
            }
        });

    }
}