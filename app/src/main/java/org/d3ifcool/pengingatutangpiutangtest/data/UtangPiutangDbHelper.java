package org.d3ifcool.pengingatutangpiutangtest.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class UtangPiutangDbHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = UtangPiutangDbHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "pengingatutangpiutang.db";

    private static final int DATABASE_VERSION = 1;

    private Context mContext;

    public UtangPiutangDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_UTANGPIUTANG_TABLE =  "CREATE TABLE " + UtangPiutangContract.UtangPiutangEntry.TABLE_NAME + " ("
                + UtangPiutangContract.UtangPiutangEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + UtangPiutangContract.UtangPiutangEntry.KEY_NAMA + " TEXT NOT NULL, "
                + UtangPiutangContract.UtangPiutangEntry.KEY_JUMLAH + " TEXT NOT NULL, "
                + UtangPiutangContract.UtangPiutangEntry.KEY_DESKRIPSI + " TEXT NOT NULL, "
                + UtangPiutangContract.UtangPiutangEntry.KEY_DATE + " TEXT NOT NULL, "
                + UtangPiutangContract.UtangPiutangEntry.KEY_TIME + " TEXT NOT NULL, "
                + UtangPiutangContract.UtangPiutangEntry.KEY_REPEAT + " TEXT NOT NULL, "
                + UtangPiutangContract.UtangPiutangEntry.KEY_REPEAT_NO + " TEXT NOT NULL, "
                + UtangPiutangContract.UtangPiutangEntry.KEY_REPEAT_TYPE + " TEXT NOT NULL, "
                + UtangPiutangContract.UtangPiutangEntry.KEY_ACTIVE + " TEXT NOT NULL, "
                + UtangPiutangContract.UtangPiutangEntry.KEY_JENIS + " TEXT NOT NULL, "
                + UtangPiutangContract.UtangPiutangEntry.KEY_STATUS + " TEXT NOT NULL "+  " );";

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_UTANGPIUTANG_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public static int getDatabaseVersion() {
        return DATABASE_VERSION;
    }

    public void backup(String outFileName) {

        //database path
        final String inFileName = mContext.getDatabasePath(DATABASE_NAME).toString();

        try {

            File dbFile = new File(inFileName);
            FileInputStream fis = new FileInputStream(dbFile);

            // Open the empty db as the output stream
            OutputStream output = new FileOutputStream(outFileName);

            // Transfer bytes from the input file to the output file
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }

            // Close the streams
            output.flush();
            output.close();
            fis.close();

            Toast.makeText(mContext, "Backup Completed", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Toast.makeText(mContext, "Unable to backup database. Retry", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public void importDB(String inFileName) {

        final String outFileName = mContext.getDatabasePath(DATABASE_NAME).toString();

        try {

            File dbFile = new File(inFileName);
            FileInputStream fis = new FileInputStream(dbFile);

            // Open the empty db as the output stream
            OutputStream output = new FileOutputStream(outFileName);

            // Transfer bytes from the input file to the output file
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }

            // Close the streams
            output.flush();
            output.close();
            fis.close();

            Toast.makeText(mContext, "Import Completed", Toast.LENGTH_SHORT).show();


        } catch (Exception e) {
            Toast.makeText(mContext, "Unable to import database. Retry", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}
