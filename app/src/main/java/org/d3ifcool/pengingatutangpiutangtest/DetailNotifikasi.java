package org.d3ifcool.pengingatutangpiutangtest;

import androidx.appcompat.app.AppCompatActivity;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.d3ifcool.pengingatutangpiutangtest.data.UtangPiutangContract;
import org.d3ifcool.pengingatutangpiutangtest.piutang.DetailPiutang;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class DetailNotifikasi extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private boolean mNotifikasiHasChanged;
    private Uri mCurrentNotifikasiUri;
    private TextView mJumlah, mJumlahSisa, mCatatan, mNama, mDeskripsi, mTanggal, mStatus;
    private Button btnCicil, btnLunas, btnBayar;
    private EditText etJumlahBayar;
    private String jumlah;
    private String mJum;
    private String mSat = "Belum Lunas";
    private int id;
    private String formattedString;

    private static final int EXISTING_NOTIFIKASI_LOADER = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_notifikasi);

        Intent intent = getIntent();
        mCurrentNotifikasiUri = intent.getData();

        if (mCurrentNotifikasiUri == null) {
            mNotifikasiHasChanged = true;

        } else {

            mNotifikasiHasChanged = false;
            getLoaderManager().initLoader(EXISTING_NOTIFIKASI_LOADER, null, this);
        }

        mJumlah = findViewById(R.id.tv_jumlah_detail_notifikasi);
        mJumlahSisa = findViewById(R.id.tv_jumlahsisa_detail_notifikasi);
        btnCicil = findViewById(R.id.btn_cicil_notifikasi);
        btnLunas = findViewById(R.id.btn_lunas_notifikasi);
        etJumlahBayar = findViewById(R.id.et_jumlahbayar_notifikasi);
        btnBayar = findViewById(R.id.btn_bayar_notifikasi);
        mCatatan = findViewById(R.id.tv_catatan_notifikasi);
        mNama = findViewById(R.id.tv_nama_detail_notifikasi);
        mDeskripsi = findViewById(R.id.tv_deskirpsi_detail_notifikasi);
        mTanggal = findViewById(R.id.tv_tanggal_detail_notifikasi);
        mStatus = findViewById(R.id.tv_status_detail_notifiikasi);

        etJumlahBayar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                etJumlahBayar.removeTextChangedListener(this);
                try {
                    String originalString = s.toString();
                    originalString = originalString.replaceAll("\\.", "").replaceFirst(",", ".");
                    originalString = originalString.replaceAll("[A-Z]", "").replaceAll("[a-z]", "");
                    double doubleval = Double.parseDouble(originalString);
                    DecimalFormatSymbols symbols = new DecimalFormatSymbols();
                    symbols.setDecimalSeparator(',');
                    symbols.setGroupingSeparator('.');
                    String pattern = "#,###,###";
                    DecimalFormat formatter = new DecimalFormat(pattern, symbols);
                    String formattedStringku = formatter.format(doubleval);
                    mJum = formattedStringku.replace(".","");;
                    etJumlahBayar.setText(formattedStringku);
                    etJumlahBayar.setSelection(etJumlahBayar.getText().length());
                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }
                etJumlahBayar.addTextChangedListener(this);
                etJumlahBayar.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void btnCicil(View view) {
        btnCicil.setBackground(this.getResources().getDrawable(R.drawable.btnpembayaranselected));
        btnLunas.setBackground(this.getResources().getDrawable(R.drawable.bgbtnpembayaran));
        mCatatan.setText("Pinjaman ini dibayar dengan cara cicilan");
        etJumlahBayar.setText("");
        mJumlahSisa.setText("Rp " + formattedString + "");
        mStatus.setText("Belum Lunas");
        mSat = "Belum Lunas";
    }

    public void btnLunas(View view) {
        btnLunas.setBackground(this.getResources().getDrawable(R.drawable.btnpembayaranselected));
        btnCicil.setBackground(this.getResources().getDrawable(R.drawable.bgbtnpembayaran));
        mCatatan.setText("Pinjaman ini dibayar dengan cara lunas");
        mJumlahSisa.setText("Rp 0");
        etJumlahBayar.setText(jumlah);
        mStatus.setText("Lunas");
        mSat = "Lunas";
    }

    public void bayarNotifikasi(View view) {
        try {

            int etjum = Integer.parseInt(mJum);
            int totjum = Integer.parseInt(jumlah);

            if (etjum > totjum) {
                Toast.makeText(DetailNotifikasi.this, "Jumlah tidak boleh melebihi jumlah utang", Toast.LENGTH_SHORT).show();
            } else {
                bayarPiutangku();
            }

        } catch (NumberFormatException e) {
            Toast.makeText(DetailNotifikasi.this, "Jumlah tidak boleh kosong", Toast.LENGTH_SHORT).show();
        }

    }

    public void bayarPiutangku() {

        int mJumku = Integer.parseInt(mJum);
        int jumlahku = Integer.parseInt(jumlah);

        int bayarku = jumlahku - mJumku;

        ContentValues values = new ContentValues();

        values.put(UtangPiutangContract.UtangPiutangEntry.KEY_JUMLAH, bayarku);
        values.put(UtangPiutangContract.UtangPiutangEntry.KEY_STATUS, mSat);

        int rowsAffected = getContentResolver().update(mCurrentNotifikasiUri, values, null, null);

        // Show a toast message depending on whether or not the update was successful.
        if (rowsAffected == 0) {
            // If no rows were affected, then there was an error with the update.
            Toast.makeText(this, "Gagal melakukan pembayaran",
                    Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the update was successful and we can display a toast.
            Toast.makeText(this, "Berhasil melakukan pembayaran",
                    Toast.LENGTH_SHORT).show();
        }

        finish();

    }

    // On pressing the back button
    @Override
    public void onBackPressed() {
        super.onBackPressed();

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
                mCurrentNotifikasiUri,         // Query the content URI for the current reminder
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

        // Proceed with moving to the first row of the cursor and reading data from it
        // (This should be the only row in the cursor)
        if (cursor.moveToFirst()) {

            int idku = cursor.getColumnIndex(UtangPiutangContract.UtangPiutangEntry._ID);
            int jumlahColumnIndex = cursor.getColumnIndex(UtangPiutangContract.UtangPiutangEntry.KEY_JUMLAH);
            int deskripsiColumnIndex = cursor.getColumnIndex(UtangPiutangContract.UtangPiutangEntry.KEY_DESKRIPSI);
            int tanggalColumnIndex = cursor.getColumnIndex(UtangPiutangContract.UtangPiutangEntry.KEY_DATE);
            int namaColumnIndex = cursor.getColumnIndex(UtangPiutangContract.UtangPiutangEntry.KEY_NAMA);
            int statusColumnIndex = cursor.getColumnIndex(UtangPiutangContract.UtangPiutangEntry.KEY_STATUS);

            id = cursor.getInt(idku);
            jumlah = cursor.getString(jumlahColumnIndex);
            String nama = cursor.getString(namaColumnIndex);
            String deskripsi = cursor.getString(deskripsiColumnIndex);
            String tanggal = cursor.getString(tanggalColumnIndex);
            String status = cursor.getString(statusColumnIndex);

            DecimalFormatSymbols symbols = new DecimalFormatSymbols();
            symbols.setDecimalSeparator(',');
            symbols.setGroupingSeparator('.');
            String pattern = "#,###,###";
            DecimalFormat formatter = new DecimalFormat(pattern, symbols);
            formattedString = formatter.format(Double.parseDouble(jumlah));

            String jumlahPiutang = "Rp " + formattedString + "";
            mJumlah.setText(jumlahPiutang);
            mJumlahSisa.setText(jumlahPiutang);
            mNama.setText(nama);
            mDeskripsi.setText(deskripsi);
            mTanggal.setText(tanggal);
            mStatus.setText(status);

        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
