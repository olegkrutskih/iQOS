package ru.krat0s.iqos.helpers

import kotlin.experimental.and

object BinaryHelper {
    fun arrayToHexString(paramArrayOfByte: ByteArray?): String {
        if (paramArrayOfByte == null) {
            return "null"
        }
        if (paramArrayOfByte.isEmpty()) {
            return "[]"
        }
        val localStringBuilder = StringBuilder(paramArrayOfByte.size * 6)
        localStringBuilder.append("HEX[")
        localStringBuilder.append(String.format("%02X ", *arrayOf<Any>(java.lang.Byte.valueOf(paramArrayOfByte[0]))))
        var i = 1
        while (i < paramArrayOfByte.size) {
            localStringBuilder.append(", ")
            localStringBuilder.append(String.format("%02X ", *arrayOf<Any>(java.lang.Byte.valueOf(paramArrayOfByte[i]))))
            i += 1
        }
        localStringBuilder.append(']')
        return localStringBuilder.toString()
    }

    fun isFlag(paramInt1: Int, paramInt2: Int): Boolean {
        return paramInt1 shr paramInt2 and 0x1 != 0
    }

    fun getBits(paramInt1: Int, paramInt2: Int, paramInt3: Int): Int {
        return paramInt1 and (255.ushr(8 - paramInt3) shl paramInt2) shr paramInt2
    }


}