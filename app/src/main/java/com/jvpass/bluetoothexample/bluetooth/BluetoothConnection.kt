package com.jvpass.bluetoothexample.bluetooth

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.util.Log
import java.io.IOException
import java.util.UUID


class BluetoothConnection(
    private val device: BluetoothDevice,
    private val deviceUUID: UUID
): Thread() {

    private companion object {
        const val TAG = "BluetoothConnection"
    }

    private val mmSocket: BluetoothSocket? by lazy(LazyThreadSafetyMode.NONE) {
        device.createRfcommSocketToServiceRecord(deviceUUID)
    }

    private val outStream = mmSocket?.outputStream

    override fun run() {
        try {
            device.createBond()
            mmSocket?.connect()
        } catch (e: IOException) {
            Log.e(TAG, "Error connecting through socket", e)
            return
        }

        Log.d(TAG, "Connected to ${device.name}")
    }

    fun write(bytes: ByteArray) {
        Log.d(TAG, bytes.toString())
        try {
            outStream?.write(bytes)
        } catch (e: IOException) {
            Log.e(TAG, "Error occurred when sending data", e)
        }
    }
}