package com.example.android.bakingtime;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import com.example.android.bakingtime.utils.SimpleIdlingResource;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static org.hamcrest.Matchers.not;

/**
 * Created by Suraz Verma on 2/11/2018.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class IntentTest {
    @Rule
    public IntentsTestRule<MainActivity> mActivityRule = new IntentsTestRule<>(MainActivity.class);
    private IdlingResource mIdlingResources;
    @Before
    public void registerIdlingResource(){
        MainActivity activity = mActivityRule.getActivity();
        mIdlingResources = new SimpleIdlingResource(activity);

        Espresso.registerIdlingResources(mIdlingResources);
    }
    @Before
    public void stubAllExternalIntents(){
        intending(not(isInternal())).respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK, null));

    }
    @Test
    public void onClickRecipe_Check(){
        onView(ViewMatchers.withId(R.id.recipe_recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));
        intended(hasComponent(RecipeDetailsActivity.class.getName()));
    }

    @After
    public void unregisterIdlingResource(){
        if (mIdlingResources!=null){
            Espresso.unregisterIdlingResources(mIdlingResources);
        }
    }
}
