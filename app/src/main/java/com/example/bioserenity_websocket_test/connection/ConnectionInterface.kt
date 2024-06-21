package com.example.bioserenity_websocket_test.connection

interface ConnectionInterface {
    fun onConnect()
    fun onClose()
    fun onError(error:String)
}