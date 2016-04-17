package com.example.tenantsproject.flatmates.model.rest;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.tenantsproject.flatmates.model.data.Product;
import com.example.tenantsproject.flatmates.model.data.Statistics;
import com.example.tenantsproject.flatmates.security.Authenticator;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;

public class ArchiveREST {
    private Context currentContext;

    public ArchiveREST() {
        SSLUtils.trustEveryone();
    }


    public Response getFlatArchive(Context context, int flatID, int userID, int filter, int page) {
        currentContext = context;
        GetFlatArchiveTask task = new GetFlatArchiveTask();
        Response response = new Response();
        task.execute(flatID, userID, filter, page);
        try {
            response = task.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.e("REST", "Can't run task: GetFlatArchive", e);
        }
        return response;
    }
    public Response getStatsTask(Context context, int userID,int flatID) {
        currentContext = context;
        GetStatsTask task = new GetStatsTask();
        Response response = new Response();
        task.execute(userID,flatID);
        try {
            response = task.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.e("REST", "Can't run task: getStatsTask", e);
        }
        return response;
    }
    public Response cancelBuy(Context context, Product product) {
        currentContext = context;
        CancelBuyTask task = new CancelBuyTask();
        Response response = new Response();
        task.execute(product.getId());
        try {
            response = task.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.e("REST", "Can't run task: cancelBuy", e);
        }
        return response;
    }

    private class GetFlatArchiveTask extends AsyncTask<Integer, Void, Response> {

        private HttpsURLConnection urlConnection;

        @Override
        protected Response doInBackground(Integer... params) {
            Response response = new Response();
            StringBuilder total = new StringBuilder();
            int flatID = params[0];
            int userID = params[1];
            int filter = params[2];
            int page = params[3];
            List<Product> products;

            try {
                URL url = new URL(Properties.SERVER_SECURE_URL + "archive/flat/" + flatID+"/user/"
                        + userID + "/filter/"+filter+"/page/"+page);
                urlConnection = (HttpsURLConnection) url.openConnection();

                urlConnection.setDoOutput(false);
                urlConnection.setDoInput(true);
                urlConnection.setRequestMethod("GET");

                if (ArchiveREST.this.currentContext != null) {
                    urlConnection.setRequestProperty("Authorization", Authenticator.getUserToken(currentContext));
                } else {
                    urlConnection.setRequestProperty("Authorization", "");
                }

                InputStream in = urlConnection.getInputStream();

                BufferedReader r = new BufferedReader(new InputStreamReader(in));

                String line;
                while ((line = r.readLine()) != null) {
                    total.append(line);
                }
                response.setMessageCode(urlConnection.getResponseCode());
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (!total.toString().isEmpty()) {
                    Type listType = new TypeToken<ArrayList<Product>>() {
                    }.getType();
                    products = new Gson().fromJson(total.toString(), listType);
                    response.setObject(products);
                }
                urlConnection.disconnect();
            }
            return response;
        }
    }
    private class GetStatsTask extends AsyncTask<Integer, Void, Response> {

        private HttpsURLConnection urlConnection;

        @Override
        protected Response doInBackground(Integer... params) {
            Response response = new Response();
            StringBuilder total = new StringBuilder();
            int userID = params[0];
            int flatID = params[1];

            try {
                URL url = new URL(Properties.SERVER_SECURE_URL + "stats/user/" + userID+"/flat/"+flatID);
                urlConnection = (HttpsURLConnection) url.openConnection();

                urlConnection.setDoOutput(false);
                urlConnection.setDoInput(true);
                urlConnection.setRequestMethod("GET");

                if(ArchiveREST.this.currentContext!=null){
                    urlConnection.setRequestProperty("Authorization",Authenticator.getUserToken(currentContext));
                } else{
                    urlConnection.setRequestProperty("Authorization", "");
                }

                response.setMessageCode(urlConnection.getResponseCode());
                if(response.getMessageCode()==Response.MESSAGE_OK) {
                    InputStream in = urlConnection.getInputStream();

                    BufferedReader r = new BufferedReader(new InputStreamReader(in));

                    String line;
                    while ((line = r.readLine()) != null) {
                        total.append(line);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (!total.toString().isEmpty()) {
                    Statistics stats = new Gson().fromJson(total.toString(), Statistics.class);
                    response.setObject(stats);
                }

                urlConnection.disconnect();
            }
            return response;
        }
    }
    private class CancelBuyTask extends AsyncTask<Integer, Void, Response> {

        private HttpsURLConnection urlConnection;

        @Override
        protected Response doInBackground(Integer... params) {
            Response response = new Response();
            StringBuilder total = new StringBuilder();
            int productID = params[0];

            try {
                URL url = new URL(Properties.SERVER_SECURE_URL + "products/" + productID + "/purchase");
                urlConnection = (HttpsURLConnection) url.openConnection();

                urlConnection.setDoOutput(false);
                urlConnection.setDoInput(false);
                urlConnection.setRequestMethod("DELETE");

                if (ArchiveREST.this.currentContext != null) {
                    urlConnection.setRequestProperty("Authorization", Authenticator.getUserToken(currentContext));
                } else {
                    urlConnection.setRequestProperty("Authorization", "");
                }
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
