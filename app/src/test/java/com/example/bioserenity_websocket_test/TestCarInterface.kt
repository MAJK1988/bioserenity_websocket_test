package com.example.bioserenity_websocket_test

import com.example.bioserenity_websocket_test.data.utils.Constant
import com.example.bioserenity_websocket_test.data.websockt.ClientSocket
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.net.URI
/**
 * Uses MockitoJUnitRunner to test interactions between ClientSocket and CarSocketInterface implementations.*/
@RunWith(MockitoJUnitRunner::class)
class TestCarInterface{


    @Mock
    lateinit var carOnMessageInterface: CarSocketInterface // Mock interface for handling car messages
    lateinit var socket: ClientSocket // Client socket instance to be tested

    @Before
    fun setup() {
        socket = ClientSocket(URI(Constant.url), true) // Initialize the client socket with a mock URI
        socket.initializeCarOnMessageInterface(carOnMessageInterface) // Inject the mock interface into the socket
    }

    @Test
    fun testInterfaceOnMessage() {
        socket.onMessage("null") // Simulate receiving a message on the socket
        Mockito.verify(carOnMessageInterface).onMessage("null")// Verify that the mock interface's onMessage method was called with "null"
    }

}