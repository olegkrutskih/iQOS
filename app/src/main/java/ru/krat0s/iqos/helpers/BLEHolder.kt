package ru.krat0s.iqos.helpers

import android.bluetooth.*
import android.content.Context
import android.util.Log
import ru.krat0s.iqos.common.UUID_BATTERY_INFORMATION
import ru.krat0s.iqos.common.UUID_DEVICE_STATUS
import ru.krat0s.iqos.common.convertFromInteger
import ru.krat0s.iqos.handlers.BLEGattCallback
import ru.krat0s.iqos.models.CharacteristicModel
import java.util.*
import java.util.concurrent.ConcurrentHashMap

object BLEHolder {
    val tag = javaClass.name

    var initialized = false
    var bluetoothManager: BluetoothManager? = null
    var bluetoothAdapter: BluetoothAdapter? = null
    private val gattCallBack = BLEGattCallback()
    var iQOS: BluetoothDevice? = null
//        set(value) {
//            field = value
//        }

    var gatt: BluetoothGatt? = null

    var characteristics: ConcurrentHashMap<UUID, CharacteristicModel> = ConcurrentHashMap()

    fun initialize(context: Context){
        bluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothAdapter = bluetoothManager?.adapter
//            bluetoothLeScanner = bluetoothAdapter?.bluetoothLeScanner
//            bluetoothLeScanner?.startScan(scanCallback)

        bluetoothAdapter?.bondedDevices?.forEach {
            if (it.address == "D7:B6:A6:5C:E2:3F") {
                Log.e(tag, "updateState, device: ${it.name}, ${it.address}")
                iQOS = it
                gatt = iQOS?.connectGatt(context, true, gattCallBack)
            }
        }
        initialized = true
    }

    fun subscibeToBLENotify() {
        characteristics[UUID_DEVICE_STATUS]?.let { ch ->

            //gatt?.readCharacteristic(ch.characteristic)
            gatt?.setCharacteristicNotification(ch.characteristic, true)
            val descriptor = ch.characteristic?.getDescriptor(convertFromInteger(0x2902))
            descriptor?.let { d ->
                Log.e(tag, "Set notification status")
                d.value = BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE
                gatt?.writeDescriptor(d)
            }
        }

        characteristics[UUID_BATTERY_INFORMATION]?.let { ch1 ->

            //gatt?.readCharacteristic(ch1.characteristic)
            gatt?.setCharacteristicNotification(ch1.characteristic, true)
            val descriptor = ch1.characteristic?.getDescriptor(convertFromInteger(0x2902))
            descriptor?.let { d ->
                Log.e(tag, "Set notification status")
                d.value = BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE
                gatt?.writeDescriptor(d)
            }
        }
    }

    fun updateCharacteristics(characteristic: CharacteristicModel) {
        characteristics[characteristic.uuid] = characteristic
    }
}