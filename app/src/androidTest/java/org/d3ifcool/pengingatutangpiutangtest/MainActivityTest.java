package org.d3ifcool.pengingatutangpiutangtest;

import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void UtangTest(){
        onView(withId(R.id.home_utang)).perform(click());
    }

    @Test
    public void PiutangTest(){
        onView(withId(R.id.home_piutang)).perform(click());
    }

    @Test
    public void AktivitasTest(){
        onView(withId(R.id.home_aktivitas)).perform(click());
    }

    @Test
    public void AboutTest(){
        onView(withId(R.id.home_about)).perform(click());
    }

}