package org.d3ifcool.pengingatutangpiutangtest.utang;

import androidx.appcompat.app.AppCompatActivity;


import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.d3ifcool.pengingatutangpiutangtest.R;
import org.d3ifcool.pengingatutangpiutangtest.data.UtangPiutangContract;
import org.d3ifcool.pengingatutangpiutangtest.reminder.AlarmScheduler;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Calendar;

public class TambahUtang extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener, LoaderManager.LoaderCallbacks<Cursor> {
    Button btnsave;
    private static final int EXISTING_UTANG_LOADER = 0;

    private EditText mTitleText, mJumlahText, mDeskripsiText;
    private TextView mDateText, mTimeText, mRepeatText, mRepeatNoText, mRepeatTypeText;
    private FloatingActionButton mFAB1;
    private FloatingActionButton mFAB2;
    private Calendar mCalendar;
    private int mYear, mMonth, mHour, mMinute, mDay;
    private long mRepeatTime;
    private Switch mRepeatSwitch;
    private String mTitle;
    private String mJum;
    private String mDes;
    private String mTime;
    private String mDate;
    private String mRepeat;
    private String mRepeatNo;
    private String mRepeatType;
    private String mActive;
    private String mJen;
    private String mStat;

    private Uri mCurrentUtangUri;
    private boolean mUtangHasChanged = false;

    // Values for orientation change
    public static final String KEY_NAMA = "nama_key";
    public static final String KEY_JUMLAH = "jumlah_key";
    public static final String KEY_DESKRIPSI = "deskripsi_key";
    public static final String KEY_DATE = "date_key";
    public static final String KEY_TIME = "time_key";
    public static final String KEY_REPEAT = "repeat_key";
    public static final String KEY_REPEAT_NO = "repeat_no_key";
    public static final String KEY_REPEAT_TYPE = "repeat_type_key";
    public static final String KEY_ACTIVE = "active_key";
    public static final String KEY_JENIS = "jenis_key";
    public static final String KEY_STATUS = "status_key";

    // Constant values in milliseconds
    private static final long milMinute = 60000L;
    private static final long milHour = 3600000L;
    private static final long milDay = 86400000L;
    private static final long milWeek = 604800000L;
    private static final long milMonth = 2592000000L;

    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            mUtangHasChanged = true;
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_utang);

        btnsave = findViewById(R.id.btn_simpan_utang);

        Intent intent = getIntent();
        mCurrentUtangUri = intent.getData();

        if (mCurrentUtangUri == null) {

            setTitle("Tambah Data Utang");

        } else {

            setTitle("Edit Data Utang");

            getLoaderManager().initLoader(EXISTING_UTANG_LOADER, null, this);
        }

        mTitleText = (EditText) findViewById(R.id.et_nama_utang);
        mJumlahText = (EditText) findViewById(R.id.et_jmlutang);
        mDeskripsiText = (EditText) findViewById(R.id.et_deskripsi_utang);
        mDateText = (TextView) findViewById(R.id.set_date_utang);
        mTimeText = (TextView) findViewById(R.id.set_time_utang);
        mRepeatText = (TextView) findViewById(R.id.set_repeat_utang);
        mRepeatNoText = (TextView) findViewById(R.id.set_repeat_no_utang);
        mRepeatTypeText = (TextView) findViewById(R.id.set_repeat_type_utang);
        mRepeatSwitch = (Switch) findViewById(R.id.repeat_switch_utang);
        mFAB1 = (FloatingActionButton) findViewById(R.id.starrted1utang);
        mFAB2 = (FloatingActionButton) findViewById(R.id.starrted2utang);

        // Initialize default values
        mActive = "true";
        mRepeat = "true";
        mRepeatNo = Integer.toString(1);
        mRepeatType = "Hour";
        mJen = "Utang";
        mStat = "Belum Lunas";

        mCalendar = Calendar.getInstance();
        mHour = mCalendar.get(Calendar.HOUR_OF_DAY);
        mMinute = mCalendar.get(Calendar.MINUTE);
        mYear = mCalendar.get(Calendar.YEAR);
        mMonth = mCalendar.get(Calendar.MONTH) + 1;
        mDay = mCalendar.get(Calendar.DATE);

        mDate = mDay + "/" + mMonth + "/" + mYear;
        mTime = mHour + ":" + mMinute;

        // Setup Reminder Title EditText
        mTitleText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mTitle = s.toString().trim();
                mTitleText.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mJumlahText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mJumlahText.removeTextChangedListener(this);
                try {
                    String originalString = s.toString();
                    originalString = originalString.replaceAll("\\.", "").replaceFirst(",", ".");
                    originalString = originalString.replaceAll("[A-Z]", "").replaceAll("[a-z]", "");
                    double doubleval = Double.parseDouble(originalString);
                    DecimalFormatSymbols symbols = new DecimalFormatSymbols();
                    symbols.setDecimalSeparator(',');
                    symbols.setGroupingSeparator('.');
                    String pattern = "#,###,###,###";
                    DecimalFormat formatter = new DecimalFormat(pattern, symbols);
                    String formattedString = formatter.format(doubleval);
                    mJum = formattedString;
                    mJumlahText.setText(formattedString);
                    mJumlahText.setSelection(mJumlahText.getText().length());
                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }
                mJumlahText.addTextChangedListener(this);


                mJumlahText.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mDeskripsiText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mDes = s.toString().trim();
                mDeskripsiText.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // Setup TextViews using reminder values
        mDateText.setText(mDate);
        mTimeText.setText(mTime);
        mRepeatNoText.setText(mRepeatNo);
        mRepeatTypeText.setText(mRepeatType);
        mRepeatText.setText("Every " + mRepeatNo + " " + mRepeatType + "(s)");

        // To save state on device rotation
        if (savedInstanceState != null) {

            String savedTitle = savedInstanceState.getString(KEY_NAMA);
            mTitleText.setText(savedTitle);
            mTitle = savedTitle;

            String savedJumlah = savedInstanceState.getString(KEY_JUMLAH);
            mJumlahText.setText(savedJumlah);
            mJum = savedJumlah;

            String savedDesk = savedInstanceState.getString(KEY_DESKRIPSI);
            mDeskripsiText.setText(savedDesk);
            mDes = savedDesk;

            String savedTime = savedInstanceState.getString(KEY_TIME);
            mTimeText.setText(savedTime);
            mTime = savedTime;

            String savedDate = savedInstanceState.getString(KEY_DATE);
            mDateText.setText(savedDate);
            mDate = savedDate;

            String saveRepeat = savedInstanceState.getString(KEY_REPEAT);
            mRepeatText.setText(saveRepeat);
            mRepeat = saveRepeat;

            String savedRepeatNo = savedInstanceState.getString(KEY_REPEAT_NO);
            mRepeatNoText.setText(savedRepeatNo);
            mRepeatNo = savedRepeatNo;

            String savedRepeatType = savedInstanceState.getString(KEY_REPEAT_TYPE);
            mRepeatTypeText.setText(savedRepeatType);
            mRepeatType = savedRepeatType;

            mActive = savedInstanceState.getString(KEY_ACTIVE);

            mJen = savedInstanceState.getString(KEY_JENIS);

            mStat = savedInstanceState.getString(KEY_STATUS);
        }

        if (mActive.equals("false")) {
            mFAB1.setVisibility(View.VISIBLE);
            mFAB2.setVisibility(View.GONE);

        } else if (mActive.equals("true")) {
            mFAB1.setVisibility(View.GONE);
            mFAB2.setVisibility(View.VISIBLE);
        }

        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveReminder();
                finish();
            }
        });
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putCharSequence(KEY_NAMA, mTitleText.getText());
        outState.putCharSequence(KEY_JUMLAH, mJumlahText.getText());
        outState.putCharSequence(KEY_DESKRIPSI, mDeskripsiText.getText());
        outState.putCharSequence(KEY_TIME, mTimeText.getText());
        outState.putCharSequence(KEY_DATE, mDateText.getText());
        outState.putCharSequence(KEY_REPEAT, mRepeatText.getText());
        outState.putCharSequence(KEY_REPEAT_NO, mRepeatNoText.getText());
        outState.putCharSequence(KEY_REPEAT_TYPE, mRepeatTypeText.getText());
        outState.putCharSequence(KEY_ACTIVE, mActive);
        outState.putCharSequence(KEY_JENIS, mJen);
        outState.putCharSequence(KEY_STATUS, mStat);
    }

    // On clicking Time picker
    public void setTimeUtang(View v) {
        Calendar now = Calendar.getInstance();
        TimePickerDialog tpd = TimePickerDialog.newInstance(
                this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                false
        );
        tpd.setThemeDark(false);
        tpd.show(getSupportFragmentManager(), "Timepickerdialog");
    }

    // On clicking Date picker
    public void setDateUtang(View v) {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show(getSupportFragmentManager(), "Datepickerdialog");
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        mHour = hourOfDay;
        mMinute = minute;
        if (minute < 10) {
            mTime = hourOfDay + ":" + "0" + minute;
        } else {
            mTime = hourOfDay + ":" + minute;
        }
        mTimeText.setText(mTime);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        monthOfYear++;
        mDay = dayOfMonth;
        mMonth = monthOfYear;
        mYear = year;
        mDate = dayOfMonth + "/" + monthOfYear + "/" + year;
        mDateText.setText(mDate);
    }

    // On clicking the active button
    public void selectFab1Utang(View v) {
        mFAB1 = (FloatingActionButton) findViewById(R.id.starrted1utang);
        mFAB1.setVisibility(View.GONE);
        mFAB2 = (FloatingActionButton) findViewById(R.id.starrted2utang);
        mFAB2.setVisibility(View.VISIBLE);
        mActive = "true";
    }

    // On clicking the inactive button
    public void selectFab2Utang(View v) {
        mFAB2 = (FloatingActionButton) findViewById(R.id.starrted2utang);
        mFAB2.setVisibility(View.GONE);
        mFAB1 = (FloatingActionButton) findViewById(R.id.starrted1utang);
        mFAB1.setVisibility(View.VISIBLE);
        mActive = "false";
    }

    // On clicking the repeat switch
    public void onSwitchRepeatUtang(View view) {
        boolean on = ((Switch) view).isChecked();
        if (on) {
            mRepeat = "true";
            mRepeatText.setText("Every " + mRepeatNo + " " + mRepeatType + "(s)");
        } else {
            mRepeat = "false";
            mRepeatText.setText("Off");
        }
    }

    // On clicking repeat type button
    public void selectRepeatTypeUtang(View v) {
        final String[] items = new String[5];

        items[0] = "Minute";
        items[1] = "Hour";
        items[2] = "Day";
        items[3] = "Week";
        items[4] = "Month";

        // Create List Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Type");
        builder.setItems(items, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int item) {

                mRepeatType = items[item];
                mRepeatTypeText.setText(mRepeatType);
                mRepeatText.setText("Every " + mRepeatNo + " " + mRepeatType + "(s)");
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    // On clicking repeat interval button
    public void setRepeatNoUtang(View v) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Enter Number");

        // Create EditText box to input repeat number
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        alert.setView(input);
        alert.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        if (input.getText().toString().length() == 0) {
                            mRepeatNo = Integer.toString(1);
                            mRepeatNoText.setText(mRepeatNo);
                            mRepeatText.setText("Every " + mRepeatNo + " " + mRepeatType + "(s)");
                        } else {
                            mRepeatNo = input.getText().toString().trim();
                            mRepeatNoText.setText(mRepeatNo);
                            mRepeatText.setText("Every " + mRepeatNo + " " + mRepeatType + "(s)");
                        }
                    }
                });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // do nothing
            }
        });
        alert.show();
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
            int titleColumnIndex = cursor.getColumnIndex(UtangPiutangContract.UtangPiutangEntry.KEY_NAMA);
            int jumlahColumnIndex = cursor.getColumnIndex(UtangPiutangContract.UtangPiutangEntry.KEY_JUMLAH);
            int deskripsiColumnIndex = cursor.getColumnIndex(UtangPiutangContract.UtangPiutangEntry.KEY_DESKRIPSI);
            int dateColumnIndex = cursor.getColumnIndex(UtangPiutangContract.UtangPiutangEntry.KEY_DATE);
            int timeColumnIndex = cursor.getColumnIndex(UtangPiutangContract.UtangPiutangEntry.KEY_TIME);
            int repeatColumnIndex = cursor.getColumnIndex(UtangPiutangContract.UtangPiutangEntry.KEY_REPEAT);
            int repeatNoColumnIndex = cursor.getColumnIndex(UtangPiutangContract.UtangPiutangEntry.KEY_REPEAT_NO);
            int repeatTypeColumnIndex = cursor.getColumnIndex(UtangPiutangContract.UtangPiutangEntry.KEY_REPEAT_TYPE);
            int activeColumnIndex = cursor.getColumnIndex(UtangPiutangContract.UtangPiutangEntry.KEY_ACTIVE);
            int jenisColumnIndex = cursor.getColumnIndex(UtangPiutangContract.UtangPiutangEntry.KEY_JENIS);
            int statusColumnIndex = cursor.getColumnIndex(UtangPiutangContract.UtangPiutangEntry.KEY_STATUS);

            // Extract out the value from the Cursor for the given column index
            String title = cursor.getString(titleColumnIndex);
            String jumlah = cursor.getString(jumlahColumnIndex);
            String deskripsi = cursor.getString(deskripsiColumnIndex);
            String date = cursor.getString(dateColumnIndex);
            String time = cursor.getString(timeColumnIndex);
            String repeat = cursor.getString(repeatColumnIndex);
            String repeatNo = cursor.getString(repeatNoColumnIndex);
            String repeatType = cursor.getString(repeatTypeColumnIndex);
            String active = cursor.getString(activeColumnIndex);
            String jenis = cursor.getString(jenisColumnIndex);
            String status = cursor.getString(statusColumnIndex);


            // Update the views on the screen with the values from the database
            mTitleText.setText(title);
            mJumlahText.setText(jumlah);
            mDeskripsiText.setText(deskripsi);
            mDateText.setText(date);
            mTimeText.setText(time);
            mRepeatNoText.setText(repeatNo);
            mRepeatTypeText.setText(repeatType);
            mRepeatText.setText("Every " + repeatNo + " " + repeatType + "(s)");

            // Setup up active buttons
            // Setup repeat switch
            if (repeat.equals("false")) {
                mRepeatSwitch.setChecked(false);
                mRepeatText.setText("Off");

            } else if (repeat.equals("true")) {
                mRepeatSwitch.setChecked(true);
            }

        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }


    public void saveReminder() {

     /*   if (mCurrentReminderUri == null ) {
            // Since no fields were modified, we can return early without creating a new reminder.
            // No need to create ContentValues and no need to do any ContentProvider operations.
            return;
        }
*/

        ContentValues values = new ContentValues();

        values.put(UtangPiutangContract.UtangPiutangEntry.KEY_NAMA, mTitle);
        values.put(UtangPiutangContract.UtangPiutangEntry.KEY_JUMLAH, mJum);
        values.put(UtangPiutangContract.UtangPiutangEntry.KEY_DESKRIPSI, mDes);
        values.put(UtangPiutangContract.UtangPiutangEntry.KEY_DATE, mDate);
        values.put(UtangPiutangContract.UtangPiutangEntry.KEY_TIME, mTime);
        values.put(UtangPiutangContract.UtangPiutangEntry.KEY_REPEAT, mRepeat);
        values.put(UtangPiutangContract.UtangPiutangEntry.KEY_REPEAT_NO, mRepeatNo);
        values.put(UtangPiutangContract.UtangPiutangEntry.KEY_REPEAT_TYPE, mRepeatType);
        values.put(UtangPiutangContract.UtangPiutangEntry.KEY_ACTIVE, mActive);
        values.put(UtangPiutangContract.UtangPiutangEntry.KEY_JENIS, mJen);
        values.put(UtangPiutangContract.UtangPiutangEntry.KEY_STATUS, mStat);


        // Set up calender for creating the notification
        mCalendar.set(Calendar.MONTH, --mMonth);
        mCalendar.set(Calendar.YEAR, mYear);
        mCalendar.set(Calendar.DAY_OF_MONTH, mDay);
        mCalendar.set(Calendar.HOUR_OF_DAY, mHour);
        mCalendar.set(Calendar.MINUTE, mMinute);
        mCalendar.set(Calendar.SECOND, 0);

        long selectedTimestamp = mCalendar.getTimeInMillis();

        // Check repeat type
        if (mRepeatType.equals("Minute")) {
            mRepeatTime = Integer.parseInt(mRepeatNo) * milMinute;
        } else if (mRepeatType.equals("Hour")) {
            mRepeatTime = Integer.parseInt(mRepeatNo) * milHour;
        } else if (mRepeatType.equals("Day")) {
            mRepeatTime = Integer.parseInt(mRepeatNo) * milDay;
        } else if (mRepeatType.equals("Week")) {
            mRepeatTime = Integer.parseInt(mRepeatNo) * milWeek;
        } else if (mRepeatType.equals("Month")) {
            mRepeatTime = Integer.parseInt(mRepeatNo) * milMonth;
        }

        if (mCurrentUtangUri == null) {
            // This is a NEW reminder, so insert a new reminder into the provider,
            // returning the content URI for the new reminder.
            Uri newUri = getContentResolver().insert(UtangPiutangContract.UtangPiutangEntry.CONTENT_URI_UTANG, values);

            // Show a toast message depending on whether or not the insertion was successful.
            if (newUri == null) {
                // If the new content URI is null, then there was an error with insertion.
                Toast.makeText(this, getString(R.string.editor_insert_reminder_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the insertion was successful and we can display a toast.
                Toast.makeText(this, getString(R.string.editor_insert_reminder_successful),
                        Toast.LENGTH_SHORT).show();
            }
        } else {

            int rowsAffected = getContentResolver().update(mCurrentUtangUri, values, null, null);

            // Show a toast message depending on whether or not the update was successful.
            if (rowsAffected == 0) {
                // If no rows were affected, then there was an error with the update.
                Toast.makeText(this, getString(R.string.editor_update_reminder_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the update was successful and we can display a toast.
                Toast.makeText(this, getString(R.string.editor_update_reminder_successful),
                        Toast.LENGTH_SHORT).show();
            }
        }

        // Create a new notification
        if (mActive.equals("true")) {
            if (mRepeat.equals("true")) {
                new AlarmScheduler().setRepeatAlarm(getApplicationContext(), selectedTimestamp, mCurrentUtangUri, mRepeatTime);
            } else if (mRepeat.equals("false")) {
                new AlarmScheduler().setAlarm(getApplicationContext(), selectedTimestamp, mCurrentUtangUri);
            }

            Toast.makeText(this, "Alarm time is " + selectedTimestamp,
                    Toast.LENGTH_LONG).show();
        }

        // Create toast to confirm new reminder
        Toast.makeText(getApplicationContext(), "Saved",
                Toast.LENGTH_SHORT).show();

    }
}
