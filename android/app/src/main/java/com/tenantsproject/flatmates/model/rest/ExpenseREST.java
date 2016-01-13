package com.tenantsproject.flatmates.model.rest;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tenantsproject.flatmates.model.data.ExpensePOJO;

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

public class ExpenseREST {

    public Response getAllExpenses(int id) {
        GetAllExpensesTask task = new GetAllExpensesTask();
        Response response = new Response();
        try {
            task.execute(id);
            response  = task.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.e("REST", "Can't run task: getAllExpenses", e);
        }
        return response;
    }

    public Response update(ExpensePOJO expensePOJO) {
        UpdateExpenseTask task = new UpdateExpenseTask();
        Response response = new Response();
        task.execute(expensePOJO);
        try {
            response = task.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.e("REST", "Can't run task: updateExpense", e);
        }
        return response;
    }

    public Response newExpense(ExpensePOJO expensePOJO) {
        NewExpenseTask task = new NewExpenseTask();
        task.execute(expensePOJO);
        Response response = new Response();
        try {
            response = task.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.e("REST", "Can't run task: newExpense", e);
        }
        return response;
    }

    public Response get(int id) {
        GetExpenseTask task = new GetExpenseTask();
        task.execute(id);
        Response response = new Response();
        try {
            response = task.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.e("REST", "Can't run task: getExpense", e);
        }

        return response;
    }

    public Response delete(ExpensePOJO expensePOJO) {
        DeleteExpenseTask task = new DeleteExpenseTask();
        task.execute(expensePOJO);
        Response response = new Response();
        try {
            response = task.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.e("REST", "Can't run task: deleteExpense", e);
        }
        return response;
    }

    private class GetAllExpensesTask extends AsyncTask<Integer, Void, Response> {

        private HttpURLConnection urlConnection;

        @Override
        protected Response doInBackground(Integer... params) {
            int flatId = params[0];
            String json = "";
            Response response = new Response();
            try {
                URL url = new URL(Properties.SERVER_URL + "expenses/flats/"+flatId);
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
                if(response.getMessageCode()==Response.MESSAGE_OK){
                    Gson gson = new Gson();
                    Type listType = new TypeToken<List<ExpensePOJO>>() {
                    }.getType();
                    response.setObject(gson.fromJson(json, listType));
                }

            }
            return response;
        }
    }

    private class UpdateExpenseTask extends AsyncTask<ExpensePOJO, Void, Response> {

        private HttpURLConnection urlConnection;

        @Override
        protected Response doInBackground(ExpensePOJO... params) {
            ExpensePOJO expensePOJO = params[0];
            Gson gson = new Gson();
            String json = gson.toJson(expensePOJO);
            Response response = new Response();
            try {
                URL url = new URL(Properties.SERVER_URL + "expenses/" + expensePOJO.getId());
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

    private class NewExpenseTask extends AsyncTask<ExpensePOJO, Void, Response> {

        private HttpURLConnection urlConnection;

        @Override
        protected Response doInBackground(ExpensePOJO... params) {
            ExpensePOJO expensePOJO = params[0];
            Gson gson = new Gson();
            String json = gson.toJson(expensePOJO);
            StringBuilder responseMsg = new StringBuilder();
            int msgCode = -1;
            Response response = new Response();
            try {
                URL url = new URL(Properties.SERVER_URL + "expenses/");
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
                    expensePOJO = gson.fromJson(responseMsg.toString(), ExpensePOJO.class);
                    response.setObject(expensePOJO);
                }
                response.setMessageCode(msgCode);
                urlConnection.disconnect();
            }
            return response;
        }
    }

    private class GetExpenseTask extends AsyncTask<Integer, Void, Response> {

        HttpURLConnection urlConnection;

        @Override
        protected Response doInBackground(Integer... params) {
            int id = params[0];

            String responseMsg = "";
            ExpensePOJO expense;
            Response response = new Response();
            try {
                URL url = new URL(Properties.SERVER_URL + "expenses/" + id);
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
                if(response.getMessageCode()==Response.MESSAGE_OK){
                    Gson gson = new Gson();
                    expense = gson.fromJson(responseMsg, ExpensePOJO.class);
                    response.setObject(expense);
                }

            }

            return response;
        }
    }

    private class DeleteExpenseTask extends AsyncTask<ExpensePOJO, Void, Response> {
        private HttpURLConnection urlConnection;

        @Override
        protected Response doInBackground(ExpensePOJO... params) {
            ExpensePOJO expensePOJO = params[0];
            int id = expensePOJO.getId();
            Response response = new Response();
            try {
                URL url = new URL(Properties.SERVER_URL + "expenses/" + id);
                urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setDoOutput(false);
                urlConnection.setDoInput(true);
                urlConnection.setRequestMethod("DELETE");

                InputStream in = urlConnection.getInputStream();
                BufferedReader rd = new BufferedReader(new InputStreamReader(in));
                String line;
                StringBuilder responseMsg = new StringBuilder();
                while ((line = rd.readLine()) != null) {
                    responseMsg.append(line);
                }
                rd.close();
                response.setMessageCode(urlConnection.getResponseCode());

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                urlConnection.disconnect();
            }
            return response;
        }
    }

}
