package org.d3ifcool.pengingatutangpiutangtest.piutang;

import androidx.appcompat.app.AppCompatActivity;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.d3ifcool.pengingatutangpiutangtest.R;
import org.d3ifcool.pengingatutangpiutangtest.data.UtangPiutangContract;
import org.d3ifcool.pengingatutangpiutangtest.utang.DetailUtangLunas;
import org.d3ifcool.pengingatutangpiutangtest.utang.UtangLunas;
import org.d3ifcool.pengingatutangpiutangtest.utang.UtangLunasCursorAdapter;

public class PiutangLunas extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    ListView listViewPiutangLunas;
    View emptyViewPiutangLunas;
    PiutangLunasCursorAdapter mCursorAdapterPiutangLunas;

    private static final int PIUTANGLUNAS_LOADER = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_piutang_lunas);

        listViewPiutangLunas = findViewById(R.id.list_piutang_lunas);
        emptyViewPiutangLunas = findViewById(R.id.empty_view_piutanglunas);
        listViewPiutangLunas.setEmptyView(emptyViewPiutangLunas);

        mCursorAdapterPiutangLunas = new PiutangLunasCursorAdapter(this, null);
        listViewPiutangLunas.setAdapter(mCursorAdapterPiutangLunas);

        listViewPiutangLunas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(PiutangLunas.this, DetailPiutangLunas.class);

                Uri currentPiutangkuUri = ContentUris.withAppendedId(UtangPiutangContract.UtangPiutangEntry.CONTENT_URI_PIUTANG, id);

                // Set the URI on the data field of the intent
                intent.setData(currentPiutangkuUri);

                startActivity(intent);
            }
        });

        getLoaderManager().initLoader(PIUTANGLUNAS_LOADER, null, this);
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
                UtangPiutangContract.UtangPiutangEntry.CONTENT_URI_PIUTANG_LUNAS,   // Provider content URI to query
                projection,             // Columns to include in the resulting Cursor
                null,                   // No selection clause
                null,                   // No selection arguments
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mCursorAdapterPiutangLunas.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCursorAdapterPiutangLunas.swapCursor(null);
    }
}
