package org.d3ifcool.pengingatutangpiutangtest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.d3ifcool.pengingatutangpiutangtest.piutang.Piutang;
import org.d3ifcool.pengingatutangpiutangtest.utang.Utang;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    CardView utang, piutang, aktivitas, about;

    private TextView tvutang, jmlutang, tvpiutang, jmlpiutang, tvaktivitas, tvabout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        utang = findViewById(R.id.home_utang);
        piutang = findViewById(R.id.home_piutang);
        aktivitas = findViewById(R.id.home_aktivitas);
        about = findViewById(R.id.home_about);

        tvutang = findViewById(R.id.tv_home_utang);
        jmlutang = findViewById(R.id.tv_home_jmlutang);
        tvpiutang = findViewById(R.id.tv_home_piutang);
        jmlpiutang = findViewById(R.id.tv_home_jmlpiutang);
        tvaktivitas = findViewById(R.id.tv_home_aktivitas);
        tvabout = findViewById(R.id.tv_home_about);

        utang.setOnClickListener(this);
        piutang.setOnClickListener(this);
        aktivitas.setOnClickListener(this);
        about.setOnClickListener(this);

        Typeface MLight = Typeface.createFromAsset(getAssets(), "fonts/ML.otf");
        Typeface MMedium = Typeface.createFromAsset(getAssets(), "fonts/MM.ttf");

        tvutang.setTypeface(MMedium);
        jmlutang.setTypeface(MLight);
        tvpiutang.setTypeface(MMedium);
        jmlpiutang.setTypeface(MLight);
        tvaktivitas.setTypeface(MMedium);
        tvabout.setTypeface(MMedium);
    }

    @Override
    public void onClick(View v) {
        Intent i;

        switch (v.getId()) {
            case R.id.home_utang:
                i = new Intent(this, Utang.class);
                startActivity(i);
                break;
            case R.id.home_piutang:
                i = new Intent(this, Piutang.class);
                startActivity(i);
                break;
            case R.id.home_aktivitas:
                i = new Intent(this, Aktivitas.class);
                startActivity(i);
                break;
            case R.id.home_about:
                i = new Intent(this, About.class);
                startActivity(i);
                break;
        }

    }
}
