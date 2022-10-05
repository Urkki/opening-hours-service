package com.exercise.hoursservice.models

import com.exercise.hoursservice.deserializers.RestaurantScheduleDeserializer
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import java.time.DayOfWeek

@JsonDeserialize(using = RestaurantScheduleDeserializer::class)
data class RestaurantSchedule(val daySchedules: List<DaySchedule>) {
    fun getNextDaySchedule(day: DayOfWeek): DaySchedule? {
        val nextDay = day + 1
        return daySchedules.find { it.day == nextDay }
    }
}