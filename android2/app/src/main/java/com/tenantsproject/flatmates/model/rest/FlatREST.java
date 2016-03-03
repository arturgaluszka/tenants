package com.tenantsproject.flatmates.model.rest;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tenantsproject.flatmates.security.Authenticator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;

public class FlatREST {

    private Context currentContext;

    public FlatREST() {
        SSLUtils.trustEveryone();
    }


    public Response createFlat(Context context, String password) {
        currentContext = context;
        CreateFlatTask task = new CreateFlatTask();
        Response response = new Response();
        task.execute(password);
        try {
            response = task.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.e("REST", "Can't run task: createUser", e);
        }
        return response;
    }

    public Response getFlatMembers(Context context, int flatID){
        currentContext = context;
        GetFlatMembersTask task = new GetFlatMembersTask();
        Response response = new Response();
        task.execute(flatID);
        try {
            response = task.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.e("REST", "Can't run task: getFlatMembers", e);
        }
        return response;
    }

    private class CreateFlatTask extends AsyncTask<String, Void, Response> {

        private HttpsURLConnection urlConnection;

        @Override
        protected Response doInBackground(String... params) {
            Response response = new Response();
            StringBuilder total = new StringBuilder();
            String password = params[0];

            try {
                URL url = new URL(Properties.SERVER_SECURE_URL + "flats/");
                urlConnection = (HttpsURLConnection) url.openConnection();

                urlConnection.setDoOutput(true);
                urlConnection.setDoInput(true);
                urlConnection.setRequestMethod("POST");

                if(FlatREST.this.currentContext!=null){
                    urlConnection.setRequestProperty("Authorization",Authenticator.getUserToken(currentContext));
                } else{
                    urlConnection.setRequestProperty("Authorization", "");
                }
                OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
                out.write("password=" + password);
                out.close();

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
                if(!total.toString().isEmpty()){
                    response.setObject(Integer.valueOf(total.toString()));
                }
                urlConnection.disconnect();
            }
            return response;
        }
    }

    private class GetFlatMembersTask extends AsyncTask<Integer, Void, Response> {

        private HttpsURLConnection urlConnection;

        @Override
        protected Response doInBackground(Integer... params) {
            Response response = new Response();
            StringBuilder total = new StringBuilder();
            int flatID= params[0];
            List<Integer> members;

            try {
                URL url = new URL(Properties.SERVER_SECURE_URL + "flats/"+flatID+"/users");
                urlConnection = (HttpsURLConnection) url.openConnection();

                urlConnection.setDoOutput(false);
                urlConnection.setDoInput(true);
                urlConnection.setRequestMethod("GET");

                if(FlatREST.this.currentContext!=null){
                    urlConnection.setRequestProperty("Authorization",Authenticator.getUserToken(currentContext));
                } else{
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
                if(!total.toString().isEmpty()){
                    Type listType = new TypeToken<ArrayList<Integer>>() {}.getType();
                    members =  new Gson().fromJson(total.toString(), listType);
                    response.setObject(members);
                }
                urlConnection.disconnect();
            }
            return response;
        }
    }

}
