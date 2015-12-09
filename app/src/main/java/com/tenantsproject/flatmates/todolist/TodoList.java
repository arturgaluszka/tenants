package com.tenantsproject.flatmates.todolist;
import com.tenantsproject.flatmates.R;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.ArrayList;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.EditText;
import android.view.View;

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
    }
    public void onPriority(View v) {
    }
}