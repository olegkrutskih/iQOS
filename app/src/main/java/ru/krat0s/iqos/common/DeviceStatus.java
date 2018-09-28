package ru.krat0s.iqos.common;

import android.bluetooth.BluetoothGattCharacteristic;
import android.util.Log;

import ru.krat0s.iqos.helpers.BinaryHelperJava;

public class DeviceStatus {
    private static final String TAG = DeviceStatus.class.getSimpleName();
    private int flags;
    private ChargerSystemError chargerSystemError;
    public ChargerSystemStatus chargerSystemStatus;
    private HolderErrorFlags holder1Error;
    public ExperienceInfo holder1LastExperienceInfo;
    private Long holder1SystemError;
    private HolderAmbientTemperature holder1TemperatureStatus;
    private HolderWarningFlags holder1Warning;
    private HolderErrorFlags holder2Error;
    public ExperienceInfo holder2LastExperienceInfo;
    private Long holder2SystemError;
    private HolderAmbientTemperature holder2TemperatureStatus;
    private HolderWarningFlags holder2Warning;

    public DeviceStatus(BluetoothGattCharacteristic characteristic){
        byte[] value = characteristic.getValue();
        Log.d(TAG, BinaryHelperJava.arrayToHexString(value));
        this.flags = characteristic.getIntValue(18, 0);
        int offset = 0 + 2;
        try {
            if (isFlag(0)) {
                this.chargerSystemError = ChargerSystemError.Companion.fromInt(characteristic.getIntValue(17, offset));
                offset++;
            }
            if (isFlag(1)) {
                this.chargerSystemStatus = new ChargerSystemStatus(characteristic.getIntValue(17, offset), characteristic.getIntValue(17, offset+1));
                offset++;
                offset++;
            }
            if (isFlag(2) || isFlag(7)) {
                int holdersTemperatureStatus = characteristic.getIntValue(17, offset);
                offset++;
                if (isFlag(2)) {
                    this.holder1TemperatureStatus = HolderAmbientTemperature.Companion.fromInt(BinaryHelperJava.getBits(holdersTemperatureStatus, 0, 2));
                }
                if (isFlag(7)) {
                    this.holder2TemperatureStatus = HolderAmbientTemperature.Companion.fromInt(BinaryHelperJava.getBits(holdersTemperatureStatus, 2, 2));
                }
            }
            if (isFlag(3)) {
                this.holder1Error = HolderErrorFlags.Companion.fromInt(characteristic.getIntValue(17, offset));
                offset++;
            }
            if (isFlag(4)) {
                this.holder1Warning = HolderWarningFlags.Companion.fromInt(characteristic.getIntValue(17, offset));
                offset++;
            }
            if (isFlag(5)) {
                this.holder1SystemError = BinaryHelperJava.convertFourBytesToLong(value[offset], value[offset + 1], value[offset + 2], value[offset + 3]);
                offset += 4;
            }
            if (isFlag(6)) {
                this.holder1LastExperienceInfo = new ExperienceInfo(characteristic.getIntValue(17, offset), characteristic.getIntValue(17, offset + 1));
                offset++;
                offset++;
            }
            if (isFlag(8)) {
                this.holder2Error = HolderErrorFlags.Companion.fromInt(characteristic.getIntValue(17, offset));
                offset++;
            }
            if (isFlag(9)) {
                this.holder2Warning = HolderWarningFlags.Companion.fromInt(characteristic.getIntValue(17, offset));
                offset++;
            }
            if (isFlag(10)) {
                this.holder2SystemError = BinaryHelperJava.convertFourBytesToLong(value[offset], value[offset + 1], value[offset + 2], value[offset + 3]);
                offset += 4;
            }
            if (isFlag(11)) {
                this.holder2LastExperienceInfo = new ExperienceInfo(characteristic.getIntValue(17, offset), characteristic.getIntValue(17, offset + 1));
            }
            //this.isFullyReceived = true;
        } catch (NullPointerException e) {
            Log.w(TAG, "Is not fully received");
        } catch (ArrayIndexOutOfBoundsException e2) {
            Log.w(TAG, "Is not fully received1");
        }
    }

    public boolean isFlag(int position) {
        return BinaryHelperJava.isFlag(this.flags, position);
    }
}
