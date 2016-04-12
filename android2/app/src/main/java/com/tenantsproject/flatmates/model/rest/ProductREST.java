package com.tenantsproject.flatmates.model.rest;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tenantsproject.flatmates.model.data.Product;
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

public class ProductREST {

    private Context currentContext;

    public ProductREST() {
        SSLUtils.trustEveryone();
    }


    public Response addProduct(Context context, Product product) {
        currentContext = context;
        AddProductTask task = new AddProductTask();
        Response response = new Response();
        task.execute(product);
        try {
            response = task.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.e("REST", "Can't run task: addProduct", e);
        }
        return response;
    }

    public Response removeFromMainList(Context context, Product product) {
        currentContext = context;
        RemoveFromMainListTask task = new RemoveFromMainListTask();
        Response response = new Response();
        task.execute(product.getId());
        try {
            response = task.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.e("REST", "Can't run task: removeFromMainList", e);
        }
        return response;
    }

    public Response getFlatProducts(Context context, int flatID, int userID, int filter, int page) {
        currentContext = context;
        GetFlatProductsTask task = new GetFlatProductsTask();
        Response response = new Response();
        task.execute(flatID, userID, filter, page);
        try {
            response = task.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.e("REST", "Can't run task: getProducts", e);
        }
        return response;
    }
    public Response update(Context context,Product product) {
        currentContext = context;
        UpdateProductTask task = new UpdateProductTask();
        Response response = new Response();
        task.execute(product);
        try {
            response = task.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.e("REST", "Can't run task: updateProduct", e);
        }
        return response;
    }

    public Response reserve(Context context,Product product) {
        currentContext = context;
        ReserveProductTask task = new ReserveProductTask();
        Response response = new Response();
        task.execute(product);
        try {
            response = task.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.e("REST", "Can't run task: updateProduct", e);
        }
        return response;
    }

    public Response removeFromUserList(Context context, Product product) {
        currentContext = context;
        RemoveFromUserListTask task = new RemoveFromUserListTask();
        Response response = new Response();
        task.execute(product.getId());
        try {
            response = task.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.e("REST", "Can't run task: removeFromUserList", e);
        }
        return response;
    }

    private class AddProductTask extends AsyncTask<Product, Void, Response> {

        private HttpsURLConnection urlConnection;

        @Override
        protected Response doInBackground(Product... params) {
            Response response = new Response();
            StringBuilder total = new StringBuilder();
            Product product = params[0];

            try {
                URL url = new URL(Properties.SERVER_SECURE_URL + "products/");
                urlConnection = (HttpsURLConnection) url.openConnection();

                urlConnection.setDoOutput(true);
                urlConnection.setDoInput(true);
                urlConnection.setRequestMethod("POST");

                if (ProductREST.this.currentContext != null) {
                    urlConnection.setRequestProperty("Authorization", Authenticator.getUserToken(currentContext));
                } else {
                    urlConnection.setRequestProperty("Authorization", "");
                }
                OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
                Gson gson = new Gson();
                String productJson = gson.toJson(product);
                out.write("product=" + productJson);
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

    private class RemoveFromMainListTask extends AsyncTask<Integer, Void, Response> {

        private HttpsURLConnection urlConnection;

        @Override
        protected Response doInBackground(Integer... params) {
            Response response = new Response();
            StringBuilder total = new StringBuilder();
            int productID = params[0];

            try {
                URL url = new URL(Properties.SERVER_SECURE_URL + "products/" + productID + "/mainlist");
                urlConnection = (HttpsURLConnection) url.openConnection();

                urlConnection.setDoOutput(false);
                urlConnection.setDoInput(false);
                urlConnection.setRequestMethod("DELETE");

                if (ProductREST.this.currentContext != null) {
                    urlConnection.setRequestProperty("Authorization", Authenticator.getUserToken(currentContext));
                } else {
                    urlConnection.setRequestProperty("Authorization", "");
                }
//                OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
//                out.write("userID=" + userID + "&flatID=" + flatID);
//                out.close();

                response.setMessageCode(urlConnection.getResponseCode());
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                urlConnection.disconnect();
            }
            return response;
        }
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

                if (ProductREST.this.currentContext != null) {
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

    private class GetFlatProductsTask extends AsyncTask<Integer, Void, Response> {

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
                URL url = new URL(Properties.SERVER_SECURE_URL + "products/flat/" + flatID+"/user/"
                + userID + "/filter/"+filter+"/page/"+page);
                urlConnection = (HttpsURLConnection) url.openConnection();

                urlConnection.setDoOutput(false);
                urlConnection.setDoInput(true);
                urlConnection.setRequestMethod("GET");

                if (ProductREST.this.currentContext != null) {
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
    private class UpdateProductTask extends AsyncTask<Product, Void, Response> {

        private HttpsURLConnection urlConnection;

        @Override
        protected Response doInBackground(Product... params) {
            Product product = params[0];
            Gson gson = new Gson();
            String json = gson.toJson(product);
            Response response = new Response();
            try {
                URL url = new URL(Properties.SERVER_SECURE_URL + "products/" + product.getId());
                urlConnection = (HttpsURLConnection) url.openConnection();

                urlConnection.setDoInput(false);
                urlConnection.setDoOutput(true);
                urlConnection.setRequestMethod("PUT");

                if (ProductREST.this.currentContext != null) {
                    urlConnection.setRequestProperty("Authorization", Authenticator.getUserToken(currentContext));
                } else {
                    urlConnection.setRequestProperty("Authorization", "");
                }

                OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
                out.write("product=" + json);
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

    private class ReserveProductTask extends AsyncTask<Product, Void, Response> {

        private HttpsURLConnection urlConnection;

        @Override
        protected Response doInBackground(Product... params) {
            Product product = params[0];
            Gson gson = new Gson();
            String json = gson.toJson(product);
            Response response = new Response();
            try {
                URL url = new URL(Properties.SERVER_SECURE_URL + "products/" + product.getId()+"/userlist");
                urlConnection = (HttpsURLConnection) url.openConnection();

                urlConnection.setDoInput(false);
                urlConnection.setDoOutput(true);
                urlConnection.setRequestMethod("POST");

                if (ProductREST.this.currentContext != null) {
                    urlConnection.setRequestProperty("Authorization", Authenticator.getUserToken(currentContext));
                } else {
                    urlConnection.setRequestProperty("Authorization", "");
                }

                OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
                out.write("product=" + json);
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

    private class RemoveFromUserListTask extends AsyncTask<Integer, Void, Response> {

        private HttpsURLConnection urlConnection;

        @Override
        protected Response doInBackground(Integer... params) {
            Response response = new Response();
            StringBuilder total = new StringBuilder();
            int productID = params[0];

            try {
                URL url = new URL(Properties.SERVER_SECURE_URL + "products/" + productID + "/userlist");
                urlConnection = (HttpsURLConnection) url.openConnection();

                urlConnection.setDoOutput(false);
                urlConnection.setDoInput(false);
                urlConnection.setRequestMethod("DELETE");

                if (ProductREST.this.currentContext != null) {
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
