package com.example.summertimeapp.worldtime.client

import com.example.summertimeapp.worldtime.dto.WorldTimeResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(name = "world-time-api", url = "http://worldtimeapi.org/api")
interface WorldTimeClient {

    // TODO: header bypass
    @GetMapping("/timezone/{continent}/{city}")
    fun getTime(@PathVariable continent: String, @PathVariable city: String): ResponseEntity<WorldTimeResponse>
}
