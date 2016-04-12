package com.tenantsproject.flatmates.model.service;

import android.content.Context;

import com.tenantsproject.flatmates.model.data.Product;
import com.tenantsproject.flatmates.model.rest.ProductREST;
import com.tenantsproject.flatmates.model.rest.Response;

public class ProductService {
    public static int FILTER_ALL = 1;
    public static int FILTER_ACTIVE = 2;
    public static int FILTER_RESERVED = 3;

    private ProductREST productREST;

    public ProductService() {
        productREST = new ProductREST();
    }

    /**
     * Returns products within given flat
     *
     * @param context  Current context
     * @param flatID   ID of user's flat with statistics
     * @param filter   choose one predefined filter
     * @param page     result's page number
     * @param userID   ID of user (can be null or 0 for all users)
     * @return Response object with List of Product objects <br>
     * ErrorCodes: <br>
     * - MESSAGE_OK - retrieved<br>
     * - MESSAGE_FORBIDDEN -  can't check others' flats stats <br>
     * - MESSAGE_UNAUTHORIZED -  user unauthorized <br>
     */
    public Response getFlatProducts(Context context, int flatID, int userID, int filter, int page) {
        return productREST.getFlatProducts(context,flatID,userID,filter,page);
    }

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
        return productREST.removeFromMainList(context,product);
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
        return productREST.update(context,product);
    }

    /**
     * Adds product to flat's main list
     *
     * @param context Current context
     * @param product Product to be added
     * @return Response object <br>
     * ErrorCodes: <br>
     * - MESSAGE_OK - added<br>
     * - MESSAGE_FORBIDDEN - creating other users/flats products
     * - MESSAGE_UNAUTHORIZED -  user unauthorized <br>
     */
    public Response addProduct(Context context, Product product) {
        return productREST.addProduct(context,product);
    }

    /**
     * Reserves given product for current user
     *
     * @param context Current context
     * @param product Product to reserve
     * @return Response object <br>
     * ErrorCodes: <br>
     * - MESSAGE_OK - reserved<br>
     * - MESSAGE_NOT_FOUND - product not found <br>
     * - MESSAGE_CONFLICT - somebody already reserved after Product has been loaded <br>
     * - MESSAGE_FORBIDDEN - reserving other flat's products
     * - MESSAGE_UNAUTHORIZED -  user unauthorized <br>
     */
    public Response reserveProduct(Context context, Product product) {
        return productREST.reserve(context,product);
    }

    /**
     * Buys given product for current user. If it wasn't in user's list - moves it.
     *
     * @param context Current context
     * @param product Product to buy
     * @return Response object <br>
     * ErrorCodes: <br>
     * - MESSAGE_OK - bought<br>
     * - MESSAGE_NOT_FOUND - product not found <br>
     * - MESSAGE_CONFLICT - somebody already bought after Product has been loaded <br>
     * - MESSAGE_UNAUTHORIZED -  user unauthorized <br>
     */
    public Response buyProduct(Context context, Product product) {
        throw new UnsupportedOperationException();
    }

    /**
     * Unreserves given product for current user
     *
     * @param context Current context
     * @param product Product to unreserve
     * @return Response object
     * ErrorCodes: <br>
     * - MESSAGE_OK - unreserved <br>
     * - MESSAGE_NOT_FOUND - product not found <br>
     * - MESSAGE_FORBIDDEN - unreserving other users products
     * - MESSAGE_UNAUTHORIZED -  user unauthorized <br>
     */

    public Response unreserveProduct(Context context, Product product) {
       return productREST.removeFromUserList(context,product);
    }

    /**
     * Cancels buy of a given product
     *
     * @param context Current context
     * @param product Product to cancel
     * @return Response object
     * ErrorCodes: <br>
     * - MESSAGE_OK - cancelled <br>
     * - MESSAGE_NOT_FOUND - product not found <br>
     * - MESSAGE_UNAUTHORIZED -  user unauthorized <br>
     */
    public Response cancelBuy(Context context, Product product) {
        throw new UnsupportedOperationException();
    }


}
