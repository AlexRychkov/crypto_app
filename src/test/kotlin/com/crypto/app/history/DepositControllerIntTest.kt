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
import java.math.BigDecimal

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
        val depositRequest = DepositRequest("2017-01-01T01:00:00+03:00", BigDecimal.TEN)

        Helper.createDeposit(webClient, depositRequest)
                .expectStatus().isNoContent
    }

    @Test
    fun `history - deposits in right range`() {
        val datetime1 = "2018-02-02T02:00:00+01:00"
        val amount1 = BigDecimal("1.0")
        val depositRequest1 = DepositRequest(datetime1, amount1)
        val datetime2 = "2019-01-01T03:00:00+02:00"
        val amount2 = BigDecimal("1.01")
        val depositRequest2 = DepositRequest(datetime2, amount2)

        Helper.createDeposit(webClient, depositRequest1)
        Helper.createDeposit(webClient, depositRequest2)

        Helper.getDeposits(webClient, "2018-01-01T00:00:00+00:00", "2018-12-12T00:00:00+00:00")
                .expectStatus().isOk
                .expectBodyList(HistoryResponse::class.java)
                .contains(HistoryResponse("2018-02-02T01:00:00+00:00", amount1))

        Helper.getDeposits(webClient, "2019-01-01T00:00:00+00:00", "2019-12-12T00:00:00+00:00")
                .expectStatus().isOk
                .expectBodyList(HistoryResponse::class.java)
                .contains(HistoryResponse("2019-01-01T01:00:00+00:00", amount2))
    }

    @Test
    fun `history - right balance for every hour`() {
        val datetime1 = "2020-01-01T00:10:00+00:00"
        val datetime2 = "2020-01-01T00:20:00+00:00"
        val datetime3 = "2020-01-01T00:25:00+00:00"
        val datetime4 = "2020-01-01T01:00:00+00:00"
        val datetime5 = "2020-01-01T01:10:00+00:00"
        val amount = BigDecimal("1.0")

        val depositsRequests = listOf(
                DepositRequest(datetime1, amount),
                DepositRequest(datetime2, amount),
                DepositRequest(datetime3, amount),
                DepositRequest(datetime4, amount),
                DepositRequest(datetime5, amount)
        )
        Helper.createDeposits(webClient, depositsRequests)

        Helper.getDeposits(webClient, "2020-01-01T00:00:00+00:00", "2020-01-01T05:10:00+00:00")
                .expectBodyList(HistoryResponse::class.java)
                .contains(
                        HistoryResponse("2020-01-01T00:00:00+00:00", BigDecimal("3.0")),
                        HistoryResponse("2020-01-01T01:00:00+00:00", BigDecimal("2.0"))
                )
    }
}