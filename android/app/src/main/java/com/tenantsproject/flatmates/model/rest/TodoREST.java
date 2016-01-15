package com.tenantsproject.flatmates.model.rest;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tenantsproject.flatmates.model.data.TodoTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class TodoREST {
    public Response getAllTodos(int id) {
        GetAllTodosTask task = new GetAllTodosTask();
        Response response = new Response();
        try {
            task.execute(id);
            response = task.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.e("REST", "Can't run task: getAllTodos", e);
        }
        return response;
    }

    public Response update(TodoTask todoTask) {
        UpdateTodoTask task = new UpdateTodoTask();
        Response response = new Response();
        task.execute(todoTask);
        try {
            response = task.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.e("REST", "Can't run task: updateTodo", e);
        }
        return response;
    }

    public Response newTodo(TodoTask todoTask) {
        NewTodoTask task = new NewTodoTask();
        task.execute(todoTask);
        Response response = new Response();
        try {
            response = task.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.e("REST", "Can't run task: newTodo", e);
        }
        return response;
    }

    public Response get(int id) {
        GetTodoTask task = new GetTodoTask();
        task.execute(id);
        Response response = new Response();
        try {
            response = task.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.e("REST", "Can't run task: getTodo", e);
        }

        return response;
    }

    public Response delete(TodoTask todoTask) {
        DeleteTodoTask task = new DeleteTodoTask();
        task.execute(todoTask);
        Response response = new Response();
        try {
            response = task.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.e("REST", "Can't run task: deleteTodo", e);
        }
        return response;
    }

    private class GetAllTodosTask extends AsyncTask<Integer, Void, Response> {

        private HttpURLConnection urlConnection;

        @Override
        protected Response doInBackground(Integer... params) {
            int flatId = params[0];
            String json = "";
            Response response = new Response();
            try {
                URL url = new URL(Properties.SERVER_URL + "todos/flats/" + flatId);
                urlConnection = (HttpURLConnection) url.openConnection();

                InputStream in = urlConnection.getInputStream();
                InputStreamReader isw = new InputStreamReader(in);
                int data = isw.read();
                while (data != -1) {
                    char current = (char) data;
                    json += current;
                    data = isw.read();
                }
                response.setMessageCode(urlConnection.getResponseCode());
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                urlConnection.disconnect();
                if (response.getMessageCode() == Response.MESSAGE_OK) {
                    Gson gson = new Gson();
                    Type listType = new TypeToken<List<TodoTask>>() {
                    }.getType();
                    response.setObject(gson.fromJson(json, listType));
                }
            }
            return response;
        }
    }

    private class UpdateTodoTask extends AsyncTask<TodoTask, Void, Response> {

        private HttpURLConnection urlConnection;

        @Override
        protected Response doInBackground(TodoTask... params) {
            TodoTask todoTask = params[0];
            Gson gson = new Gson();
            String json = gson.toJson(todoTask);
            Response response = new Response();
            try {
                URL url = new URL(Properties.SERVER_URL + "todos/" + todoTask.getId());
                urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setDoOutput(true);
                urlConnection.setRequestMethod("PUT");
                OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
                out.write("obj=" + json);
                out.close();
//                urlConnection.getInputStream();
                response.setMessageCode(urlConnection.getResponseCode());
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                urlConnection.disconnect();
            }
            return response;
        }
    }

    private class NewTodoTask extends AsyncTask<TodoTask, Void, Response> {

        private HttpURLConnection urlConnection;

        @Override
        protected Response doInBackground(TodoTask... params) {
            TodoTask todoTask = params[0];
            Gson gson = new Gson();
            String json = gson.toJson(todoTask);
            StringBuilder responseMsg = new StringBuilder();
            int msgCode = -1;
            Response response = new Response();
            try {
                URL url = new URL(Properties.SERVER_URL + "todos/");
                urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setDoOutput(true);
                urlConnection.setDoInput(true);
                urlConnection.setRequestMethod("POST");
                OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
                out.write("obj=" + json);
                out.close();

                InputStream in = urlConnection.getInputStream();
                BufferedReader rd = new BufferedReader(new InputStreamReader(in));
                String line;

                while ((line = rd.readLine()) != null) {
                    responseMsg.append(line);
                }
                rd.close();
                msgCode = urlConnection.getResponseCode();

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (msgCode == 200) {
                    todoTask = gson.fromJson(responseMsg.toString(), TodoTask.class);
                    response.setObject(todoTask);
                }
                response.setMessageCode(msgCode);
                urlConnection.disconnect();
            }
            return response;
        }
    }

    private class GetTodoTask extends AsyncTask<Integer, Void, Response> {

        HttpURLConnection urlConnection;

        @Override
        protected Response doInBackground(Integer... params) {
            int id = params[0];

            String responseMsg = "";
            TodoTask todoTask;
            Response response = new Response();
            try {
                URL url = new URL(Properties.SERVER_URL + "todos/" + id);
                urlConnection = (HttpURLConnection) url.openConnection();

                InputStream in = urlConnection.getInputStream();
                InputStreamReader isw = new InputStreamReader(in);
                int data = isw.read();
                while (data != -1) {
                    char current = (char) data;
                    responseMsg += current;
                    data = isw.read();
                }
                response.setMessageCode(urlConnection.getResponseCode());
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                urlConnection.disconnect();
                if (response.getMessageCode() == Response.MESSAGE_OK) {
                    Gson gson = new Gson();
                    todoTask = gson.fromJson(responseMsg, TodoTask.class);
                    response.setObject(todoTask);
                }

            }

            return response;
        }
    }

    private class DeleteTodoTask extends AsyncTask<TodoTask, Void, Response> {
        private HttpURLConnection urlConnection;

        @Override
        protected Response doInBackground(TodoTask... params) {
            TodoTask todoTask = params[0];
            int id = todoTask.getId();
            Response response = new Response();
            try {
                URL url = new URL(Properties.SERVER_URL + "todos/" + id);
                urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setDoOutput(false);
                urlConnection.setDoInput(false);
                urlConnection.setRequestMethod("DELETE");
                response.setMessageCode(urlConnection.getResponseCode());


            } catch (IOException e) {
                Log.e("EXPENSE_REST", "Can't load inputstream", e);
            } finally {
                urlConnection.disconnect();
            }
            return response;
        }
    }
}
