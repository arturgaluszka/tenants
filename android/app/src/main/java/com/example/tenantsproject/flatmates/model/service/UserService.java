package com.example.tenantsproject.flatmates.model.service;

import android.content.Context;

import com.example.tenantsproject.flatmates.model.rest.Response;
import com.example.tenantsproject.flatmates.model.rest.UsersREST;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

public class UserService {
    private UsersREST usersREST;

    public UserService() {
        usersREST = new UsersREST();
    }

    /**
     * Tries to log in user using given username and password
     *
     * @param context Current context
     * @param username Users name
     * @param password SHA-1 hashed password
     * @return Response object with a token <br>
     * ErrorCodes: <br>
     * - MESSAGE_OK - logged in<br>
     * - MESSAGE_NOT_FOUND - user not found <br>
     * - MESSAGE_FORBIDDEN -  incorrect password <br>
     */
    public Response login(Context context, String username, String password) {
        return usersREST.login(context, username, password);
    }

    /**
     * Signs user to given flat
     *
     * @param context Current context
     * @param userID ID of user
     * @param flatID ID of a flat to join
     * @param flatPassword SHA-1 hashed flat password
     * @return Response object <br>
     * ErrorCodes: <br>
     * - MESSAGE_OK - signed<br>
     * - MESSAGE_FORBIDDEN -  incorrect password <br>
     * - MESSAGE_UNAUTHORIZED -  user unauthorized <br>
     */
    public Response signToFlat(Context context, int userID, int flatID, String flatPassword) {
        String encryptedPassword = String.valueOf(Hex.encodeHex(DigestUtils.sha1(flatPassword)));
        return usersREST.signToFlat(context, userID, flatID, encryptedPassword);
    }

    /**
     * Deletes user's assignment to a flat
     *
     * @param context Current context
     * @param userID ID of user to move out
     * @param flatID ID of a flat to be moved out from
     * @return Response object <br>
     * ErrorCodes: <br>
     * - MESSAGE_OK - signed out<br>
     * - MESSAGE_NOT_FOUND - user was not signed to this flat <br>
     * - MESSAGE_UNAUTHORIZED -  user unauthorized <br>
     */
    public Response signOut(Context context, int userID, int flatID) {
        return usersREST.moveOut(context, userID, flatID);
    }

    /**
     * Retrieves user's ID when given name
     * @param context Current context
     * @param username Name of a user
     * @return Response object with ID (int) <br>
     * ErrorCodes: <br>
     * - MESSAGE_OK - retrieved<br>
     * - MESSAGE_NOT_FOUND - username not found <br>
     * - MESSAGE_UNAUTHORIZED -  user unauthorized <br>
     */
    public Response getUserID(Context context, String username) {
        return usersREST.getUserID(context, username);
    }

    /**
     * Retrieves name of given user
     * @param context Current context
     * @param userID ID of a user
     * @return Response object with user name <br>
     * ErrorCodes: <br>
     * - MESSAGE_OK - retrieved <br>
     * - MESSAGE_NOT_FOUND - user not found <br>
     * - MESSAGE_UNAUTHORIZED -  user unauthorized <br>
     */
    //TODO: make this return user object
    public Response getUser(Context context, int userID) {
        throw new UnsupportedOperationException();
    }

    /**
     * Creates user with given username and password
     *
     * @param context Current context
     * @param username New username
     * @param password Password
     * @return Response object with userName <br>
     * ErrorCodes: <br>
     * - MESSAGE_OK - created <br>
     * - MESSAGE_FORBIDDEN -  incorrect name or already in use <br>
     */
    public Response createUser(Context context,String username, String password) {
        String encryptedPassword = String.valueOf(Hex.encodeHex(DigestUtils.sha1(password)));
        return usersREST.createUser(context,username,encryptedPassword);
    }

    /**
     * @param context Current context
     * @param userID ID of user to change password
     * @param oldPassword Old password
     * @param newPassword New password
     * @return Response object <br>
     * ErrorCodes: <br>
     * - MESSAGE_OK - changed <br>
     * - MESSAGE_FORBIDDEN -  incorrect password <br>
     */

    public Response changePassword(Context context,int userID, String oldPassword, String newPassword) {
        String encryptedOldPassword = String.valueOf(Hex.encodeHex(DigestUtils.sha1(oldPassword)));
        String encryptedNewPassword = String.valueOf(Hex.encodeHex(DigestUtils.sha1(newPassword)));
        return usersREST.changePassword(context,userID,encryptedOldPassword,encryptedNewPassword);
    }


}
