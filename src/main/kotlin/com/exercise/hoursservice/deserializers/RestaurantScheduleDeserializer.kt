package com.exercise.hoursservice.deserializers

import com.exercise.hoursservice.models.DaySchedule
import com.exercise.hoursservice.models.RestaurantSchedule
import com.exercise.hoursservice.models.Time
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import java.io.IOException
import java.time.DayOfWeek


class RestaurantScheduleDeserializer @JvmOverloads constructor(vc: Class<*>? = null) : StdDeserializer<RestaurantSchedule?>(vc) {
    /*
    Deserializer for
    {
      "monday" : [
         {
             "type" : "open",
             "value" : 32400
         },
         {
             "type" : "close",
             "value" : 72000
         }
      ],
      ...
      "friday" : [
        {
            "type" : "open",
            "value" : 64800
        }
      ],
    }
    JSON
     */
    @Throws(IOException::class, JsonProcessingException::class)
    override fun deserialize(jp: JsonParser, ctxt: DeserializationContext?): RestaurantSchedule {
        val node: JsonNode = jp.codec.readTree(jp)
        val weekDays = emptyList<DaySchedule>().toMutableList()

        for ((weekDay, hours) in node.fields()) {
            // convert weekday to enum
            val weekDayEnum = DayOfWeek.valueOf(weekDay.toUpperCase())
            // deserialize list of open/close times
            val parser = hours.traverse()
            parser.codec = jp.codec
            val listOfTimes: List<Time> = parser.readValueAs(object : TypeReference<List<Time?>?>() {})
            // add to list
            weekDays += DaySchedule(weekDayEnum, listOfTimes)
        }
        return RestaurantSchedule(weekDays)
    }
}