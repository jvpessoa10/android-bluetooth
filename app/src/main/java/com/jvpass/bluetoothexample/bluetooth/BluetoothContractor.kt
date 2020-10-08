package com.jvpass.bluetoothexample.bluetooth

import android.companion.CompanionDeviceManager

class BluetoothContractor {
    interface BluetoothPresenter {
        fun onCreate()
        fun onBluetoothEnabled()
        fun onBluetoothPermissionGranted()
    }

    interface BluetoothView {
        val deviceFoundCallback: CompanionDeviceManager.Callback
        fun displayBluetoothUnavailable()
        fun requestEnableBluetooth()
        fun requestBluetoothPermissions()
        fun close()
    }
}