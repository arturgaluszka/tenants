package com.example.tenantsproject.flatmates.settings.user;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;

import com.example.tenantsproject.flatmates.R;

public class Meil extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meil);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * .4), (int) (height * .4));
    }
}
