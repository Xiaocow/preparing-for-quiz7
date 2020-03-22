package edu.singaporetech.factorial

import android.util.Log
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.ext.junit.rules.activityScenarioRule
import org.hamcrest.Matchers.*

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class UiInstrumentedTest {
    companion object {
        private const val TAG = "UiInstrumentedTest"

        // test data
        private const val BTN_TXT = "Get Factorial Result"
    }

    @get:Rule
    var activityRule = activityScenarioRule<FactorialActivity>()

    @Test
    fun onLaunch_containsRequiredUI() {
        Log.i(TAG, """
            ### UI elements all exist
            - text element ids textViewOutput and textEditInput exist
            - radio button ids radio_mj radio_jr radio_ji radio_nr radio_ni exist
            - button "$BTN_TXT" exists
            """.trimIndent()
        )

        onView(withId(R.id.textedit_input)).check(matches(isDisplayed()));
        onView(withId(R.id.textview_output)).check(matches(isDisplayed()));
        onView(withId(R.id.radio_mj)).check(matches(isDisplayed()));
        onView(withId(R.id.radio_jr)).check(matches(isDisplayed()));
        onView(withId(R.id.radio_ji)).check(matches(isDisplayed()));
        onView(withId(R.id.radio_nr)).check(matches(isDisplayed()));
        onView(withId(R.id.radio_ni)).check(matches(isDisplayed()));
        onView(allOf(withClassName(endsWith("Button")), withText(containsString(BTN_TXT))))
            .check(matches(isDisplayed()));
    }
}
