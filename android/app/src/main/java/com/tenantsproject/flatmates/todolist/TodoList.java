package com.tenantsproject.flatmates.todolist;

import com.tenantsproject.flatmates.R;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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
public int isStillExist(String check){
    if(list.tasks.isEmpty()){
        return -1;
    }
    for(int i=0;i<list.tasks.size();i++){
        if(list.tasks.get(i).getMessage().equals(check)){
            return i;
        }else if(i+1 == list.tasks.size() ) {
            return -1;
        }
    }
    return -1;
}
    public void remove(String check){
                if(isStillExist(check) != -1){
                    Toast.makeText(getApplicationContext(), "Usunięto z zadań!", Toast.LENGTH_LONG).show();
                    list.tasks.remove(isStillExist(check));
                    handler.save(list);
                    tasksAdapter.notifyDataSetChanged();
                    this.finish();
                }else{
                    Toast.makeText(getApplicationContext(), "Zadanie zostało już usunięte!", Toast.LENGTH_LONG).show();;
                    this.finish();
                }
    }

    public void onNotify(View v) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, 1);
        int position = tasksView.getPositionForView
                ((RelativeLayout) v.getParent());
        setAlarm(cal, v);
        Toast.makeText(getApplicationContext(),"Przypomnienie za 1 minute!", Toast.LENGTH_SHORT).show();
    }

    public void setAlarm(Calendar targetCal,View v){
        int position = tasksView.getPositionForView
                ((RelativeLayout) v.getParent());
        String temp = list.tasks.get(position).getMessage();
        Toast.makeText(getApplicationContext(), "Ustawiono przypomnienie!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
        intent.putExtra("temp",temp);
        final int _id = (int) System.currentTimeMillis();
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), _id, intent, 0);
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), pendingIntent);
    }

    public void setAlarm(Calendar targetCal,String check){
        if (isStillExist(check) != -1){
            Toast.makeText(getApplicationContext(), "Ustawiono przypomnienie!", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
            intent.putExtra("temp", list.tasks.get(isStillExist(check)).getMessage());
            final int _id = (int) System.currentTimeMillis();
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), _id, intent, 0);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), pendingIntent);
            this.finish();

        }else{
            Toast.makeText(getApplicationContext(), "To zadanie zostało już usunięte", Toast.LENGTH_SHORT).show();
            this.finish();
        }
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

   

    class List {
        protected ArrayList<TodoTask> tasks;

        List() {
            tasks = new ArrayList<TodoTask>();
        }
    }



}