package com.example.tenantsproject.flatmates.model.service;

import android.content.Context;

import com.example.tenantsproject.flatmates.model.data.Product;
import com.example.tenantsproject.flatmates.model.rest.ArchiveREST;
import com.example.tenantsproject.flatmates.model.rest.Response;

public class StatsService {

    public static int FILTER_ALL = 1;

    private ArchiveREST archiveREST;

    public StatsService() {
        archiveREST = new ArchiveREST();
    }
    /**
     * @param context Current context
     * @param userID  ID of user with statistics
     * @param flatID  ID of user's flat with statistics
     * @return Response object with Statistics object <br>
     * ErrorCodes: <br>
     * - MESSAGE_OK - retrieved <br>
     * - MESSAGE_FORBIDDEN -  can't check others' flats stats <br>
     * - MESSAGE_UNAUTHORIZED -  user unauthorized <br>
     */
    public Response getStats(Context context, int userID, int flatID) {
       return archiveREST.getStatsTask(context,userID,flatID);
    }

    /**
     * Returns archived products for given user within given flat
     *
     * @param context Current context
     * @param userID  ID of user with statistics
     * @param flatID  ID of user's flat with statistics
     * @param filter  one of predefined filters
     * @param page    page number
     * @return Response object with List of Product objects <br>
     * ErrorCodes: <br>
     * - MESSAGE_OK - retrieved <br>
     * - MESSAGE_NOT_FOUND - no such user in given flat <br>
     * - MESSAGE_FORBIDDEN -  can't check others' flats stats <br>
     * - MESSAGE_UNAUTHORIZED -  user unauthorized <br>
     */
    public Response getArchivalProducts(Context context, int userID, int flatID, int filter, int page) {
        return archiveREST.getFlatArchive(context,flatID,userID,filter,page);
    }

    /**
     * Reverses buy operation for given archival product and puts it in user's list.
     *
     * @param context Current context
     * @param product Archival product
     * @return Response object <br>
     * ErrorCodes: <br>
     * - MESSAGE_OK - updated<br>
     * - MESSAGE_NOT_FOUND - product not found <br>
     * - MESSAGE_CONFLICT - product not bought <br>
     * - MESSAGE_FORBIDDEN - its other users product
     * - MESSAGE_UNAUTHORIZED -  user unauthorized <br>
     */
    public Response undoBuy(Context context, Product product) {
        return archiveREST.cancelBuy(context,product);
    }
}
