package com.jvpass.bluetoothexample.ui

import android.bluetooth.BluetoothDevice
import com.jvpass.bluetoothexample.bluetooth.BluetoothManager
import com.jvpass.bluetoothexample.bluetooth.LedDevice

class MainActivityPresenterImpl (
    private val view: MainActivityContractor.View,
    private val bluetoothManager: BluetoothManager
): MainActivityContractor.Presenter {

    private var ledDevice: LedDevice? = null

    override fun onCreate() {
        if (!bluetoothManager.isBluetoothAvailable()) {
            view.displayBluetoothUnavailable()
            view.close()
            return
        }

        if (!bluetoothManager.isBluetoothEnabled()) {
            view.requestEnableBluetooth()
        } else {
            bluetoothManager.startScan(view.deviceFoundCallback)
        }
    }

    override fun onBluetoothEnabled() {
        bluetoothManager.startScan(view.deviceFoundCallback)
    }

    override fun onDeviceSelected(device: BluetoothDevice) {
        ledDevice = LedDevice(device)
    }

    override fun onClickLedTurnOnBtn() {
        ledDevice?.turnLedOn()
    }

    override fun onClickLedTurnOffBtn() {
        ledDevice?.turnLedOff()
    }
}