package com.example.summertimeapp.summertime.service

import com.example.summertimeapp.worldtime.client.WorldTimeClient
import com.example.summertimeapp.worldtime.dto.WorldTimeResponse
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime

@Service
class SummerTimeService(
    private val worldTimeClient: WorldTimeClient,
) {
    fun request(continent: String, city: String) = worldTimeClient.getTime(continent, city)
    fun convertSummerTime(continent: String, city: String, time: LocalDateTime): LocalDateTime {
        val worldTimeResponse = request(continent, city)
        if (!worldTimeResponse.dst) {
            return time
        }

        require(isValidRequestTime(time, worldTimeResponse)) {
            "The requested time is not in the current conversion time zone."
        }

        return time.plusSeconds(worldTimeResponse.dstOffset.toLong())
    }

    private fun isValidRequestTime(
        time: LocalDateTime,
        response: WorldTimeResponse,
    ) = ZonedDateTime.of(time, ZoneId.of(response.timezone)) in response.dstFrom!!..response.dstUntil!!
}