package com.example.tenantsproject.flatmates.archive.list;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.tenantsproject.flatmates.R;

public class RowAdapterArchive extends ArrayAdapter<RowBeanArchive> {
    Context context;
    int layoutResourceId;
    RowBeanArchive data[] = null;

    public RowAdapterArchive(Context context, int layoutResourceId, RowBeanArchive[] data) {
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

        RowBeanArchive object = data[position];
        holder.txtTitle.setText(object.title);
    //    holder.imgIcon.setImageResource(object.icon);
        holder.price.setText(object.price);

        return row;
    }

    static class RowBeanHolder {
    //    ImageView imgIcon;
        TextView txtTitle;
        TextView price;
    }
}
