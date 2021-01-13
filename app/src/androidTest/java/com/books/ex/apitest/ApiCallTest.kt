package com.books.ex.apitest

import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.books.ex.apitest.apiclient.MainActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
@MediumTest
class ApiCallTest {
    lateinit var signal: CountDownLatch

    @Before
    fun createTask() {
        launchActivity<MainActivity>()
        signal = CountDownLatch(1)
    }

    @Test
    fun concurrent_api_callTest() {
        onView(withId(R.id.concurrent_call)).perform(click())
        signal.await(3, TimeUnit.SECONDS)
    }

    @Test
    fun rxjava_api_callTest() {
        onView(withId(R.id.rxjava_call)).perform(click())
        signal.await(3, TimeUnit.SECONDS)
    }
}