package org.d3ifcool.pengingatutangpiutangtest.piutang;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import org.d3ifcool.pengingatutangpiutangtest.R;
import org.d3ifcool.pengingatutangpiutangtest.data.UtangPiutangContract;

public class PiutangCursorAdapter extends CursorAdapter {
    private TextView mTitleText, mDateAndTimeText, mRepeatInfoText, mDeskripsiText;
    private ImageView mActiveImage, mThumbnailImage;
    private ColorGenerator mColorGenerator = ColorGenerator.DEFAULT;
    private TextDrawable mDrawableBuilder;

    public PiutangCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.piutang_items, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        mDeskripsiText = (TextView) view.findViewById(R.id.recycle_deskripsi_piutang);
        mTitleText = (TextView) view.findViewById(R.id.recycle_title_piutang);
        mDateAndTimeText = (TextView) view.findViewById(R.id.recycle_date_time_piutang);
        mRepeatInfoText = (TextView) view.findViewById(R.id.recycle_repeat_info_piutang);
        mActiveImage = (ImageView) view.findViewById(R.id.active_image_piutang);
        mThumbnailImage = (ImageView) view.findViewById(R.id.thumbnail_image_piutang);

        int titleColumnIndex = cursor.getColumnIndex(UtangPiutangContract.UtangPiutangEntry.KEY_NAMA);
        int jumlahColumnIndex = cursor.getColumnIndex(UtangPiutangContract.UtangPiutangEntry.KEY_JUMLAH);
        int deskripsiColumnIndex = cursor.getColumnIndex(UtangPiutangContract.UtangPiutangEntry.KEY_DESKRIPSI);
        int dateColumnIndex = cursor.getColumnIndex(UtangPiutangContract.UtangPiutangEntry.KEY_DATE);
        int timeColumnIndex = cursor.getColumnIndex(UtangPiutangContract.UtangPiutangEntry.KEY_TIME);
        int repeatColumnIndex = cursor.getColumnIndex(UtangPiutangContract.UtangPiutangEntry.KEY_REPEAT);
        int repeatNoColumnIndex = cursor.getColumnIndex(UtangPiutangContract.UtangPiutangEntry.KEY_REPEAT_NO);
        int repeatTypeColumnIndex = cursor.getColumnIndex(UtangPiutangContract.UtangPiutangEntry.KEY_REPEAT_TYPE);
        int activeColumnIndex = cursor.getColumnIndex(UtangPiutangContract.UtangPiutangEntry.KEY_ACTIVE);

        String title = cursor.getString(titleColumnIndex);
        String jumlah = cursor.getString(jumlahColumnIndex);
        String deskripsi = cursor.getString(deskripsiColumnIndex);
        String date = cursor.getString(dateColumnIndex);
        String time = cursor.getString(timeColumnIndex);
        String repeat = cursor.getString(repeatColumnIndex);
        String repeatNo = cursor.getString(repeatNoColumnIndex);
        String repeatType = cursor.getString(repeatTypeColumnIndex);
        String active = cursor.getString(activeColumnIndex);

        String dateTime = date + " " + time;
        String titleku = title + " " + "(Rp" + " " + jumlah + ")";

        setReminderTitle(titleku);
        setReminderDateTime(dateTime);
        setReminderRepeatInfo(repeat, repeatNo, repeatType);
        setActiveImage(active);
        setDeskripsi(deskripsi);
    }

    // Set reminder title view
    public void setReminderTitle(String title) {
        mTitleText.setText(title);
        String letter = "A";

        if (title != null && !title.isEmpty()) {
            letter = title.substring(0, 1);
        }

        int color = mColorGenerator.getRandomColor();

        // Create a circular icon consisting of  a random background colour and first letter of title
        mDrawableBuilder = TextDrawable.builder()
                .buildRound(letter, color);
        mThumbnailImage.setImageDrawable(mDrawableBuilder);
    }

    // Set date and time views
    public void setReminderDateTime(String datetime) {
        mDateAndTimeText.setText(datetime);
    }


    public void setDeskripsi(String deskripsi) {
        mDeskripsiText.setText(deskripsi);
    }

    // Set repeat views
    public void setReminderRepeatInfo(String repeat, String repeatNo, String repeatType) {
        if (repeat.equals("true")) {
            mRepeatInfoText.setText("Every " + repeatNo + " " + repeatType + "(s)");
        } else if (repeat.equals("false")) {
            mRepeatInfoText.setText("Repeat Off");
        }
    }

    // Set active image as on or off
    public void setActiveImage(String active) {
        if (active.equals("true")) {
            mActiveImage.setImageResource(R.drawable.ic_notifications_active_black_24dp);
        } else if (active.equals("false")) {
            mActiveImage.setImageResource(R.drawable.ic_notifications_off_black_24dp);
        }
    }
}
