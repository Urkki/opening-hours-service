package com.exercise.hoursservice.models

import com.exercise.hoursservice.deserializers.TimeDeserializer
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*


@JsonDeserialize(using = TimeDeserializer::class)
data class Time(val action: Action, val timeSeconds: Int) {

    fun to12HourFormat(): String {
        val d = Date(timeSeconds.toLong() * 1000).toInstant()
        val formatter = DateTimeFormatter
                .ofPattern("h[:mm] a")
                .withLocale(Locale.ENGLISH)
                .withZone(ZoneOffset.UTC)
        val hoursIn12Format = formatter.format(d)
        // hackish solution to remove ":00"
        return hoursIn12Format.replace(":00", "")
    }

    companion object {
        const val HOUR_AS_SECONDS = 60 * 60 // minutes, seconds
    }

    enum class Action(val value: String) {
        OPEN("open"),
        CLOSE("close")
    }
}