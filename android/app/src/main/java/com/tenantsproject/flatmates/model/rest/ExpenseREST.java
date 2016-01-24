package com.tenantsproject.flatmates.model.rest;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tenantsproject.flatmates.model.data.Expense;

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

    public Response update(Expense expense) {
        UpdateExpenseTask task = new UpdateExpenseTask();
        Response response = new Response();
        task.execute(expense);
        try {
            response = task.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.e("REST", "Can't run task: updateExpense", e);
        }
        return response;
    }

    public Response newExpense(Expense expense) {
        NewExpenseTask task = new NewExpenseTask();
        task.execute(expense);
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

    public Response delete(Expense expense) {
        DeleteExpenseTask task = new DeleteExpenseTask();
        task.execute(expense);
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
                    Type listType = new TypeToken<List<Expense>>() {
                    }.getType();
                    response.setObject(gson.fromJson(json, listType));
                }

            }
            return response;
        }
    }

    private class UpdateExpenseTask extends AsyncTask<Expense, Void, Response> {

        private HttpURLConnection urlConnection;

        @Override
        protected Response doInBackground(Expense... params) {
            Expense expense = params[0];
            Gson gson = new Gson();
            String json = gson.toJson(expense);
            Response response = new Response();
            try {
                URL url = new URL(Properties.SERVER_URL + "expenses/" + expense.getId());
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

    private class NewExpenseTask extends AsyncTask<Expense, Void, Response> {

        private HttpURLConnection urlConnection;

        @Override
        protected Response doInBackground(Expense... params) {
            Expense expense = params[0];
            Gson gson = new Gson();
            String json = gson.toJson(expense);
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
                    expense = gson.fromJson(responseMsg.toString(), Expense.class);
                    response.setObject(expense);
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
            Expense expense;
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
                    expense = gson.fromJson(responseMsg, Expense.class);
                    response.setObject(expense);
                }

            }

            return response;
        }
    }

    private class DeleteExpenseTask extends AsyncTask<Expense, Void, Response> {
        private HttpURLConnection urlConnection;

        @Override
        protected Response doInBackground(Expense... params) {
            Expense expense = params[0];
            int id = expense.getId();
            Response response = new Response();
            try {
                URL url = new URL(Properties.SERVER_URL + "expenses/" + id);
                urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setDoOutput(false);
                urlConnection.setDoInput(false);
                urlConnection.setRequestMethod("DELETE");
                response.setMessageCode(urlConnection.getResponseCode());


            } catch (IOException e) {
                Log.e("EXPENSE_REST","Can't load inputstream",e);
            } finally {
                urlConnection.disconnect();
            }
            return response;
        }
    }

}
