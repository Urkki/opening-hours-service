package com.exercise.hoursservice.models

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.DayOfWeek

internal class RestaurantScheduleTest {

    @Test
    fun getNextDayScheduleMondayTuesday() {
        val monday = DaySchedule(DayOfWeek.MONDAY, times = emptyList())
        val tuesday = DaySchedule(DayOfWeek.TUESDAY, times = emptyList())
        val restaurantSchedule = RestaurantSchedule(arrayListOf(monday, tuesday))
        val nextSchedule = restaurantSchedule.getNextDaySchedule(monday.day)
        assertEquals(tuesday, nextSchedule)
    }

    @Test
    fun getNextDayScheduleSundayMonday() {
        val sunday = DaySchedule(DayOfWeek.SUNDAY, times = emptyList())
        val monday = DaySchedule(DayOfWeek.MONDAY, times = emptyList())
        val restaurantSchedule = RestaurantSchedule(arrayListOf(monday, sunday))
        val nextSchedule = restaurantSchedule.getNextDaySchedule(sunday.day)
        assertEquals(monday, nextSchedule)
    }
}