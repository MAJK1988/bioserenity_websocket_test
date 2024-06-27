package com.example.bioserenity_websocket_test

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.bioserenity_websocket_test.data.utils.Constant
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * This class tests UI interactions and state updates related to WebSocket connection management in a Jetpack Compose application.*/
@RunWith(AndroidJUnit4::class)
class AutoConnectTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testOpenConnection() {
        // Ensure the switch button is displayed and click it
        composeTestRule.onNodeWithTag(Constant.switchTag)
            .assertIsDisplayed()
            .performClick()

        // Wait for 1 second
        Thread.sleep(1000)

        // Verify connection status change to open
        Assert.assertTrue(
            "open connection",
            composeTestRule.activity.connectionManager.isConnectS.value
        )
    }

    @Test
    fun testOpenConnectionValidationStatus() {
        // Ensure the switch button and its status text are displayed
        composeTestRule.onNodeWithTag(Constant.switchTag)
            .assertIsDisplayed()
        composeTestRule.onNodeWithTag(Constant.switchTagStatus)
            .assertIsDisplayed()

        // Click the switch button to connect
        composeTestRule.onNodeWithTag(Constant.switchTag)
            .performClick()

        // Wait for 1 second
        Thread.sleep(1000)

        // Verify connection status changes to open
        Assert.assertTrue(
            "open connection",
            composeTestRule.activity.connectionManager.isConnectS.value
        )

        // Assert that the switch status text shows "Auto Connect"
        composeTestRule.onNodeWithTag(Constant.switchTagStatus)
            .assertTextEquals(Constant.autoConnect)
    }


}