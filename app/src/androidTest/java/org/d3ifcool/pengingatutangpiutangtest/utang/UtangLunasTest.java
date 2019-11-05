package org.d3ifcool.pengingatutangpiutangtest.utang;

import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.d3ifcool.pengingatutangpiutangtest.R;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.anything;
import static org.junit.Assert.*;
@RunWith(AndroidJUnit4.class)
public class UtangLunasTest {

    @Rule
    public ActivityTestRule<UtangLunas> mActivityTestRule =
            new ActivityTestRule<>(UtangLunas.class);

    @Test
    public void DetailUtangLunas() {
        onView(withId(R.id.list_utang_lunas)).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(withId(R.id.list_utang_lunas)).atPosition(0).perform(click());
    }

}