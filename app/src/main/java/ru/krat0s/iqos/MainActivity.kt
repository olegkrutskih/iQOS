package ru.krat0s.iqos

import android.Manifest
import android.os.Bundle
import android.content.Context
import android.content.Intent
import android.util.Log
import ru.krat0s.iqos.handlers.BLEScanCallback
import android.content.pm.PackageManager
import android.bluetooth.*
import android.bluetooth.le.BluetoothLeScanner
import android.os.Build
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import ru.krat0s.iqos.common.UUID_BATTERY_INFORMATION
import ru.krat0s.iqos.common.UUID_DEVICE_STATUS
import ru.krat0s.iqos.common.convertFromInteger
import ru.krat0s.iqos.handlers.BLEGattCallback
import ru.krat0s.iqos.models.CharacteristicModel
import ru.krat0s.iqos.services.IQOSService
import java.util.*


class MainActivity : AppCompatActivity() {
    val tag = javaClass.name
//    var bluetoothManager: BluetoothManager? = null
//    var bluetoothAdapter: BluetoothAdapter? = null
//    var bluetoothLeScanner: BluetoothLeScanner? = null
//    val scanCallback = BLEScanCallback(this)
//    val gattCallBack = BLEGattCallback(this)
//    var iQOS: BluetoothDevice? = null
//        set(value) {
//            field = value
//            checkGATT()
//        }
//
//    var gatt: BluetoothGatt? = null
//
//    var characteristics: MutableMap<UUID, CharacteristicModel> = mutableMapOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(bottom_app_bar)
        val handler = Handler(Looper.getMainLooper())



        fab.setOnClickListener {
            Log.e(tag, "OnClickListener")
            startService(Intent(this, IQOSService::class.java))


        }

    }



    fun updateState(){
        if (!hasSelfPermissions()) {
            requestMultiplePermissions()
        } else {
//            bluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
//            bluetoothAdapter = bluetoothManager?.adapter
////            bluetoothLeScanner = bluetoothAdapter?.bluetoothLeScanner
////            bluetoothLeScanner?.startScan(scanCallback)
//
//            bluetoothAdapter?.bondedDevices?.forEach {
//                if (it.address == "D7:B6:A6:5C:E2:3F") {
//                    Log.e(tag, "updateState, device: ${it.name}, ${it.address}")
//                    iQOS = it
//
//                }
//            }

            //My IQOS 2.4+
            //D7:B6:A6:5C:E2:3F
        }
    }



    fun checkGATT(){
        //Log.e(tag, "checkGATT")
//        gatt = iQOS?.connectGatt(this, true, gattCallBack)
//        gatt?.connect()?.let {
////            btn.isEnabled = it
//        }
//        gatt?.discoverServices()
    }

    override fun onResume() {
        super.onResume()
        Log.d(tag, "onResume")
//        this.startForeground(Intent(this, IQOSService::class.java))
//        btn.isEnabled = false
        updateState()
    }

    override fun onDestroy() {
        stopService(Intent(this, IQOSService::class.java))
        super.onDestroy()
    }

    private fun hasSelfPermissions(): Boolean {
        var result = true
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED) {
            result = false
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_ADMIN) != PackageManager.PERMISSION_GRANTED) {
            result = false
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            result = false
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.FOREGROUND_SERVICE) != PackageManager.PERMISSION_GRANTED) {
                result = false
            }
        }

        return result
    }

    private fun requestMultiplePermissions() {


        val permArray = mutableListOf(
                Manifest.permission.BLUETOOTH,
                Manifest.permission.BLUETOOTH_ADMIN,
                Manifest.permission.ACCESS_COARSE_LOCATION
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            permArray.add(Manifest.permission.FOREGROUND_SERVICE)
        }
            ActivityCompat.requestPermissions(
                    this,
                    permArray.toTypedArray(),
                    888)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 888) {
            Log.e(tag, "result of $requestCode = $resultCode, hasSelfPermissions = ${hasSelfPermissions()}")
        }
    }
}
