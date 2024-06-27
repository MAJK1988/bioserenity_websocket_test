package com.example.bioserenity_websocket_test.data.repository

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.bioserenity_websocket_test.data.model.Car
import com.example.bioserenity_websocket_test.data.model.NameCar
import com.example.bioserenity_websocket_test.data.model.convertToCar
import com.example.bioserenity_websocket_test.data.model.findCarIndicesByCV
import com.example.bioserenity_websocket_test.data.model.MessageReceiver
import com.example.bioserenity_websocket_test.data.utils.TestLog

import com.example.bioserenity_websocket_test.data.model.getCar
import com.example.bioserenity_websocket_test.data.utils.Constant
import com.example.bioserenity_websocket_test.data.websockt.ClientSocket
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * ManagerCar is responsible for managing a collection of car objects (Car). It uses a ClientSocket to receive messages and
 * updates the list of cars when new messages are received. The class implements the CarSocketInterface, which requires
 * defining the onMessage method for handling incoming messages.
 * */
class ManagerCarInfo(

    var socket: ClientSocket,
    var forTest: Boolean,
    var managerConnection: ManagerConnection
)  {
    val tag: String = "ManagerCar"
    lateinit var usedSocket: ClientSocket
    val carsT : MutableState<Array<Car>> = mutableStateOf(arrayOf())


    init {
        initializeUsedSocket(socket)
    }

    fun initializeUsedSocket(s: ClientSocket) {
        usedSocket = s

        CoroutineScope(Dispatchers.Main).launch {
            usedSocket.messages.collect { message ->
                addCarToList(message)
            }
        }
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
                val index: List<Int> = findCarIndicesByCV(carsT.value.toList(), i.cv)
                if (index.isEmpty()) {
                    carsT.value += i
                } else {
                    if (index[0] < carsT.value.size && i.currentSpeed != 0.0) {
                        val currentCars = carsT.value.toMutableList()
                        currentCars[index[0]] = i
                        carsT.value = currentCars.toTypedArray()
                    }
                }
            }
            TestLog.i(tag, "Cars length= ${carsT.value.size}", forTest)
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