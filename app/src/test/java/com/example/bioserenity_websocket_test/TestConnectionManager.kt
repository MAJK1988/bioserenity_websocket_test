package com.example.bioserenity_websocket_test

import android.os.Handler
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.bioserenity_websocket_test.data.repository.ManagerConnection
import com.example.bioserenity_websocket_test.data.utils.Constant
import com.example.bioserenity_websocket_test.data.websockt.ClientSocket
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner
import java.net.URI
/**
 * This class uses MockitoJUnitRunner to test the behavior of ManagerConnection in response to events from ClientSocket.
 * These tests validate that the ManagerConnection properly handles socket events (onOpen and onClose) and updates its
 * internal state (isConnect and status) accordingly. The use of mocks and assertions ensures the expected behavior between components and validates the correctness of the ManagerConnection implementation.*/
@RunWith(MockitoJUnitRunner::class)
class TestConnectionManager {
    @Mock
    lateinit var manager: ManagerConnection
    lateinit var isConnect: MutableState<Boolean>
    lateinit var isAuto: MutableState<Boolean>
    lateinit var status: MutableState<String>
    lateinit var clientSocket: ClientSocket
    private lateinit var handler: Handler

    @Before
    fun setup() {
        // Initialize MutableState variables and ClientSocket
        isConnect = mutableStateOf(false)
        isAuto = mutableStateOf(false)
        status = mutableStateOf("Try to connect")
        clientSocket = ClientSocket(URI(Constant.url), true)
        handler = mock(Handler::class.java)

        // Initialize ManagerConnection with mocked dependencies
        manager = ManagerConnection(
            socket = clientSocket,
            callback = {},
            forTest = true
        )
    }

    @Test
    fun testOnOpen() {
        // Simulate onOpen() method call on clientSocket
        clientSocket.onOpen(null)

        // Assert that isConnect.value should be true after onOpen()
        assert(isConnect.value)

        // Assert that status.value should be "Constant.connect" after onOpen()
        assert(status.value.equals(Constant.connect))
    }

    @Test
    fun testOnClose() {
        // Simulate onClose() method call on clientSocket
        clientSocket.onClose(1, null, true)

        // Assert that isConnect.value should be false after onClose()
        assert(!isConnect.value)

        // Assert that status.value should be "Constant.closeConnect" after onClose()
        assert(status.value.equals(Constant.closeConnect))
    }
}



