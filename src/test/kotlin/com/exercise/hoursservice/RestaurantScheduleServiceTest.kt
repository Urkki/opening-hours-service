package com.exercise.hoursservice

import com.exercise.hoursservice.models.DaySchedule
import com.exercise.hoursservice.models.RestaurantSchedule
import com.exercise.hoursservice.models.Time
import org.junit.Assert.assertEquals
import org.junit.jupiter.api.Test
import java.time.DayOfWeek

internal class RestaurantScheduleServiceTest {
    private var service: RestaurantScheduleService = RestaurantScheduleService()
    private val open10Am = Time(Time.Action.OPEN, 10 * Time.HOUR_AS_SECONDS)
    private val open7Pm = Time(Time.Action.OPEN, 19 * Time.HOUR_AS_SECONDS)
    private val open12Pm = Time(Time.Action.OPEN, 12 * Time.HOUR_AS_SECONDS)
    private val close6Pm = Time(Time.Action.CLOSE, 18 * Time.HOUR_AS_SECONDS)
    private val close1Am = Time(Time.Action.CLOSE, 1 * Time.HOUR_AS_SECONDS)
    private val close9Pm = Time(Time.Action.CLOSE, 21 * Time.HOUR_AS_SECONDS)

    @Test
    fun toHumanFormatClosed() {
        val monday = DaySchedule(DayOfWeek.MONDAY, times = emptyList())
        val actualResult = service.toHumanFormat(RestaurantSchedule(arrayListOf(monday)))
        assertEquals("Monday: Closed", actualResult.trim())
    }

    @Test
    fun toHumanFormatOpen() {
        val sunday = DaySchedule(DayOfWeek.SUNDAY, times = arrayListOf(close6Pm, open10Am))
        val actualResult = service.toHumanFormat(RestaurantSchedule(arrayListOf(sunday)))
        assertEquals("Sunday: 10 AM - 6 PM", actualResult.trim())
    }

    @Test
    fun toHumanFormatOpenTwoTimesInDay() {
        val saturday = DaySchedule(DayOfWeek.SATURDAY, times = arrayListOf(open10Am, close6Pm, open7Pm, close9Pm))
        val actualResult = service.toHumanFormat(RestaurantSchedule(arrayListOf(saturday)))
        assertEquals("Saturday: 10 AM - 6 PM, 7 PM - 9 PM", actualResult.trim())
    }

    @Test
    fun toHumanFormatOpenCloseInDifferentDays() {
        val tuesday = DaySchedule(DayOfWeek.TUESDAY, times = arrayListOf(open10Am))
        val wednesday = DaySchedule(DayOfWeek.WEDNESDAY, times = arrayListOf(close1Am))
        val actualResult = service.toHumanFormat(RestaurantSchedule(arrayListOf(wednesday, tuesday)))
        val ls = System.lineSeparator()
        assertEquals("Tuesday: 10 AM - 1 AM${ls}Wednesday: Closed", actualResult.trim())
    }

    @Test
    fun toHumanFormatEveryDay() {
        // demonstrates homework case
        val monday = DaySchedule(DayOfWeek.MONDAY, times = emptyList())
        val tuesday = DaySchedule(DayOfWeek.TUESDAY, times = arrayListOf(open10Am, close6Pm))
        val wednesday = DaySchedule(DayOfWeek.WEDNESDAY, times = emptyList())
        val thursday = DaySchedule(DayOfWeek.THURSDAY, times = arrayListOf(open10Am, close6Pm))
        val friday = DaySchedule(DayOfWeek.FRIDAY, times = arrayListOf(open10Am))
        val saturday = DaySchedule(DayOfWeek.SATURDAY, times = arrayListOf(close1Am, open10Am))
        val sunday = DaySchedule(DayOfWeek.SUNDAY, times = arrayListOf(close1Am, open12Pm, close9Pm))
        val restaurantSchedule = RestaurantSchedule(
                arrayListOf(sunday, saturday, friday, thursday, wednesday, tuesday, monday)
        )
        val actualResult = service.toHumanFormat(restaurantSchedule)
        val ls = System.lineSeparator()
        val expectedOutput = arrayListOf(
                "Monday: Closed",
                "Tuesday: 10 AM - 6 PM",
                "Wednesday: Closed",
                "Thursday: 10 AM - 6 PM",
                "Friday: 10 AM - 1 AM",
                "Saturday: 10 AM - 1 AM",
                "Sunday: 12 PM - 9 PM"
        )

        assertEquals(expectedOutput.joinToString(ls), actualResult.trim())
    }
}