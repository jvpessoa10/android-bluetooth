package com.jvpass.bluetoothexample.bluetooth

import android.bluetooth.BluetoothDevice
import android.util.Log
import java.util.*

open class Device (
    private val bluetoothDevice: BluetoothDevice,
    private val uuid: UUID
) {

    private companion object {
        const val TAG = "Device"
    }

    private lateinit var connectionThread: BluetoothConnection

    fun connect() {
        connectionThread = BluetoothConnection(bluetoothDevice, uuid)
        connectionThread.start()
    }

    fun write(data: String) {
        if (!this::connectionThread.isInitialized) {
            Log.e(TAG, "Device not connected")
            return
        }
        connectionThread.write(data.toByteArray())
    }
}