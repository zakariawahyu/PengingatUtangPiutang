package org.d3ifcool.pengingatutangpiutangtest.utang;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;
import org.d3ifcool.pengingatutangpiutangtest.MainActivity;
import org.d3ifcool.pengingatutangpiutangtest.R;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static org.hamcrest.Matchers.anything;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class UtangTest {

    @Rule
    public ActivityTestRule<Utang> mActivityTestRule =
            new ActivityTestRule<>(Utang.class);

    @Test
    public void UtangTest(){

    }

    @Test
    public void PembayaranLunasTest(){
        onView(withId(R.id.list_utang)).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(withId(R.id.list_utang)).atPosition(0).perform(click());
        onView(withId(R.id.btn_lunas_utang)).perform(click());
        onView(withId(R.id.btn_bayar_utang)).perform(scrollTo()).perform(click());
    }



}