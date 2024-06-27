package com.example.bioserenity_websocket_test


import com.example.bioserenity_websocket_test.data.model.Car
import com.example.bioserenity_websocket_test.data.model.NameCar
import com.example.bioserenity_websocket_test.data.model.convertToCar
import com.example.bioserenity_websocket_test.data.model.convertToNameCar
import com.example.bioserenity_websocket_test.data.model.MessageReceiver
import com.example.bioserenity_websocket_test.data.model.getCar
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
        // Create a sample Car object
        val car = Car(
            name = "catTest",
            brand = "M",
            speedMax = 1.0,
            currentSpeed = 1.0,
            cv = 1
        )

        // Create a MessageReceiver with the Car payload and convert to JSON
        val messageReceiver = MessageReceiver(
            type = "start",
            userToken = 42,
            payload = car
        )
        val json = messageReceiver.toJson()

        // Convert JSON back to Car object and validate
        val result = convertToCar(map = getCar(json) as Map<String, Any>)
        assertNotNull(result)
        assertEquals(car.name, result?.name)
    }

    @Test
    fun testGetCarValidateJsonListCar() {
        // Create a sample Car object in a list
        val car = Car(
            name = "catTest",
            brand = "M",
            speedMax = 1.0,
            currentSpeed = 1.0,
            cv = 1
        )

        // Create a MessageReceiver with the list of Cars payload and convert to JSON
        val messageReceiver = MessageReceiver(
            type = "start",
            userToken = 42,
            payload = listOf(car)
        )
        val json = messageReceiver.toJson()

        // Convert JSON list back to Car object and validate
        val result = convertToCar(map = (getCar(json) as List<*>)[0] as Map<String, Any>)
        assertNotNull(result)
        assertEquals(car.name, result?.name)
    }

    @Test
    fun testGetCarValidateJsonCarName() {
        // Create a sample NameCar object
        val car = NameCar(name = "test")

        // Create a MessageReceiver with the NameCar payload and convert to JSON
        val messageReceiver = MessageReceiver(
            type = "start",
            userToken = 42,
            payload = car
        )
        val json = messageReceiver.toJson()

        // Convert JSON back to NameCar object and validate
        val result = convertToNameCar(map = getCar(json) as Map<String, Any>)
        assertNotNull(result)
        assertEquals(car.name, result?.name)
    }

    @Test
    fun testGetCarInvalidJson() {
        // Provide an invalid JSON string
        val json = "{invalid json}"

        // Attempt to parse invalid JSON
        val result = getCar(json)

        // Ensure result is null for invalid JSON
        assertNull(result)
    }

}



