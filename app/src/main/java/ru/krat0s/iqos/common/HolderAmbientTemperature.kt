package ru.krat0s.iqos.common

enum class HolderAmbientTemperature {
    AMBIENT_TEMPERATURE_IN_RANGE,
    AMBIENT_TEMPERATURE_TOO_LOW,
    AMBIENT_TEMPERATURE_TOO_HIGH;

    val key: String
        get() {
            when (this) {
                AMBIENT_TEMPERATURE_TOO_LOW -> return "NOTIFICATION_AMBIENT_TEMPERATURE_N5"
                AMBIENT_TEMPERATURE_TOO_HIGH -> return "NOTIFICATION_AMBIENT_TEMPERATURE_N9"
                else -> return ""
            }
        }

    companion object {

        fun fromInt(value: Int): HolderAmbientTemperature {
            when (value) {
                1 -> return AMBIENT_TEMPERATURE_TOO_LOW
                2 -> return AMBIENT_TEMPERATURE_TOO_HIGH
                else -> return AMBIENT_TEMPERATURE_IN_RANGE
            }
        }
    }
}