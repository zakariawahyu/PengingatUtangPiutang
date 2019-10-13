package org.d3ifcool.pengingatutangpiutangtest;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

public class About extends AppCompatActivity {
    private TextView tvAbout,tvzaka,tvelsa,tvtania;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        tvAbout = findViewById(R.id.tv_aboutteam);
        tvzaka = findViewById(R.id.tv_zaka);
        tvelsa = findViewById(R.id.tv_elsa);
        tvtania = findViewById(R.id.tv_tania);

        Typeface MLight = Typeface.createFromAsset(getAssets(), "fonts/ML.otf");
        Typeface MMedium = Typeface.createFromAsset(getAssets(), "fonts/MM.ttf");

        tvAbout.setTypeface(MMedium);
        tvzaka.setTypeface(MLight);
        tvelsa.setTypeface(MLight);
        tvtania.setTypeface(MLight);


    }
}
