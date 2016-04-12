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
import com.example.tenantsproject.flatmates.main_list.list.MyList;
import com.example.tenantsproject.flatmates.main_list.list.RowAdapter;
import com.example.tenantsproject.flatmates.main_list.list.RowBean;

/**
 * Created by Ratan on 7/29/2015.
 */
public class SocialFragment extends ListFragment {

    ListView social;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_messages, container,
                false);
        RowBean RowBean_data[] = new RowBean[] {

                new RowBean(R.drawable.avatar2, "Kupić masło!"),
                new RowBean(R.drawable.avatar2, "Ketchup"),
                new RowBean(R.drawable.avatar2, "Parówki"),
                new RowBean(R.drawable.avatar2, "Ketchup"),


        };

        RowAdapter adapterMain = new RowAdapter(getActivity(),
                R.layout.custom_row, RowBean_data);
        super.onActivityCreated(savedInstanceState);
        social  = (ListView)rootView.findViewById(R.id.social);

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
