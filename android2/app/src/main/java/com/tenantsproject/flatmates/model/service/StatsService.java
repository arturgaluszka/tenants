package com.tenantsproject.flatmates.model.service;

import android.content.Context;

import com.tenantsproject.flatmates.model.data.Product;
import com.tenantsproject.flatmates.model.rest.Response;

public class StatsService {
    /**
     *
     * @param context Current context
     * @param userID ID of user with statistics
     * @param flatID ID of user's flat with statistics
     * @return Response object with Statistics object <br>
     * ErrorCodes: <br>
     * - MESSAGE_OK - retrieved <br>
     * - MESSAGE_NOT_FOUND - no such user in given flat <br>
     * - MESSAGE_FORBIDDEN -  can't check others' flats stats <br>
     * - MESSAGE_UNAUTHORIZED -  user unauthorized <br>
     */
    public Response getStats(Context context, int userID, int flatID){
        throw new UnsupportedOperationException();
    }

    /**
     * Returns archived products for given user within given flat
     * @param context Current context
     * @param userID ID of user with statistics
     * @param flatID ID of user's flat with statistics
     * @return Response object with List of Product objects <br>
     * ErrorCodes: <br>
     * - MESSAGE_OK - retrieved <br>
     * - MESSAGE_NOT_FOUND - no such user in given flat <br>
     * - MESSAGE_FORBIDDEN -  can't check others' flats stats <br>
     * - MESSAGE_UNAUTHORIZED -  user unauthorized <br>
     */
    public Response getArchivalProductsForUser(Context context, int userID, int flatID){
        throw new UnsupportedOperationException();
    }

    /**
     * Returns archived products within given flat
     * @param context Current context
     * @param flatID ID of user's flat with statistics
     * @return Response object with List of Product objects <br>
     * ErrorCodes: <br>
     * - MESSAGE_OK - retrieved<br>
     * - MESSAGE_FORBIDDEN -  can't check others' flats stats <br>
     * - MESSAGE_UNAUTHORIZED -  user unauthorized <br>
     */
    public Response getArchivalProductsForFlat(Context context, int flatID){
        throw new UnsupportedOperationException();
    }

    /**
     * Reverses buy operation for given archival product and puts it in user's list.
     * @param context Current context
     * @param product Archival product
     * @return Response object <br>
     * ErrorCodes: <br>
     * - MESSAGE_OK - updated<br>
     * - MESSAGE_NOT_FOUND - product not found <br>
     * - MESSAGE_UNAUTHORIZED -  user unauthorized <br>
     */
    public Response undoBuy(Context context, Product product){
        throw new UnsupportedOperationException();
    }
}