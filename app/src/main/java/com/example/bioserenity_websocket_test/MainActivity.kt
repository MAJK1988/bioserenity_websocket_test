package com.example.bioserenity_websocket_test

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.bioserenity_websocket_test.data.repository.ManagerCarInfo
import com.example.bioserenity_websocket_test.data.repository.ManagerConnection
import com.example.bioserenity_websocket_test.data.utils.Utils
import com.example.bioserenity_websocket_test.data.model.Car
import com.example.bioserenity_websocket_test.data.utils.Constant
import com.example.bioserenity_websocket_test.ui.theme.Bioserenity_websocket_testTheme
import com.example.bioserenity_websocket_test.data.utils.TestLog
import com.example.bioserenity_websocket_test.view.MainView
import com.example.bioserenity_websocket_test.data.websockt.ClientSocket
import java.net.URI

class MainActivity : ComponentActivity() {
    lateinit var managerCar: ManagerCarInfo
    lateinit var connectionManager: ManagerConnection
    lateinit var socketMut: ClientSocket
    val forTest = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Utils.isValidUrl(Constant.url)) {

            socketMut = ClientSocket(URI(Constant.url), forTest)
            connectionManager = ManagerConnection(
                socket = socketMut,

                callback = { reCreateSocket() }, forTest = forTest
            )
            managerCar = ManagerCarInfo(
                socket = socketMut,
                forTest = forTest,
                managerConnection = connectionManager
            )

        } else {
            TestLog.i(tag = "MainActivityTag", message = "url error.!", forTest)
        }
    }

    override fun onStart() {
        super.onStart()

        setContent {
            if(connectionManager.isConnectS.value!!){
                TestLog.i(tag= "onStartLog", message = "Is open", forTest=false)
            }else{ TestLog.i(tag= "onStartLog", message = "Is close", forTest=false)}

            Bioserenity_websocket_testTheme(darkTheme = false) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {

                    if (::socketMut.isInitialized) {
                        MainView().MainScreen(
                            manager = managerCar,
                            managerConnection = connectionManager
                        )
                    }
                }
            }
        }
    }

    fun reCreateSocket() {
        socketMut = ClientSocket(URI(Constant.url), forTest)
        managerCar.initializeUsedSocket(socketMut)
        connectionManager.initializeUsedSocket(socketMut)
    }

    @SuppressLint("UnrememberedMutableState")
    @Preview(showBackground = true)
    @Composable
    fun PreviewCarList() {
        val car = listOf(
            Car("Aston Martin", Constant.testCar, 180.0, 163, 100.0),
            Car("Peugeot", "307", 210.0, 180, 0.0),
            Car("Ferrari", "458", 310.0, 450, 0.0)
        )
        val cars: MutableState<Array<Car>> = mutableStateOf(car.toTypedArray())

        val socketManager: ManagerConnection =
            ManagerConnection(
                socket = ClientSocket(URI(""), false),
                callback = {},
                forTest = false,
            )
        val managerCar: ManagerCarInfo =
            ManagerCarInfo(
                socket = ClientSocket(URI(""), false),
                false,
                managerConnection = socketManager
            )

        MainView().MainScreen(manager = managerCar, managerConnection = socketManager)
    }
}



