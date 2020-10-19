package com.jvpass.bluetoothexample.ui

import android.bluetooth.BluetoothDevice
import android.companion.CompanionDeviceManager
import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.jvpass.bluetoothexample.R
import com.jvpass.bluetoothexample.bluetooth.BluetoothManager
import com.jvpass.bluetoothexample.bluetooth.BluetoothManager.Companion.ACTION_ENABLE_BLUETOOTH
import com.jvpass.bluetoothexample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), MainActivityContractor.View {

    private companion object {
        private const val TAG = "FindDeviceActivity"
        const val ENABLE_BT_REQUEST_CODE = 1000
        const val SELECT_DEVICE_REQUEST_CODE = 2000
    }

    private val presenter: MainActivityContractor.Presenter
            = MainActivityPresenterImpl(
        this,
        BluetoothManager(this)
    )

    override val deviceFoundCallback = object : CompanionDeviceManager.Callback() {
        override fun onDeviceFound(chooseLauncher: IntentSender?) {
            startIntentSenderForResult(chooseLauncher,
                SELECT_DEVICE_REQUEST_CODE, null, 0, 0, 0)
        }

        override fun onFailure(p0: CharSequence?) {
            TODO("Not yet implemented")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_main
        )

        binding.presenter = presenter
        presenter.onCreate()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            ENABLE_BT_REQUEST_CODE -> {
                when (resultCode) {
                    RESULT_OK -> presenter.onBluetoothEnabled()
                }
            }
            SELECT_DEVICE_REQUEST_CODE -> {
                when (resultCode) {
                    RESULT_OK -> {
                        val deviceToPair: BluetoothDevice =
                                data?.getParcelableExtra(CompanionDeviceManager.EXTRA_DEVICE)
                                        ?: return
                        presenter.onDeviceSelected(deviceToPair)
                    }
                }
            }
        }
    }

    override fun displayBluetoothUnavailable() {
        Toast.makeText(this,
            R.string.bluetooth_unavailable_msg, Toast.LENGTH_LONG).show()
    }

    override fun requestEnableBluetooth() {
        val enableBtIntent = Intent(ACTION_ENABLE_BLUETOOTH)
        startActivityForResult(enableBtIntent,
            ENABLE_BT_REQUEST_CODE
        )
    }

    override fun close() {
        finish()
    }
}