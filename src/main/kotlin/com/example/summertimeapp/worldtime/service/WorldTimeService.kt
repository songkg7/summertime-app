package com.example.summertimeapp.worldtime.service

import com.example.summertimeapp.worldtime.client.WorldTimeClient
import org.springframework.stereotype.Service

@Service
class WorldTimeService(
        private val worldTimeClient: WorldTimeClient
) {
    fun getTime(continent: String, city: String) = worldTimeClient.getTime(continent, city)
}