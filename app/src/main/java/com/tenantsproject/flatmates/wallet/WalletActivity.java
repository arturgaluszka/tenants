package com.tenantsproject.flatmates.wallet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.tenantsproject.flatmates.R;
import com.tenantsproject.flatmates.utils.DecimalDigitsInputFilter;
import com.tenantsproject.flatmates.utils.JSONFileHandler;

public class WalletActivity extends AppCompatActivity {

    private Wallet wallet;
    private JSONFileHandler handler;
    private static final String WALLET_FILE_NAME = "walletfile";
    private TextView walletAmmountTextView;
    private EditText input;
    private Button depositButton;
    private Button withdrawButton;
    private DecimalDigitsInputFilter decimalFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);

        initObjects();

        // # loading previous state #
        this.handler = new JSONFileHandler(WALLET_FILE_NAME, this);
        this.wallet = (Wallet) handler.load(Wallet.class);
        if (this.wallet == null) {
            this.wallet = new Wallet();
        }
        handler.save(this.wallet);
        reloadView();
    }

    private void initObjects() {
        this.decimalFilter = new DecimalDigitsInputFilter();
        this.walletAmmountTextView = (TextView) findViewById(R.id.walletValue);
        this.depositButton = (Button) findViewById(R.id.walletdepositbutton);
        this.depositButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = WalletActivity.this.input.getText().toString();
                if (WalletActivity.this.decimalFilter.filter(value)) {
                    WalletActivity.this.wallet.deposit(Double.valueOf(value));
                }
                handler.save(wallet);
                reloadView();
            }
        });
        this.withdrawButton = (Button) findViewById(R.id.walletwithdrawbutton);
        this.withdrawButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = WalletActivity.this.input.getText().toString();
                if (WalletActivity.this.decimalFilter.filter(value)) {
                    WalletActivity.this.wallet.withdraw(Double.valueOf(value));
                }
                handler.save(wallet);
                reloadView();
            }
        });
        this.input = (EditText) findViewById(R.id.walletinput);
    }

    public void reloadView() {
        this.walletAmmountTextView.setText(Double.toString(this.wallet.getCurrent()));
    }
}
