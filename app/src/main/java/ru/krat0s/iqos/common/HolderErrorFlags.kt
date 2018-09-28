package ru.krat0s.iqos.common

enum class HolderErrorFlags {
    NO_ERROR,
    HOLDER_SYSTEM_DEFECT,
    HOLDER_BATTERY_CHARGING_TIMEOUT,
    HOLDER_TEMPERATURE_OUTSIDE_OPERATING_RANGE,
    HOLDER_IDENTIFICATION_FAILURE;

    val key: String
        get() {
            when (this) {
                HOLDER_SYSTEM_DEFECT -> return "NOTIFICATION_MESSAGE_HOLDER_ERROR_0x01"
                HOLDER_BATTERY_CHARGING_TIMEOUT -> return "NOTIFICATION_MESSAGE_HOLDER_ERROR_0x02"
                else -> return ""
            }
        }

    val intValue: Int
        get() {
            when (this) {
                HOLDER_SYSTEM_DEFECT -> return 1
                HOLDER_BATTERY_CHARGING_TIMEOUT -> return 2
                HOLDER_TEMPERATURE_OUTSIDE_OPERATING_RANGE -> return 4
                HOLDER_IDENTIFICATION_FAILURE -> return 8
                else -> return 0
            }
        }

    val title: String
        get() = "NOTIFICATION_11_12_TITLE"

    companion object {

        fun fromInt(value: Int): HolderErrorFlags {
            when (value) {
                1 -> return HOLDER_SYSTEM_DEFECT
                2 -> return HOLDER_BATTERY_CHARGING_TIMEOUT
                4 -> return HOLDER_TEMPERATURE_OUTSIDE_OPERATING_RANGE
                8 -> return HOLDER_IDENTIFICATION_FAILURE
                else -> return NO_ERROR
            }
        }
    }
}
