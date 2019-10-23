package org.d3ifcool.pengingatutangpiutangtest;

import androidx.appcompat.app.AppCompatActivity;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.Toast;

import org.d3ifcool.pengingatutangpiutangtest.backup.LocalBackup;
import org.d3ifcool.pengingatutangpiutangtest.data.UtangPiutangContract;
import org.d3ifcool.pengingatutangpiutangtest.data.UtangPiutangDbHelper;
import org.d3ifcool.pengingatutangpiutangtest.utang.UtangCursorAdapter;

import java.io.File;

public class Aktivitas extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final int REQUEST_CODE_PERMISSIONS = 2;
    private static final int AKTIVITAS_LOADER = 0;

    private LocalBackup localBackup;

    View emptyViewAktivitas;
    ListView listViewAktivitas;
    AktivitasCursorAdapter mCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aktivitas);

        localBackup = new LocalBackup(this);

        listViewAktivitas = findViewById(R.id.list_aktivitas);
        emptyViewAktivitas = findViewById(R.id.empty_view_aktivitas);
        listViewAktivitas.setEmptyView(emptyViewAktivitas);

        mCursorAdapter = new AktivitasCursorAdapter(this, null);
        listViewAktivitas.setAdapter(mCursorAdapter);


        getLoaderManager().initLoader(AKTIVITAS_LOADER, null, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_aktivitas, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        final UtangPiutangDbHelper db = new UtangPiutangDbHelper(getApplicationContext());

        switch (id) {
            case R.id.action_backup:
                String outFileName = Environment.getExternalStorageDirectory() + File.separator + getResources().getString(R.string.db) + File.separator;
                localBackup.performBackup(db, outFileName);
                break;
            case R.id.action_import:
                localBackup.performRestore(db);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
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
                UtangPiutangContract.UtangPiutangEntry.CONTENT_URI_AKTIVITAS,   // Provider content URI to query
                projection,             // Columns to include in the resulting Cursor
                null,                   // No selection clause
                null,                   // No selection arguments
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCursorAdapter.swapCursor(null);
    }
}
