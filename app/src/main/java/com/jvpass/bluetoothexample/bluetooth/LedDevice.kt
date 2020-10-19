package com.jvpass.bluetoothexample.bluetooth

import android.bluetooth.BluetoothDevice
import android.util.Log
import java.util.UUID

class LedDevice(
    bluetoothDevice: BluetoothDevice
): Device(bluetoothDevice, H5_02_UUID) {

    private companion object {
        const val TAG = "LedDevice"
        const val LED_ON = "1"
        const val LED_OFF = "0"

        val H5_02_UUID: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
    }

    fun turnLedOn() {
        write(LED_ON)
    }

    fun turnLedOff() {
        write(LED_OFF)
    }
}