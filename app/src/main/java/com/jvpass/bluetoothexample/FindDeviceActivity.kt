package com.jvpass.bluetoothexample

import android.Manifest
import android.app.Activity
import android.bluetooth.BluetoothDevice
import android.companion.CompanionDeviceManager
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.jvpass.bluetoothexample.bluetooth.BluetoothContractor
import com.jvpass.bluetoothexample.bluetooth.BluetoothManager
import com.jvpass.bluetoothexample.bluetooth.BluetoothManager.Companion.ACTION_ENABLE_BLUETOOTH
import com.jvpass.bluetoothexample.bluetooth.BluetoothPresenterImpl
import com.jvpass.bluetoothexample.databinding.ActivityFindDeviceBinding

class FindDeviceActivity : AppCompatActivity(), BluetoothContractor.BluetoothView {

    private companion object {
        private const val TAG = "FindDeviceActivity"
        const val ENABLE_BT_REQUEST_CODE = 1000
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
        Log.d(TAG, "onCreate")
        val binding: ActivityFindDeviceBinding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_find_device
        )

        binding.presenter = bluetoothPresenter
        bluetoothPresenter.onCreate()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            ENABLE_BT_REQUEST_CODE -> {
                when (resultCode) {
                    RESULT_OK -> bluetoothPresenter.onBluetoothEnabled()
                }
            }
            SELECT_DEVICE_REQUEST_CODE -> {
                when (resultCode) {
                    RESULT_OK -> {
                        val deviceToPair: BluetoothDevice =
                                data?.getParcelableExtra(CompanionDeviceManager.EXTRA_DEVICE)
                                        ?: return
                        bluetoothPresenter.onDeviceSelected(deviceToPair)
                    }
                }
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

    override fun close() {
        finish()
    }
}