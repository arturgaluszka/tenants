package com.tenantsproject.flatmates.model.service;

import android.content.Context;

import com.tenantsproject.flatmates.model.data.Product;
import com.tenantsproject.flatmates.model.rest.Response;

public class ExpenseService {

    /**
     * @param context Current context
     * @param product Product to be deleted
     * @return Response object <br>
     * ErrorCodes: <br>
     * - MESSAGE_OK - deleted<br>
     * - MESSAGE_NOT_FOUND - already deleted/ product not found <br>
     * - MESSAGE_FORBIDDEN -  removing other user's product <br>
     * - MESSAGE_UNAUTHORIZED -  user unauthorized <br>
     */
    public Response removeFromMainList(Context context, Product product) {
        throw new UnsupportedOperationException();
    }

    /**
     * @param context Current context
     * @param product Product with updated info
     * @return Response object <br>
     * ErrorCodes: <br>
     * - MESSAGE_OK - updated<br>
     * - MESSAGE_NOT_FOUND - product not found <br>
     * - MESSAGE_CONFLICT - somebody already updated after Product has been loaded <br>
     * - MESSAGE_FORBIDDEN -  updating other user's product <br>
     * - MESSAGE_UNAUTHORIZED -  user unauthorized <br>
     */
    public Response updateProduct(Context context, Product product) {
        throw new UnsupportedOperationException();
    }

    /**
     * Adds product to flat's main list
     * @param context Current context
     * @param product Product to be added
     * @param flatID ID of a flat with a list
     * @return Response object <br>
     * ErrorCodes: <br>
     * - MESSAGE_OK - added<br>
     * - MESSAGE_UNAUTHORIZED -  user unauthorized <br>
     */
    public Response addProduct(Context context, Product product, int flatID) {
        throw new UnsupportedOperationException();
    }

    /**
     * Reserves given product for current user
     * @param context Current context
     * @param product Product to reserve
     * @return Response object <br>
     * ErrorCodes: <br>
     * - MESSAGE_OK - reserved<br>
     * - MESSAGE_NOT_FOUND - product not found <br>
     * - MESSAGE_CONFLICT - somebody already reserved after Product has been loaded <br>
     * - MESSAGE_UNAUTHORIZED -  user unauthorized <br>
     */
    public Response reserveProduct(Context context,Product product){
        throw new UnsupportedOperationException();
    }

    /**
     * Buys given product for current user. If it wasn't in user's list - moves it.
     * @param context Current context
     * @param product Product to buy
     * @return Response object <br>
     * ErrorCodes: <br>
     * - MESSAGE_OK - bought<br>
     * - MESSAGE_NOT_FOUND - product not found <br>
     * - MESSAGE_CONFLICT - somebody already bought after Product has been loaded <br>
     * - MESSAGE_UNAUTHORIZED -  user unauthorized <br>
     */
    public Response buyProduct(Context context,Product product){
        throw new UnsupportedOperationException();
    }

    /**
     * Reserves given product for current user
     * @param context Current context
     * @param product Product to reserve
     * @return Response object
     * ErrorCodes: <br>
     * - MESSAGE_OK - unreserved <br>
     * - MESSAGE_NOT_FOUND - product not found <br>
     * - MESSAGE_UNAUTHORIZED -  user unauthorized <br>
     */

    public Response unreserveProduct (Context context, Product product){
        throw new UnsupportedOperationException();
    }

    /**
     * Cancels buy of a given product
     * @param context Current context
     * @param product Product to cancel
     * @return Response object
     * ErrorCodes: <br>
     * - MESSAGE_OK - cancelled <br>
     * - MESSAGE_NOT_FOUND - product not found <br>
     * - MESSAGE_UNAUTHORIZED -  user unauthorized <br>
     */
    public Response cancelBuy(Context context, Product product){
        throw new UnsupportedOperationException();
    }


}
