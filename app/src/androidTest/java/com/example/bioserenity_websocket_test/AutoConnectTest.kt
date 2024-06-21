package com.example.bioserenity_websocket_test

import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.semantics.getOrNull
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.bioserenity_websocket_test.utils.Constant
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
    fun  testOpenConnection(){
        composeTestRule.onNodeWithTag(
            Constant.switchTag
        ).assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            Constant.switchTag
        ).performClick()
        Thread.sleep(1000)
        Assert.assertTrue("open connection", composeTestRule.activity.isConnect.value)
    }

    @Test
    fun  testOpenConnectionValidationStatus(){
        composeTestRule.onNodeWithTag(
            Constant.switchTag
        ).assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            Constant.switchTagStatus
        ).assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            Constant.switchTag
        ).performClick()
        Thread.sleep(1000)
        Assert.assertTrue("open connection", composeTestRule.activity.isConnect.value)

        composeTestRule.onNodeWithTag(
            Constant.switchTagStatus
        ).assertTextEquals(Constant.autoConnect)
    }


}