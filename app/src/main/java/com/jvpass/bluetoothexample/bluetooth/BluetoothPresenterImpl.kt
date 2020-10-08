package com.jvpass.bluetoothexample.bluetooth

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
        }
    }

    override fun onBluetoothEnabled() {
        bluetoothView.requestBluetoothPermissions()
    }

    override fun onBluetoothPermissionGranted() {
        bluetoothManager.startScan(bluetoothView.deviceFoundCallback)
    }
}