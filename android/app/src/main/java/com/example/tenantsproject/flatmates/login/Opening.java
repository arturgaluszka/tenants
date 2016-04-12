package com.example.tenantsproject.flatmates.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import com.example.tenantsproject.flatmates.R;

public class Opening extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.opening);

        final ImageView iv = (ImageView) findViewById(R.id.imageView5);
        final Animation an = AnimationUtils.loadAnimation(getBaseContext(), R.anim.rotate);
        final Animation an2 = AnimationUtils.loadAnimation(getBaseContext(), R.anim.abc_fade_out);

        iv.startAnimation(an);

        an.setAnimationListener(new Animation.AnimationListener() {

                                    public void onAnimationStart(Animation animation) {

                                    }

                                    public void onAnimationEnd(Animation animation) {
                                        iv.startAnimation(an2);
                                        finish();
                                        Intent i = new Intent(getBaseContext(), Login.class);
                                        startActivity(i);

                                    }

                                    public void onAnimationRepeat(Animation animation) {

                                    }
                                }
        );
    }

}
