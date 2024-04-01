package com.example.summertimeapp.summertime.service

import com.example.summertimeapp.summertime.dto.SummerTimeResponse
import com.example.summertimeapp.worldtime.client.WorldTimeClient
import com.example.summertimeapp.worldtime.dto.WorldTimeResponse
import com.navercorp.fixturemonkey.FixtureMonkey
import com.navercorp.fixturemonkey.kotlin.KotlinPlugin
import com.navercorp.fixturemonkey.kotlin.giveMeBuilder
import com.navercorp.fixturemonkey.kotlin.setExp
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.springframework.http.ResponseEntity
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime

// FIXME: 테스트 코드의 depth 가 마음에 들지 않음
class SummerTimeServiceTest : FreeSpec({

    val fixture = FixtureMonkey.builder()
        .plugin(KotlinPlugin())
        .build()
    val mockClient = mockk<WorldTimeClient>()
    val service = SummerTimeService(mockClient)

    "convertSummerTime" - {
        "서머타임 기간이 아닐 경우" - {
            "번환하지 않는다" {
                val expected = LocalDateTime.parse("2021-06-01T00:00:00")
                val response = fixture.giveMeBuilder<WorldTimeResponse>()
                    .setExp(WorldTimeResponse::dst, false)
                    .sample()
                every { mockClient.getTime("Europe", "Berlin") } returns ResponseEntity.ok(response)

                val actual = service.convertSummerTime("Europe", "Berlin", expected)

                actual shouldBe ResponseEntity.noContent().build()
            }
        }

        "서머타임 기간일 경우" - {
            val expected = LocalDateTime.parse("2021-06-01T00:00:00")
            val timezone = "America/Chicago"
            val summerTimeBuilder = fixture.giveMeBuilder<WorldTimeResponse>()
                .setExp(WorldTimeResponse::dst, true)
                .setExp(WorldTimeResponse::timezone, timezone)
                .setExp(WorldTimeResponse::dstOffset, 3600)

            "요청된 시간이 현재 변환가능한 시간대에 포함되어 있는 경우" - {
                val requestTimeIsBetween = summerTimeBuilder
                    .setExp(WorldTimeResponse::dstFrom, ZonedDateTime.of(expected.minusDays(1), ZoneId.of(timezone)))
                    .setExp(WorldTimeResponse::dstUntil, ZonedDateTime.of(expected.plusDays(1), ZoneId.of(timezone)))

                "dstOffset 만큼 시간을 더해준다" {
                    every { mockClient.getTime("America", "Chicago") } returns ResponseEntity.ok(requestTimeIsBetween.sample())

                    val actual = service.convertSummerTime("America", "Chicago", expected)

                    actual.body shouldBe SummerTimeResponse(expected.plusHours(1))
                }
            }

            "요청된 시간이 현재 변환가능한 시간대에 포함되어 있지 않은 경우" - {
                "IllegalArgumentException 을 던진다" {
                    val tooLongAgoTime = LocalDateTime.parse("2000-01-01T00:00:00")

                    every { mockClient.getTime("America", "Chicago") } returns ResponseEntity.ok(summerTimeBuilder.sample())

                    shouldThrow<IllegalArgumentException> {
                        service.convertSummerTime("America", "Chicago", tooLongAgoTime)
                    }
                }
            }
        }
    }
})
