package com.example.bioserenity_websocket_test.car

import androidx.compose.runtime.MutableState
import com.example.bioserenity_websocket_test.connection.ManagerConnection
import com.example.bioserenity_websocket_test.message.MessageReceiver
import com.example.bioserenity_websocket_test.message.NameCar
import com.example.bioserenity_websocket_test.utils.TestLog

import com.example.bioserenity_websocket_test.message.getCar
import com.example.bioserenity_websocket_test.utils.Constant
import com.example.bioserenity_websocket_test.websockt.ClientSocket

/**
 * ManagerCar is responsible for managing a collection of car objects (Car). It uses a ClientSocket to receive messages and
 * updates the list of cars when new messages are received. The class implements the CarSocketInterface, which requires
 * defining the onMessage method for handling incoming messages.
 * */
class ManagerCarInfo(
    var cars: MutableState<Array<Car>>,
    var socket: ClientSocket,
    var forTest: Boolean,
    var managerConnection: ManagerConnection
) : CarSocketInterface {
    val tag: String = "ManagerCar"
    lateinit var usedSocket: ClientSocket


    override fun onMessage(message: String?) {
        TestLog.i(tag, "onMessage: ${message!!}", forTest)
        addCarToList(message)
    }

    init {
        initializeUsedSocket(socket)
    }

    fun initializeUsedSocket(s: ClientSocket) {
        usedSocket = s
        usedSocket.initializeCarOnMessageInterface(this)
    }

    @Synchronized
    fun addCarToList(message: String) {
        val c: Any? = getCar(message)
        TestLog.i(tag, "c: ${c}", forTest)
        if (c != null) {
            val carsNew = mutableListOf<Car>()
            if (c is List<*>) {
                for (i in c) {
                    if (i is Map<*, *>) {
                        carsNew.add(convertToCar(i as Map<String, Any>)!!)
                    }
                }
            } else {
                carsNew.add(convertToCar(c as Map<String, Any>)!!)
            }
            for (i in carsNew) {
                val index: List<Int> = findCarIndicesByCV(cars.value.toList(), i.cv)
                if (index.isEmpty()) {
                    cars.value += i
                } else {
                    if (index[0] < cars.value.size && i.currentSpeed != 0.0) {
                        val currentCars = cars.value.toMutableList()
                        currentCars[index[0]] = i
                        cars.value = currentCars.toTypedArray()
                    }
                }
            }
            TestLog.i(tag, "Cars length= ${cars.value.size}", forTest)
        } else {
            TestLog.i(tag, "Error in read message.!", forTest)
        }
    }

    fun getCurrentSpeed(name: String, isForClose: Boolean) {
        managerConnection.sendMessage(
            MessageReceiver(
                type = if (!isForClose) Constant.start else Constant.stop,
                userToken = Constant.userToken,
                payload = if (!isForClose) NameCar(name = name) else ""
            ).toJson()
        )
    }


}