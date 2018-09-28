package ru.krat0s.iqos.common

enum class ChargerSystemError {
    NO_ERROR,
    HOLDER_BATTERY_CHARGER_DEFECT,
    CHARGER_BATTERY_CHARGING_TIMEOUT,
    CHARGER_BATTERY_CHARGER_DEFECT,
    SELF_TEST_FAILURE;

    val intValue: Int
        get() {
            when (this) {
                HOLDER_BATTERY_CHARGER_DEFECT -> return 1
                CHARGER_BATTERY_CHARGING_TIMEOUT -> return 2
                CHARGER_BATTERY_CHARGER_DEFECT -> return 4
                SELF_TEST_FAILURE -> return 8
                else -> return 0
            }
        }

    val key: String
        get() {
            when (this) {
                HOLDER_BATTERY_CHARGER_DEFECT -> return "NOTIFICATION_MESSAGE_CHARGER_ERROR_0x01"
                CHARGER_BATTERY_CHARGING_TIMEOUT -> return "NOTIFICATION_MESSAGE_CHARGER_ERROR_0x02"
                CHARGER_BATTERY_CHARGER_DEFECT -> return "NOTIFICATION_MESSAGE_CHARGER_ERROR_0x04"
                SELF_TEST_FAILURE -> return "NOTIFICATION_MESSAGE_CHARGER_ERROR_0x08"
                else -> return ""
            }
        }

    val title: String
        get() = "NOTIFICATION_11_12_TITLE"

    companion object {

        fun fromInt(value: Int): ChargerSystemError {
            when (value) {
                1 -> return HOLDER_BATTERY_CHARGER_DEFECT
                2 -> return CHARGER_BATTERY_CHARGING_TIMEOUT
                4 -> return CHARGER_BATTERY_CHARGER_DEFECT
                8 -> return SELF_TEST_FAILURE
                else -> return NO_ERROR
            }
        }
    }
}