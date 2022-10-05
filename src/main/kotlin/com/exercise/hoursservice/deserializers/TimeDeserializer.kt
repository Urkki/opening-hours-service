package com.exercise.hoursservice.deserializers

import com.exercise.hoursservice.models.Time
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.fasterxml.jackson.databind.node.IntNode
import java.io.IOException


class TimeDeserializer @JvmOverloads constructor(vc: Class<*>? = null) : StdDeserializer<Time?>(vc) {
    /* Deserializes type of { "type" : "close", "value" : 72000 } JSON to Time instances */

    @Throws(IOException::class, JsonProcessingException::class)
    override fun deserialize(jp: JsonParser, ctxt: DeserializationContext?): Time {
        val node: JsonNode = jp.codec.readTree(jp)
        val time = (node.get("value") as IntNode).numberValue() as Int
        val typeText = node.get("type").asText().toUpperCase()
        val type = Time.Action.valueOf(typeText)
        return Time(type, time)
    }
}