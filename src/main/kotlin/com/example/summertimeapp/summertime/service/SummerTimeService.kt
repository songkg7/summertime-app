package com.example.summertimeapp.summertime.service

import com.example.summertimeapp.worldtime.client.WorldTimeClient
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class SummerTimeService(
    private val worldTimeClient: WorldTimeClient,
) {
    @Cacheable(value = ["world-time"], key = "{#continent, #city}")
    fun request(continent: String, city: String) = worldTimeClient.getTime(continent, city)
}
