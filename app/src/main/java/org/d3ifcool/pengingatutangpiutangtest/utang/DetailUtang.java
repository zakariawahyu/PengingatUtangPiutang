package org.d3ifcool.pengingatutangpiutangtest.utang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import org.d3ifcool.pengingatutangpiutangtest.R;
import org.d3ifcool.pengingatutangpiutangtest.data.UtangPiutangContract;
import org.d3ifcool.pengingatutangpiutangtest.reminder.AlarmScheduler;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class DetailUtang extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private boolean mHasChanged;
    private Uri mCurrentUtangUri;
    private TextView mJumlah, mJumlahSisa, mCatatanUtang, mNama, mDeskripsi, mTanggal, mStatus;
    private Button btnCicilUtang, btnLunasUtang, btnBayarUtang;
    private EditText etJumlahBayar;
    private String jumlah;
    private String mJum;
    private String mSat = "Belum Lunas";
    private int id;
    private String formattedString;


    private static final int EXISTING_UTANG_LOADER = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_utang);

        Intent intent = getIntent();
        mCurrentUtangUri = intent.getData();

        if (mCurrentUtangUri == null) {
            mHasChanged = true;

        } else {

            mHasChanged = false;
            getLoaderManager().initLoader(EXISTING_UTANG_LOADER, null, this);
        }

        mJumlah = findViewById(R.id.tv_jumlah_detail_utang);
        mJumlahSisa = findViewById(R.id.tv_jumlahsisa_detail_utang);
        btnCicilUtang = findViewById(R.id.btn_cicil_utang);
        btnLunasUtang = findViewById(R.id.btn_lunas_utang);
        etJumlahBayar = findViewById(R.id.et_jumlahbayar_utang);
        btnBayarUtang = findViewById(R.id.btn_bayar_utang);
        mCatatanUtang = findViewById(R.id.tv_catatan_utang);
        mNama = findViewById(R.id.tv_nama_detail_utang);
        mDeskripsi = findViewById(R.id.tv_deskirpsi_detail_utang);
        mTanggal = findViewById(R.id.tv_tanggal_detail_utang);
        mStatus = findViewById(R.id.tv_status_detail_utang);


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
                    int doubleval = Integer.parseInt(originalString);
                    DecimalFormatSymbols symbols = new DecimalFormatSymbols();
                    symbols.setDecimalSeparator(',');
                    symbols.setGroupingSeparator('.');
                    String pattern = "#,###,###";
                    DecimalFormat formatter = new DecimalFormat(pattern, symbols);
                    String formattedStringku = formatter.format(doubleval);
                    mJum = formattedStringku.replace(".","");
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i;
        switch (item.getItemId()) {
            case R.id.edit:
                i = new Intent(DetailUtang.this, TambahUtang.class);

                Uri currentUtangkuUri = ContentUris.withAppendedId(UtangPiutangContract.UtangPiutangEntry.CONTENT_URI_UTANG, id);

                // Set the URI on the data field of the intent
                i.setData(currentUtangkuUri);
                startActivity(i);
                return true;

            case R.id.delete:

                showDeleteConfirmationDialog();

                return true;

            case android.R.id.home:
                // If the reminder hasn't changed, continue with navigating up to parent activity
                // which is the {@link MainActivity}.
                if (!mHasChanged) {
                    NavUtils.navigateUpFromSameTask(DetailUtang.this);
                    return true;
                }
        }
        return true;
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
                mCurrentUtangUri,         // Query the content URI for the current reminder
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

            String jumlahUtang = "Rp " + formattedString + "";
            mJumlah.setText(jumlahUtang);
            mJumlahSisa.setText(jumlahUtang);
            mNama.setText(nama);
            mDeskripsi.setText(deskripsi);
            mTanggal.setText(tanggal);
            mStatus.setText(status);

        }
    }

    private void showDeleteConfirmationDialog() {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the postivie and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Delete catatan ini ?");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Delete" button, so delete the reminder.
                deleteReminder();
            }
        });
        builder.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Cancel" button, so dismiss the dialog
                // and continue editing the reminder.
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void deleteReminder() {
        // Only perform the delete if this is an existing reminder.
        if (mCurrentUtangUri != null) {
            // Call the ContentResolver to delete the reminder at the given content URI.
            // Pass in null for the selection and selection args because the mCurrentreminderUri
            // content URI already identifies the reminder that we want.
            int rowsDeleted = getContentResolver().delete(mCurrentUtangUri, null, null);

            new AlarmScheduler().cancelAlarm(getApplicationContext(), mCurrentUtangUri);

            // Show a toast message depending on whether or not the delete was successful.
            if (rowsDeleted == 0) {
                // If no rows were deleted, then there was an error with the delete.
                Toast.makeText(this, getString(R.string.editor_delete_reminder_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the delete was successful and we can display a toast.
                Toast.makeText(this, getString(R.string.editor_delete_reminder_successful),
                        Toast.LENGTH_SHORT).show();
            }
        }

        // Close the activity
        finish();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    public void btnCicilUtang(View view) {
        btnCicilUtang.setBackground(this.getResources().getDrawable(R.drawable.btnpembayaranselected));
        btnLunasUtang.setBackground(this.getResources().getDrawable(R.drawable.bgbtnpembayaran));
        mCatatanUtang.setText("Pinjaman ini dibayar dengan cara cicilan");
        etJumlahBayar.setText("");
        mJumlahSisa.setText("Rp " + formattedString + "");
        mStatus.setText("Belum Lunas");
        mSat = "Belum Lunas";
    }

    public void btnLunasUtang(View view) {
        btnLunasUtang.setBackground(this.getResources().getDrawable(R.drawable.btnpembayaranselected));
        btnCicilUtang.setBackground(this.getResources().getDrawable(R.drawable.bgbtnpembayaran));
        mCatatanUtang.setText("Pinjaman ini dibayar dengan cara lunas");
        mJumlahSisa.setText("Rp 0");
        etJumlahBayar.setText(jumlah);
        mStatus.setText("Lunas");
        mSat = "Lunas";
    }

    public void bayarUtang(View view) {
        try {

            int etjum = Integer.parseInt(mJum);
            int totjum = Integer.parseInt(jumlah);

            if (etjum > totjum) {
                Toast.makeText(DetailUtang.this, "Jumlah tidak boleh melebihi jumlah utang", Toast.LENGTH_SHORT).show();
            } else {
                bayarUtangku();
            }

        } catch (NumberFormatException e) {
            Toast.makeText(DetailUtang.this, "Jumlah tidak boleh kosong", Toast.LENGTH_SHORT).show();
        }

    }

    public void bayarUtangku() {

        int mJumku = Integer.parseInt(mJum);
        int jumlahku = Integer.parseInt(jumlah);

        int bayarku = jumlahku - mJumku;

        ContentValues values = new ContentValues();

        values.put(UtangPiutangContract.UtangPiutangEntry.KEY_JUMLAH, bayarku);
        values.put(UtangPiutangContract.UtangPiutangEntry.KEY_STATUS, mSat);

        int rowsAffected = getContentResolver().update(mCurrentUtangUri, values, null, null);

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
}
