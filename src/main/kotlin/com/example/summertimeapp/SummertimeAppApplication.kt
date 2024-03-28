package com.example.summertimeapp

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

val log = KotlinLogging.logger {}

@EnableFeignClients
@SpringBootApplication
class SummertimeAppApplication

fun main(args: Array<String>) {
    runApplication<SummertimeAppApplication>(*args)
}
