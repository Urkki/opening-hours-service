package com.exercise.hoursservice.models

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class TimeTest {

    @Test
    fun to12HourFormatPm() {
        assertEquals("6 PM", Time(Time.Action.OPEN, 64800).to12HourFormat())
    }

    @Test
    fun to12HourFormatAm() {
        assertEquals("3 AM", Time(Time.Action.OPEN, 10800).to12HourFormat())
    }

    @Test
    fun to12HourFormatTenThirtyAm() {
        assertEquals("10:30 AM", Time(Time.Action.OPEN, 37800).to12HourFormat())
    }

    @Test
    fun to12HourFormatMidnight() {
        assertEquals("12 AM", Time(Time.Action.OPEN, 0).to12HourFormat())
    }

    @Test
    fun to12HourFormatMidDay() {
        assertEquals("12 PM", Time(Time.Action.OPEN, 43200).to12HourFormat())
    }
}