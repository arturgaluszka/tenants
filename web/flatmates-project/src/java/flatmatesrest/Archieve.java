/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flatmatesrest;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import flatmatesrest.model.data.Product;
import flatmatesrest.model.data.Statistics;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.net.ssl.HttpsURLConnection;

/**
 *
 * @author Karol
 */
public class Archieve {

    HttpsURLConnection urlConnection;

    public Response getStats(String userID, String flatID, String token, String filter, String page) {

        Response response = new Response();
        StringBuilder total = new StringBuilder();

        List<Product> products;

        try {
            URL url = new URL(Properties.SERVER_SECURE_URL + "archive/flat/" + flatID + "/user/"
                    + userID + "/filter/" + filter + "/page/" + page);
            urlConnection = (HttpsURLConnection) url.openConnection();

            urlConnection.setDoOutput(false);
            urlConnection.setDoInput(true);
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Authorization", token);

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

    public Response getAmount(String userID, String flatID, String token) {
        Response response = new Response();
        StringBuilder total = new StringBuilder();

        try {
            URL url = new URL(Properties.SERVER_SECURE_URL + "stats/user/" + userID + "/flat/" + flatID);
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
                Statistics stats = new Gson().fromJson(total.toString(), Statistics.class);
                response.setObject(stats);
            }

            urlConnection.disconnect();
        }
        return response;

    }

    public Response cancelBuy(String productID, String token) {
        Response response = new Response();
        StringBuilder total = new StringBuilder();

        try {
            URL url = new URL(Properties.SERVER_SECURE_URL + "products/" + productID + "/purchase");
            urlConnection = (HttpsURLConnection) url.openConnection();

            urlConnection.setDoOutput(false);
            urlConnection.setDoInput(true);
            urlConnection.setRequestMethod("DELETE");
            urlConnection.setRequestProperty("Authorization", token);
            
            response.setMessageCode(urlConnection.getResponseCode());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }
        return response;
    }
}
