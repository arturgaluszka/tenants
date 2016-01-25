package com.tenantsproject.flatmates.wallet;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.tenantsproject.flatmates.R;
import com.tenantsproject.flatmates.model.data.Wallet;
import com.tenantsproject.flatmates.model.rest.Response;
import com.tenantsproject.flatmates.model.service.WalletService;
import com.tenantsproject.flatmates.user.UserActivity;
import com.tenantsproject.flatmates.utils.DecimalDigitsInputFilter;

public class WalletActivity extends AppCompatActivity {

    private Wallet wallet;
    private TextView walletAmmountTextView;
    private EditText input;
    private TextView errorText;
    private Button depositButton;
    private Button withdrawButton;
    private DecimalDigitsInputFilter decimalFilter;
    private WalletService walletService;
    private Button refreshButton;
    public String v1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);

        initObjects();
        loadWallet();
    }

    private void loadWallet() {
        Response response = walletService.getState(getFlat());
        switch (response.getMessageCode()) {
            case Response.MESSAGE_OK:
                wallet = (Wallet) response.getObject();
                break;
            case Response.MESSAGE_NOT_FOUND:
                wallet = new Wallet();
                errorText.setText("Wallet not found");
                break;
            default:
                wallet = new Wallet();
                errorText.setText("Can't load wallet");
                break;
        }
        reloadView();
    }

    private void initObjects() {
        this.walletService = new WalletService();
        this.decimalFilter = new DecimalDigitsInputFilter();
        this.walletAmmountTextView = (TextView) findViewById(R.id.walletValue);
        this.depositButton = (Button) findViewById(R.id.walletdepositbutton);
        this.depositButton.setOnClickListener(getDepositAction());
        this.withdrawButton = (Button) findViewById(R.id.walletwithdrawbutton);
        this.withdrawButton.setOnClickListener(getWithdrawAction());
        this.input = (EditText) findViewById(R.id.walletinput);
        this.errorText = (TextView) findViewById(R.id.wallet_errortext);
        this.refreshButton = (Button) findViewById(R.id.wallet_refreshbutton);
        this.refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadWallet();
            }
        });
    }

    @NonNull
    private View.OnClickListener getWithdrawAction() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = WalletActivity.this.input.getText().toString();
                if (WalletActivity.this.decimalFilter.filter(value)) {
                    double previousValue = wallet.getCurrent();
                    String previousUser = wallet.getUser();
                    v1 = value;
                    boolean didWithdraw = WalletActivity.this.wallet.withdraw(Double.valueOf(value));
                    if (didWithdraw) {
                        Response response = walletService.update(wallet);
                        switch (response.getMessageCode()) {
                            case Response.MESSAGE_OK:
                                loadWallet();
                                break;
                            case Response.MESSAGE_NOT_FOUND:
                                errorText.setText("Wallet not found");
                                wallet.setCurrent(previousValue);
                                wallet.setUser(previousUser);
                                break;
                            case Response.MESSAGE_CONFLICT:
                                errorText.setText("Modified by other user. Please refresh");
                                wallet.setCurrent(previousValue);
                                wallet.setUser(previousUser);
                                break;
                            default:
                                errorText.setText("Unknown error. Please refresh");
                                wallet.setCurrent(previousValue);
                                wallet.setUser(previousUser);
                                break;
                        }
                    } else {
                        errorText.setText("Can't withdraw more that you have!");
                    }
                }
                reloadView();
            }
        };
    }

    @NonNull
    private View.OnClickListener getDepositAction() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = WalletActivity.this.input.getText().toString();
                double previousValue = wallet.getCurrent();
                String previousUser = wallet.getUser();
                if (WalletActivity.this.decimalFilter.filter(value)) {
                    WalletActivity.this.wallet.deposit(Double.valueOf(value));
                    wallet.setUser(getUser());
                    Response response = walletService.update(wallet);
                    switch (response.getMessageCode()) {
                        case Response.MESSAGE_OK:
                            loadWallet();
                            break;
                        case Response.MESSAGE_NOT_FOUND:
                            errorText.setText("Wallet not found");
                            wallet.setCurrent(previousValue);
                            wallet.setUser(previousUser);
                            break;
                        case Response.MESSAGE_CONFLICT:
                            errorText.setText("Modified by other user. Please refresh");
                            wallet.setCurrent(previousValue);
                            wallet.setUser(previousUser);
                            break;
                        default:
                            errorText.setText("Unknown error. Please refresh");
                            wallet.setCurrent(previousValue);
                            wallet.setUser(previousUser);
                            break;
                    }
                }
            }
        };
    }

    public void reloadView() {
        this.walletAmmountTextView.setText(Double.toString(this.wallet.getCurrent()));
    }

    //TODO: improve and/or delete (shared pref user info)
    private int getFlat() {
        SharedPreferences sP = getSharedPreferences(UserActivity.USER_PREF_NAME, Context.MODE_PRIVATE);
        return sP.getInt(UserActivity.USER_PREF_FLAT, 0);
    }

    //TODO: improve and/or delete (shared pref user info)
    private String getUser() {
        SharedPreferences sP = getSharedPreferences(UserActivity.USER_PREF_NAME, Context.MODE_PRIVATE);
        return sP.getString(UserActivity.USER_PREF_USER, "default");
    }

    private void goToHistory() {
        Intent i = new Intent(this, HistoryActivity.class);
        try {
            i.putExtra("deleted", v1);
            Log.d("check", v1);
            startActivity(i);
        } catch (NullPointerException a) {
            startActivity(i);
        }
    }

    public void onClick(View v) {
        goToHistory();
    }

}
