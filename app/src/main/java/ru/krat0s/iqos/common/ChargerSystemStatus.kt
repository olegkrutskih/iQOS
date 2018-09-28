package ru.krat0s.iqos.common

import ru.krat0s.iqos.helpers.BinaryHelperJava
import ru.krat0s.iqos.helpers.BinaryHelperJava.isFlag
import ru.krat0s.iqos.helpers.BinaryHelperJava.getBits


class ChargerSystemStatus(offset2: Int, offset3: Int) {
    val isChargerDefect: Boolean
    val chargerState: ChargerState?
    val holderConnection: HolderConnection?
    val isHolderDefect: Boolean
    val holderState: HolderState?
    val isLowBattery: Boolean

    enum class ChargerState {
        IDLE,
        CHARGING,
        CHARGED;


        companion object {

            fun fromInt(value: Int): ChargerState {
                when (value) {
                    1 -> return CHARGING
                    2 -> return CHARGED
                    else -> return IDLE
                }
            }
        }
    }

    enum class HolderConnection {
        FULLY_CONNECTED,
        RX_PIN_NOT_CONNECTED,
        TX_PIN_NOT_CONNECTED,
        NOT_CONNECTED;

        val key: String
            get() {
                when (this) {
                    RX_PIN_NOT_CONNECTED -> return "RX_PIN_NOT_CONNECTED"
                    TX_PIN_NOT_CONNECTED -> return "TX_PIN_NOT_CONNECTED"
                    NOT_CONNECTED -> return "NOT_CONNECTED"
                    else -> return "FULLY_CONNECTED"
                }
            }

        val title: String
            get() = "NOTIFICATION_11_12_TITLE"

        companion object {

            fun fromInt(value: Int): HolderConnection {
                when (value) {
                    1 -> return RX_PIN_NOT_CONNECTED
                    2 -> return TX_PIN_NOT_CONNECTED
                    3 -> return NOT_CONNECTED
                    else -> return FULLY_CONNECTED
                }
            }
        }
    }

    enum class HolderState {
        UNPLUGGED,
        CHARGING,
        CHARGED,
        READY_TO_USE,
        CLEANING,
        UNCHARGED;


        companion object {

            fun fromInt(value: Int): HolderState {
                when (value) {
                    1 -> return CHARGING
                    2 -> return CHARGED
                    3 -> return READY_TO_USE
                    4 -> return CLEANING
                    5 -> return UNCHARGED
                    else -> return UNPLUGGED
                }
            }
        }
    }

    init {
        this.chargerState = ChargerState.fromInt(BinaryHelperJava.getBits(offset2, 0, 4))
        this.isLowBattery = BinaryHelperJava.isFlag(offset2, 6)
        this.isChargerDefect = BinaryHelperJava.isFlag(offset2, 7)
        this.holderState = HolderState.fromInt(BinaryHelperJava.getBits(offset3, 0, 4))
        this.holderConnection = HolderConnection.fromInt(BinaryHelperJava.getBits(offset3, 4, 2))
        this.isHolderDefect = BinaryHelperJava.isFlag(offset3, 7)
    }

    override fun toString(): String {
        return "ChargerSystemStatus{chargerState=" + this.chargerState + ", holderState=" + this.holderState + ", holderConnection=" + this.holderConnection + ", lowBattery=" + this.isLowBattery + ", chargerDefect=" + this.isChargerDefect + ", holderDefect=" + this.isHolderDefect + '}'.toString()
    }

    override fun equals(o: Any?): Boolean {
        var z = true
        if (this === o) {
            return true
        }
        if (o == null || javaClass != o.javaClass) {
            return false
        }
        val that = o as ChargerSystemStatus?
        if (this.isLowBattery != that!!.isLowBattery || this.isChargerDefect != that.isChargerDefect || this.isHolderDefect != that.isHolderDefect || this.chargerState != that.chargerState || this.holderState != that.holderState) {
            return false
        }
        if (this.holderConnection != that.holderConnection) {
            z = false
        }
        return z
    }

    override fun hashCode(): Int {
        val result: Int
        var hashCode: Int
        var i = 1
        if (this.chargerState != null) {
            result = this.chargerState.hashCode()
        } else {
            result = 0
        }
        var i2 = result * 31
        if (this.holderState != null) {
            hashCode = this.holderState.hashCode()
        } else {
            hashCode = 0
        }
        i2 = (i2 + hashCode) * 31
        if (this.holderConnection != null) {
            hashCode = this.holderConnection.hashCode()
        } else {
            hashCode = 0
        }
        i2 = (i2 + hashCode) * 31
        if (this.isLowBattery) {
            hashCode = 1
        } else {
            hashCode = 0
        }
        i2 = (i2 + hashCode) * 31
        if (this.isChargerDefect) {
            hashCode = 1
        } else {
            hashCode = 0
        }
        hashCode = (i2 + hashCode) * 31
        if (!this.isHolderDefect) {
            i = 0
        }
        return hashCode + i
    }
}
