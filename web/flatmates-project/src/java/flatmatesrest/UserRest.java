/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flatmatesrest;

import flatmatesrest.model.data.Product;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.net.ssl.HttpsURLConnection;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 *
 * @author Karol
 */
public class UserRest {

    public UserRest() {
    }

    private HttpsURLConnection urlConnection;

    public Response Login(String username, String password) {

        Response response = new Response();
        SSLUtils.trustEveryone();
        StringBuilder total = new StringBuilder();
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
                response.setObject(total.toString());
            }
            urlConnection.disconnect();
        }
        return response;

    }

    public Response signUserToFlat(String userID, String flatID, String flatPassword, String token) {

        Response response = new Response();
        StringBuilder total = new StringBuilder();
        try {
            URL url = new URL(Properties.SERVER_SECURE_URL + "users/" + userID + "/flat/");
            urlConnection = (HttpsURLConnection) url.openConnection();

            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Authorization", token);

            OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
            out.write("userID=" + userID + "&flatID=" + flatID + "&flatPassword=" + flatPassword);
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
                response.setObject(total);
            }
            urlConnection.disconnect();
        }
        return response;

    }

    public Response deleteUserFromFlat(String userID, String flatID, String token) {
        Response response = new Response();
        StringBuilder total = new StringBuilder();
        try {
            URL url = new URL(Properties.SERVER_SECURE_URL + "users/" + userID + "/flat/" + flatID);
            urlConnection = (HttpsURLConnection) url.openConnection();

            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setRequestMethod("DELETE");
            urlConnection.setRequestProperty("Authorization", token);
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

    public Response getUserId(String username, String token) {
        Response response = new Response();
        StringBuilder total = new StringBuilder();
        try {
            URL url = new URL(Properties.SERVER_SECURE_URL + "users/name/" + username);
            urlConnection = (HttpsURLConnection) url.openConnection();

            urlConnection.setDoOutput(false);
            urlConnection.setDoInput(true);
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Authorization",token);

            response.setMessageCode(urlConnection.getResponseCode());
            if (response.getMessageCode() == Response.MESSAGE_OK) {
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
                response.setObject(Integer.valueOf(total.toString()));
            }
            urlConnection.disconnect();
        }
        return response;

    }

    public Response getUserName(int userID, String token) {
        Response response = new Response();
        StringBuilder total = new StringBuilder();
        try {
            URL url = new URL(Properties.SERVER_SECURE_URL + "users/" + userID);
            urlConnection = (HttpsURLConnection) url.openConnection();

            urlConnection.setDoOutput(false);
            urlConnection.setDoInput(true);
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Authorization", token );

            response.setMessageCode(urlConnection.getResponseCode());
            if (response.getMessageCode() == Response.MESSAGE_OK) {
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

    public Response CreateUser(String username, String password) {
        Response response = new Response();
        StringBuilder total = new StringBuilder();

        try {
            URL url = new URL(Properties.SERVER_SECURE_URL + "users/");
            urlConnection = (HttpsURLConnection) url.openConnection();

            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Authorization", "1Strajk");

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
            if (!total.toString().isEmpty()) {
                response.setObject(Integer.valueOf(total.toString()));
            }
            urlConnection.disconnect();
        }
        return response;
    }

    public Response ChangePassword(String oldPassword, String newPassword, String userID, String token) {
        Response response = new Response();
        StringBuilder total = new StringBuilder();
        try {
            URL url = new URL(Properties.SERVER_SECURE_URL + "users/" + userID + "/password/");
            urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setRequestMethod("PUT");
            urlConnection.setRequestProperty("Authorization", token);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
            //out.write("userID=" + userID + "&oldPassword=" + oldPassword + "&newPassword=" + newPassword);
            out.write("{ \"oldPassword\":" + "\"" + oldPassword + "\"" + ",\"newPassword\":" + "\"" + newPassword + "\"" + "}");
            out.close();

            response.setMessageCode(urlConnection.getResponseCode());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            urlConnection.disconnect();
        }
        return response;

    }

    public Response getFlats(String userID, String token) {
        Response response = new Response();
        StringBuilder total = new StringBuilder();
        try {
            URL url = new URL(Properties.SERVER_SECURE_URL + "users/" + userID + "/flats");
            urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setDoOutput(false);
            urlConnection.setDoInput(true);
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Authorization", token);
            response.setMessageCode(urlConnection.getResponseCode());
            if (response.getMessageCode() == Response.MESSAGE_OK) {
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
