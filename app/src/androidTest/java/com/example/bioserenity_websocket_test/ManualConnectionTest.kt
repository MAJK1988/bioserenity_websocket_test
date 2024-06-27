package com.example.bioserenity_websocket_test

import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.semantics.getOrNull
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed

import androidx.compose.ui.test.assertTextEquals

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.bioserenity_websocket_test.data.utils.Constant

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

/**
 * Unit test class employing AndroidJUnit4 for testing manual connection management within the MainActivity using Jetpack Compose UI.*/
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
        ).assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            Constant.connectToWss
        ).performClick()
        Thread.sleep(1000)
        assertTrue("open connection",
            composeTestRule.activity.isConnect.value)

    }

    @Test
    fun testCloseConnection() {
        // Ensure the button exists
        composeTestRule.onNodeWithTag(Constant.connectToWss)
            .assertIsDisplayed()

        // Click the button to establish connection
        composeTestRule.onNodeWithTag(Constant.connectToWss)
            .performClick()

        // Wait for 1 second
        Thread.sleep(1000)

        // Verify connection status change to open
        assertTrue("Connection should be open", composeTestRule.activity.isConnect.value)

        // Click the button to close the connection
        composeTestRule.onNodeWithTag(Constant.connectToWss)
            .performClick()

        // Wait for 3 seconds
        Thread.sleep(3000)

        // Verify connection status change to closed
        assertTrue("Connection should be closed", !composeTestRule.activity.isConnect.value)
    }

    @Test
    fun testCloseOpenConnection() {
        // Ensure the button exists
        composeTestRule.onNodeWithTag(Constant.connectToWss)
            .assertIsDisplayed()

        // Click the button to establish connection
        composeTestRule.onNodeWithTag(Constant.connectToWss)
            .performClick()

        // Wait for 1 second
        Thread.sleep(1000)

        // Verify connection status change to open
        assertTrue("Connection should be open", composeTestRule.activity.isConnect.value)

        // Click the button to close the connection
        composeTestRule.onNodeWithTag(Constant.connectToWss)
            .performClick()

        // Wait for 3 seconds
        Thread.sleep(3000)

        // Verify connection status change to closed
        assertTrue("Connection should be closed", !composeTestRule.activity.isConnect.value)

        // Click the button to establish connection again
        composeTestRule.onNodeWithTag(Constant.connectToWss)
            .performClick()

        // Wait for 1 second
        Thread.sleep(1000)

        // Verify connection status change to open again
        assertTrue("Connection should be open", composeTestRule.activity.isConnect.value)
    }


    @Test
    fun testAddCar() {
        // Ensure the button exists
        composeTestRule.onNodeWithTag(Constant.connectToWss)
            .assertIsDisplayed()

        // Click the button to establish connection
        composeTestRule.onNodeWithTag(Constant.connectToWss)
            .performClick()

        // Wait for 1 second
        Thread.sleep(1000)

        // Verify connection status change to open
        assertTrue("Connection should be open", composeTestRule.activity.isConnect.value)

        // Wait for 5 seconds for cars to be added
        Thread.sleep(5000)

        // Check if cars have been added to the list
        val carsListSize = composeTestRule.activity.managerCar.cars.value.size
        assertTrue("The cars list should not be empty", carsListSize > 0)
    }


    @Test
    fun testGetCarSpeed() {
        // Ensure necessary buttons and text are displayed
        composeTestRule.onNodeWithTag(Constant.connectToWss)
            .assertIsDisplayed()
        composeTestRule.onNodeWithTag(Constant.notConnected)
            .assertIsDisplayed()
        composeTestRule.onNodeWithTag(Constant.notConnected)
            .assertTextEquals(Constant.pleaseTry)

        // Connect to WSS
        composeTestRule.onNodeWithTag(Constant.connectToWss)
            .performClick()

        // Wait for 1 second
        Thread.sleep(1000)

        // Verify connection status change to open
        assertTrue("Connection should be open", composeTestRule.activity.isConnect.value)

        // Wait for 5 seconds for cars to be added
        Thread.sleep(5000)
        assertTrue("The cars list should not be empty", composeTestRule.activity.managerCar.cars.value.isNotEmpty())

        // Click on the first car item
        val firstCarCV = composeTestRule.activity.managerCar.cars.value[0].cv
        composeTestRule.onNodeWithTag(Constant.ItemCar + firstCarCV)
            .assertIsDisplayed()
            .performClick()

        // Wait for 1 second
        Thread.sleep(1000)

        // Assert that the current speed is not 0.0
        assert(composeTestRule.activity.managerCar.cars.value[0].currentSpeed != 0.0)

        // Check if the progress bar animation is activated
        composeTestRule.onNodeWithTag(Constant.animationTag)
            .assertIsDisplayed()
            .assert(hasProgressBar())
    }


    @Test
    fun testStopGetCarSpeed() {
        // Ensure the connect button is displayed and click it
        composeTestRule.onNodeWithTag(Constant.connectToWss)
            .assertIsDisplayed()
            .performClick()

        // Wait for 1 second
        Thread.sleep(1000)

        // Verify connection status change to open
        assertTrue("Connection should be open", composeTestRule.activity.isConnect.value)

        // Wait for 5 seconds for cars to be added
        Thread.sleep(5000)
        assertTrue("The cars list should not be empty", composeTestRule.activity.managerCar.cars.value.isNotEmpty())

        // Click on the first car item to start receiving speed data
        val firstCarCV = composeTestRule.activity.managerCar.cars.value[0].cv
        composeTestRule.onNodeWithTag(Constant.ItemCar + firstCarCV)
            .assertIsDisplayed()
            .performClick()

        // Wait for 1 second
        Thread.sleep(1000)

        // Assert that the current speed is not 0.0 and progress bar animation is displayed
        assert(composeTestRule.activity.managerCar.cars.value[0].currentSpeed != 0.0)
        composeTestRule.onNodeWithTag(Constant.animationTag)
            .assertIsDisplayed()
            .assert(hasProgressBar())

        // Click on the first car item again to stop receiving speed data
        composeTestRule.onNodeWithTag(Constant.ItemCar + firstCarCV)
            .performClick()

        // Wait for 1 second
        Thread.sleep(1000)

        // Assert that the progress bar animation is no longer displayed
        composeTestRule.onNodeWithTag(Constant.animationTag)
            .assertDoesNotExist()
    }


    private fun hasProgressBar(): SemanticsMatcher {
        return SemanticsMatcher("Has LinearProgressIndicator") {
            it.config.getOrNull(SemanticsProperties.ProgressBarRangeInfo) != null
        }
    }

}