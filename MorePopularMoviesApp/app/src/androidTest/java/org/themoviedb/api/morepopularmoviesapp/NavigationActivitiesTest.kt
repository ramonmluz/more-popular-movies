package org.themoviedb.api.morepopularmoviesapp

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.themoviedb.api.morepopularmoviesapp.ui.MainActivity

// Activity a ser lançada
@RunWith(AndroidJUnit4::class)
class NavigationActivitiesTest {

    @get:Rule
    public var activityTesteRole = ActivityTestRule<MainActivity>(MainActivity::class.java)


    @Test
    fun clickMovieItem_OpenMovieDetail(){
        onView(withId(R.id.recyclerView))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        onView(withId(R.id.nestedScrollView)).check(matches(isDisplayed()));
    }


}