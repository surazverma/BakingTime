package com.example.android.bakingtime;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.android.bakingtime.Utils.SimpleIdlingResource;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


/**
 * Created by Suraz Verma on 2/10/2018.
 */
@RunWith(AndroidJUnit4.class)
public class ActivityBasicTest {
    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule
             = new ActivityTestRule<>(MainActivity.class);
    private IdlingResource mIdlingResources;
    @Before
    public void registerIdlingResource(){
        MainActivity activity = mainActivityActivityTestRule.getActivity();
        mIdlingResources = new SimpleIdlingResource(activity);

        Espresso.registerIdlingResources(mIdlingResources);
    }
    @Test
    public void checkRecipeName_onStart(){
        onView(ViewMatchers.withId(R.id.recipe_recycler_view)).perform(RecyclerViewActions.scrollToPosition(1));
        onView(withText("Brownies")).check(matches(isDisplayed()));

    }
    @After
    public void unregisterIdlingResource(){
        if (mIdlingResources!=null){
            Espresso.unregisterIdlingResources(mIdlingResources);
        }
    }
}
