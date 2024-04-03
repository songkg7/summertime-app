package com.example.summertimeapp.summertime.controller

import com.example.summertimeapp.summertime.dto.SummerTimeResponse
import com.example.summertimeapp.summertime.service.SummerTimeService
import com.example.summertimeapp.worldtime.dto.WorldTimeResponse
import com.fasterxml.jackson.databind.ObjectMapper
import com.navercorp.fixturemonkey.FixtureMonkey
import com.navercorp.fixturemonkey.kotlin.KotlinPlugin
import com.navercorp.fixturemonkey.kotlin.giveMeBuilder
import com.navercorp.fixturemonkey.kotlin.setExp
import io.kotest.core.spec.style.FunSpec
import org.mockito.BDDMockito.given
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import java.time.LocalDateTime

@WebMvcTest
class SummerTimeControllerTest(
    @MockBean private val summerTimeService: SummerTimeService,
    private val mockMvc: MockMvc,
    private val objectMapper: ObjectMapper,
) : FunSpec({

    val fixture: FixtureMonkey = FixtureMonkey.builder()
        .plugin(KotlinPlugin())
        .build()

    context("continent 와 city 가 주어지면 daylight saving time 을 반환한다") {
        test("WorldTime API 의 response.dst 가 false 라면 dst 를 적용하지 않는다") {
            val timeString = "2021-07-01T00:00:00"
            val expected = SummerTimeResponse(LocalDateTime.parse(timeString))

            given(summerTimeService.request("Asia", "Seoul"))
                .willReturn(
                    fixture.giveMeBuilder<WorldTimeResponse>()
                        .setExp(WorldTimeResponse::dst, false)
                        .sample()
                )

            mockMvc.get("/summertime") {
                param("continent", "Asia")
                param("city", "Seoul")
                param("time", timeString)
            }.andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
                content { json(objectMapper.writeValueAsString(expected)) }
            }.andDo {
                print()
            }
        }

        test("WorldTime API 의 response.dst 가 true 라면 dst 를 적용한다") {
            val timeString = "2021-07-01T00:00:00"
            val offset = 32400
            val expected = SummerTimeResponse(LocalDateTime.parse(timeString).plusSeconds(offset.toLong()))

            given(summerTimeService.request("Asia", "Seoul"))
                .willReturn(
                    fixture.giveMeBuilder<WorldTimeResponse>()
                        .setExp(WorldTimeResponse::dst, true)
                        .setExp(WorldTimeResponse::dstOffset, offset)
                        .sample()
                )

            mockMvc.get("/summertime") {
                param("continent", "Asia")
                param("city", "Seoul")
                param("time", timeString)
            }.andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
                content { json(objectMapper.writeValueAsString(expected)) }
            }.andDo {
                print()
            }
        }
    }
})
