package com.example.summertimeapp.worldtime.service

import com.example.summertimeapp.worldtime.client.WorldTimeClient
import com.example.summertimeapp.worldtime.dto.WorldTimeResponse
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime

@Service
class WorldTimeService(
    private val worldTimeClient: WorldTimeClient,
) {
    fun getTime(continent: String, city: String) = worldTimeClient.getTime(continent, city)
    fun convertSummerTime(continent: String, city: String, time: LocalDateTime): LocalDateTime {
        val timezone = ZoneId.of("$continent/$city")

        val worldTimeResponse = getTime(continent, city)
        if (!worldTimeResponse.dst) {
            return time;
        }

        require(isValidRquestTime(time, timezone, worldTimeResponse)) {
            "The requested time is not in the current conversion time zone."
        }

        return time.plusSeconds(worldTimeResponse.dstOffset.toLong());
    }

    private fun isValidRquestTime(
        time: LocalDateTime,
        timezone: ZoneId?,
        worldTimeResponse: WorldTimeResponse,
    ) = ZonedDateTime.of(time, timezone) in worldTimeResponse.dstFrom!!..worldTimeResponse.dstUntil!!
}