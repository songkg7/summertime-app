package com.example.summertimeapp.summertime.dto

import java.time.LocalDateTime
import java.time.ZonedDateTime

data class SummerTimeRequest(
    val continent: String,
    val city: String,
    val time: LocalDateTime,
)
