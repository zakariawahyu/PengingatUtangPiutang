package org.d3ifcool.pengingatutangpiutangtest.utang;

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

public class UtangLunas extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    ListView listViewUtangLunas;
    View emptyViewUtangLunas;
    UtangLunasCursorAdapter mCursorAdapterUtangLunas;

    private static final int UTANGLUNAS_LOADER = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utang_lunas);

        listViewUtangLunas = findViewById(R.id.list_utang_lunas);
        emptyViewUtangLunas = findViewById(R.id.empty_view_utanglunas);
        listViewUtangLunas.setEmptyView(emptyViewUtangLunas);

        mCursorAdapterUtangLunas = new UtangLunasCursorAdapter(this, null);
        listViewUtangLunas.setAdapter(mCursorAdapterUtangLunas);

        listViewUtangLunas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(UtangLunas.this, DetailUtangLunas.class);

                Uri currentUtangkuUri = ContentUris.withAppendedId(UtangPiutangContract.UtangPiutangEntry.CONTENT_URI_UTANG, id);

                // Set the URI on the data field of the intent
                intent.setData(currentUtangkuUri);

                startActivity(intent);
            }
        });

        getLoaderManager().initLoader(UTANGLUNAS_LOADER, null, this);
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
                UtangPiutangContract.UtangPiutangEntry.CONTENT_URI_UTANG_LUNAS,   // Provider content URI to query
                projection,             // Columns to include in the resulting Cursor
                null,                   // No selection clause
                null,                   // No selection arguments
                null);
    }

    @Override
    public void onLoadFinished(android.content.Loader<Cursor> loader, Cursor data) {
        mCursorAdapterUtangLunas.swapCursor(data);
    }

    @Override
    public void onLoaderReset(android.content.Loader<Cursor> loader) {
        mCursorAdapterUtangLunas.swapCursor(null);
    }

}
