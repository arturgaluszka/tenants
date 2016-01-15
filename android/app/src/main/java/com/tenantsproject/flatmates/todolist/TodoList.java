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
import android.widget.RelativeLayout;
import android.view.View;

import com.tenantsproject.flatmates.model.data.TodoTask;
import com.tenantsproject.flatmates.utils.JSONFileHandler;

public class TodoList extends AppCompatActivity {

    private ArrayAdapter<String> tasksAdapter;
    private List list = null;
    private ListView tasksView;
    private static final String TODO_FILE_NAME = "todo_file";
    private JSONFileHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);
        tasksView = (ListView) findViewById(R.id.tasksView);
        this.handler = new JSONFileHandler(TODO_FILE_NAME, this);
        this.list = (List) handler.load(List.class);
        if (this.list == null) {
            list = new List();
        }
        tasksAdapter = new TodoAdapter(this, list.tasks);
        tasksView.setAdapter(tasksAdapter);
        tasksView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> av, View v, int pos, long id) {
                list.tasks.remove(pos);
                handler.save(list);
                tasksAdapter.notifyDataSetChanged();
                return true;
            }
        });
        tasksView.setLongClickable(true);
        handler.save(this.list);
    }

    public void onAddItem(View v) {
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        TodoTask task = new TodoTask();
        task.setMessage(itemText);
        list.tasks.add(task);
        etNewItem.setText("");
        handler.save(this.list);
    }

    public void onNotify(View v) {
        Notification.Builder builder = new Notification.Builder(this)
                .setContentText("Nie zapomnij!")
                .setContentTitle("Masz nowe zadanie!")
                .setSmallIcon(android.R.drawable.btn_star);
        builder.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
        builder.setLights(Color.RED, 300, 300);
        Notification notification1 = builder.getNotification();
        NotificationManager notificationManager = (NotificationManager)
                getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification1);
        builder.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
        builder.setLights(Color.CYAN, 3000, 3000);
        notification1.flags |= Notification.FLAG_AUTO_CANCEL;
    }

    public void onPriority(View v) {
        final int position = tasksView.getPositionForView
                ((RelativeLayout) v.getParent());
        final TodoTask.Priority previousPriority = list.tasks.get(position).getPriority();
        final AlertDialog levelDialog;
        final CharSequence[] items = {" Low ", " Medium ", " High "};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                tasksAdapter.notifyDataSetChanged();
                handler.save(list);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                list.tasks.get(position).setPriority(previousPriority);
            }
        });
        builder.setTitle("Select The Priority Level");
        builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                switch (item) {
                    case 0:
                        list.tasks.get(position).setPriority(TodoTask.Priority.LOW);
                        break;
                    case 1:
                        list.tasks.get(position).setPriority(TodoTask.Priority.MEDIUM);
                        break;
                    case 2:
                        list.tasks.get(position).setPriority(TodoTask.Priority.HIGH);
                        break;
                }
            }
        });
        levelDialog = builder.create();
        levelDialog.show();
    }

    private class List {
        private ArrayList<TodoTask> tasks;

        List() {
            tasks = new ArrayList<TodoTask>();
        }
    }
}