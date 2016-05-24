package com.example.tenantsproject.flatmates.main_list.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.tenantsproject.flatmates.R;
import com.example.tenantsproject.flatmates.main_list.list.MainActivity;
import com.example.tenantsproject.flatmates.main_list.list.MainList;
import com.example.tenantsproject.flatmates.main_list.list.RowAdapter;
import com.example.tenantsproject.flatmates.model.data.Product;
import com.example.tenantsproject.flatmates.model.rest.Response;
import com.example.tenantsproject.flatmates.model.service.ProductService;
import com.example.tenantsproject.flatmates.model.service.UserService;
import com.example.tenantsproject.flatmates.security.Authenticator;

import java.util.ArrayList;


public class PrimaryFragment extends ListFragment implements Updateable {
    int page = 1;
    ListView Mainlist;
    SwipeRefreshLayout swipeContainer;
    Response r3 = new Response();
    ArrayList<Product> products = new ArrayList<>();
    ProductService productService = new ProductService();
    ArrayList<Product> RowBean_data = new ArrayList<>();
    boolean flag_loading;
    RowAdapter adapterMain;
    Product prod;
    int flat = -1;
    MainActivity mn = new MainActivity();


    //TODO for Artur FILTERS not working

    @Override

    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_messages, container,
                false);
        swipeContainer = (SwipeRefreshLayout) rootView.findViewById(R.id.swiperefresh);
        Mainlist = (ListView) rootView.findViewById(R.id.Mainlist);
        adapterMain = new RowAdapter(getActivity(),
                R.layout.custom_row, RowBean_data);
        setListAdapter(adapterMain);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onUpdate();
            }
        });
        try{
            MainActivity activity = (MainActivity) getActivity();
            int a = activity.pos;
            flat = a;
            Log.d("liczba", String.valueOf(a));
        }
        catch (NullPointerException e){
            Log.d("liczba", "lol");
            flat = getMyActualFlat();
        }


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
                int act = 0;
                ProductService productService = new ProductService();
                if (RowBean_data.get(position).getUser()==getUserId()){
                    re =productService.unreserveProduct(getContext(),RowBean_data.get(position));
                    act = 1;
                }else {
                    re = productService.reserveProduct(getContext(), RowBean_data.get(position));
                }
                switch (re.getMessageCode()) {
                    case Response.MESSAGE_OK:
                        if(act==0){
                        Toast.makeText(getActivity(), getString(R.string.add_to_list), Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(getActivity(), "Product unreserved", Toast.LENGTH_LONG).show();
                        }
                        onUpdate();
                        break;
                    default:
                        if (RowBean_data.get(position).getUser() != 0) {
                            Toast.makeText(getActivity(), "Product already reserved", Toast.LENGTH_LONG).show();
                        } else {
                            if(products.isEmpty()){
                                Toast.makeText(getActivity(), getString(R.string.error6), Toast.LENGTH_LONG).show();
                            }
                            else{
                                Toast.makeText(getActivity(), getString(R.string.error2), Toast.LENGTH_LONG).show();}
                        }
                }

            }

        });

        getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int arg2, long arg3) {
                prod = RowBean_data.get(arg2);
                Intent i = new Intent(getActivity(), MainList.class);
                Bundle b = new Bundle();
                b.putSerializable("Object", prod);
                i.putExtras(b);
                Log.d("Lol", prod.getDescription());
                startActivity(i);
                onUpdate();
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
        if(flat > -1){
        r5 = productService.getFlatProducts(getActivity(), flat, 0, MainActivity.FILTER, ++page);}
        else{
            r5 = productService.getFlatProducts(getActivity(), getMyActualFlat(), 0, MainActivity.FILTER, ++page);
        }
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




    public void uploadInBacground() {
        getListView().post(new Runnable() {
            @Override
            public void run() {
                additems();
            }
        });
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

    public void onUpdateInBacground() {


    }

    public void onUpdate() {
        Response r4;
        if(flat > -1){r4 = productService.getFlatProducts(getActivity(), flat, 0, MainActivity.FILTER, 1);}
        else{r4 = productService.getFlatProducts(getActivity(), getMyActualFlat(), 0, MainActivity.FILTER, 1);}
        switch (r4.getMessageCode()) {
            case Response.MESSAGE_OK:
                page = 2;
                RowBean_data.clear();
                products.clear();
                products = (ArrayList<Product>) r4.getObject();
                for (int i = 0; i < products.size(); i++) {
                    RowBean_data.add(products.get(i));
                }

                if(flat > -1){r4 = productService.getFlatProducts(getActivity(), flat, 0, MainActivity.FILTER, 2);}
                else{r4 = productService.getFlatProducts(getActivity(), getMyActualFlat(), 0, MainActivity.FILTER, 2);}
                products = (ArrayList<Product>) r4.getObject();
                if (!products.isEmpty()) {
                    for (int i = 0; i < products.size(); i++) {
                        RowBean_data.add(products.get(i));
                    }
                }
                adapterMain.notifyDataSetChanged();
                setHasOptionsMenu(true);
                swipeContainer.setRefreshing(false);
                break;
            default:
                if(products.isEmpty()){
                    Toast.makeText(getActivity(), getString(R.string.error6), Toast.LENGTH_LONG).show();
                    swipeContainer.setRefreshing(false);
                }
                else{
                    Toast.makeText(getActivity(), getString(R.string.error2), Toast.LENGTH_LONG).show();
                    swipeContainer.setRefreshing(false);}

        }

    }

}