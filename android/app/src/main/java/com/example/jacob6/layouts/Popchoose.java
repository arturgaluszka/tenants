package com.example.jacob6.layouts;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

/**
 * Created by JaCoB6 on 2016-03-03.
 */
public class Popchoose extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popchoose);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setLayout((int)(width*.8),(int)(height*.3));

    }
    public void information(View view){
        Intent nextActivity = new Intent(this,InformationAboutToDo.class);
        startActivity(nextActivity);

    }
}
