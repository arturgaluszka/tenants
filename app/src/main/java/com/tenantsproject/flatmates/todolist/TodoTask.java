package com.tenantsproject.flatmates.todolist;
import java.util.Date;
public class TodoTask {
    enum Priority {HIGH,MEDIUM,LOW};
    Priority priority;
    String message;
    Date date;
}
