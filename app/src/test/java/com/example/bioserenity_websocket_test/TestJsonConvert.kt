package com.example.bioserenity_websocket_test


import com.example.bioserenity_websocket_test.car.Car
import com.example.bioserenity_websocket_test.car.convertToCar
import com.example.bioserenity_websocket_test.car.convertToNameCar
import com.example.bioserenity_websocket_test.message.MessageReceiver
import com.example.bioserenity_websocket_test.message.NameCar
import com.example.bioserenity_websocket_test.message.getCar
import org.junit.Test
import org.junit.Assert.*


/**
These tests verify the correctness of the getCar function in two scenarios: valid JSON parsing and handling of invalid JSON.
They ensure that the function correctly converts valid JSON representations of Car objects into MessageReceiver instances
and gracefully handles invalid JSON input by returning null. This helps maintain robustness and reliability in handling
JSON data within your application.
 */
class TestJsonConvert {
    @Test
    fun testGetCarValidateJsonCar() {
        val car = Car(
            name = "catTest", brand = "M",
            speedMax = 1.0, currentSpeed = 1.0, cv = 1
        )
        val messageReceiver = MessageReceiver(type = "start", userToken = 42, payload = car)
        val json = messageReceiver.toJson()

        val result =convertToCar(map= getCar(json) as Map<String, Any>)
        assertNotNull(result)
        assertEquals(car.name, result?.name)
    }
    @Test
    fun testGetCarValidateJsonListCar() {
        val car = Car(
            name = "catTest", brand = "M",
            speedMax = 1.0, currentSpeed = 1.0, cv = 1
        )
        val messageReceiver = MessageReceiver(type = "start", userToken = 42, payload = listOf(car))
        val json = messageReceiver.toJson()

        val result =convertToCar(map= (getCar(json) as List<*>)[0] as Map<String, Any>)
        assertNotNull(result)
        assertEquals(car.name, result?.name)
    }

    @Test
    fun testGetCarValidateJsonCarName() {
        val car =  NameCar(name = "test")
        val messageReceiver = MessageReceiver(type = "start", userToken = 42, payload =car )
        val json = messageReceiver.toJson()

        val result =convertToNameCar(map= (getCar(json))as Map<String, Any>)
        assertNotNull(result)
        assertEquals(car.name, result?.name)
    }

    @Test
    fun testGetCarInvalidJson() {
        val json = "{invalid json}"
        val result = getCar(json)
        assertNull(result)
    }

}


