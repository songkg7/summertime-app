package com.example.summertimeapp.summertime.controller

import com.example.summertimeapp.log
import com.example.summertimeapp.summertime.dto.SummerTimeRequest
import com.example.summertimeapp.summertime.dto.SummerTimeResponse
import com.example.summertimeapp.summertime.service.SummerTimeService
import com.example.summertimeapp.worldtime.dto.WorldTimeResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController


@RestController
class SummerTimeController(
    private val summerTimeService: SummerTimeService,
) {
    @GetMapping("/time/{continent}/{city}")
    fun getTime(@PathVariable continent: String, @PathVariable city: String): ResponseEntity<WorldTimeResponse> {
        log.info { "Getting time for $continent/$city" }
        return ResponseEntity.ok(summerTimeService.request(continent, city))
    }

    @GetMapping("/summertime")
    fun convertSummerTime(request: SummerTimeRequest): ResponseEntity<SummerTimeResponse> {
        val res = summerTimeService.request(request.continent, request.city)
        if (!res.dst) {
            log.info { "The requested time zone: ${request.continent}/${request.city} does not have daylight saving time." }
            return ResponseEntity.ok(SummerTimeResponse(request.time))
        }

        val dstTime = request.time.plusSeconds(res.dstOffset.toLong())
        return ResponseEntity.ok(SummerTimeResponse(dstTime))
    }
}
