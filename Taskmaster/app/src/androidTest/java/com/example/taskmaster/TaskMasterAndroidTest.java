package com.example.taskmaster;


import androidx.recyclerview.widget.RecyclerView;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withResourceName;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import androidx.test.espresso.contrib.RecyclerViewActions;


@RunWith(AndroidJUnit4.class)
public class TaskMasterAndroidTest {


    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testTheExistenceOfTheTitleOnHomePage() {
        // check if the title textView in the main activity is exist.
        onView(withId(R.id.home_page_title)).check(matches(isDisplayed()));
    }

    @Test
    public void testTheExistenceOfTheRecyclerViewOnHomePage() {
        // check if the RecyclerView  in the main activity is exist.
        onView(withId(R.id.RV_main)).check(matches(isDisplayed()));
    }

    @Test
    public void testTheClickOnItemInsideTheRecyclerView() {
        // test click on the item in the recyclerView which has descendant of text ".."  ..
        onView(withId(R.id.RV_main)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("go to market")),click()));
        // check the title of the actionBar.. to match the task title:
        onView( allOf(withParent(withResourceName("action_bar")), withText("go to market"))).check(matches(isDisplayed()));
//        onView(withText("go to market")).check(matches(isDisplayed()));
    }

    @Test
    public void testTheExistenceOfTheSettingButtonOnHomePage() {
        // check if the settings button  in the main activity is exist.
        onView(withId(R.id.button_settings)).check(matches(isDisplayed()));
    }

    @Test
    public void testButton_AddTask_Performance() {
        // test the add task button..
        onView(withId(R.id.button_add_task_main)).perform(click());
        // and then check the existence of the task description textView on the Add Task Activity
        onView(withId(R.id.taskDescription)).check(matches(isDisplayed()));
    }

     @Test
        public void testEditTheUserName() {
            // go to settings page
            onView(withId(R.id.button_settings)).perform(click());
            // edit the user’s username
            onView(withId(R.id.input_user_name)).perform( clearText(), typeText("Mohammad") );
            // save changes
            onView(withId(R.id.button_save_name)).perform(click());
            // and assert that it says the correct thing on the homepage
            onView(withId(R.id.home_page_title)).check(matches(withText("Mohammad' Tasks")));
        }

}