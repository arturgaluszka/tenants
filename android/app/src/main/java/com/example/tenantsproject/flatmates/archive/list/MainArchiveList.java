package com.example.tenantsproject.flatmates.archive.list;

import android.content.Intent;
import android.os.Bundle;
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
import com.example.tenantsproject.flatmates.model.data.Product;
import com.example.tenantsproject.flatmates.model.data.Statistics;
import com.example.tenantsproject.flatmates.model.rest.Response;
import com.example.tenantsproject.flatmates.model.service.StatsService;
import com.example.tenantsproject.flatmates.model.service.UserService;
import com.example.tenantsproject.flatmates.security.Authenticator;

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
    ///double sum = 0;
    int a = -1;
    Product prod;

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.archives_amount, container,
                false);
        setHasOptionsMenu(true);
        txt1 = (TextView) rootView.findViewById(R.id.textView11);
      /* Response rs1 = stServ.getStats(getActivity(), 2,2);
        switch (rs1.getMessageCode()){
            case Response.MESSAGE_OK:
                sum = (Double) rs1.getObject();
                txt1.setText(String.valueOf(sum));
                break;
            default:
                txt1.setText("sum");
        }*/

        Response response = new StatsService().getStats(getActivity(), 0, getMyActualFlat());
        Statistics stats = null;
        if(response.getMessageCode()==Response.MESSAGE_OK){
            stats = (Statistics) response.getObject();
        }
        double sum = 0;
        if(stats!=null) {
            sum = stats.getSum();
            txt1.setText(String.valueOf(sum));
        }
        else{
            txt1.setText(String.valueOf(sum));
        }

        Archives activity = (Archives) getActivity();
        a = activity.flatID;
        Log.d("zmienna", String.valueOf(a));



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
               /* Response re;
                StatsService stsServ = new StatsService();
                //TODO for Arur check reserver Product don't working
                re = stsServ.undoBuy(getContext(), RowBean_data.get(position));
                switch (re.getMessageCode()) {
                    case Response.MESSAGE_OK:
                        Toast.makeText(getActivity(), getString(R.string.add_to_list), Toast.LENGTH_LONG).show();
                        onUpdate();
                        break;
                    default:
                        if(products.isEmpty()){
                            Toast.makeText(getActivity(), getString(R.string.error6), Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(getActivity(), getString(R.string.error2), Toast.LENGTH_LONG).show();}
                }*/
                prod = RowBean_data.get(position);
                Intent i = new Intent(getActivity(), MyArchiveList.class);
                Bundle b = new Bundle();
                b.putSerializable("Object", prod);
                i.putExtras(b);
                startActivity(i);

            }


        });

    }

    public void onUpdate(){
        Response r1;
        if(a==-1){
            r1 = stServ.getArchivalProducts(getActivity(), 0, getMyActualFlat(), StatsService.FILTER_ALL, 1);}
        else{
            r1 = stServ.getArchivalProducts(getActivity(), 0, a, StatsService.FILTER_ALL, 1);
        }

        switch (r1.getMessageCode()) {
            case Response.MESSAGE_OK:
                page = 2;
                RowBean_data.clear();
                products.clear();
                products = (ArrayList<Product>) r1.getObject();
                for (int i = 0; i < products.size(); i++) {
                    RowBean_data.add(products.get(i));
                }
                if(a==-1){
                    r1 = stServ.getArchivalProducts(getActivity(), 0, getMyActualFlat(), StatsService.FILTER_ALL, 2);}
                else{
                    r1 = stServ.getArchivalProducts(getActivity(), 0, a, StatsService.FILTER_ALL, 2);
                }
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