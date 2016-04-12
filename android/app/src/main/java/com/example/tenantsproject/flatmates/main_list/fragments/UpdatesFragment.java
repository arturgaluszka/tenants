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

import com.example.tenantsproject.flatmates.R;
import com.example.tenantsproject.flatmates.archive.list.MyArchiveList;
import com.example.tenantsproject.flatmates.main_list.list.RowAdapter;
import com.example.tenantsproject.flatmates.main_list.list.RowBean;

public class UpdatesFragment extends ListFragment {

    ListView Mylist;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_messages, container,
                false);
        RowBean RowBean_data[] = new RowBean[]{

                new RowBean(R.drawable.avatar2, "Chleb!"),
                new RowBean(R.drawable.avatar2, "Ketchup"),
                new RowBean(R.drawable.avatar2, "Parówki"),
                new RowBean(R.drawable.avatar2, "Parówki"),

        };

        RowAdapter adapterMain = new RowAdapter(getActivity(),
                R.layout.custom_row, RowBean_data);
        super.onActivityCreated(savedInstanceState);
        Mylist = (ListView) rootView.findViewById(R.id.Mylistt);

        setListAdapter(adapterMain);

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
}

