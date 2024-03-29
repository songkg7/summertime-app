package com.example.summertimeapp.worldtime

import com.example.summertimeapp.log
import com.example.summertimeapp.worldtime.dto.SummerTimeRequest
import com.example.summertimeapp.worldtime.dto.SummerTimeResponse
import com.example.summertimeapp.worldtime.dto.WorldTimeResponse
import com.example.summertimeapp.worldtime.service.WorldTimeService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController


@RestController
class SummerTimeController(
    private val worldTimeService: WorldTimeService,
) {
    @GetMapping("/time/{continent}/{city}")
    fun getTime(@PathVariable continent: String, @PathVariable city: String): WorldTimeResponse {
        log.info { "Getting time for $continent/$city" }
        return worldTimeService.request(continent, city)
    }

    @GetMapping("/summertime")
    fun convertSummerTime(request: SummerTimeRequest): SummerTimeResponse {
        log.info { "Converting time to summer time" }
        worldTimeService.convertSummerTime(
            request.continent,
            request.city,
            request.time
        ).let {
            return SummerTimeResponse(it)
        }
    }
}
