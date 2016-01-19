package com.tenantsproject.flatmates.model.service;

import com.tenantsproject.flatmates.model.data.Wallet;
import com.tenantsproject.flatmates.model.rest.Response;
import com.tenantsproject.flatmates.model.rest.WalletREST;

public class WalletService {
    private WalletREST walletREST;

    public WalletService(){
        this.walletREST = new WalletREST();
    }

    public Response getState(int flatId){
        return walletREST.getWalletState(flatId);
    }

    public Response update(Wallet wallet){
        return walletREST.update(wallet);
    }
}
