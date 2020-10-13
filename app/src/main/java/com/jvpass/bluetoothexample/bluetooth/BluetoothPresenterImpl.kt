package com.jvpass.bluetoothexample.bluetooth

import android.bluetooth.BluetoothDevice
import com.jvpass.bluetoothexample.LedDevice

class BluetoothPresenterImpl (
    private val bluetoothView: BluetoothContractor.BluetoothView,
    private val bluetoothManager: BluetoothManager
): BluetoothContractor.BluetoothPresenter {

    override fun onCreate() {
        if (!bluetoothManager.isBluetoothAvailable()) {
            bluetoothView.displayBluetoothUnavailable()
            bluetoothView.close()
            return
        }

        if (!bluetoothManager.isBluetoothEnabled()) {
            bluetoothView.requestEnableBluetooth()
        } else {
            bluetoothManager.startScan(bluetoothView.deviceFoundCallback)
        }
    }

    override fun onBluetoothEnabled() {
        bluetoothManager.startScan(bluetoothView.deviceFoundCallback)
    }

    override fun onDeviceSelected(device: BluetoothDevice) {
        bluetoothManager.connect(device)
    }

    override fun onClickLedTurnOnBtn() {
        bluetoothManager.write(LedDevice.LED_ON)
    }

    override fun onClickLedTurnOffBtn() {
        bluetoothManager.write(LedDevice.LED_OFF)
    }
}