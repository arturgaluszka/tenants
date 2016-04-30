package com.example.tenantsproject.flatmates.archive.list;

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

public class RowAdapterArchive extends ArrayAdapter<Product> {
    Context context;
    int layoutResourceId;
    ArrayList<Product> data = new ArrayList<Product>();

    public RowAdapterArchive(Context context, int layoutResourceId, ArrayList<Product> data) {
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
           // holder.imgIcon = (ImageView) row.findViewById(R.id.imgIcon);
            holder.txtTitle = (TextView) row.findViewById(R.id.txtTitle);
            holder.price = (TextView) row.findViewById(R.id.price);


            row.setTag(holder);
        } else {
            holder = (RowBeanHolder) row.getTag();
        }

        Product object = data.get(position);
        holder.txtTitle.setText(object.getDescription());
    //    holder.imgIcon.setImageResource(object.icon);
         holder.price.setText(String.valueOf(object.getPrice()));

        return row;
    }

    static class RowBeanHolder {
    //    ImageView imgIcon;
        TextView txtTitle;
        TextView price;
    }
}
