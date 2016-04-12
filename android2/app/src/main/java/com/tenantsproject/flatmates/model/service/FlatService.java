package com.tenantsproject.flatmates.model.service;

import android.content.Context;

import com.tenantsproject.flatmates.model.rest.FlatREST;
import com.tenantsproject.flatmates.model.rest.Response;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

public class FlatService {
    //TODO: make getID(name), and getName(ID)
    private FlatREST flatREST;

    public FlatService() {
        flatREST = new FlatREST();
    }

    /**
     * @param context Current context
     * @param password password for accessing created flat
     * @return Response object with flat id (int)<br>
     * ErrorCodes: <br>
     * - MESSAGE_OK - created<br>
     * - MESSAGE_UNAUTHORIZED -  user unauthorized <br>
     */
    public Response createFlat(Context context, String password) {
        String encryptedPassword = String.valueOf(Hex.encodeHex(DigestUtils.sha1(password)));
        return flatREST.createFlat(context,encryptedPassword);
    }

    /**
     * Returns list of members signed to given flat
     * @param context Current context
     * @param flatID ID of current user's flat
     * @return Response object with List of users IDs' (Integer) <br>
     * ErrorCodes: <br>
     * - MESSAGE_OK - retrieved <br>
     * - MESSAGE_FORBIDDEN -  not a member of given flat <br>
     * - MESSAGE_UNAUTHORIZED -  user unauthorized <br>
     */
    public Response getFlatMembers(Context context, int flatID){
        return flatREST.getFlatMembers(context, flatID);
    }

    /**
     * @param context Current context
     * @param flatID ID of flat to change password
     * @param oldPassword Old password
     * @param newPassword New password
     * @return Response object <br>
     * ErrorCodes: <br>
     * - MESSAGE_OK - changed <br>
     * - MESSAGE_FORBIDDEN -  incorrect password <br>
     */

    public Response changePassword(Context context,int flatID, String oldPassword, String newPassword) {
        String encryptedOldPassword = String.valueOf(Hex.encodeHex(DigestUtils.sha1(oldPassword)));
        String encryptedNewPassword = String.valueOf(Hex.encodeHex(DigestUtils.sha1(newPassword)));
        return flatREST.changePassword(context, flatID, encryptedOldPassword, encryptedNewPassword);
    }

    /**
     * Retrieves flat's ID when given name
     * @param context Current context
     * @param flatName Name of a flat
     * @return Response object with ID (int) <br>
     * ErrorCodes: <br>
     * - MESSAGE_OK - retrieved<br>
     * - MESSAGE_NOT_FOUND - flat not found <br>
     * - MESSAGE_UNAUTHORIZED -  user unauthorized <br>
     */
    public Response getUserID(Context context, String flatName) {
        return flatREST.getFlatID(context, flatName);
    }

    /**
     * Retrieves name of given flat
     * @param context Current context
     * @param flatID ID of a flat
     * @return Response object with flat name <br>
     * ErrorCodes: <br>
     * - MESSAGE_OK - retrieved <br>
     * - MESSAGE_NOT_FOUND - flat not found <br>
     * - MESSAGE_UNAUTHORIZED -  user unauthorized <br>
     */

    public Response getFlat(Context context, int flatID) {
        return flatREST.getFlat(context,flatID);
    }
}
