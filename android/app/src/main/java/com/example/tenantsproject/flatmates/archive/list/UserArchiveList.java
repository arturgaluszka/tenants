package com.example.tenantsproject.flatmates.archive.list;


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

public class UserArchiveList extends ListFragment {
    ListView MainlistArch;

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.archives_amount, container,
                false);
        RowBeanArchive RowBean_data[] = new RowBeanArchive[]{

                new RowBeanArchive(R.drawable.avatar3, "Chleb!", "3.12"),
                new RowBeanArchive(R.drawable.avatar3, "Chleb!", "3.12"),
                new RowBeanArchive(R.drawable.avatar3, "Chleb!", "3.12"),
                new RowBeanArchive(R.drawable.avatar3, "Chleb!", "3.12"),
                new RowBeanArchive(R.drawable.avatar3, "Szyszki!", "3.12")


        };
        setHasOptionsMenu(true);
        RowAdapterArchive adapterMain = new RowAdapterArchive(getActivity(),
                R.layout.custom_row_archive, RowBean_data);
        super.onActivityCreated(savedInstanceState);
        MainlistArch = (ListView) rootView.findViewById(R.id.MainArchiveList);

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
                startActivity(new Intent(getActivity(), MyArchiveListMenu.class));

                return true;
            }
        });

    }
}
