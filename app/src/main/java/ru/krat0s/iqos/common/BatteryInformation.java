package ru.krat0s.iqos.common;

import android.bluetooth.BluetoothGattCharacteristic;
import android.util.Log;

import ru.krat0s.iqos.helpers.BinaryHelperJava;

public class BatteryInformation {

    private static final String TAG = BatteryInformation.class.getSimpleName();
    private Integer chargerBatteryLevel;
    private Integer chargerBatteryTemperature;
    private Integer chargerBatteryVoltage;
    private int flags;
    private Integer holder1BatteryLevel;
    private Integer holder1BatteryTemperature;
    private Integer holder1BatteryVoltage;
    private Integer holder2BatteryLevel;
    private Integer holder2BatteryTemperature;
    private Integer holder2BatteryVoltage;

    public boolean isFlag(int position) {
        return BinaryHelperJava.isFlag(this.flags, position);
    }

    public BatteryInformation(BluetoothGattCharacteristic characteristic) {
        Log.d(TAG, BinaryHelperJava.arrayToHexString(characteristic.getValue()));
        this.flags = characteristic.getIntValue(18, 0);
        int offset = 0 + 2;
        if (isFlag(0)) {
            this.chargerBatteryLevel = characteristic.getIntValue(17, offset);
            offset++;
        }
        if (isFlag(1)) {
            this.chargerBatteryTemperature = characteristic.getIntValue(17, offset);
            offset++;
        }
        if (isFlag(2)) {
            this.chargerBatteryVoltage = characteristic.getIntValue(18, offset);
            offset += 2;
//            offset++;
        }
        if (isFlag(3)) {
            this.holder1BatteryLevel = characteristic.getIntValue(17, offset);
            offset++;
        }
        if (isFlag(4)) {
            this.holder1BatteryTemperature = characteristic.getIntValue(17, offset);
            offset++;
        }
        if (isFlag(5)) {
            this.holder1BatteryVoltage = characteristic.getIntValue(18, offset);
            offset += 2;
        }
        if (isFlag(6)) {
            this.holder2BatteryLevel = characteristic.getIntValue(17, offset);
            offset++;
        }
        if (isFlag(7)) {
            this.holder2BatteryTemperature = characteristic.getIntValue(17, offset);
            offset++;
        }
        if (isFlag(8)) {
            this.holder2BatteryVoltage = characteristic.getIntValue(18, offset);
        }
    }

    public int getFlags() {
        return this.flags;
    }

    public Integer getChargerBatteryLevel() {
        return this.chargerBatteryLevel;
    }

    public Integer getChargerBatteryTemperature() {
        return this.chargerBatteryTemperature;
    }

    public Integer getChargerBatteryVoltage() {
        return this.chargerBatteryVoltage;
    }

    public Integer getHolder1BatteryLevel() {
        return this.holder1BatteryLevel;
    }

    public Integer getHolder1BatteryTemperature() {
        return this.holder1BatteryTemperature;
    }

    public Integer getHolder1BatteryVoltage() {
        return this.holder1BatteryVoltage;
    }

    public Integer getHolder2BatteryLevel() {
        return this.holder2BatteryLevel;
    }

    public Integer getHolder2BatteryTemperature() {
        return this.holder2BatteryTemperature;
    }

    public Integer getHolder2BatteryVoltage() {
        return this.holder2BatteryVoltage;
    }

    public String toString() {
        return "BatteryInformation{\n flags (binary)=" + Integer.toBinaryString(this.flags) + ",\n chargerBatteryLevel=" + this.chargerBatteryLevel + ",\n chargerBatteryTemperature=" + this.chargerBatteryTemperature + ",\n chargerBatteryVoltage=" + this.chargerBatteryVoltage + ",\n holder1BatteryLevel=" + this.holder1BatteryLevel + ",\n holder1BatteryTemperature=" + this.holder1BatteryTemperature + ",\n holder1BatteryVoltage=" + this.holder1BatteryVoltage + ",\n holder2BatteryLevel=" + this.holder2BatteryLevel + ",\n holder2BatteryTemperature=" + this.holder2BatteryTemperature + ",\n holder2BatteryVoltage=" + this.holder2BatteryVoltage + "\n}";
    }
}
