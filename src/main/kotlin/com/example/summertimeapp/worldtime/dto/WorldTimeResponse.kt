package com.example.summertimeapp.worldtime.dto

import java.time.ZonedDateTime

// TODO: change response string format
data class WorldTimeResponse(
    val abbreviation: String?,
    val client_ip: String?,
    val datetime: ZonedDateTime?,
    val day_of_week: Int,
    val day_of_year: Int,
    val dst: Boolean,
    val dst_from: ZonedDateTime?,
    val dst_offset: Int,
    val dst_until: ZonedDateTime?,
    val raw_offset: Int,
    val timezone: String,
    val unixtime: Int,
    val utc_datetime: ZonedDateTime,
    val utc_offset: String,
    val week_number: Int
)
