package com.example.myasus.footballKADE

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import com.example.myasus.footballKADE.R.id.bottom_navigation
import com.example.myasus.footballKADE.feature.mainactivity.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest{
    @Rule
    @JvmField var activityRule =
        ActivityTestRule(MainActivity::class.java)

    @Test
    fun testRecyclerViewBehaviour(){
        Thread.sleep(3000)
        onView(withId(R.id.list_match))
            .check(matches(isDisplayed()))
        Thread.sleep(2000)
        onView(withId(R.id.list_match)).perform(RecyclerViewActions
            .scrollToPosition<RecyclerView.ViewHolder>(10))
        onView(withId(R.id.list_match)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(10, click()))
        Thread.sleep(1000)
    }

    @Test
    fun testBottomNavigationViewBehaviour(){
        Thread.sleep(3000)
        onView(withId(R.id.list_match))
            .check(matches(isDisplayed()))
        onView(withId(R.id.bottom_navigation)).check(matches(isDisplayed()))
        Thread.sleep(2000)
        onView(withId(R.id.favorites)).perform(click())

        Thread.sleep(2000)
        onView(withId(R.id.matchesUp)).perform(click())

        Thread.sleep(2000)
        onView(withId(R.id.matchesLast)).perform(click())

        Thread.sleep(1000)
    }

    @Test
    fun testAddRemoveBehaviour(){
        Thread.sleep(3000)
        onView(withId(R.id.list_match))
            .check(matches(isDisplayed()))

        Thread.sleep(1000)
        onView(withId(R.id.spinner)).check(matches(isDisplayed()))
        onView(withId(R.id.spinner)).perform(click())

        Thread.sleep(2000)
        onView(withText("German Bundesliga")).perform(click())

        Thread.sleep(2000)
        onView(withId(R.id.list_match)).perform(RecyclerViewActions
            .scrollToPosition<RecyclerView.ViewHolder>(10))
        onView(withId(R.id.list_match)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(10, click()))

        Thread.sleep(2000)
        onView(withId(R.id.add_to_favorite)).check(matches(isDisplayed()))
        onView(withId(R.id.add_to_favorite)).perform(click())
        onView(withText("Added to Favorite")).check(matches(isDisplayed()))

        Thread.sleep(2000)
        pressBack()

        Thread.sleep(1000)
        onView(withId(bottom_navigation))
            .check(matches(isDisplayed()))
        onView(withId(R.id.favorites)).perform(click())

        Thread.sleep(2000)
        onView(withId(R.id.list_match))
            .check(matches(isDisplayed()))
        onView(withId(R.id.list_match)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click())
        )

        Thread.sleep(1000)
        onView(withId(R.id.add_to_favorite)).check(matches(isDisplayed()))
        onView(withId(R.id.add_to_favorite)).perform(click())
        onView(withText("Removed from Favorite")).check(matches(isDisplayed()))

        Thread.sleep(1000)
        pressBack()

        Thread.sleep(1000)
    }
}