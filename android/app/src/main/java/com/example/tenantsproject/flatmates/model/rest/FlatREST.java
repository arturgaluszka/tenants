package com.example.tenantsproject.flatmates.model.rest;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.tenantsproject.flatmates.security.Authenticator;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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

    public Response getFlatMembers(Context context, int flatID) {
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

    public Response changePassword(Context context, int flatID, String oldPassword, String newPassword) {
        currentContext = context;
        ChangePasswordTask task = new ChangePasswordTask();
        Response response = new Response();
        task.execute(String.valueOf(flatID), oldPassword, newPassword);
        try {
            response = task.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.e("REST", "Can't run task: changePassword", e);
        }
        return response;
    }

    public Response getFlatID(Context context, String name) {
        currentContext = context;
        GetFlatIDTask task = new GetFlatIDTask ();
        Response response = new Response();
        task.execute(name);
        try {
            response = task.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.e("REST", "Can't run task: getUserID", e);
        }
        return response;
    }

    public Response getFlat(Context context, int flatID) {
        currentContext = context;
        GetFlatTask task = new GetFlatTask ();
        Response response = new Response();
        task.execute(flatID);
        try {
            response = task.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.e("REST", "Can't run task: getUserID", e);
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

                if (FlatREST.this.currentContext != null) {
                    urlConnection.setRequestProperty("Authorization", Authenticator.getUserToken(currentContext));
                } else {
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
                if (!total.toString().isEmpty()) {
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
            int flatID = params[0];
            List<Integer> members;

            try {
                URL url = new URL(Properties.SERVER_SECURE_URL + "flats/" + flatID + "/users");
                urlConnection = (HttpsURLConnection) url.openConnection();

                urlConnection.setDoOutput(false);
                urlConnection.setDoInput(true);
                urlConnection.setRequestMethod("GET");

                if (FlatREST.this.currentContext != null) {
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
                    Type listType = new TypeToken<ArrayList<Integer>>() {
                    }.getType();
                    members = new Gson().fromJson(total.toString(), listType);
                    response.setObject(members);
                }
                urlConnection.disconnect();
            }
            return response;
        }
    }

    private class ChangePasswordTask extends AsyncTask<String, Void, Response> {

        private HttpsURLConnection urlConnection;

        @Override
        protected Response doInBackground(String... params) {
            Response response = new Response();
            StringBuilder total = new StringBuilder();
            String flatID = params[0];
            String oldPassword = params[1];
            String newPassword = params[2];
            try {
                URL url = new URL(Properties.SERVER_SECURE_URL + "flats/" + flatID + "/password/");
                urlConnection = (HttpsURLConnection) url.openConnection();

                urlConnection.setDoOutput(true);
                urlConnection.setDoInput(true);
                urlConnection.setRequestMethod("PUT");

                if (FlatREST.this.currentContext != null) {
                    urlConnection.setRequestProperty("Authorization", Authenticator.getUserToken(currentContext));
                } else {
                    urlConnection.setRequestProperty("Authorization", "");
                }
                OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
                out.write("flatID=" + flatID + "&oldPassword=" + oldPassword + "&newPassword=" + newPassword);
                out.close();

                response.setMessageCode(urlConnection.getResponseCode());
            } catch (IOException e) {
                e.printStackTrace();
            } finally {

                urlConnection.disconnect();
            }
            return response;
        }
    }

    private class GetFlatIDTask extends AsyncTask<String, Void, Response> {

        private HttpsURLConnection urlConnection;

        @Override
        protected Response doInBackground(String... params) {
            Response response = new Response();
            StringBuilder total = new StringBuilder();
            String name = params[0];

            try {
                URL url = new URL(Properties.SERVER_SECURE_URL + "flats/name/" + name);
                urlConnection = (HttpsURLConnection) url.openConnection();

                urlConnection.setDoOutput(false);
                urlConnection.setDoInput(true);
                urlConnection.setRequestMethod("GET");

                if(FlatREST.this.currentContext!=null){
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
                if(!total.toString().isEmpty()){
                    response.setObject(Integer.valueOf(total.toString()));
                }
                urlConnection.disconnect();
            }
            return response;
        }
    }

    private class GetFlatTask extends AsyncTask<Integer, Void, Response> {

        private HttpsURLConnection urlConnection;

        @Override
        protected Response doInBackground(Integer... params) {
            Response response = new Response();
            StringBuilder total = new StringBuilder();
            int flatID = params[0];

            try {
                URL url = new URL(Properties.SERVER_SECURE_URL + "flats/" + flatID);
                urlConnection = (HttpsURLConnection) url.openConnection();

                urlConnection.setDoOutput(false);
                urlConnection.setDoInput(true);
                urlConnection.setRequestMethod("GET");

                if(FlatREST.this.currentContext!=null){
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
                if(!total.toString().isEmpty()){
                    response.setObject(total.toString());
                }
                urlConnection.disconnect();
            }
            return response;
        }
    }
}
