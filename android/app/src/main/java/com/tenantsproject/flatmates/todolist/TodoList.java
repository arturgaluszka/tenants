package com.tenantsproject.flatmates.todolist;
import com.tenantsproject.flatmates.R;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Notification;
import android.content.DialogInterface;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;

import android.content.DialogInterface;
import android.content.Intent;

import android.content.SharedPreferences;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import java.util.ArrayList;
import java.util.Calendar;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.view.View;
import android.widget.Toast;

import com.tenantsproject.flatmates.model.data.TodoTask;
import com.tenantsproject.flatmates.model.service.TodoService;
import com.tenantsproject.flatmates.user.UserActivity;
import com.tenantsproject.flatmates.model.rest.Response;

public class TodoList extends AppCompatActivity {

    private ArrayAdapter<String> tasksAdapter;
    private ArrayList<TodoTask> tasks;
    private ListView tasksView;
    private TodoService todoService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);
        tasksView = (ListView) findViewById(R.id.tasksView);
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
        TodoTask task = new TodoTask(itemText,(long)1,getFlat(),getUser());
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
    }
    public int isStillExist(String check){
      if(tasks.isEmpty()){
            return -1;
     }
        for(int i=0;i<tasks.size();i++){
         if(tasks.get(i).getMessage().equals(check)){
             return i;
         }else if(i+1 == tasks.size() ) {
               return -1;
          }
     }
     return -1;
    }

    public void remove(String check){
                if(isStillExist(check) != -1){
                    Toast.makeText(getApplicationContext(), "Deleted from the tasks!", Toast.LENGTH_LONG).show();
                    tasks.remove(isStillExist(check));
                    tasksAdapter.notifyDataSetChanged();
                    this.finish();
                }else{
                    Toast.makeText(getApplicationContext(), "The task was deleted!", Toast.LENGTH_LONG).show();;
                    this.finish();
                }
    }

    public void onNotify(View v) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, 1);
        int position = tasksView.getPositionForView
                ((RelativeLayout) v.getParent());
        setAlarm(cal, v);
        Toast.makeText(getApplicationContext(),"Reminder in 1 minute!", Toast.LENGTH_SHORT).show();
    }

    public void setAlarm(Calendar targetCal,View v){
        int position = tasksView.getPositionForView
                ((RelativeLayout) v.getParent());
        String temp = tasks.get(position).getMessage();
        Toast.makeText(getApplicationContext(), "Reminder is set!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
        intent.putExtra("temp",temp);
        final int _id = (int) System.currentTimeMillis();
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), _id, intent, 0);
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), pendingIntent);
    }

    public void setAlarm(Calendar targetCal,String check){
        if (isStillExist(check) != -1){
            Toast.makeText(getApplicationContext(), "Reminder is set!", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
            intent.putExtra("temp", tasks.get(isStillExist(check)).getMessage());
            final int _id = (int) System.currentTimeMillis();
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), _id, intent, 0);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), pendingIntent);
            this.finish();

        }else{
            Toast.makeText(getApplicationContext(), "The task was deleted", Toast.LENGTH_SHORT).show();
            this.finish();
        }
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
                AlertDialog alertDialog = new AlertDialog.Builder(TodoList.this).create();

                Response response = todoService.update(tasks.get(position));
                switch (response.getMessageCode()) {
                    case Response.MESSAGE_OK:
                        tasks.get(position).setUser(getUser());
                        tasksAdapter.notifyDataSetChanged();
                        break;
                    case Response.MESSAGE_NOT_FOUND:
                        alertDialog.setMessage("Todo not found. Please refresh.");
                        alertDialog.show();
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


   

    class List {
        protected ArrayList<TodoTask> tasks;

        List() {
            tasks = new ArrayList<TodoTask>();
        }
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
                        tasks.remove(pos);;
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