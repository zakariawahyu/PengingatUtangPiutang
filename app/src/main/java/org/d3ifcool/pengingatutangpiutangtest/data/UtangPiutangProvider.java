package org.d3ifcool.pengingatutangpiutangtest.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class UtangPiutangProvider extends ContentProvider {

    public static final String LOG_TAG = UtangPiutangProvider.class.getSimpleName();

    private static final int AKTIVITAS = 100;

    private static final int AKTIVITAS_ID = 101;

    private static final int UTANG = 200;

    private static final int UTANG_ID = 201;

    private static final int UTANG_LUNAS = 300;

    private static final int UTANG_LUNAS_ID = 301;

    private static final int PIUTANG = 400;

    private static final int PIUTANG_ID = 401;

    private static final int PIUTANG_LUNAS = 500;

    private static final int PIUTANG_LUNAS_ID = 501;


    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(UtangPiutangContract.CONTENT_AUTHORITY, UtangPiutangContract.PATH_AKTIVITAS, AKTIVITAS);

        sUriMatcher.addURI(UtangPiutangContract.CONTENT_AUTHORITY, UtangPiutangContract.PATH_AKTIVITAS + "/#", AKTIVITAS_ID);

        sUriMatcher.addURI(UtangPiutangContract.CONTENT_AUTHORITY, UtangPiutangContract.PATH_UTANG, UTANG);

        sUriMatcher.addURI(UtangPiutangContract.CONTENT_AUTHORITY, UtangPiutangContract.PATH_UTANG + "/#", UTANG_ID);

        sUriMatcher.addURI(UtangPiutangContract.CONTENT_AUTHORITY, UtangPiutangContract.PATH_UTANG_LUNAS, UTANG_LUNAS);

        sUriMatcher.addURI(UtangPiutangContract.CONTENT_AUTHORITY, UtangPiutangContract.PATH_UTANG_LUNAS + "/#", UTANG_LUNAS_ID);

        sUriMatcher.addURI(UtangPiutangContract.CONTENT_AUTHORITY, UtangPiutangContract.PATH_PIUTANG, PIUTANG);

        sUriMatcher.addURI(UtangPiutangContract.CONTENT_AUTHORITY, UtangPiutangContract.PATH_PIUTANG + "/#", PIUTANG_ID);

        sUriMatcher.addURI(UtangPiutangContract.CONTENT_AUTHORITY, UtangPiutangContract.PATH_PIUTANG_LUNAS, PIUTANG_LUNAS);

        sUriMatcher.addURI(UtangPiutangContract.CONTENT_AUTHORITY, UtangPiutangContract.PATH_PIUTANG_LUNAS + "/#", PIUTANG_LUNAS_ID);
    }

    private UtangPiutangDbHelper mDbHelper;

    @Override
    public boolean onCreate() {
        mDbHelper = new UtangPiutangDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase database = mDbHelper.getReadableDatabase();

        // This cursor will hold the result of the query
        Cursor cursor = null;

        int match = sUriMatcher.match(uri);
        switch (match) {
            case AKTIVITAS:
                sortOrder = "_id DESC";
                cursor = database.query(UtangPiutangContract.UtangPiutangEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case AKTIVITAS_ID:
                selection = UtangPiutangContract.UtangPiutangEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };

                cursor = database.query(UtangPiutangContract.UtangPiutangEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case UTANG:
                sortOrder = "_id DESC";
                selection = UtangPiutangContract.UtangPiutangEntry.KEY_STATUS +"= 'Belum Lunas'" + " AND " + UtangPiutangContract.UtangPiutangEntry.KEY_JENIS +"= 'Utang'";
                cursor = database.query(UtangPiutangContract.UtangPiutangEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case UTANG_ID:
                selection = UtangPiutangContract.UtangPiutangEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };

                cursor = database.query(UtangPiutangContract.UtangPiutangEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case UTANG_LUNAS:
                sortOrder = "_id DESC";
                selection = UtangPiutangContract.UtangPiutangEntry.KEY_STATUS +"= 'Lunas'" + " AND " + UtangPiutangContract.UtangPiutangEntry.KEY_JENIS +"= 'Utang'";
                cursor = database.query(UtangPiutangContract.UtangPiutangEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case UTANG_LUNAS_ID:
                selection = UtangPiutangContract.UtangPiutangEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };

                cursor = database.query(UtangPiutangContract.UtangPiutangEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case PIUTANG:
                sortOrder = "_id DESC";
                selection = UtangPiutangContract.UtangPiutangEntry.KEY_STATUS +"= 'Belum Lunas'" + " AND " + UtangPiutangContract.UtangPiutangEntry.KEY_JENIS +"= 'Piutang'";
                cursor = database.query(UtangPiutangContract.UtangPiutangEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case PIUTANG_ID:
                selection = UtangPiutangContract.UtangPiutangEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };

                cursor = database.query(UtangPiutangContract.UtangPiutangEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case PIUTANG_LUNAS:
                sortOrder = "_id DESC";
                selection = UtangPiutangContract.UtangPiutangEntry.KEY_STATUS +"= 'Lunas'" + " AND " + UtangPiutangContract.UtangPiutangEntry.KEY_JENIS +"= 'Piutang'";
                cursor = database.query(UtangPiutangContract.UtangPiutangEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case PIUTANG_LUNAS_ID:
                selection = UtangPiutangContract.UtangPiutangEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };

                cursor = database.query(UtangPiutangContract.UtangPiutangEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }


        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case AKTIVITAS:
                return UtangPiutangContract.UtangPiutangEntry.CONTENT_LIST_TYPE_AKTIVITAS;
            case AKTIVITAS_ID:
                return UtangPiutangContract.UtangPiutangEntry.CONTENT_ITEM_TYPE_AKTIVITAS;
            case UTANG:
                return UtangPiutangContract.UtangPiutangEntry.CONTENT_LIST_TYPE_UTANG;
            case UTANG_ID:
                return UtangPiutangContract.UtangPiutangEntry.CONTENT_ITEM_TYPE_UTANG;
            case UTANG_LUNAS:
                return UtangPiutangContract.UtangPiutangEntry.CONTENT_LIST_TYPE_UTANG_LUNAS;
            case UTANG_LUNAS_ID:
                return UtangPiutangContract.UtangPiutangEntry.CONTENT_ITEM_TYPE_UTANG_LUNAS;
            case PIUTANG:
                return UtangPiutangContract.UtangPiutangEntry.CONTENT_LIST_TYPE_PIUTANG;
            case PIUTANG_ID:
                return UtangPiutangContract.UtangPiutangEntry.CONTENT_ITEM_TYPE_PIUTANG;
            case PIUTANG_LUNAS:
                return UtangPiutangContract.UtangPiutangEntry.CONTENT_LIST_TYPE_PIUTANG_LUNAS;
            case PIUTANG_LUNAS_ID:
                return UtangPiutangContract.UtangPiutangEntry.CONTENT_ITEM_TYPE_PIUTANG_LUNAS;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case UTANG:
                return insertUtang(uri, contentValues);

            case PIUTANG:
                return insertUtang(uri, contentValues);

            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    private Uri insertUtang(Uri uri, ContentValues values) {

        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        long id = database.insert(UtangPiutangContract.UtangPiutangEntry.TABLE_NAME, null, values);

        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        getContext().getContentResolver().notifyChange(uri, null);


        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        int rowsDeleted;

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case UTANG:
                rowsDeleted = database.delete(UtangPiutangContract.UtangPiutangEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case UTANG_ID:
                selection = UtangPiutangContract.UtangPiutangEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                rowsDeleted = database.delete(UtangPiutangContract.UtangPiutangEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case UTANG_LUNAS:
                rowsDeleted = database.delete(UtangPiutangContract.UtangPiutangEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case UTANG_LUNAS_ID:
                selection = UtangPiutangContract.UtangPiutangEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                rowsDeleted = database.delete(UtangPiutangContract.UtangPiutangEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case PIUTANG:
                rowsDeleted = database.delete(UtangPiutangContract.UtangPiutangEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case PIUTANG_ID:
                selection = UtangPiutangContract.UtangPiutangEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                rowsDeleted = database.delete(UtangPiutangContract.UtangPiutangEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case PIUTANG_LUNAS:
                rowsDeleted = database.delete(UtangPiutangContract.UtangPiutangEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case PIUTANG_LUNAS_ID:
                selection = UtangPiutangContract.UtangPiutangEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                rowsDeleted = database.delete(UtangPiutangContract.UtangPiutangEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }

        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case UTANG:
                return updateUtang(uri, values, selection, selectionArgs);
            case UTANG_ID:
                selection = UtangPiutangContract.UtangPiutangEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                return updateUtang(uri, values, selection, selectionArgs);
            case PIUTANG:
                return updateUtang(uri, values, selection, selectionArgs);
            case PIUTANG_ID:
                selection = UtangPiutangContract.UtangPiutangEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                return updateUtang(uri, values, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    private int updateUtang(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        if (values.size() == 0) {
            return 0;
        }

        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        int rowsUpdated = database.update(UtangPiutangContract.UtangPiutangEntry.TABLE_NAME, values, selection, selectionArgs);

        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsUpdated;
    }
}
