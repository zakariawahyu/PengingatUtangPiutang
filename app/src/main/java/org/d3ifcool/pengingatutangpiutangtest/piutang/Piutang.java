package org.d3ifcool.pengingatutangpiutangtest.piutang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

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
import org.d3ifcool.pengingatutangpiutangtest.utang.Utang;
import org.d3ifcool.pengingatutangpiutangtest.utang.UtangCursorAdapter;

public class Piutang extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private boolean mHasChangedPiutang = false;
    FloatingActionButton floatingActionButton;

    View emptyViewPiutang;
    ListView listViewPiutang;
    PiutangCursorAdapter mCursorAdapterPiutang;

    private static final int PIUTANG_LOADER = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_piutang);

        listViewPiutang = findViewById(R.id.list_piutang);
        emptyViewPiutang = findViewById(R.id.empty_view_piutang);
        listViewPiutang.setEmptyView(emptyViewPiutang);

        mCursorAdapterPiutang = new PiutangCursorAdapter(this, null);
        listViewPiutang.setAdapter(mCursorAdapterPiutang);

        listViewPiutang.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Piutang.this, DetailPiutang.class);

                Uri currentPiutangkuUri = ContentUris.withAppendedId(UtangPiutangContract.UtangPiutangEntry.CONTENT_URI_PIUTANG, id);

                // Set the URI on the data field of the intent
                intent.setData(currentPiutangkuUri);

                startActivity(intent);
            }
        });

        floatingActionButton = findViewById(R.id.fb_add_piutang);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Piutang.this, TambahPiutang.class);
                startActivity(i);
            }
        });

        getLoaderManager().initLoader(PIUTANG_LOADER, null, this);
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

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                UtangPiutangContract.UtangPiutangEntry._ID,
                UtangPiutangContract.UtangPiutangEntry.KEY_NAMA,
                UtangPiutangContract.UtangPiutangEntry.KEY_JUMLAH,
                UtangPiutangContract.UtangPiutangEntry.KEY_DESKRIPSI,
                UtangPiutangContract.UtangPiutangEntry.KEY_DATE,
                UtangPiutangContract.UtangPiutangEntry.KEY_TIME,
                UtangPiutangContract.UtangPiutangEntry.KEY_REPEAT,
                UtangPiutangContract.UtangPiutangEntry.KEY_REPEAT_NO,
                UtangPiutangContract.UtangPiutangEntry.KEY_REPEAT_TYPE,
                UtangPiutangContract.UtangPiutangEntry.KEY_ACTIVE,
                UtangPiutangContract.UtangPiutangEntry.KEY_JENIS,
                UtangPiutangContract.UtangPiutangEntry.KEY_STATUS

        };

        return new CursorLoader(this,   // Parent activity context
                UtangPiutangContract.UtangPiutangEntry.CONTENT_URI_PIUTANG,   // Provider content URI to query
                projection,             // Columns to include in the resulting Cursor
                null,                   // No selection clause
                null,                   // No selection arguments
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mCursorAdapterPiutang.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCursorAdapterPiutang.swapCursor(null);
    }
}
