package org.d3ifcool.pengingatutangpiutangtest.piutang;

import androidx.appcompat.app.AppCompatActivity;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import org.d3ifcool.pengingatutangpiutangtest.R;
import org.d3ifcool.pengingatutangpiutangtest.data.UtangPiutangContract;

public class DetailPiutangLunas extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private boolean mHasChanged;
    private Uri mCurrentPiutangLunasUri;
    private static final int EXISTING_PIUTANG_LOADER = 0;

    private TextView mJumlah, mJumlahSisa, mNama, mDeskripsi, mTanggal, mStatus;
    private String jumlah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_piutang_lunas);

        Intent intent = getIntent();
        mCurrentPiutangLunasUri = intent.getData();

        if (mCurrentPiutangLunasUri == null) {
            mHasChanged = true;

        } else {

            mHasChanged = false;
            getLoaderManager().initLoader(EXISTING_PIUTANG_LOADER, null, this);
        }

        mJumlah = findViewById(R.id.tv_jumlah_detail_piutang_lunas);
        mJumlahSisa = findViewById(R.id.tv_jumlahsisa_detail_piutang_lunas);
        mNama = findViewById(R.id.tv_nama_detail_piutang_lunas);
        mDeskripsi = findViewById(R.id.tv_deskirpsi_detail_piutang_lunas);
        mTanggal = findViewById(R.id.tv_tanggal_detail_piutang_lunas);
        mStatus = findViewById(R.id.tv_status_detail_piutang_lunas);
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

        // This loader will execute the ContentProvider's query method on a background thread
        return new CursorLoader(this,   // Parent activity context
                mCurrentPiutangLunasUri,         // Query the content URI for the current reminder
                projection,             // Columns to include in the resulting Cursor
                null,                   // No selection clause
                null,                   // No selection arguments
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor == null || cursor.getCount() < 1) {
            return;
        }

        if (cursor.moveToFirst()) {

            int jumlahColumnIndex = cursor.getColumnIndex(UtangPiutangContract.UtangPiutangEntry.KEY_JUMLAH);
            int deskripsiColumnIndex = cursor.getColumnIndex(UtangPiutangContract.UtangPiutangEntry.KEY_DESKRIPSI);
            int tanggalColumnIndex = cursor.getColumnIndex(UtangPiutangContract.UtangPiutangEntry.KEY_DATE);
            int namaColumnIndex = cursor.getColumnIndex(UtangPiutangContract.UtangPiutangEntry.KEY_NAMA);
            int statusColumnIndex = cursor.getColumnIndex(UtangPiutangContract.UtangPiutangEntry.KEY_STATUS);

            jumlah = cursor.getString(jumlahColumnIndex);
            String nama = cursor.getString(namaColumnIndex);
            String deskripsi = cursor.getString(deskripsiColumnIndex);
            String tanggal = cursor.getString(tanggalColumnIndex);
            String status = cursor.getString(statusColumnIndex);

            String jumlahUtang = "Rp " + jumlah + "";
            mJumlah.setText(jumlahUtang);
            mJumlahSisa.setText(jumlahUtang);
            mNama.setText(nama);
            mDeskripsi.setText(deskripsi);
            mTanggal.setText(tanggal);
            mStatus.setText(status);

        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
