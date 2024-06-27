package com.example.bioserenity_websocket_test.data.model

import com.google.gson.Gson
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

@JsonClass(generateAdapter = true)
data class MessageReceiver(
    @Json(name = "type") val type: String,
    @Json(name = "userToken") val userToken: Int,
    @Json(name = "payload") val payload: Any=""

){
    fun toJson():String{
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        val jsonAdapter = moshi.adapter(MessageReceiver::class.java)
        return  jsonAdapter.toJson(this)
    }

}
fun getCar(json:String)
    : Any? {
        return try {
            val gson = Gson()
            gson.fromJson(json, MessageReceiver::class.java).payload
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    return null
}
