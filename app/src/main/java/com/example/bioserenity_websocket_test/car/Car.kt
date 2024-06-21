package com.example.bioserenity_websocket_test.car

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlin.math.roundToInt

@JsonClass(generateAdapter = true)
data class Car(
    @Json(name = "brand") val brand: String,
    @Json(name = "name") val name: String,
    @Json(name = "speedMax") val speedMax: Double,
    @Json(name = "cv") val cv: Int,
    @Json(name = "currentSpeed") var currentSpeed: Double,
)

fun findCarIndicesByCV(cars: List<Car>, cv: Int): List<Int> {
    val indices = mutableListOf<Int>()
    cars.forEachIndexed { index, car ->
        if (car.cv == cv) {
            indices.add(index)
        }
    }
    return indices
}

fun convertToCar(map: Map<String, Any>): Car? {
    // Extract values from the map
    val brand = map["brand"] as? String ?: return null
    val name = map["name"] as? String ?: return null
    val speedMax = (map["speedMax"] as? Double)?: return null
    val cv = (map["cv"] as? Double)?.roundToInt() ?: return null
    val currentSpeed = (map["currentSpeed"] as? Double)?: return null

    // Create and return a Car object
    return Car(brand, name, speedMax, cv, currentSpeed)
}
@JsonClass(generateAdapter = true)
data class NameCar(
    @Json(name = "name") val name: String
)
fun convertToNameCar(map: Map<String, Any>): NameCar? {
    // Extract values from the map
    val name = map["name"] as? String ?: return null
    // Create and return a NameCar object
    return NameCar(name)
}
