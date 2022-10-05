package com.exercise.hoursservice.controllers

import com.exercise.hoursservice.RestaurantScheduleService
import com.exercise.hoursservice.models.RestaurantSchedule
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController


@RestController
class RestaurantTimesController {

    @PostMapping("/times")
    fun setOpeningTimes(@RequestBody restaurantSchedule: RestaurantSchedule): String {
        val restaurantService = RestaurantScheduleService()
        val restaurantTimeTable = restaurantService.toHumanFormat(restaurantSchedule)
        println(restaurantTimeTable)
        return restaurantTimeTable
    }
}
