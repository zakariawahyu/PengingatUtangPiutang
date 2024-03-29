package org.d3ifcool.pengingatutangpiutangtest;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.action.ViewActions.pressBack;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.anything;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    private String nama, jumlah, deskripsi, namaedit, jumlahedit, deskripsiedit;

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Before
    public void isi(){
        nama = "Zakaria Wahyu";
        jumlah = "250500";
        deskripsi = "Beli seafood";

        namaedit = "Nur Utomo";
        jumlahedit = "50000";
        deskripsiedit = "Beli bensin";
    }


    @Test
    public void HomeTest(){

    }

    @Test
    public void UITest(){
        onView(withId(R.id.home_utang)).perform(click());

        onView(withId(R.id.fb_add_utang)).perform(click());
        onView(withId(R.id.et_nama_utang)).perform(typeText(nama));
        closeSoftKeyboard();
        onView(withId(R.id.et_jmlutang)).perform(typeText(jumlah));
        closeSoftKeyboard();
        onView(withId(R.id.et_deskripsi_utang)).perform(typeText(deskripsi));
        closeSoftKeyboard();
        onView(withId(R.id.btn_simpan_utang)).perform(scrollTo()).perform(click());

        onView(withId(R.id.list_utang)).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(withId(R.id.list_utang)).atPosition(0).perform(click());

        onView(withId(R.id.edit)).perform(click());

        onView(withId(R.id.et_nama_utang)).perform(replaceText(""));
        closeSoftKeyboard();
        onView(withId(R.id.et_jmlutang)).perform(replaceText(""));
        closeSoftKeyboard();
        onView(withId(R.id.et_deskripsi_utang)).perform(replaceText(""));
        closeSoftKeyboard();
        onView(withId(R.id.et_nama_utang)).perform(typeText(namaedit));
        closeSoftKeyboard();
        onView(withId(R.id.et_jmlutang)).perform(typeText(jumlahedit));
        closeSoftKeyboard();
        onView(withId(R.id.et_deskripsi_utang)).perform(typeText(deskripsiedit));
        closeSoftKeyboard();
        onView(withId(R.id.btn_simpan_utang)).perform(scrollTo()).perform(click());

        onView(withId(R.id.btn_lunas_utang)).perform(click());
        onView(withId(R.id.btn_bayar_utang)).perform(scrollTo()).perform(click());
        onView(withId(R.id.lunas)).perform(click());
        onView(withId(R.id.list_utang_lunas)).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(withId(R.id.list_utang_lunas)).atPosition(0).perform(click());

    }
}