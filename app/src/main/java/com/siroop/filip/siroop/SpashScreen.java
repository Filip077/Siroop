package com.siroop.filip.siroop;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SpashScreen extends AppCompatActivity {


        private ImageView happy_shopping;
//        private ImageView siroop_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spash_screen);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        happy_shopping = findViewById(R.id.happy_shopping);
//        siroop_image = findViewById(R.id.siroop_image);

        Animation animation = AnimationUtils.loadAnimation(SpashScreen.this,R.anim.animation);
        happy_shopping.startAnimation(animation);
//        siroop_image.startAnimation(animation);


        int SPLASH_DELAY = 4500;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SpashScreen.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_DELAY);

    }
}
