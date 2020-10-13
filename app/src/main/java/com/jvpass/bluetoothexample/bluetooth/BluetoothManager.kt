package com.jvpass.bluetoothexample.bluetooth

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.companion.AssociationRequest
import android.companion.BluetoothDeviceFilter
import android.companion.CompanionDeviceManager
import android.content.Context
import android.content.IntentSender
import android.media.AudioDeviceCallback
import android.util.Log
import java.io.IOException
import java.util.*

class BluetoothManager(context: Context) {

    companion object {
        private const val TAG = "BluetoothManager"
        private val H5_02_UUID: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")

        const val ACTION_ENABLE_BLUETOOTH = BluetoothAdapter.ACTION_REQUEST_ENABLE
    }

    private var bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()

    private val deviceManager: CompanionDeviceManager by lazy(LazyThreadSafetyMode.NONE) {
        context.getSystemService(CompanionDeviceManager::class.java)
    }

    private lateinit var connectionThread: ConnectionThread

    fun isBluetoothAvailable() = bluetoothAdapter != null

    fun isBluetoothEnabled() = bluetoothAdapter?.isEnabled ?: false

    fun connect(device: BluetoothDevice) {
        device.createBond()
        connectionThread = ConnectionThread(device)
        connectionThread.start()
    }

    fun startScan(deviceFoundCallback: CompanionDeviceManager.Callback) {
        Log.d(TAG, "startScan")

        val deviceFilter = BluetoothDeviceFilter.Builder().build()

        val pairingRequest: AssociationRequest = AssociationRequest.Builder()
                .addDeviceFilter(deviceFilter)
                .build()
        
        deviceManager.associate(pairingRequest, deviceFoundCallback, null)
    }

    fun write(value: String) {
        if (!this::connectionThread.isInitialized) {
            Log.e(TAG, "No device connected")
            return
        }

        connectionThread.write(value.toByteArray())
    }

    private inner class ConnectionThread(private val device: BluetoothDevice): Thread() {

        private val mmSocket: BluetoothSocket? by lazy(LazyThreadSafetyMode.NONE) {
            device.createRfcommSocketToServiceRecord(H5_02_UUID)
        }

        private val inStream = mmSocket?.inputStream
        private val outStream = mmSocket?.outputStream

        override fun run() {
            bluetoothAdapter?.cancelDiscovery()

            try {
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
}