package com.example.summertimeapp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@EnableFeignClients
@SpringBootApplication
class SummertimeAppApplication

fun main(args: Array<String>) {
    runApplication<SummertimeAppApplication>(*args)
}
