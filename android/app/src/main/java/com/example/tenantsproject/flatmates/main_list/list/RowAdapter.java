package com.example.tenantsproject.flatmates.main_list.list;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.tenantsproject.flatmates.R;
import com.example.tenantsproject.flatmates.model.data.Product;

import java.util.ArrayList;

public class RowAdapter extends ArrayAdapter<Product> {

    Context context;
    int layoutResourceId;
    ArrayList<Product> data = new ArrayList<Product>();

    public RowAdapter(Context context, int layoutResourceId,ArrayList<Product> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        RowBeanHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new RowBeanHolder();
<<<<<<< HEAD
=======
            //  holder.imgIcon = (ImageView) row.findViewById(R.id.imgIcon);
>>>>>>> e1a3dda09abc260a54f0847185cef34790a24c01
            holder.txtTitle = (TextView) row.findViewById(R.id.txtTitle);

            row.setTag(holder);
            if(data.get(position).getUser()!=0){
            row.setBackgroundColor(0xff888888);
            }else{
                row.setBackgroundColor(0xffffffff);

            }
        } else {
            if(data.get(position).getUser()!=0){
                row.setBackgroundColor(0xff888888);
            }else{
                row.setBackgroundColor(0xffffffff);

            }
            holder = (RowBeanHolder) row.getTag();

<<<<<<< HEAD
        }
=======
        Product object = data.get(position);
        holder.txtTitle.setText(object.getDescription());
//        holder.imgIcon.setImageResource(object.icon);
>>>>>>> e1a3dda09abc260a54f0847185cef34790a24c01

        Product object = data.get(position);
        holder.txtTitle.setText(object.getDescription());

        return row;
    }

    static class RowBeanHolder {
<<<<<<< HEAD
=======
        //      ImageView imgIcon;
>>>>>>> e1a3dda09abc260a54f0847185cef34790a24c01
        TextView txtTitle;
    }


}