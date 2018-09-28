package ru.krat0s.iqos.common

import android.Manifest
import android.content.Context
import android.os.Build
import java.util.*



val IQOSCharacteristics = mapOf<String, String>(
        Pair("ecdfa4c0-b041-11e4-8b67-0002a5d5c51b", "UUID_DEVICE_STATUS"),
        Pair("e16c6e20-b041-11e4-a4c3-0002a5d5c51b", "UUID_SCP_CONTROL_POINT"),
        Pair("f8a54120-b041-11e4-9be7-0002a5d5c51b", "UUID_BATTERY_INFORMATION")
)

val UUID_DEVICE_STATUS = UUID.fromString("ecdfa4c0-b041-11e4-8b67-0002a5d5c51b")
val UUID_SCP_CONTROL_POINT = UUID.fromString("e16c6e20-b041-11e4-a4c3-0002a5d5c51b")
val UUID_BATTERY_INFORMATION = UUID.fromString("f8a54120-b041-11e4-9be7-0002a5d5c51b")

val UUID_RRP_SERVICE = UUID.fromString("daebb240-b041-11e4-9e45-0002a5d5c51b")

val tt: HashMap<String, UUID> = hashMapOf()

fun resolveCharacteristicName(uuid: String): String {
    return if (IQOSCharacteristics.containsKey(uuid)) {
        IQOSCharacteristics[uuid]!!
    } else {
        "UNKNOWN_CHARACTERISTIC"
    }
}

fun checkPerm(context: Context) {

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
    {
        val permissions = ArrayList<String>()
        permissions.add(Manifest.permission.CAMERA)
        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION)
        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION)
        permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        permissions.add(Manifest.permission.READ_PHONE_STATE)
        permissions.add(Manifest.permission.CALL_PHONE)
        permissions.add(Manifest.permission.READ_CONTACTS)


    }


}

fun convertFromInteger(i: Int): UUID {
    val MSB = 0x0000000000001000L
    val LSB = -0x7fffff7fa064cb05L
    val value = (i and -0x1).toLong()
    return UUID(MSB or (value shl 32), LSB)
}
