package org.d3ifcool.pengingatutangpiutangtest.utang;

import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.d3ifcool.pengingatutangpiutangtest.R;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.anything;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class TambahUtangTest {
    private String nama;
    private String jumlahutang;
    private String deskirpsi, namaedit, jumlahedit, deskripsiedit;

    @Rule
    public ActivityTestRule<Utang> mActivityTestRule =
            new ActivityTestRule<>(Utang.class);
    @Before
    public void isi(){
        nama = "Zakaria";
        jumlahutang = "25000";
        deskirpsi = "Makan sate ayam";

        namaedit = "Nur Utomo";
        jumlahedit = "50000";
        deskripsiedit = "Beli bensin";
    }

    @Test
    public void TambahUtangTest(){
        onView(withId(R.id.fb_add_utang)).perform(click());
        onView(withId(R.id.et_nama_utang)).perform(typeText(nama));
        closeSoftKeyboard();
        onView(withId(R.id.et_jmlutang)).perform(typeText(jumlahutang));
        closeSoftKeyboard();
        onView(withId(R.id.et_deskripsi_utang)).perform(typeText(deskirpsi));
        closeSoftKeyboard();
        onView(withId(R.id.btn_simpan_utang)).perform(scrollTo()).perform(click());
    }

    @Test
    public void EditUtangTest() {
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
    }

}