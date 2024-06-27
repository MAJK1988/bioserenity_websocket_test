package com.example.bioserenity_websocket_test.data.websockt


import com.example.bioserenity_websocket_test.data.utils.TestLog
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.lang.Exception
import java.net.URI
/**
 * This class ClientSocket extends WebSocketClient and is designed to manage a WebSocket connection.
 * It has two main interfaces: ConnectionInterface for handling connection events and CarSocketInterface for handling messages.
 * It includes methods to initialize these interfaces and overrides WebSocketClient methods (onOpen, onMessage, onClose, onError)
 * to log events and delegate actions to the interfaces if they are initialized. The forTest flag is used for logging purposes.*/
class ClientSocket(serverUri: URI, var forTest:Boolean) : WebSocketClient(serverUri) {

    private val _messages = MutableStateFlow("")
    val messages: StateFlow<String> get() = _messages

    private val _status = MutableStateFlow(0)
    val status: StateFlow<Int> get() = _status

    var tag: String= "ClientSocket"

    override fun onOpen(handshakedata: ServerHandshake?) {
         TestLog.i(tag=tag, message = "onOpen.!!",forTest)
        _status.value=1
    }

    override fun onMessage(message: String?) {
        TestLog.i(tag=tag, message = "onMessage: $message",forTest)
        if(message!=null) {
            _messages.value =message
        }
    }

    override fun onClose(code: Int, reason: String?, remote: Boolean) {
        TestLog.i(tag=tag, message = "onClose.!!",forTest)
        _status.value=0
    }

    override fun onError(ex: Exception?) {
        TestLog.i(tag=tag, message = "onError: ${ex!!.printStackTrace()}",forTest)
        _status.value=-1
    }
}
