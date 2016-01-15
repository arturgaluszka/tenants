package com.tenantsproject.flatmates.todolist;
import com.tenantsproject.flatmates.R;
import com.tenantsproject.flatmates.model.data.TodoTask;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import java.util.ArrayList;
import android.widget.Button;
import android.widget.TextView;

public class TodoAdapter extends ArrayAdapter {
    private LayoutInflater inflater;
    private ArrayList<TodoTask> tasks;
    public TodoAdapter(Activity activity, ArrayList<TodoTask> tasks){
        super(activity, R.layout.todo_row, tasks);
        this.inflater = activity.getWindow().getLayoutInflater();
        this.tasks = tasks;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View rowView = inflater.inflate(R.layout.todo_row, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.textView);
        Button btnNxt = (Button) rowView.findViewById(R.id.buttonNotify);
        if(tasks.get(position).getPriority() == TodoTask.Priority.MEDIUM) {
            textView.setTextColor(Color.BLACK);
        }
        else if(tasks.get(position).getPriority() == TodoTask.Priority.HIGH){
            textView.setTextColor(Color.RED);
        }
        else{
            textView.setTextColor(Color.GREEN);
        }

        btnNxt.setTag(position);
        textView.setText(tasks.get(position).getMessage());
        return rowView;
    }
}