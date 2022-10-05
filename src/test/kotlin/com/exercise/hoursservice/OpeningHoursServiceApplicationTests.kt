package com.exercise.hoursservice

import org.junit.Assert
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class OpeningHoursServiceApplicationTests {
    @Autowired
    private val restTemplate: TestRestTemplate? = null
    private val urlTimes = "/times"

    @Test
    fun getRestaurantOpeningHoursAsHumanFormatFromPostRequest() {
        var data = """
			{
              "monday": [],
              "tuesday": [{"type": "open", "value": 36000}, {"type": "close", "value": 64800}],
              "wednesday": [],
              "thursday": [{"type": "open", "value": 36000}, {"type": "close", "value": 64800}],
              "friday": [{"type": "open", "value": 36000}],
              "saturday": [{"type": "close", "value": 3600}, {"type": "open", "value": 36000}],
              "sunday": [{"type": "close", "value": 3600}, {"type": "open", "value": 43200}, {"type": "close", "value": 75600}]
            }
		""".trimIndent()
        val resultString = sendPostRequest(data)
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

        Assert.assertEquals(expectedOutput.joinToString(ls), resultString.trim())
    }

    @Test
    fun getRestaurantOpeningHoursInvalidJsonBadRequest() {
        var data = """
			{
              "monday": [],
              "tuesday": [{"type": "INVALID_TYPE", "value": 36000}, {"type": "close", "value": 64800}],
              "wednesday": [],
              "thursday": [{"type": "open", "value": 36000}, {"type": "close", "value": 64800}],
              "friday": [{"type": "open", "value": 36000}],
              "saturday": [{"type": "close", "value": 3600}, {"type": "open", "value": 36000}],
              "sunday": [{"type": "close", "value": 3600}, {"type": "open", "value": 43200}, {"type": "close", "value": 75600}]
            }
		""".trimIndent()
        val resultsString = sendPostRequest(data)
        Assert.assertTrue(resultsString.contains("JSON parse error"))
    }

    private fun sendPostRequest(data: String): String {
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        val request = HttpEntity<String>(data, headers)

        return restTemplate!!.postForObject(urlTimes, request, String::class.java)
    }
}
