package ru.krat0s.iqos.common

enum class HolderWarningFlags {
    NO_ERROR,
    HOLDER_COMMUNICATION_PROBLEM,
    NULL_HOLDER_CHARGING_COUNT,
    HOLDER_COMMUNICATION_PROTOCOL_NOT_SUPPORTED,
    HOLDER_BATTERY_AGED;

    val key: String
        get() {
            when (this) {
                HOLDER_COMMUNICATION_PROBLEM -> return "NOTIFICATION_MESSAGE_HOLDER_WARNING_0x01"
                NULL_HOLDER_CHARGING_COUNT -> return "NOTIFICATION_MESSAGE_HOLDER_WARNING_0x02"
                HOLDER_COMMUNICATION_PROTOCOL_NOT_SUPPORTED -> return "NOTIFICATION_MESSAGE_HOLDER_WARNING_0x04"
                HOLDER_BATTERY_AGED -> return "NOTIFICATION_MESSAGE_HOLDER_WARNING_0x08"
                else -> return ""
            }
        }

    val intValue: Int
        get() {
            when (this) {
                HOLDER_COMMUNICATION_PROBLEM -> return 1
                NULL_HOLDER_CHARGING_COUNT -> return 2
                HOLDER_COMMUNICATION_PROTOCOL_NOT_SUPPORTED -> return 4
                HOLDER_BATTERY_AGED -> return 8
                else -> return 0
            }
        }

    val title: String
        get() = "NOTIFICATION_11_12_TITLE"

    companion object {

        fun fromInt(value: Int): HolderWarningFlags {
            when (value) {
                1 -> return HOLDER_COMMUNICATION_PROBLEM
                2 -> return NULL_HOLDER_CHARGING_COUNT
                4 -> return HOLDER_COMMUNICATION_PROTOCOL_NOT_SUPPORTED
                8 -> return HOLDER_BATTERY_AGED
                else -> return NO_ERROR
            }
        }
    }
}
