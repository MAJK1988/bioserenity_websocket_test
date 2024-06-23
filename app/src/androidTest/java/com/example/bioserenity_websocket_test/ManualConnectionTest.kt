package com.example.bioserenity_websocket_test

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.semantics.getOrNull
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsFocused
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.bioserenity_websocket_test.utils.Constant

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

/**
 * Unit test class using AndroidJUnit4 to test manual connection management in the MainActivity using Jetpack Compose UI.*/
@RunWith(AndroidJUnit4::class)
class ManualConnectionTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testOpenConnection() {
        composeTestRule.onNodeWithTag(
            Constant.connectToWss
        ).assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            Constant.connectToWss
        ).performClick()
        Thread.sleep(1000)
        assertTrue("open connection", composeTestRule.activity.isConnect.value)
    }

    @Test
    fun testCloseConnection() {
        //check button exist
        composeTestRule.onNodeWithTag(
            Constant.connectToWss
        ).assertIsDisplayed()
        //Test Connection? click button to connect
        composeTestRule.onNodeWithTag(
            Constant.connectToWss
        ).performClick()
        //wait 1 s
        Thread.sleep(1000)
        //check connection status change
        assertTrue("open connection", composeTestRule.activity.isConnect.value)
        //Test Connection? click button to close connection
        composeTestRule.onNodeWithTag(
            Constant.connectToWss
        ).performClick()
        //wait 3 s
        Thread.sleep(3000)
        //check connection status change
        assertTrue("close connection", !composeTestRule.activity.isConnect.value)
    }

    @Test
    fun testCloseOpenConnection() {
        //check button exist
        composeTestRule.onNodeWithTag(
            Constant.connectToWss
        ).assertIsDisplayed()
        //Test Connection? click button to connect
        composeTestRule.onNodeWithTag(
            Constant.connectToWss
        ).performClick()
        //wait 1 s
        Thread.sleep(1000)
        //check connection status change
        assertTrue("open connection", composeTestRule.activity.isConnect.value)
        //Test Connection? click button to close connection
        composeTestRule.onNodeWithTag(
            Constant.connectToWss
        ).performClick()
        //wait 3 s
        Thread.sleep(3000)
        //check connection status change
        assertTrue("close connection", !composeTestRule.activity.isConnect.value)

        composeTestRule.onNodeWithTag(
            Constant.connectToWss
        ).performClick()
        //wait 1 s
        Thread.sleep(1000)
        //check connection status change
        assertTrue("open connection", composeTestRule.activity.isConnect.value)
    }

    @Test
    fun testAddCar() {
        //check button exist
        composeTestRule.onNodeWithTag(
            Constant.connectToWss
        ).assertIsDisplayed()
        //Check if connect to WSS
        composeTestRule.onNodeWithTag(
            Constant.connectToWss
        ).performClick()
        //wait 1 s
        Thread.sleep(1000)
        //check connection status change
        assertTrue("open connection", composeTestRule.activity.isConnect.value)
        //wait 5 s
        Thread.sleep(5000)
        val carsListSize = composeTestRule.activity.managerCar.cars.value.size
        assertTrue("The cars list should not be empty", carsListSize > 0)
    }

    @Test
    fun testGetCarSpeed() {
        //check button exist
        composeTestRule.onNodeWithTag(
            Constant.connectToWss
        ).assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            Constant.notConnected
        ).assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            Constant.notConnected
        ).assertTextEquals(Constant.pleaseTry)
        //Check if connect to WSS
        composeTestRule.onNodeWithTag(
            Constant.connectToWss
        ).performClick()
        //wait 1 s
        Thread.sleep(1000)
        //check connection status change
        assertTrue("open connection", composeTestRule.activity.isConnect.value)
        //wait 5 s
        Thread.sleep(5000)
        val carsListSize = composeTestRule.activity.managerCar.cars.value.size
        assertTrue("The cars list should not be empty", carsListSize > 0)

        //check button exist
        composeTestRule.onNodeWithTag(
            Constant.ItemCar + composeTestRule.activity.managerCar.cars.value.get(0).cv
        ).assertIsDisplayed()

        //Check if connect to WSS
        composeTestRule.onNodeWithTag(
            Constant.ItemCar + composeTestRule.activity.managerCar.cars.value[0].cv
        ).performClick()
        Thread.sleep(1000)
        assert(0.0!=composeTestRule.activity.managerCar.cars.value[0].currentSpeed)
        // Check if the animation is activate
        composeTestRule.onNodeWithTag(
            Constant.animationTag
        ).assertIsDisplayed().assert(hasProgressBar())

    }

    @Test
    fun testStopGetCarSpeed() {
        composeTestRule.onNodeWithTag(
            Constant.connectToWss
        ).assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            Constant.connectToWss
        ).performClick()
        Thread.sleep(1000)
        assertTrue("open connection", composeTestRule.activity.isConnect.value)
        Thread.sleep(5000)
        val carsListSize = composeTestRule.activity.managerCar.cars.value.size
        assertTrue("The cars list should not be empty", carsListSize > 0)

        composeTestRule.onNodeWithTag(
            Constant.ItemCar + composeTestRule.activity.managerCar.cars.value.get(0).cv
        ).assertIsDisplayed()

        composeTestRule.onNodeWithTag(
            Constant.ItemCar + composeTestRule.activity.managerCar.cars.value[0].cv
        ).performClick()
        Thread.sleep(1000)
        assert(0.0!=composeTestRule.activity.managerCar.cars.value[0].currentSpeed)
        composeTestRule.onNodeWithTag(
            Constant.animationTag
        ).assertIsDisplayed().assert(hasProgressBar())
        composeTestRule.onNodeWithTag(
            Constant.ItemCar + composeTestRule.activity.managerCar.cars.value[0].cv
        ).performClick()
        Thread.sleep(1000)
        composeTestRule.onNodeWithTag(
            Constant.animationTag
        ).assertDoesNotExist()
    }

    private fun hasProgressBar(): SemanticsMatcher {
        return SemanticsMatcher("Has LinearProgressIndicator") {
            it.config.getOrNull(SemanticsProperties.ProgressBarRangeInfo) != null
        }
    }

}