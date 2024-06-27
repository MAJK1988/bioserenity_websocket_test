package com.example.bioserenity_websocket_test.data.utils

import android.util.Log

 class TestLog {
    companion object {
        fun e(tag: String, message: String, forTest:Boolean) {
            if(!forTest)
                Log.e(tag, message)
        }
        fun i(tag:String,message:String, forTest:Boolean){
            if(!forTest)
                Log.i(tag, message)
        }
    }
}