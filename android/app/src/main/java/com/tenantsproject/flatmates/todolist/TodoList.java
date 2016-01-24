package com.tenantsproject.flatmates.todolist;
import com.tenantsproject.flatmates.R;

import android.app.AlertDialog;
import android.app.Notification;
import android.content.DialogInterface;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import java.util.ArrayList;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.view.View;

import com.tenantsproject.flatmates.model.data.TodoTask;
import com.tenantsproject.flatmates.model.service.TodoService;
import com.tenantsproject.flatmates.user.UserActivity;
import com.tenantsproject.flatmates.model.rest.Response;

public class TodoList extends AppCompatActivity {

    private ArrayAdapter<String> tasksAdapter;
    private ArrayList<TodoTask> tasks;
    private ListView tasksView;
   private TodoService todoService;

    //private static final String TODO_FILE_NAME = "todo_file";
    //private JSONFileHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);
        tasksView = (ListView) findViewById(R.id.tasksView);
        //this.handler = new JSONFileHandler(TODO_FILE_NAME, this);
        //this.list = (List) handler.load(List.class);
        todoService = new TodoService();
        tasks = loadData();
        tasksAdapter = new TodoAdapter(this, tasks);
        tasksView.setAdapter(tasksAdapter);
        tasksView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> av, View v, int pos, long id) {
                AlertDialog alertDialog = new AlertDialog.Builder(TodoList.this).create();
                Response response = todoService.delete( tasks.get(pos));
                switch (response.getMessageCode()) {
                    case Response.MESSAGE_OK:
                        tasks.remove(pos);
                        //alertDialog.dismiss();
                        break;
                    case Response.MESSAGE_NOT_FOUND:
                        alertDialog.setMessage("Already deleted Please refresh");
                        alertDialog.show();
                        break;
                    default:
                        alertDialog.setMessage("Unknown error. Please refresh");
                        alertDialog.show();
                        break;
                }
                tasksAdapter.notifyDataSetChanged();
                return true;
            }
        });
        tasksView.setLongClickable(true);
        //handler.save(this.list); DONE
    }

    private ArrayList<TodoTask> loadData(){
        Response response = todoService.getAllTodos(getFlat());
        ArrayList<TodoTask> data;
        switch (response.getMessageCode()) {
            case Response.MESSAGE_OK:
                data = (ArrayList<TodoTask>) response.getObject();
                break;
            default:
                data = new ArrayList<>();
                Log.e("expense load", "Can't load expense list");
        }
        return data;
    }

    public void onAddItem(View v) {
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        TodoTask task = new TodoTask();
        task.setMessage(itemText);
        task.setFlat(getFlat());
        task.setUser(getUser());
        AlertDialog alertDialog = new AlertDialog.Builder(TodoList.this).create();
        Response response = todoService.newTodo(task);
        switch (response.getMessageCode()) {
            case Response.MESSAGE_OK:
                tasks.add(task);
                tasksAdapter.notifyDataSetChanged();
                break;
            case Response.MESSAGE_CONFLICT:
                alertDialog.setMessage("Somebody already updated. Please refresh.");
                alertDialog.show();
                break;
            default:
                alertDialog.setMessage("Error while updating. Please refresh.");
                alertDialog.show();
                break;
        }
        etNewItem.setText("");
        //handler.save(this.list);
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
        final TodoTask.Priority previousPriority = tasks.get(position).getPriority();
        final AlertDialog levelDialog;
        final CharSequence[] items = {" Low ", " Medium ", " High "};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                tasks.get(position).setUser(getUser());
                AlertDialog alertDialog = new AlertDialog.Builder(TodoList.this).create();
                Response response = todoService.update(tasks.get(position));
                switch (response.getMessageCode()) {
                    case Response.MESSAGE_OK:
                        tasksAdapter.notifyDataSetChanged();
                        break;
                    case Response.MESSAGE_NOT_FOUND:
                        alertDialog.setMessage("Todo not found. Please refresh.");
                        break;
                    case Response.MESSAGE_CONFLICT:
                        alertDialog.setMessage("Somebody already updated. Please refresh.");
                        alertDialog.show();
                        break;
                    default:
                        alertDialog.setMessage("Error while updating. Please refresh.");
                        alertDialog.show();
                        break;
                }
                //handler.save(list);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                tasks.get(position).setPriority(previousPriority);
            }
        });
        builder.setTitle("Select The Priority Level");
        builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                switch (item) {
                    case 0:
                        tasks.get(position).setPriority(TodoTask.Priority.LOW);
                        break;
                    case 1:
                        tasks.get(position).setPriority(TodoTask.Priority.MEDIUM);
                        break;
                    case 2:
                        tasks.get(position).setPriority(TodoTask.Priority.HIGH);
                        break;
                }
            }
        });
        levelDialog = builder.create();
        levelDialog.show();
    }

    public void onRefresh(View v){
        tasks = loadData();
        tasksAdapter = new TodoAdapter(this, tasks);
        tasksView.setAdapter(tasksAdapter);
        tasksView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> av, View v, int pos, long id) {
                AlertDialog alertDialog = new AlertDialog.Builder(TodoList.this).create();
                Response response = todoService.delete(tasks.get(pos));
                switch (response.getMessageCode()) {
                    case Response.MESSAGE_OK:
                        tasks.remove(pos);
                        //alertDialog.dismiss();
                        break;
                    case Response.MESSAGE_NOT_FOUND:
                        alertDialog.setMessage("Already deleted Please refresh");
                        alertDialog.show();
                        break;
                    default:
                        alertDialog.setMessage("Unknown error. Please refresh");
                        alertDialog.show();
                        break;
                }
                tasksAdapter.notifyDataSetChanged();
                return true;
            }
        });
        tasksView.setLongClickable(true);
    }

    //TODO: improve and/or delete (shared pref user info)
    private int getFlat(){
        SharedPreferences sP = getSharedPreferences(UserActivity.USER_PREF_NAME, Context.MODE_PRIVATE);
        return sP.getInt(UserActivity.USER_PREF_FLAT,0);
    }
    //TODO: improve and/or delete (shared pref user info)
    private String getUser(){
        SharedPreferences sP = getSharedPreferences(UserActivity.USER_PREF_NAME, Context.MODE_PRIVATE);
        return sP.getString(UserActivity.USER_PREF_USER,"default");
    }

}