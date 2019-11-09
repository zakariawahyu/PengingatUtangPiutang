package org.d3ifcool.pengingatutangpiutangtest.utang;

import android.text.TextUtils;
import android.widget.DatePicker;

import androidx.test.espresso.contrib.PickerActions;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.d3ifcool.pengingatutangpiutangtest.R;
import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressBack;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;
@RunWith(AndroidJUnit4.class)
public class DataDrivenTest {
    @Rule
    public ActivityTestRule<Utang> mActivityTestRule =
            new ActivityTestRule<>(Utang.class);

    @Test
    public void dataDriven() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(openFile("DataDrivenTest.csv")));
        String line = "";

        while ((line = bufferedReader.readLine()) != null) {
            String[] str = line.split(",");

            String nama = str[0].replace(" ","");
            String jumlah = str[1].toString().replace(" ","");
            String deskripsi = str[2].toString().replace(" ","");


            onView(withId(R.id.fb_add_utang)).perform(click());

            onView(withId(R.id.et_nama_utang)).perform(typeText(nama), closeSoftKeyboard());

            onView(withId(R.id.et_jmlutang)).perform(typeText(jumlah), closeSoftKeyboard());

            onView(withId(R.id.et_deskripsi_utang)).perform(typeText(deskripsi), closeSoftKeyboard());

            onView(withId(R.id.btn_simpan_utang)).perform(scrollTo()).perform(click());

        }
    }

    private InputStream openFile(String filename) throws IOException {
        return getClass().getClassLoader().getResourceAsStream(filename);
    }

}