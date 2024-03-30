package com.example.summertimeapp

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

val log = KotlinLogging.logger {}

@SpringBootApplication
class SummertimeAppApplication

fun main(args: Array<String>) {
    runApplication<SummertimeAppApplication>(*args)
}
