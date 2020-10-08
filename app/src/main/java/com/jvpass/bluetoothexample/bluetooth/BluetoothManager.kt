package com.jvpass.bluetoothexample.bluetooth

import android.bluetooth.BluetoothAdapter
import android.companion.AssociationRequest
import android.companion.BluetoothDeviceFilter
import android.companion.CompanionDeviceManager
import android.content.Context
import android.content.IntentSender
import android.media.AudioDeviceCallback

class BluetoothManager(context: Context) {

    companion object {
        const val ACTION_ENABLE_BLUETOOTH = BluetoothAdapter.ACTION_REQUEST_ENABLE
    }

    private var bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()

    private val deviceManager: CompanionDeviceManager by lazy(LazyThreadSafetyMode.NONE) {
        context.getSystemService(CompanionDeviceManager::class.java)
    }

    fun isBluetoothAvailable() = bluetoothAdapter != null

    fun isBluetoothEnabled() = bluetoothAdapter?.isEnabled ?: false

    fun startScan(deviceFoundCallback: CompanionDeviceManager.Callback) {
        val pairingRequest: AssociationRequest = AssociationRequest.Builder().build()

        deviceManager.associate(pairingRequest, deviceFoundCallback, null)
    }
}