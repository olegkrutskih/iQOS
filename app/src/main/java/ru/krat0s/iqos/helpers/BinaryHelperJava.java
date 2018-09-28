package ru.krat0s.iqos.helpers;

public class BinaryHelperJava {

    public static long convertFourBytesToLong(byte paramByte1, byte paramByte2, byte paramByte3, byte paramByte4)
    {
        return (paramByte4 & 0xFF) << 24 | (paramByte3 & 0xFF) << 16 | (paramByte2 & 0xFF) << 8 | paramByte1 & 0xFF;
    }

    public static int getBits(int paramInt1, int paramInt2, int paramInt3)
    {
        return (paramInt1 & 255 >>> 8 - paramInt3 << paramInt2) >> paramInt2;
    }

    public static boolean isFlag(int paramInt1, int paramInt2)
    {
        return (paramInt1 >> paramInt2 & 0x1) != 0;
    }

    public static String arrayToHexString(byte[] paramArrayOfByte)
    {
        if (paramArrayOfByte == null) {
            return "null";
        }
        if (paramArrayOfByte.length == 0) {
            return "[]";
        }
        StringBuilder localStringBuilder = new StringBuilder(paramArrayOfByte.length * 6);
        localStringBuilder.append("HEX[");
        localStringBuilder.append(String.format("%02X ", new Object[] { Byte.valueOf(paramArrayOfByte[0]) }));
        int i = 1;
        while (i < paramArrayOfByte.length)
        {
            localStringBuilder.append(", ");
            localStringBuilder.append(String.format("%02X ", new Object[] { Byte.valueOf(paramArrayOfByte[i]) }));
            i += 1;
        }
        localStringBuilder.append(']');
        return localStringBuilder.toString();
    }


}
