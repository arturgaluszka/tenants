package com.tenantsproject.flatmates.todolist;
import com.tenantsproject.flatmates.R;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import java.util.ArrayList;
import android.widget.TextView;
/**
 * Created by Karol on 2015-12-08.
 */
public class TodoAdapter extends ArrayAdapter {
    private LayoutInflater inflater;
    private ArrayList<String> tasks;
    public TodoAdapter(Activity activity, ArrayList<String> tasks){
        super(activity, R.layout.todo_row, tasks);
        this.inflater = activity.getWindow().getLayoutInflater();
        this.tasks = tasks;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View rowView = inflater.inflate(R.layout.todo_row, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.textView);
        textView.setText(tasks.get(position));
        return rowView;
    }
}