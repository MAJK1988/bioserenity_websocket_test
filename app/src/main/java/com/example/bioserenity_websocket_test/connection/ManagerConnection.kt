package com.example.bioserenity_websocket_test.connection

import android.os.Handler
import android.os.Looper
import androidx.compose.runtime.MutableState
import com.example.bioserenity_websocket_test.message.MessageReceiver
import com.example.bioserenity_websocket_test.utils.TestLog
import com.example.bioserenity_websocket_test.utils.Constant
import com.example.bioserenity_websocket_test.websockt.ClientSocket
/**
 * ManagerConnection is a Kotlin class that manages a WebSocket connection, maintaining the connection status and handling
 * automatic reconnections. It initializes and configures the WebSocket, provides methods to connect and disconnect,
 * and handles connection events such as open, close, and error. The class also includes functionality to send messages
 * through the WebSocket and logs connection activities for debugging purposes.**/
class ManagerConnection(
    var socket: ClientSocket,
    var status: MutableState<String>,
    var isConnect: MutableState<Boolean>,
    private val callback: () -> Unit,
    var isAuto: MutableState<Boolean>, var forTest:Boolean
) : ConnectionInterface {
    val tag: String = "ManagerConnection"
    lateinit var usedSocket: ClientSocket

    init {
        initializeUsedSocket(socket)
    }

    fun initializeUsedSocket(s: ClientSocket) {
        usedSocket = s
        usedSocket.initializeConnectionInterface(this)
    }

    private val connectObject = Object()


    fun connect(): Boolean {
        synchronized(connectObject)
        {
            if (::usedSocket.isInitialized && !isConnect.value) {
                try {
                    status.value=Constant.wait
                    usedSocket.connectBlocking()
                    return true
                } catch (ex: Exception) {
                    TestLog.e(tag, "Error on connect: ${ex.printStackTrace()}",forTest)
                }
            }
            return false
        }
    }

    fun closeConnection(): Boolean {
        TestLog.i(tag = tag, message = "Try to close connection",forTest)

        if (::usedSocket.isInitialized && !usedSocket.isClosed) {
            try {
                status.value=Constant.wait
                sendMessage( MessageReceiver(type=Constant.stop,userToken= 42 ).toJson())
                Thread.sleep(10)
                usedSocket.close()
            } catch (ex: Exception) {
                TestLog.e(tag, "Error on on close: ${ex.printStackTrace()}",forTest)
                return false
            }
        } else {
            TestLog.i(
                tag = tag,
                message = "Error: init: ${::usedSocket.isInitialized}, is open: ${!usedSocket.isClosed}",forTest
            )
        }
        return true
    }



    override fun onConnect() {
        changeConnectionStatus(
            message = Constant.connect,
            status = true
        )
        sendMessage(MessageReceiver(type=Constant.info,userToken= 42 ).toJson())
    }

    override fun onClose() {
        changeConnectionStatus(
            message = Constant.closeConnect,
            status = false
        )
        callback()
        if (isAuto.value) {
            autoConnection()
        }
    }

    override fun onError(error: String) {
        changeConnectionStatus(
            message = Constant.error,
            status = !usedSocket.isClosed
        )
        if (isAuto.value && ::usedSocket.isInitialized && usedSocket.isClosed) {
            autoConnection()
        }
    }


    @Synchronized
    fun changeConnectionStatus(message: String, status: Boolean): Boolean {
        if (status) {
            open(message)
        } else {
            close(message)
        }
        return true
    }

    private fun close(message: String) {
        status.value = message
        isConnect.value = false
        TestLog.i(tag, "close connection.!",forTest)
    }

    private fun open(message: String) {
        status.value = message
        isConnect.value = true
        TestLog.i(tag, "Open connection.!",forTest)
    }

    @Synchronized
    fun sendMessage(message: String): Boolean {
        if (::usedSocket.isInitialized && !usedSocket.isClosed && usedSocket.isOpen) {
            try {
                usedSocket.send(message)
                TestLog.i(tag, "Message sent!., message: $message",forTest)
                return true
            } catch (e: Exception) {
                TestLog.e(tag, e.printStackTrace().toString(),forTest)
                e.printStackTrace()
            }
        }

        return false
    }
    fun autoConnection() {

        Handler(Looper.getMainLooper()).postDelayed({
            TestLog.i(tag=tag, message ="autoConnection launch connect function.!" ,forTest)
            connect()
             }, 5000)

        TestLog.i(tag = tag, message = "autoConnection timer is activated",forTest)
    }
}