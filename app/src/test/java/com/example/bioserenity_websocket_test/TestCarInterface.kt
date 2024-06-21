package com.example.bioserenity_websocket_test

import com.example.bioserenity_websocket_test.utils.Constant
import com.example.bioserenity_websocket_test.websockt.ClientSocket
import com.example.bioserenity_websocket_test.car.CarSocketInterface
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
    lateinit var carOnMessageInterface: CarSocketInterface
    lateinit var socket: ClientSocket

    @Before
    fun setup(){
        socket= ClientSocket(URI(Constant.url),true)
        socket.initializeCarOnMessageInterface(carOnMessageInterface)
    }


    @Test
    fun testInterFaceOnMessage(){
        socket.onMessage("null")
        Mockito.verify(carOnMessageInterface).onMessage("null")
    }

}