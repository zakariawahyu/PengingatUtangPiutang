package org.d3ifcool.pengingatutangpiutangtest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreenActivity extends AppCompatActivity {

    private ImageView logo;
    private TextView splash1, splash2;
    private static int splashTimeOut = 2500;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        logo = findViewById(R.id.logo);
        splash1 = findViewById(R.id.tv_splash1);
        splash2 = findViewById(R.id.tv_splash2);

        Typeface MLight = Typeface.createFromAsset(getAssets(), "fonts/ML.otf");
        Typeface MMedium = Typeface.createFromAsset(getAssets(), "fonts/MM.ttf");

        splash1.setTypeface(MMedium);
        splash2.setTypeface(MLight);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent home = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(home);
                finish();
            }
        },splashTimeOut);

        Animation myanim = AnimationUtils.loadAnimation(this, R.anim.splashscreenanimation);
        logo.startAnimation(myanim);
        splash1.startAnimation(myanim);
        splash2.startAnimation(myanim);




    }
}
