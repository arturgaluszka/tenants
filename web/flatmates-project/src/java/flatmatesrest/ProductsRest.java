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
import java.lang.reflect.Type;

public class ProductsRest {
    private HttpsURLConnection urlConnection;
   public  Response getListbyGivenFlat(String flatID, String userID, String filter,String page, String token){  // userID == all
        Response response = new Response();
            StringBuilder total = new StringBuilder();
            List<Product> products;
            try {
                URL url = new URL(Properties.SERVER_SECURE_URL + "products/flat/" + flatID+"/user/"  
                        + userID + "/filter/"+filter+"/page/"+page);
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
    public  Response removeProduct(int productID, String token){
      Response response = new Response();
            try {
                URL url = new URL(Properties.SERVER_SECURE_URL + "products/" + productID + "/mainlist");
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
    public Response updateProduct(Product newProduct, String token){  
      Product product = newProduct;
            Gson gson = new Gson();
            String json = gson.toJson(product);
            Response response = new Response();
            try {
                URL url = new URL(Properties.SERVER_SECURE_URL + "products/" + product.getId());
                urlConnection = (HttpsURLConnection) url.openConnection();
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
                urlConnection.setRequestMethod("PUT");
                urlConnection.setRequestProperty("Authorization",token);
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
    public Response createProduct(Product newProduct,String token){
            Response response = new Response();
            StringBuilder total = new StringBuilder();
            Product product = newProduct;
            try {
                URL url = new URL(Properties.SERVER_SECURE_URL + "products/");
                urlConnection = (HttpsURLConnection) url.openConnection();
                urlConnection.setDoOutput(true);
                urlConnection.setDoInput(true);
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Authorization", token);
                
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
  public  Response reserveProduct(Product product, String token){
            Gson gson = new Gson();
            String json = gson.toJson(product);
            Response response = new Response();
            try {
                URL url = new URL(Properties.SERVER_SECURE_URL + "products/" + product.getId()+"/userlist");
                urlConnection = (HttpsURLConnection) url.openConnection();

                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Authorization", token);
                    
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
    public Response purchaseProduct(int productID,String token){
         Response response = new Response();
            StringBuilder total = new StringBuilder();
            try {
               
                URL url = new URL(Properties.SERVER_SECURE_URL + "products/"+productID + "/purchase");
                urlConnection = (HttpsURLConnection) url.openConnection();

                urlConnection.setDoOutput(true);
                urlConnection.setDoInput(true);
                urlConnection.setRequestMethod("POST");
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
                    response.setObject(Integer.valueOf(total.toString()));
                }
                urlConnection.disconnect();
            }
            return response;
    }
    public Response unreserveProduct(Product product, String token){
                Response response = new Response();
            StringBuilder total = new StringBuilder();

            try {
                URL url = new URL(Properties.SERVER_SECURE_URL + "products/" + product.getId() + "/userlist");
                urlConnection = (HttpsURLConnection) url.openConnection();

                urlConnection.setDoOutput(false);
                urlConnection.setDoInput(true);
                urlConnection.setRequestMethod("DELETE");
                urlConnection.setRequestProperty("Authorization",token);
                
                response.setMessageCode(urlConnection.getResponseCode());
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                urlConnection.disconnect();
            }
            return response;
    } 
    public Response cancelPurchase(int productID, String token){
                 Response response = new Response();
            StringBuilder total = new StringBuilder();
            try {
               
                URL url = new URL(Properties.SERVER_SECURE_URL + "products/"+productID + "/purchase");
                urlConnection = (HttpsURLConnection) url.openConnection();

                urlConnection.setDoOutput(true);
                urlConnection.setDoInput(true);
                urlConnection.setRequestMethod("DELETE");
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
                    response.setObject(Integer.valueOf(total.toString()));
                }
                urlConnection.disconnect();
            }
            return response;
    }
    public Response purchaseProduct (Product product, String token){
         Response response = new Response();
            StringBuilder total = new StringBuilder();
            try {
                URL url = new URL(Properties.SERVER_SECURE_URL + "products/"+product.getId()+"/purchase");
                urlConnection = (HttpsURLConnection) url.openConnection();
                urlConnection.setDoOutput(true);
                urlConnection.setDoInput(true);
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Authorization", token);
                OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
                Gson gson = new Gson();
                String productJson = gson.toJson(product);
                out.write("product=" + productJson);
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
