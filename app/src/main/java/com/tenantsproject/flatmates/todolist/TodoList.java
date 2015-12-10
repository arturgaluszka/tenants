package com.tenantsproject.flatmates.todolist;
import com.tenantsproject.flatmates.R;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.ArrayList;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.EditText;
import android.view.View;
import android.widget.RelativeLayout;

public class TodoList extends AppCompatActivity {
    private ArrayAdapter<String> tasksAdapter;
    private ArrayList<TodoTask> tasks;
    private ListView tasksView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);
        tasksView = (ListView) findViewById(R.id.tasksView);
        tasks = new ArrayList<TodoTask>();
        tasksAdapter = new TodoAdapter(this, tasks);
        tasksView.setAdapter(tasksAdapter);
        tasksView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> av, View v, int pos, long id) {
                tasks.remove(pos);
                tasksAdapter.notifyDataSetChanged();
                return true;
            }
        });
        tasksView.setLongClickable(true);
    }
    public void onAddItem(View v) {
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        TodoTask task = new TodoTask();
        task.message = itemText;
        tasks.add(task);
        etNewItem.setText("");
    }
    public void onNotify(View v) {
        Notification.Builder builder = new Notification.Builder(this)
                .setContentText("Nie zapomni!")
                .setContentTitle("Masz nowe zadanie!")
                .setSmallIcon(android.R.drawable.btn_star);
        builder.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
        builder.setLights(Color.RED, 300, 300);
        Notification notification1 = builder.build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification1);
        builder.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
        builder.setLights(Color.CYAN, 3000, 3000);
        notification1.flags |= Notification.FLAG_AUTO_CANCEL;
    }
    public void onPriority(View v) {
        final int position = tasksView.getPositionForView((RelativeLayout)v.getParent());
        final  TodoTask.Priority previousPriority = tasks.get(position).priority;
        final AlertDialog  levelDialog;
        final CharSequence[] items = {" Low "," Medium "," High "};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                tasks.get(position).priority = previousPriority;
            }
        });
        builder.setTitle("Select The Priority Level");
        builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                switch(item) {
                    case 0:
                        tasks.get(position).priority = TodoTask.Priority.LOW;
                        break;
                    case 1:
                        tasks.get(position).priority = TodoTask.Priority.MEDIUM;
                        break;
                    case 2:
                        tasks.get(position).priority = TodoTask.Priority.HIGH;
                        break;
                }
            }
        });
        levelDialog = builder.create();
        levelDialog.show();
    }
}