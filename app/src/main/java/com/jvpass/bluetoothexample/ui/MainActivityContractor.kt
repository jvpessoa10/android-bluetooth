package com.jvpass.bluetoothexample.ui

import android.bluetooth.BluetoothDevice
import android.companion.CompanionDeviceManager

class MainActivityContractor {
    interface Presenter {
        fun onCreate()
        fun onBluetoothEnabled()
        fun onDeviceSelected(device: BluetoothDevice)
        fun onClickLedTurnOnBtn()
        fun onClickLedTurnOffBtn()
    }

    interface View {
        val deviceFoundCallback: CompanionDeviceManager.Callback
        fun displayBluetoothUnavailable()
        fun requestEnableBluetooth()
        fun close()
    }
}