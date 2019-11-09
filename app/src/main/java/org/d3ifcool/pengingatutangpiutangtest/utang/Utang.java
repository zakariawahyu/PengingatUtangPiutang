package org.d3ifcool.pengingatutangpiutangtest.utang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import org.d3ifcool.pengingatutangpiutangtest.R;



public class Utang extends AppCompatActivity {

    private boolean isDualPnae;
    private FragmentManager fragmentManager;

    private boolean mHasChanged = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utang);

        fragmentManager = getSupportFragmentManager();

        if (findViewById(R.id.detailcontainerUtang) != null){
            isDualPnae = true;
        } else {
            isDualPnae = false;
        }

        if (savedInstanceState == null ){
            fragmentManager.beginTransaction()
                    .add(R.id.containerUtang, new UtangFragment())
                    .commit();
        }

        if (isDualPnae){
            fragmentManager.beginTransaction()
                    .replace(R.id.detailcontainerUtang, new DetailUtangFragment())
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i;
        switch (item.getItemId()) {
            case R.id.lunas :
                i = new Intent(Utang.this, UtangLunas.class);
                startActivity(i);
                break;

            case android.R.id.home:
                // If the reminder hasn't changed, continue with navigating up to parent activity
                // which is the {@link MainActivity}.
                if (!mHasChanged) {
                    NavUtils.navigateUpFromSameTask(Utang.this);
                    return true;
                }
        }
        return true;
    }
}
