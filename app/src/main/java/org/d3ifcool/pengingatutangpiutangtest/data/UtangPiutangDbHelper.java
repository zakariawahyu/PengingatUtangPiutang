package org.d3ifcool.pengingatutangpiutangtest.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UtangPiutangDbHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = UtangPiutangDbHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "pengingatutangpiutang.db";

    private static final int DATABASE_VERSION = 1;

    public UtangPiutangDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
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
}
