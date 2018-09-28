package ru.krat0s.iqos.common

import ru.krat0s.iqos.helpers.BinaryHelper

class ExperienceInfo(offset2: Int, val puffCount: Int) {
    val isDutyCycleFault: Boolean
    val endOfHeatReason: EndOfHeatReason?
    val isIntensivePuffing: Boolean
    val isNewInformationWasRead: Boolean

    enum class EndOfHeatReason {
        NO_DATA,
        HEATING_IN_PROGRESS,
        END_OF_PROFILE_REACHED,
        USER_ACTION,
        HEATER_TEMPERATURE_ABOVE_LIMIT,
        HEATER_POWER_OUT_OF_RANGE,
        LOW_BATTERY_VOLTAGE,
        SYSTEM_FAULT,
        PULL_LIMITATION,
        AMBIENT_TEMPERATURE_TOO_HIGH,
        EXCESSIVE_VARIATIONS_IN_HEATER_MEASUREMENTS;


        companion object {

            fun fromInt(value: Int): EndOfHeatReason {
                when (value) {
                    1 -> return HEATING_IN_PROGRESS
                    2 -> return END_OF_PROFILE_REACHED
                    3 -> return USER_ACTION
                    4 -> return HEATER_TEMPERATURE_ABOVE_LIMIT
                    5 -> return HEATER_POWER_OUT_OF_RANGE
                    6 -> return LOW_BATTERY_VOLTAGE
                    7 -> return SYSTEM_FAULT
                    8 -> return PULL_LIMITATION
                    9 -> return AMBIENT_TEMPERATURE_TOO_HIGH
                    10 -> return EXCESSIVE_VARIATIONS_IN_HEATER_MEASUREMENTS
                    else -> return NO_DATA
                }
            }
        }
    }

    init {
        this.endOfHeatReason = EndOfHeatReason.fromInt(BinaryHelper.getBits(offset2, 0, 4))
        this.isDutyCycleFault = BinaryHelper.isFlag(offset2, 5)
        this.isNewInformationWasRead = BinaryHelper.isFlag(offset2, 6)
        this.isIntensivePuffing = BinaryHelper.isFlag(offset2, 7)
    }

    override fun toString(): String {
        return "ExperienceInfo{\n endOfHeatReason=" + this.endOfHeatReason + ",\n dutyCycleFault=" + this.isDutyCycleFault + ",\n newInformationWasRead=" + this.isNewInformationWasRead + ",\n intensivePuffing=" + this.isIntensivePuffing + ",\n puffCount=" + this.puffCount + "\n}"
    }

    override fun equals(o: Any?): Boolean {
        var z = true
        if (this === o) {
            return true
        }
        if (o == null || javaClass != o.javaClass) {
            return false
        }
        val that = o as ExperienceInfo?
        if (this.isDutyCycleFault != that!!.isDutyCycleFault || this.isNewInformationWasRead != that.isNewInformationWasRead || this.isIntensivePuffing != that.isIntensivePuffing || this.puffCount != that.puffCount) {
            return false
        }
        if (this.endOfHeatReason != that.endOfHeatReason) {
            z = false
        }
        return z
    }

    override fun hashCode(): Int {
        val result: Int
        var i: Int
        var i2 = 1
        if (this.endOfHeatReason != null) {
            result = this.endOfHeatReason.hashCode()
        } else {
            result = 0
        }
        var i3 = result * 31
        if (this.isDutyCycleFault) {
            i = 1
        } else {
            i = 0
        }
        i3 = (i3 + i) * 31
        if (this.isNewInformationWasRead) {
            i = 1
        } else {
            i = 0
        }
        i = (i3 + i) * 31
        if (!this.isIntensivePuffing) {
            i2 = 0
        }
        return (i + i2) * 31 + this.puffCount
    }
}
