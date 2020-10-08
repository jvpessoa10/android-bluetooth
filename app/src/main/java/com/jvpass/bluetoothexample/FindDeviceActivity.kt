package com.jvpass.bluetoothexample

import android.Manifest
import android.companion.CompanionDeviceManager
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.jvpass.bluetoothexample.bluetooth.BluetoothContractor
import com.jvpass.bluetoothexample.bluetooth.BluetoothManager
import com.jvpass.bluetoothexample.bluetooth.BluetoothManager.Companion.ACTION_ENABLE_BLUETOOTH
import com.jvpass.bluetoothexample.bluetooth.BluetoothPresenterImpl

class FindDeviceActivity : AppCompatActivity(), BluetoothContractor.BluetoothView {

    private companion object {
        const val ENABLE_BT_REQUEST_CODE = 1000
        const val REQUEST_FINE_LOCATION = 2000
        const val SELECT_DEVICE_REQUEST_CODE = 2000
    }

    private val bluetoothPresenter: BluetoothContractor.BluetoothPresenter
            = BluetoothPresenterImpl(this, BluetoothManager(this))

    override val deviceFoundCallback = object : CompanionDeviceManager.Callback() {
        override fun onDeviceFound(chooseLauncher: IntentSender?) {
            startIntentSenderForResult(chooseLauncher, SELECT_DEVICE_REQUEST_CODE, null, 0, 0, 0)
        }

        override fun onFailure(p0: CharSequence?) {
            TODO("Not yet implemented")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_device)
        bluetoothPresenter.onCreate()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ENABLE_BT_REQUEST_CODE) {
            when (requestCode) {
                RESULT_OK -> bluetoothPresenter.onBluetoothEnabled()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == SELECT_DEVICE_REQUEST_CODE) {
            if (grantResults.isNotEmpty()
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                bluetoothPresenter.onBluetoothPermissionGranted()
            }
        }
    }

    override fun displayBluetoothUnavailable() {
        Toast.makeText(this, R.string.bluetooth_unavailable_msg, Toast.LENGTH_LONG).show()
    }

    override fun requestEnableBluetooth() {
        val enableBtIntent = Intent(ACTION_ENABLE_BLUETOOTH)
        startActivityForResult(enableBtIntent, ENABLE_BT_REQUEST_CODE)
    }

    override fun requestBluetoothPermissions() {
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) bluetoothPresenter.onBluetoothPermissionGranted()
        else {
            requestPermissions(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                SELECT_DEVICE_REQUEST_CODE
            )
        }
    }

    override fun close() {
        finish()
    }
}