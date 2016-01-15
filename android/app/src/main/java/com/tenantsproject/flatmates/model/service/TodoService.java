package com.tenantsproject.flatmates.model.service;

import com.tenantsproject.flatmates.model.data.TodoTask;
import com.tenantsproject.flatmates.model.rest.Response;
import com.tenantsproject.flatmates.model.rest.TodoREST;

public class TodoService {

    private TodoREST todoREST;

    public TodoService() {
        this.todoREST = new TodoREST();
    }

    public Response getAllTodos(int id) {
        return todoREST.getAllTodos(id);
    }

    public Response update(TodoTask todoTask) {
        return todoREST.update(todoTask);
    }

    public Response newTodo(TodoTask todoTask) {
        return todoREST.newTodo(todoTask);
    }

    public Response get(int id) {
        return todoREST.get(id);
    }

    public Response delete(TodoTask todoTask) {
        return todoREST.delete(todoTask);
    }
}
