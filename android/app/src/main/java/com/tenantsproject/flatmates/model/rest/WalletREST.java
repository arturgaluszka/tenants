package com.tenantsproject.flatmates.model.rest;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.tenantsproject.flatmates.model.data.Wallet;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;


public class WalletREST {
    public Response getWalletState(int id) {
        GetWalletStateTask task = new GetWalletStateTask();
        Response response = new Response();
        try {
            task.execute(id);
            response = task.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.e("REST", "Can't run task: getWalletState", e);
        }
        return response;
    }

    public Response update(Wallet wallet) {
        UpdateWalletTask task = new UpdateWalletTask();
        Response response = new Response();
        task.execute(wallet);
        try {
            response = task.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.e("REST", "Can't run task: updateWallet", e);
        }
        return response;
    }

    private class GetWalletStateTask extends AsyncTask<Integer, Void, Response> {

        private HttpURLConnection urlConnection;

        @Override
        protected Response doInBackground(Integer... params) {
            int flatId = params[0];
            String json = "";
            Response response = new Response();
            try {
                URL url = new URL(Properties.SERVER_URL + "wallet/flat/" + flatId);
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
                    response.setObject(gson.fromJson(json, Wallet.class));
                }
            }
            return response;
        }
    }

    private class UpdateWalletTask extends AsyncTask<Wallet, Void, Response> {

        private HttpURLConnection urlConnection;

        @Override
        protected Response doInBackground(Wallet... params) {
            Wallet wallet = params[0];
            Gson gson = new Gson();
            String json = gson.toJson(wallet);
            Response response = new Response();
            try {
                URL url = new URL(Properties.SERVER_URL + "wallet/" + wallet.getId());
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
}
