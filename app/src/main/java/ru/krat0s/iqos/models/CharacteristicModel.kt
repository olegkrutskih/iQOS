package ru.krat0s.iqos.models

import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattDescriptor
import java.util.*

class CharacteristicModel(val uuid: UUID) {
    var descriptors: List<BluetoothGattDescriptor>? = null
    var readable = false
    var writable = false
    var notyfable = false
    var value: Any? = null
    var format: Int = 0
    var characteristic: BluetoothGattCharacteristic? = null

    fun mapProps(props: Int) {
        if (props and BluetoothGattCharacteristic.PROPERTY_READ != 0) readable = true
        if (props and BluetoothGattCharacteristic.PROPERTY_WRITE != 0) writable = true
        if (props and BluetoothGattCharacteristic.PROPERTY_NOTIFY != 0) notyfable = true
        setValueFormat(props)
    }

    fun setValueFormat(props: Int) {
        format = when {
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