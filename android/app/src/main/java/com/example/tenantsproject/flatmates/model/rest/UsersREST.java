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


public class UsersREST {
    private Context currentContext;

    public UsersREST() {
        SSLUtils.trustEveryone();
    }

    public Response login(Context context, String username, String password) {
        currentContext = context;
        LoginTask task = new LoginTask();
        Response response = new Response();
        task.execute(username, password);
        try {
            response = task.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.e("REST", "Can't run task: login", e);
        }
        return response;
    }

    public Response signToFlat(Context context, int userID, int flatID, String flatPassword) {
        currentContext = context;
        SignToFlatTask task = new SignToFlatTask();
        Response response = new Response();
        task.execute(String.valueOf(userID), String.valueOf(flatID), flatPassword);
        try {
            response = task.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.e("REST", "Can't run task: signToFlat", e);
        }
        return response;
    }

    public Response moveOut(Context context, int userID, int flatID) {
        currentContext = context;
        MoveOutTask task = new MoveOutTask();
        Response response = new Response();
        task.execute(userID, flatID);
        try {
            response = task.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.e("REST", "Can't run task: moveOut", e);
        }
        return response;
    }
    public Response getUserID(Context context, String username) {
        currentContext = context;
        GetUserIDTask task = new GetUserIDTask();
        Response response = new Response();
        task.execute(username);
        try {
            response = task.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.e("REST", "Can't run task: getUserID", e);
        }
        return response;
    }
    public Response getUser(Context context, int id) {
        currentContext = context;
        GetUserTask task = new GetUserTask();
        Response response = new Response();
        task.execute(id);
        try {
            response = task.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.e("REST", "Can't run task: getUserID", e);
        }
        return response;
    }
    public Response createUser(Context context, String username, String password) {
        currentContext = context;
        CreateUserTask task = new CreateUserTask();
        Response response = new Response();
        task.execute(username, password);
        try {
            response = task.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.e("REST", "Can't run task: createUser", e);
        }
        return response;
    }

    public Response changePassword(Context context, int userID, String oldPassword, String newPassword) {
        currentContext = context;
        ChangePasswordTask task = new ChangePasswordTask();
        Response response = new Response();
        task.execute(String.valueOf(userID),oldPassword,newPassword);
        try {
            response = task.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.e("REST", "Can't run task: changePassword", e);
        }
        return response;
    }

    public Response getUserFlats(Context context, int userID) {
        currentContext = context;
        GetUserFlatsTask task = new GetUserFlatsTask();
        Response response = new Response();
        task.execute(userID);
        try {
            response = task.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.e("REST", "Can't run task: getUserFlats", e);
        }
        return response;
    }
    private class LoginTask extends AsyncTask<String, Void, Response> {

        private HttpsURLConnection urlConnection;

        @Override
        protected Response doInBackground(String... params) {
            Response response = new Response();
            StringBuilder total = new StringBuilder();
            String username = params[0];
            String password = params[1];
            try {
                URL url = new URL(Properties.SERVER_SECURE_URL + "login");
                urlConnection = (HttpsURLConnection) url.openConnection();

                urlConnection.setDoOutput(true);
                urlConnection.setDoInput(true);
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Authorization", "");
                OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
                out.write("username=" + username + "&password=" + password);
                out.close();
                response.setMessageCode(urlConnection.getResponseCode());
                if(response.getMessageCode()==Response.MESSAGE_OK){
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
                    response.setObject(total.toString());
                }
                urlConnection.disconnect();
            }
            return response;
        }
    }

    private class SignToFlatTask extends AsyncTask<String, Void, Response> {

        private HttpsURLConnection urlConnection;

        @Override
        protected Response doInBackground(String... params) {
            Response response = new Response();
            StringBuilder total = new StringBuilder();
            String userID = params[0];
            String flatID = params[1];
            String flatPassword = params[2];
            try {
                URL url = new URL(Properties.SERVER_SECURE_URL + "users/" + userID + "/flat/");
                urlConnection = (HttpsURLConnection) url.openConnection();

                urlConnection.setDoOutput(true);
                urlConnection.setDoInput(true);
                urlConnection.setRequestMethod("POST");

                if(UsersREST.this.currentContext!=null){
                    urlConnection.setRequestProperty("Authorization", Authenticator.getUserToken(currentContext));
                } else{
                    urlConnection.setRequestProperty("Authorization", "");
                }
                OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
                out.write("userID=" + userID + "&flatID=" + flatID + "&flatPassword=" + flatPassword);
                out.close();

//                InputStream in = urlConnection.getInputStream();
//
//                BufferedReader r = new BufferedReader(new InputStreamReader(in));
//
//                String line;
//                while ((line = r.readLine()) != null) {
//                    total.append(line);
//                }

                response.setMessageCode(urlConnection.getResponseCode());
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
//                if (!total.toString().isEmpty()) {
//                    response.setObject(total);
//                }
                urlConnection.disconnect();
            }
            return response;
        }
    }

    private class MoveOutTask extends AsyncTask<Integer, Void, Response> {

        private HttpsURLConnection urlConnection;

        @Override
        protected Response doInBackground(Integer... params) {
            Response response = new Response();
            StringBuilder total = new StringBuilder();
            int userID = params[0];
            int flatID = params[1];

            try {
                URL url = new URL(Properties.SERVER_SECURE_URL + "users/" + userID + "/flat/" + flatID);
                urlConnection = (HttpsURLConnection) url.openConnection();

                urlConnection.setDoOutput(true);
                urlConnection.setDoInput(false);
                urlConnection.setRequestMethod("DELETE");

                if(UsersREST.this.currentContext!=null){
                    urlConnection.setRequestProperty("Authorization",Authenticator.getUserToken(currentContext));
                } else{
                    urlConnection.setRequestProperty("Authorization", "");
                }
                OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
                out.write("userID=" + userID + "&flatID=" + flatID);
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

    private class GetUserIDTask extends AsyncTask<String, Void, Response> {

        private HttpsURLConnection urlConnection;

        @Override
        protected Response doInBackground(String... params) {
            Response response = new Response();
            StringBuilder total = new StringBuilder();
            String username = params[0];

            try {
                URL url = new URL(Properties.SERVER_SECURE_URL + "users/name/" + username);
                urlConnection = (HttpsURLConnection) url.openConnection();

                urlConnection.setDoOutput(false);
                urlConnection.setDoInput(true);
                urlConnection.setRequestMethod("GET");

                if(UsersREST.this.currentContext!=null){
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

    private class GetUserTask extends AsyncTask<Integer, Void, Response> {

        private HttpsURLConnection urlConnection;

        @Override
        protected Response doInBackground(Integer... params) {
            Response response = new Response();
            StringBuilder total = new StringBuilder();
            int userID = params[0];

            try {
                URL url = new URL(Properties.SERVER_SECURE_URL + "users/" + userID);
                urlConnection = (HttpsURLConnection) url.openConnection();

                urlConnection.setDoOutput(false);
                urlConnection.setDoInput(true);
                urlConnection.setRequestMethod("GET");

                if(UsersREST.this.currentContext!=null){
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

    private class CreateUserTask extends AsyncTask<String, Void, Response> {

        private HttpsURLConnection urlConnection;

        @Override
        protected Response doInBackground(String... params) {
            Response response = new Response();
            StringBuilder total = new StringBuilder();
            String username = params[0];
            String password = params[1];

            try {
                URL url = new URL(Properties.SERVER_SECURE_URL + "users/");
                urlConnection = (HttpsURLConnection) url.openConnection();

                urlConnection.setDoOutput(true);
                urlConnection.setDoInput(true);
                urlConnection.setRequestMethod("POST");

                if(UsersREST.this.currentContext!=null){
                    urlConnection.setRequestProperty("Authorization",Authenticator.getUserToken(currentContext));
                } else{
                    urlConnection.setRequestProperty("Authorization", "");
                }
                OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
                out.write("username=" + username + "&password=" + password);
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

    private class ChangePasswordTask extends AsyncTask<String, Void, Response> {

        private HttpsURLConnection urlConnection;

        @Override
        protected Response doInBackground(String... params) {
            Response response = new Response();
            StringBuilder total = new StringBuilder();
            String userID = params[0];
            String oldPassword = params[1];
            String newPassword = params[2];
            try {
                URL url = new URL(Properties.SERVER_SECURE_URL + "users/" + userID + "/password/");
                urlConnection = (HttpsURLConnection) url.openConnection();

                urlConnection.setDoOutput(true);
                urlConnection.setDoInput(true);
                urlConnection.setRequestMethod("PUT");

                if(UsersREST.this.currentContext!=null){
                    urlConnection.setRequestProperty("Authorization",Authenticator.getUserToken(currentContext));
                } else{
                    urlConnection.setRequestProperty("Authorization", "");
                }
                OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
                out.write("userID=" + userID + "&oldPassword=" + oldPassword + "&newPassword=" + newPassword);
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

    private class GetUserFlatsTask extends AsyncTask<Integer, Void, Response> {

        private HttpsURLConnection urlConnection;

        @Override
        protected Response doInBackground(Integer... params) {
            Response response = new Response();
            StringBuilder total = new StringBuilder();
            int userID = params[0];
            List<Integer> flats;

            try {
                URL url = new URL(Properties.SERVER_SECURE_URL + "users/" + userID + "/flats");
                urlConnection = (HttpsURLConnection) url.openConnection();

                urlConnection.setDoOutput(false);
                urlConnection.setDoInput(true);
                urlConnection.setRequestMethod("GET");

                if (UsersREST.this.currentContext != null) {
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
                    flats = new Gson().fromJson(total.toString(), listType);
                    response.setObject(flats);
                }
                urlConnection.disconnect();
            }
            return response;
        }
    }
}
