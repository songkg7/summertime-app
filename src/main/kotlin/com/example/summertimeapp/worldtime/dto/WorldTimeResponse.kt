package com.example.summertimeapp.worldtime.dto

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import java.time.ZonedDateTime

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class WorldTimeResponse(
    val abbreviation: String?,
    val clientIp: String?,
    val datetime: ZonedDateTime?,
    val dayOfWeek: Int,
    val dayOfYear: Int,
    val dst: Boolean,
    val dstFrom: ZonedDateTime?,
    val dstOffset: Int,
    val dstUntil: ZonedDateTime?,
    val rawOffset: Int,
    val timezone: String,
    val unixtime: Int,
    val utcDatetime: ZonedDateTime,
    val utcOffset: String,
    val weekNumber: Int
)
