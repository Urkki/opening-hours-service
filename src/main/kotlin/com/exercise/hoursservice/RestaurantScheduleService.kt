package com.exercise.hoursservice

import com.exercise.hoursservice.models.RestaurantSchedule
import com.exercise.hoursservice.models.Time
import java.time.format.TextStyle
import java.util.*

class RestaurantScheduleService {
    private val lineSeparator: String? = System.lineSeparator()

    fun toHumanFormat(restaurantSchedule: RestaurantSchedule): String {
        /*
        * Prints restaurant schedule in human readable format e.g.
        *
        * Monday: 10 PM - 11PM
        * Tuesday: Closed
        *
        * */

        val sortedDaySchedules = restaurantSchedule.daySchedules.sortedBy { it.day }
        var restaurantTimeTable = ""

        for (daySchedule in sortedDaySchedules) {
            val dayNamePrefix = daySchedule.day.getDisplayName(TextStyle.FULL, Locale("en"))
            val openTimes = daySchedule.times.sortedBy { it.timeSeconds }.filter { it.action == Time.Action.OPEN }
            val timeIntervals = emptyList<String>().toMutableList()

            for (openTime in openTimes) {
                var closingTime = daySchedule.getMatchingClosingTime(openTime)
                // closing time should be in next day
                if (closingTime == null) {
                    closingTime = restaurantSchedule.getNextDaySchedule(daySchedule.day)?.getEarliestClosingTime()
                }
                timeIntervals += "${openTime.to12HourFormat()} - ${closingTime?.to12HourFormat()}"
            }
            // check if restaurant is closed (Any other case did not match)
            if (timeIntervals.isEmpty()) {
                timeIntervals += "Closed"
            }
            val timeSpans = timeIntervals.joinToString()
            restaurantTimeTable += "$dayNamePrefix: $timeSpans$lineSeparator"
        }
        return restaurantTimeTable
    }
}