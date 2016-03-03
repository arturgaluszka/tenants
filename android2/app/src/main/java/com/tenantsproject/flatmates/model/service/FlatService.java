package com.tenantsproject.flatmates.model.service;

import android.content.Context;

import com.tenantsproject.flatmates.model.rest.FlatREST;
import com.tenantsproject.flatmates.model.rest.Response;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

public class FlatService {
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
        return flatREST.getFlatMembers(context,flatID);
    }

}
