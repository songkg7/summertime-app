package com.example.summertimeapp.summertime.service

import com.example.summertimeapp.summertime.dto.SummerTimeResponse
import com.example.summertimeapp.worldtime.client.WorldTimeClient
import com.example.summertimeapp.worldtime.dto.WorldTimeResponse
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime

@Service
class SummerTimeService(
    private val worldTimeClient: WorldTimeClient,
) {
    fun request(continent: String, city: String) = worldTimeClient.getTime(continent, city)
    fun convertSummerTime(continent: String, city: String, time: LocalDateTime): ResponseEntity<SummerTimeResponse> {
        val worldTimeResponse = request(continent, city)
        val worldTimeBody = worldTimeResponse.body!!

        if (!worldTimeBody.dst) {
            return ResponseEntity.noContent().build()
        }

        require(isValidRequestTime(time, worldTimeBody)) {
            "The requested time is not in the current conversion time zone."
        }

        val result = time.plusSeconds(worldTimeBody.dstOffset.toLong())
        return ResponseEntity.ok().body(SummerTimeResponse(result))
    }

    private fun isValidRequestTime(
        time: LocalDateTime,
        response: WorldTimeResponse,
    ) = ZonedDateTime.of(time, ZoneId.of(response.timezone)) in response.dstFrom!!..response.dstUntil!!
}
