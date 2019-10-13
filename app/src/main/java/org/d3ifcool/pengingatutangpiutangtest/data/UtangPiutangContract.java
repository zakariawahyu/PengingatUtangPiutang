package org.d3ifcool.pengingatutangpiutangtest.data;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class UtangPiutangContract {

    private UtangPiutangContract() {}

    public static final String CONTENT_AUTHORITY = "org.d3ifcool.pengingatutangpiutangtest";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_UTANG = "utang";
    public static final String PATH_UTANG_LUNAS = "utanglunas";

    public static final String PATH_PIUTANG = "piutang";
    public static final String PATH_PIUTANG_LUNAS = "piutanglunas";

    public static final class UtangPiutangEntry implements BaseColumns {

        public static final Uri CONTENT_URI_UTANG = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_UTANG);

        public static final Uri CONTENT_URI_UTANG_LUNAS = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_UTANG_LUNAS);

        public static final Uri CONTENT_URI_PIUTANG = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_PIUTANG);

        public static final Uri CONTENT_URI_PIUTANG_LUNAS = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_PIUTANG_LUNAS);

        public static final String CONTENT_LIST_TYPE_UTANG =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_UTANG;

        public static final String CONTENT_ITEM_TYPE_UTANG =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_UTANG;

        public static final String CONTENT_LIST_TYPE_UTANG_LUNAS =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_UTANG_LUNAS;

        public static final String CONTENT_ITEM_TYPE_UTANG_LUNAS =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_UTANG_LUNAS;

        public static final String CONTENT_LIST_TYPE_PIUTANG =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PIUTANG;

        public static final String CONTENT_ITEM_TYPE_PIUTANG =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PIUTANG;

        public static final String CONTENT_LIST_TYPE_PIUTANG_LUNAS =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PIUTANG_LUNAS;

        public static final String CONTENT_ITEM_TYPE_PIUTANG_LUNAS =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PIUTANG_LUNAS;

        public final static String TABLE_NAME = "utangpiutang";

        public final static String _ID = BaseColumns._ID;
        public static final String KEY_NAMA = "nama";
        public static final String KEY_JUMLAH = "jumlah";
        public static final String KEY_DESKRIPSI = "deskripsi";
        public static final String KEY_DATE = "date";
        public static final String KEY_TIME = "time";
        public static final String KEY_REPEAT = "repeat";
        public static final String KEY_REPEAT_NO = "repeat_no";
        public static final String KEY_REPEAT_TYPE = "repeat_type";
        public static final String KEY_ACTIVE = "active";
        public static final String KEY_JENIS = "jenis";
        public static final String KEY_STATUS = "status";

    }

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString( cursor.getColumnIndex(columnName) );
    }
}
