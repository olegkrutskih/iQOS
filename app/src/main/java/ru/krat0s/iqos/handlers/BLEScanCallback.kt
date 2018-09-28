package ru.krat0s.iqos.handlers

import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.util.Log
import ru.krat0s.iqos.MainActivity

class BLEScanCallback(activity: MainActivity): ScanCallback() {
    val tag = javaClass.name
    val mainActivity: MainActivity = activity
    override fun onScanResult(callbackType: Int, result: ScanResult?) {
        super.onScanResult(callbackType, result)
        Log.e(tag, "callbackType = $callbackType device: ${result?.device?.name}")
//        if (result?.device?.address == "D7:B6:A6:5C:E2:3F") {
//            mainActivity.iQOS = result.device
//
//        }
    }
}