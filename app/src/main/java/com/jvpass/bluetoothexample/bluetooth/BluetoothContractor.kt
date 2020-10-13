package com.jvpass.bluetoothexample.bluetooth

import android.bluetooth.BluetoothDevice
import android.companion.CompanionDeviceManager

class BluetoothContractor {
    interface BluetoothPresenter {
        fun onCreate()
        fun onBluetoothEnabled()
        fun onDeviceSelected(device: BluetoothDevice)
        fun onClickLedTurnOnBtn()
        fun onClickLedTurnOffBtn()
    }

    interface BluetoothView {
        val deviceFoundCallback: CompanionDeviceManager.Callback
        fun displayBluetoothUnavailable()
        fun requestEnableBluetooth()
        fun close()
    }
}