package org.d3ifcool.pengingatutangpiutangtest.reminder;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.Settings;

import androidx.core.app.NotificationCompat;

import org.d3ifcool.pengingatutangpiutangtest.R;
import org.d3ifcool.pengingatutangpiutangtest.data.UtangPiutangContract;
import org.d3ifcool.pengingatutangpiutangtest.piutang.DetailPiutang;
import org.d3ifcool.pengingatutangpiutangtest.utang.DetailUtang;
import org.d3ifcool.pengingatutangpiutangtest.utang.TambahUtang;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class ReminderAlarmService extends IntentService {
    private static final String TAG = ReminderAlarmService.class.getSimpleName();

    private static final int NOTIFICATION_ID = 42;

    Cursor cursorku;
    String nama, jumlah, jenis, formattedString;
    Uri uriku;

    //This is a deep link intent, and needs the task stack
    public static PendingIntent getReminderPendingIntent(Context context, Uri uri) {
        Intent action = new Intent(context, ReminderAlarmService.class);
        action.setData(uri);
        return PendingIntent.getService(context, 0, action, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public ReminderAlarmService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        uriku = intent.getData();

        //Display a notification to view the task details
        Intent action = new Intent(this, DetailUtang.class);
        action.setData(uriku);
        PendingIntent operation = TaskStackBuilder.create(this)
                .addNextIntentWithParentStack(action)
                .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

//        try {


        if (uriku != null) {
            cursorku = getContentResolver().query(uriku, null, null, null, null);

        }



//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        try {

            if (cursorku != null && cursorku.moveToFirst()) {
                nama = UtangPiutangContract.getColumnString(cursorku, UtangPiutangContract.UtangPiutangEntry.KEY_NAMA);
                jumlah = UtangPiutangContract.getColumnString(cursorku, UtangPiutangContract.UtangPiutangEntry.KEY_JUMLAH);
                jenis = UtangPiutangContract.getColumnString(cursorku, UtangPiutangContract.UtangPiutangEntry.KEY_JENIS);

                DecimalFormatSymbols symbols = new DecimalFormatSymbols();
                symbols.setDecimalSeparator(',');
                symbols.setGroupingSeparator('.');
                String pattern = "#,###,###";
                DecimalFormat formatter = new DecimalFormat(pattern, symbols);
                formattedString = formatter.format(Double.parseDouble(jumlah));
            }
        } finally {
            if (cursorku != null) {
                cursorku.close();
            }
        }

        Notification note = new NotificationCompat.Builder(this)
                .setContentTitle(getString(R.string.reminder_title))
                .setContentText("Anda mempunyai " +jenis+" Rp "+ formattedString + " dari " + nama)
                .setSmallIcon(R.drawable.ic_add_alert_black_24dp)
                .setContentIntent(operation)
                .setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 })
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setAutoCancel(true)
                .build();

        manager.notify(NOTIFICATION_ID, note);
    }
}