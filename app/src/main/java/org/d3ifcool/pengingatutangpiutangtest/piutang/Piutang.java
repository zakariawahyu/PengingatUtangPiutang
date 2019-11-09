package org.d3ifcool.pengingatutangpiutangtest.piutang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.fragment.app.FragmentManager;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.d3ifcool.pengingatutangpiutangtest.R;
import org.d3ifcool.pengingatutangpiutangtest.data.UtangPiutangContract;
import org.d3ifcool.pengingatutangpiutangtest.utang.DetailUtang;
import org.d3ifcool.pengingatutangpiutangtest.utang.DetailUtangFragment;
import org.d3ifcool.pengingatutangpiutangtest.utang.Utang;
import org.d3ifcool.pengingatutangpiutangtest.utang.UtangCursorAdapter;
import org.d3ifcool.pengingatutangpiutangtest.utang.UtangFragment;

public class Piutang extends AppCompatActivity {

    private boolean mHasChangedPiutang = false;
    private boolean isDualPnae;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_piutang);

        fragmentManager = getSupportFragmentManager();

        if (findViewById(R.id.detailcontainerPiutang) != null){
            isDualPnae = true;
        } else {
            isDualPnae = false;
        }

        if (savedInstanceState == null ){
            fragmentManager.beginTransaction()
                    .add(R.id.containerPiutang, new PiutangFragment())
                    .commit();
        }

        if (isDualPnae){
            fragmentManager.beginTransaction()
                    .replace(R.id.detailcontainerPiutang, new DetailPiutangFragment())
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
                i = new Intent(Piutang.this, PiutangLunas.class);
                startActivity(i);
                break;

            case android.R.id.home:
                // If the reminder hasn't changed, continue with navigating up to parent activity
                // which is the {@link MainActivity}.
                if (!mHasChangedPiutang) {
                    NavUtils.navigateUpFromSameTask(Piutang.this);
                    return true;
                }
                break;
        }
        return true;
    }
}
