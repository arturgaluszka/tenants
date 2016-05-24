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
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author Karol
 */
public class FlatRest {

    public FlatRest() {
    }
    private HttpsURLConnection urlConnection;

    public Response CreateFlat(String name, String password, String token) {
        Response response = new Response();
        StringBuilder total = new StringBuilder();
        try {
            URL url = new URL(Properties.SERVER_SECURE_URL + "flats/");
            urlConnection = (HttpsURLConnection) url.openConnection();

            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Authorization", token);

            OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
            out.write("password=" + password + "&name=" + name);
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

    public Response getUsersInFlat(int flatID, String token) {
        Response response = new Response();
        StringBuilder total = new StringBuilder();
        List<Integer> members;

        try {
            URL url = new URL(Properties.SERVER_SECURE_URL + "flats/" + flatID + "/users");
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
                Type listType = new TypeToken<ArrayList<Integer>>() {
                }.getType();
                members = new Gson().fromJson(total.toString(), listType);
                response.setObject(members);
            }
            urlConnection.disconnect();
        }
        return response;
    }

    public Response signUserToFlat(String flatID, String token) {
        Response response = new Response();
        StringBuilder total = new StringBuilder();
        List<Integer> members;

        try {
            URL url = new URL(Properties.SERVER_SECURE_URL + "flats/" + flatID + "/users");
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
                Type listType = new TypeToken<ArrayList<Integer>>() {
                }.getType();
                members = new Gson().fromJson(total.toString(), listType);
                response.setObject(members);
            }
            urlConnection.disconnect();
        }
        return response;
    }

    public Response ChangeFlatPassword(String flatID, String oldPassword, String newPassword, String token) {
        Response response = new Response();
        StringBuilder total = new StringBuilder();

        try {
            URL url = new URL(Properties.SERVER_SECURE_URL + "flats/" + flatID + "/password/");
            urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setRequestMethod("PUT");
            urlConnection.setRequestProperty("Authorization", token);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
            out.write("{ \"oldPassword\":" + "\"" + oldPassword + "\"" + ",\"newPassword\":" + "\"" + newPassword + "\"" + ",\"flatID\":" + "\"" + flatID + "\"" + "}");
            //out.write("flatId="+flatID+"&oldPassword=" + oldPassword + "&newPassword=" + newPassword);
            out.close();

            response.setMessageCode(urlConnection.getResponseCode());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            urlConnection.disconnect();
        }
        return response;
    }

    public Response getFlatId(String name, String token) {
        Response response = new Response();
        StringBuilder total = new StringBuilder();
        try {
            URL url = new URL(Properties.SERVER_SECURE_URL + "flats/name/" + name);
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
                response.setObject(Integer.valueOf(total.toString()));
            }
            urlConnection.disconnect();
        }
        return response;
    }

    public Response getFlatTasks(int flatID) {
        Response response = new Response();
        StringBuilder total = new StringBuilder();
        try {
            URL url = new URL(Properties.SERVER_SECURE_URL + "flats/" + flatID);
            urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setDoOutput(false);
            urlConnection.setDoInput(true);
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Authorization", "1Strajk");

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

    public Response getFlatName(String flatID, String token) {
        Response response = new Response();
        StringBuilder total = new StringBuilder();
        try {
            URL url = new URL(Properties.SERVER_SECURE_URL + "flats/" + flatID);
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

    public Response getFlatMembers(String flatID, String token) {
        Response response = new Response();
        StringBuilder total = new StringBuilder();
        List<Integer> members;
        try {
            URL url = new URL(Properties.SERVER_SECURE_URL + "flats/" + flatID + "/users");
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
