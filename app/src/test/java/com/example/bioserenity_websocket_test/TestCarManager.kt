package com.example.bioserenity_websocket_test

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.bioserenity_websocket_test.car.ManagerCarInfo
import com.example.bioserenity_websocket_test.car.Car
import com.example.bioserenity_websocket_test.connection.ManagerConnection
import com.example.bioserenity_websocket_test.message.MessageReceiver
import com.example.bioserenity_websocket_test.utils.Constant
import com.example.bioserenity_websocket_test.websockt.ClientSocket
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import java.net.URI

/**
 * This class uses MockitoJUnitRunner to test the functionality of ManagerCar when interacting with a ClientSocket
 * and updating a list of cars.
 * This test ensures that the ManagerCar correctly processes messages received through the ClientSocket and updates its
 * internal state (cars) accordingly.*/
@RunWith(MockitoJUnitRunner::class)
class TestCarManager {
    @Mock
    lateinit var manager: ManagerCarInfo
    lateinit var managerConnection: ManagerConnection
    lateinit var cars: MutableState<Array<Car>>
    lateinit var clientSocket: ClientSocket


    @Before
    fun setup() {
        cars = mutableStateOf(arrayOf())
        clientSocket = ClientSocket(URI(Constant.url), true)
        managerConnection = ManagerConnection(
            socket = clientSocket,
            status = mutableStateOf(""),
            isConnect = mutableStateOf(false), callback = {},
            isAuto = mutableStateOf(false),
            forTest = true
        )
        manager = ManagerCarInfo(
            cars = cars,
            clientSocket,
            forTest = true,
            managerConnection = managerConnection
        )
    }

    @Test
    fun testOnMessage() {
        val testMessage = MessageReceiver(
            type = "start",
            userToken = 42,
            payload = listOf(
                Car(
                    name = "name",
                    brand = "brand",
                    cv = 0,
                    speedMax = 1.0,
                    currentSpeed = 1.0
                )
            )
        ).toJson()
        clientSocket.onMessage(testMessage)
        manager.addCarToList(testMessage)
        assert(cars.value.isNotEmpty())
        assert(cars.value[0].name == "name")
    }

}