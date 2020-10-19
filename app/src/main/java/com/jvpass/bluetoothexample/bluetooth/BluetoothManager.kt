package com.jvpass.bluetoothexample.bluetooth

import android.bluetooth.BluetoothAdapter
import android.companion.AssociationRequest
import android.companion.BluetoothDeviceFilter
import android.companion.CompanionDeviceManager
import android.content.Context
import android.util.Log

class BluetoothManager(context: Context) {

    companion object {
        private const val TAG = "BluetoothManager"

        const val ACTION_ENABLE_BLUETOOTH = BluetoothAdapter.ACTION_REQUEST_ENABLE
    }

    private var bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()

    private val deviceManager: CompanionDeviceManager by lazy(LazyThreadSafetyMode.NONE) {
        context.getSystemService(CompanionDeviceManager::class.java)
    }

    fun isBluetoothAvailable() = bluetoothAdapter != null

    fun isBluetoothEnabled() = bluetoothAdapter?.isEnabled ?: false

    fun startScan(deviceFoundCallback: CompanionDeviceManager.Callback) {
        Log.d(TAG, "startScan")

        val deviceFilter = BluetoothDeviceFilter.Builder().build()

        val pairingRequest: AssociationRequest = AssociationRequest.Builder()
                .addDeviceFilter(deviceFilter)
                .build()
        
        deviceManager.associate(pairingRequest, deviceFoundCallback, null)
    }

}