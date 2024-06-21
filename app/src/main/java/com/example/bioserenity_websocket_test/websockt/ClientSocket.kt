package com.example.bioserenity_websocket_test.websockt

import com.example.bioserenity_websocket_test.car.CarSocketInterface
import com.example.bioserenity_websocket_test.connection.ConnectionInterface
import com.example.bioserenity_websocket_test.utils.TestLog
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.lang.Exception
import java.net.URI

class ClientSocket(serverUri: URI, var forTest:Boolean) : WebSocketClient(serverUri) {

    var tag: String= "ClientSocket"
    lateinit var connectionInterface: ConnectionInterface
     fun initializeConnectionInterface(conn: ConnectionInterface){
         connectionInterface=conn
    }

    lateinit var  carMessage: CarSocketInterface

     fun initializeCarOnMessageInterface(car: CarSocketInterface){
        carMessage=car
    }

    override fun onOpen(handshakedata: ServerHandshake?) {
         TestLog.i(tag=tag, message = "onOpen.!!",forTest)
        if(::connectionInterface.isInitialized){
            connectionInterface.onConnect()
        }

    }

    override fun onMessage(message: String?) {
        TestLog.i(tag=tag, message = "onMessage: $message",forTest)
        if(::carMessage.isInitialized) {
            carMessage.onMessage(message)
        }else{
            TestLog.e(tag=tag, message= "Error.!",forTest)
        }
        //TestLog.i(tag,"message: $message")

    }

    override fun onClose(code: Int, reason: String?, remote: Boolean) {
        TestLog.i(tag=tag, message = "onClose.!!",forTest)
        if(::connectionInterface.isInitialized){
            connectionInterface.onClose()
        }
    }

    override fun onError(ex: Exception?) {
        TestLog.i(tag=tag, message = "onError: ${ex!!.printStackTrace()}",forTest)
        if(::connectionInterface.isInitialized){
            if (ex != null) {
                ex.message?.let { connectionInterface.onError(it) }
            }else{
                connectionInterface.onError("Error")
            }
        }

    }
}
