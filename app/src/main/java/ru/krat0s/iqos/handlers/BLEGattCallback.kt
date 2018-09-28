package ru.krat0s.iqos.handlers

import android.bluetooth.*
import android.bluetooth.BluetoothAdapter.STATE_CONNECTED
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import ru.krat0s.iqos.MainActivity
import ru.krat0s.iqos.common.*
import ru.krat0s.iqos.models.CharacteristicModel
import java.lang.StringBuilder
import ru.krat0s.iqos.common.HolderWarningFlags
import ru.krat0s.iqos.common.HolderErrorFlags
import ru.krat0s.iqos.helpers.BLEHolder
import ru.krat0s.iqos.helpers.BinaryHelper
import ru.krat0s.iqos.helpers.BinaryHelperJava


class BLEGattCallback: BluetoothGattCallback() {
    val tag = javaClass.name
    //val mainActivity: MainActivity = activity
    val CLIENT_CHARACTERISTIC_CONFIG_UUID = convertFromInteger(0x2902)
    var i = 0
    private var mBluetoothGattServices: MutableList<BluetoothGattService>? = null


    /*
    *
    * Result of research:
    *
    * characteristics
    * 00002a00-0000-1000-8000-00805f9b34fb Device Name
    * 00002a01-0000-1000-8000-00805f9b34fb Appearance
    * 00002a04-0000-1000-8000-00805f9b34fb Peripheral Preferred Connection Parameters
    * 00002a05-0000-1000-8000-00805f9b34fb Service Changed
    *
    * 00002a24-0000-1000-8000-00805f9b34fb Model Number String
    * 00002a25-0000-1000-8000-00805f9b34fb Serial Number String
    * 00002a28-0000-1000-8000-00805f9b34fb Software Revision String
    * 00002a29-0000-1000-8000-00805f9b34fb Manufacturer Name String
    *
    * e16c6e20-b041-11e4-a4c3-0002a5d5c51b UUID_SCP_CONTROL_POINT
    * f8a54120-b041-11e4-9be7-0002a5d5c51b UUID_BATTERY_INFORMATION
    * ecdfa4c0-b041-11e4-8b67-0002a5d5c51b UUID_DEVICE_STATUS
    * 0aff6f80-b042-11e4-9b66-0002a5d5c51b ??
    * 04941060-b042-11e4-8bf6-0002a5d5c51b ??
    * 15c32c40-b042-11e4-a643-0002a5d5c51b ??
    * fe272aa0-b041-11e4-87cb-0002a5d5c51b ??
    * 04941060-b042-11e4-8bf6-0002a5d5c51b ??
    *
    *
    * services
    * 00001801-0000-1000-8000-00805f9b34fb Generic Attribute
    * 00001800-0000-1000-8000-00805f9b34fb Generic Access
    * 0000180a-0000-1000-8000-00805f9b34fb Device Information
    * daebb240-b041-11e4-9e45-0002a5d5c51b UUID_RRP_SERVICE
    *
    *
    *
    * */

    override fun onConnectionStateChange(gatt: BluetoothGatt?, status: Int, newState: Int) {
        //super.onConnectionStateChange(gatt, status, newState)
//        Log.e(tag, "onConnectionStateChange gatt = ${gatt?.device?.name}, status: $status, newState: $newState")
        if (newState == STATE_CONNECTED){
            Log.e(tag, "onConnectionStateChange start discover services")
            gatt?.discoverServices()
//            mainActivity.runOnUiThread {
//                mainActivity.btn.isEnabled = true
//            }
        }
    }

    override fun onServicesDiscovered(gatt: BluetoothGatt?, status: Int) {
        //super.onServicesDiscovered(gatt, status)
        Log.e(tag, "onServicesDiscovered == gatt $gatt")
        if (status == BluetoothGatt.GATT_SUCCESS) {
            gatt?.services?.forEach { service ->
                if (service.uuid == UUID_RRP_SERVICE) {
                    //Log.e(tag, "onServicesDiscovered ==== service ${service.uuid}")
                    service.characteristics.forEach { characteristic ->
                        Log.e(tag, "onServicesDiscovered ====== characteristic ${resolveCharacteristicName(characteristic.uuid.toString())}")
                        val ch = CharacteristicModel(characteristic.uuid)
                        ch.descriptors = characteristic.descriptors
//                        characteristic.descriptors.forEach { descriptor ->
//                            Log.e(tag, "onServicesDiscovered ======== descriptor ${descriptor.uuid}")
//                        }

                        ch.mapProps(characteristic.properties)
                        ch.characteristic = characteristic
                        //mainActivity.updateCharacteristics(ch)
                        BLEHolder.updateCharacteristics(ch)
//                        Log.e(tag, "onServicesDiscovered ========== properties ${propertyToString(characteristic.properties)}")
                    }
                }
            }
        }

    }

    fun propertyToString(props: Int): String {
        val result = StringBuilder()

        if (props and BluetoothGattCharacteristic.PROPERTY_READ != 0) result.append("read ")
        if (props and BluetoothGattCharacteristic.PROPERTY_WRITE != 0) result.append("write ")
        if (props and BluetoothGattCharacteristic.PROPERTY_NOTIFY != 0) result.append("notify ")
        if (props and BluetoothGattCharacteristic.PROPERTY_INDICATE != 0) result.append("indicate ")
        if (props and BluetoothGattCharacteristic.PROPERTY_WRITE_NO_RESPONSE != 0) result.append("write_no_response ")

        return result.toString()
    }

//
//    fun getSupportedServices() {
//        if (mBluetoothGattServices != null && mBluetoothGattServices!!.size > 0) mBluetoothGattServices!!.clear()
//        // keep reference to all services in local array:
//        if (mBluetoothGatt != null) mBluetoothGattServices = mBluetoothGatt.getServices()
//
//        mUiCallback.uiAvailableServices(mBluetoothGatt, mBluetoothDevice, mBluetoothGattServices)
//    }

    override fun onCharacteristicChanged(gatt: BluetoothGatt?, characteristic: BluetoothGattCharacteristic?) {
        //super.onCharacteristicChanged(gatt, characteristic)
        Log.e(tag, "onCharacteristicChanged characteristic ${characteristic?.uuid}, value ${characteristic?.value}, format ${getValueFormat(characteristic!!.properties)}")
        if (characteristic.uuid == UUID_DEVICE_STATUS) {
            val dev = DeviceStatus(characteristic)
            Log.e(tag, "onCharacteristicRead chargerState: ${dev.chargerSystemStatus.chargerState}")
            Log.e(tag, "onCharacteristicRead holderState: ${dev.chargerSystemStatus.holderState}")
            Log.e(tag, "onCharacteristicRead puffCount: ${dev.holder1LastExperienceInfo.puffCount}")
            Log.e(tag, "onCharacteristicRead isNewInformationWasRead: ${dev.holder1LastExperienceInfo.isNewInformationWasRead}")
            Log.e(tag, "onCharacteristicRead isIntensivePuffing: ${dev.holder1LastExperienceInfo.isIntensivePuffing}")
            Log.e(tag, "onCharacteristicRead isDutyCycleFault: ${dev.holder1LastExperienceInfo.isDutyCycleFault}")
            Log.e(tag, "onCharacteristicRead endOfHeatReason: ${dev.holder1LastExperienceInfo.endOfHeatReason}")

//            getStatusValue(characteristic)
        }
        if (characteristic.uuid == UUID_BATTERY_INFORMATION) {
            val batt = BatteryInformation(characteristic)
            Log.e(tag, "onCharacteristicRead chargerBatteryLevel: ${batt.chargerBatteryLevel}")
            Log.e(tag, "onCharacteristicRead chargerBatteryTemperature: ${batt.chargerBatteryTemperature}")
            Log.e(tag, "onCharacteristicRead chargerBatteryVoltage: ${batt.chargerBatteryVoltage}")
            Log.e(tag, "onCharacteristicRead holder1BatteryLevel: ${batt.holder1BatteryLevel}")
            Log.e(tag, "onCharacteristicRead holder1BatteryTemperature: ${batt.holder1BatteryTemperature}")
            Log.e(tag, "onCharacteristicRead holder1BatteryVoltage: ${batt.holder1BatteryVoltage}")
            Log.e(tag, "onCharacteristicRead holder2BatteryLevel: ${batt.holder2BatteryLevel}")
            Log.e(tag, "onCharacteristicRead holder2BatteryTemperature: ${batt.holder2BatteryTemperature}")
            Log.e(tag, "onCharacteristicRead holder2BatteryVoltage: ${batt.holder2BatteryVoltage}")
        }
    }

    override fun onCharacteristicRead(gatt: BluetoothGatt?, characteristic: BluetoothGattCharacteristic?, status: Int) {
        //super.onCharacteristicRead(gatt, characteristic, status)
        Log.e(tag, "onCharacteristicRead characteristic ${characteristic?.uuid}, value ${characteristic?.value}, status $status, format ${getValueFormat(characteristic!!.properties)}")
        if (characteristic.uuid == UUID_DEVICE_STATUS) {
            val dev = DeviceStatus(characteristic)
            Log.e(tag, "onCharacteristicRead chargerState: ${dev.chargerSystemStatus.chargerState}")
            Log.e(tag, "onCharacteristicRead holderState: ${dev.chargerSystemStatus.holderState}")
            Log.e(tag, "onCharacteristicRead puffCount: ${dev.holder1LastExperienceInfo.puffCount}")
            Log.e(tag, "onCharacteristicRead isNewInformationWasRead: ${dev.holder1LastExperienceInfo.isNewInformationWasRead}")
            Log.e(tag, "onCharacteristicRead isIntensivePuffing: ${dev.holder1LastExperienceInfo.isIntensivePuffing}")
            Log.e(tag, "onCharacteristicRead isDutyCycleFault: ${dev.holder1LastExperienceInfo.isDutyCycleFault}")
            Log.e(tag, "onCharacteristicRead endOfHeatReason: ${dev.holder1LastExperienceInfo.endOfHeatReason}")

//            getStatusValue(characteristic)
        }
        if (characteristic.uuid == UUID_BATTERY_INFORMATION) {
            val batt = BatteryInformation(characteristic)
            Log.e(tag, "onCharacteristicRead chargerBatteryLevel: ${batt.chargerBatteryLevel}")
            Log.e(tag, "onCharacteristicRead chargerBatteryTemperature: ${batt.chargerBatteryTemperature}")
            Log.e(tag, "onCharacteristicRead chargerBatteryVoltage: ${batt.chargerBatteryVoltage}")
            Log.e(tag, "onCharacteristicRead holder1BatteryLevel: ${batt.holder1BatteryLevel}")
            Log.e(tag, "onCharacteristicRead holder1BatteryTemperature: ${batt.holder1BatteryTemperature}")
            Log.e(tag, "onCharacteristicRead holder1BatteryVoltage: ${batt.holder1BatteryVoltage}")
            Log.e(tag, "onCharacteristicRead holder2BatteryLevel: ${batt.holder2BatteryLevel}")
            Log.e(tag, "onCharacteristicRead holder2BatteryTemperature: ${batt.holder2BatteryTemperature}")
            Log.e(tag, "onCharacteristicRead holder2BatteryVoltage: ${batt.holder2BatteryVoltage}")
        }
    }



    override fun onCharacteristicWrite(gatt: BluetoothGatt?, characteristic: BluetoothGattCharacteristic?, status: Int) {
        //super.onCharacteristicWrite(gatt, characteristic, status)
        Log.e(tag, "onCharacteristicWrite characteristic ${characteristic?.uuid}, value ${characteristic?.value}, status $status")
    }

    private fun getValueFormat(props: Int): Int {
        return when {
            (BluetoothGattCharacteristic.FORMAT_FLOAT and props != 0) -> BluetoothGattCharacteristic.FORMAT_FLOAT
            (BluetoothGattCharacteristic.FORMAT_SFLOAT and props != 0) -> BluetoothGattCharacteristic.FORMAT_SFLOAT
            (BluetoothGattCharacteristic.FORMAT_SINT16 and props != 0)  -> BluetoothGattCharacteristic.FORMAT_SINT16
            (BluetoothGattCharacteristic.FORMAT_SINT32 and props != 0) -> BluetoothGattCharacteristic.FORMAT_SINT32
            (BluetoothGattCharacteristic.FORMAT_SINT8 and props != 0) -> BluetoothGattCharacteristic.FORMAT_SINT8
            (BluetoothGattCharacteristic.FORMAT_UINT16 and props != 0) -> BluetoothGattCharacteristic.FORMAT_UINT16
            (BluetoothGattCharacteristic.FORMAT_UINT32 and props != 0) -> BluetoothGattCharacteristic.FORMAT_UINT32
            (BluetoothGattCharacteristic.FORMAT_UINT8 and props != 0) -> BluetoothGattCharacteristic.FORMAT_UINT8
            else -> 0
        }
    }

}