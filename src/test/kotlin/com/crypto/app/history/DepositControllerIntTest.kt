package com.crypto.app.history

import com.crypto.app.history.dto.DepositRequest
import com.crypto.app.history.dto.HistoryResponse
import com.crypto.app.testcontainer.EmbeddedPostgresConfig
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody
import java.math.BigDecimal
import java.time.LocalDateTime

@SpringBootTest
@AutoConfigureWebTestClient
@RunWith(SpringRunner::class)
@AutoConfigureJsonTesters
@ContextConfiguration(initializers = [EmbeddedPostgresConfig::class])
class DepositControllerIntTest {

    @Autowired
    private lateinit var webClient: WebTestClient

    @Test
    fun `one deposit`() {
        val depositRequest = DepositRequest(LocalDateTime.of(2017, 1, 1, 1, 0), BigDecimal.TEN)

        Helper.createDeposit(webClient, depositRequest)
                .expectStatus().isNoContent
    }

    @Test
    fun `history - deposits in right range`() {
        val datetime1 = LocalDateTime.of(2018, 2, 2, 2, 0)
        val amount1 = BigDecimal.ONE
        val depositRequest1 = DepositRequest(datetime1, amount1)
        val datetime2 = LocalDateTime.of(2019, 1, 1, 1, 0)
        val amount2 = BigDecimal("1.01")
        val depositRequest2 = DepositRequest(datetime2, amount2)

        Helper.createDeposit(webClient, depositRequest1)
        Helper.createDeposit(webClient, depositRequest2)

        Helper.getDeposits(webClient, LocalDateTime.of(2018, 1, 1, 0, 0), LocalDateTime.of(2018, 12, 12, 0, 0))
                .expectStatus().isOk
                .expectBody<HistoryResponse>()
                .isEqualTo(HistoryResponse(datetime1, amount1))

        Helper.getDeposits(webClient, LocalDateTime.of(2019, 1, 1, 0, 0), LocalDateTime.of(2019, 12, 12, 0, 0))
                .expectStatus().isOk
                .expectBody<HistoryResponse>()
                .isEqualTo(HistoryResponse(datetime2, amount2))
    }

    @Test
    fun `history - right balance for every hour`() {
        val firstHour = 0
        val secondHour = 1
        val datetime1 = LocalDateTime.of(2020, 1, 1, firstHour, 10)
        val datetime2 = LocalDateTime.of(2020, 1, 1, firstHour, 20)
        val datetime3 = LocalDateTime.of(2020, 1, 1, firstHour, 25)
        val datetime4 = LocalDateTime.of(2020, 1, 1, secondHour, 0)
        val datetime5 = LocalDateTime.of(2020, 1, 1, secondHour, 10)
        val amount = BigDecimal.ONE

        val depositsRequests = listOf(
                DepositRequest(datetime1, amount),
                DepositRequest(datetime2, amount),
                DepositRequest(datetime3, amount),
                DepositRequest(datetime4, amount),
                DepositRequest(datetime5, amount)
        )
        Helper.createDeposits(webClient, depositsRequests)

        Helper.getDeposits(webClient, LocalDateTime.of(2020, 1, 1, firstHour, 0), LocalDateTime.of(2020, 1, 1, secondHour + 10, 0))
                .expectBodyList(HistoryResponse::class.java)
                .contains(
                        HistoryResponse(LocalDateTime.of(2020, 1, 1, firstHour, 0), BigDecimal.valueOf(3L)),
                        HistoryResponse(LocalDateTime.of(2020, 1, 1, secondHour, 0), BigDecimal.valueOf(2L))
                )
    }
}