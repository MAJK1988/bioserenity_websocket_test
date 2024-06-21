package com.example.bioserenity_websocket_test.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.bioserenity_websocket_test.car.CarView
import com.example.bioserenity_websocket_test.car.ManagerCarInfo
import com.example.bioserenity_websocket_test.connection.ConnectionView
import com.example.bioserenity_websocket_test.connection.ManagerConnection
import com.example.bioserenity_websocket_test.utils.Constant
import com.example.bioserenity_websocket_test.utils.TestLog
/**
 * The MainView class defines a composable function MainScreen that uses Jetpack Compose's Scaffold to structure a UI with a top
 * app bar from ConnectionView, a bottom bar, and a central content area. It conditionally displays a list of cars using CarView
 * or a circular progress bar based on the connection status. The CircularProgressBar function provides a centered
 * CircularProgressIndicator component for indicating loading state in the UI.*/

class MainView {
    val tag: String = "MainViewTag"

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MainScreen(
        manager: ManagerCarInfo,
        managerConnection: ManagerConnection
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { ConnectionView().TopBarView(managerConnection = managerConnection) },
                    // Additional configurations
                )
            },
            bottomBar = {
                ConnectionView().BottomBarView(managerConnection = managerConnection)
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .padding(innerPadding),

                ) {
                if (!managerConnection.status.value.equals(Constant.wait)) {
                    CarView().CarList(
                        managerCar = manager
                    )
                }
                else {
                    TestLog.i(
                        tag = tag,
                        message = "Show CircularProgressBar.!",
                        managerConnection.forTest
                    )
                    CircularProgressBar()
                }
            }

        }
    }


    @Composable
    fun CircularProgressBar() {
        // State to control the visibility of the progress indicator
        // var isLoading by remember { mutableStateOf(true) }

        // Layout to center the CircularProgressIndicator
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {

            CircularProgressIndicator(
                color = Color.White,
                strokeWidth = 4.dp
            )

        }
    }
}