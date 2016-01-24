package com.tenantsproject.flatmates.model.data;

public class TodoTask {
    private int id;
    private Priority priority;
    private String message;
    private Long date;
    private int flat;
    private String user;
    private Long modificationDate;

    public enum Priority {HIGH, MEDIUM, LOW}

    public TodoTask() {
        priority = Priority.MEDIUM;
    }

    public TodoTask(Priority priority, String message, Long date, int flat, String user) {
        this.priority = priority;
        this.message = message;
        this.date = date;
        this.flat = flat;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Long getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(Long modificationDate) {
        this.modificationDate = modificationDate;
    }

    public int getFlat() {
        return flat;
    }

    public void setFlat(int flat) {
        this.flat = flat;
    }
}

