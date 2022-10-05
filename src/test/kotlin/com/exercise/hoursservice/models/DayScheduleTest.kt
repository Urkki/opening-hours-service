package com.exercise.hoursservice.models

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.DayOfWeek

internal class DayScheduleTest {
    private val nineAm = Time(Time.Action.OPEN, 32400)
    private val eightPm = Time(Time.Action.CLOSE, 72000)
    private val twoAm = Time(Time.Action.CLOSE, 7200)

    @Test
    fun getMatchingClosingTimeMatches() {
        val closingTime = DaySchedule(DayOfWeek.FRIDAY, times = arrayListOf(eightPm, twoAm)).getMatchingClosingTime(nineAm)
        assertEquals(eightPm, closingTime)
    }

    @Test
    fun getMatchingClosingTimeNoMatch() {
        val closingTime = DaySchedule(DayOfWeek.FRIDAY, times = arrayListOf(twoAm)).getMatchingClosingTime(nineAm)
        assertEquals(null, closingTime)
    }

    @Test
    fun getEarliestClosingTime() {
        val closingTime = DaySchedule(DayOfWeek.FRIDAY, times = arrayListOf(eightPm, twoAm)).getEarliestClosingTime()
        assertEquals(twoAm, closingTime)
    }
}