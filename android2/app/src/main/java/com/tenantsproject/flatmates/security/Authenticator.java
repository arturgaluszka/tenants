package com.tenantsproject.flatmates.security;

import android.content.Context;
import android.content.SharedPreferences;

import com.tenantsproject.flatmates.model.rest.Response;
import com.tenantsproject.flatmates.model.rest.UsersREST;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

public class Authenticator {

    public final static String AUTHENTICATION_PREFERENCES = "authenticationPreferences";
    public final static String USER_TOKEN = "userToken";
    private UsersREST usersREST;

    public Authenticator() {
        usersREST = new UsersREST();
    }

    /**
     * Logs in user using given credentials
     * @param context Current context
     * @param username Username
     * @param password plain text password
     * @return Response object
     */
    public Response login(Context context, String username, String password) {
        String encryptedPassword = String.valueOf(Hex.encodeHex(DigestUtils.sha1(password)));
        Response response = usersREST.login(context, username, encryptedPassword);
        if (response.getMessageCode() == Response.MESSAGE_OK) {
            SharedPreferences sharedPreferences = context.getSharedPreferences(AUTHENTICATION_PREFERENCES, Context.MODE_PRIVATE);
            sharedPreferences.edit().putString(USER_TOKEN, (String) response.getObject()).commit();
        }
        return response;
    }

    /**
     * Returns a token used to authenticate current user
     *
     * @param context Current context
     * @return Token
     */
    public static String getUserToken(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(AUTHENTICATION_PREFERENCES, Context.MODE_PRIVATE);
        return sharedPreferences.getString(USER_TOKEN, "0");
    }

    /**
     * Returns true if user with given token is logged in
     *
     * @param token user token
     * @return true/false
     */
    public boolean loggedIn(String token) {
        return token.startsWith("1");
    }

    /**
     * Returns true if user is logged in
     *
     * @param context Current context
     * @return true/false
     */
    public boolean loggedIn(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(AUTHENTICATION_PREFERENCES, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString(USER_TOKEN, "0");
        return token.startsWith("1");
    }

    /**
     * Logs out current user
     * @param context Current context
     */
    public void logOut(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(AUTHENTICATION_PREFERENCES, Context.MODE_PRIVATE);
        String token = "0";
        sharedPreferences.edit().putString(USER_TOKEN, token);
    }
}
