package com.exercise.hoursservice.models

import java.time.DayOfWeek

data class DaySchedule(val day: DayOfWeek, val times: List<Time>) {

    fun getMatchingClosingTime(openTime: Time): Time? {
        // Finds matching closing hour if there's any
        return times
                .filter { it.action == Time.Action.CLOSE }
                .sortedBy { it.timeSeconds }
                .find { it.timeSeconds > openTime.timeSeconds }
    }

    fun getEarliestClosingTime() = times.filter { it.action == Time.Action.CLOSE }.minBy { it.timeSeconds }
}
