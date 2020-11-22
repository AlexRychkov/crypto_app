package com.crypto.app.history

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.math.BigDecimal
import java.time.ZonedDateTime

@SpringBootTest
@AutoConfigureWebTestClient
@RunWith(SpringRunner::class)
@AutoConfigureJsonTesters
class DepositControllerTest {
    @Autowired
    private lateinit var webClient: WebTestClient

    @MockBean
    private lateinit var depositService: DepositService

    @Before
    fun setup() {
        val deposit = Mono.just(Deposit(ZonedDateTime.now(), BigDecimal.TEN))
        whenever(depositService.history(any(), any())).thenReturn(Flux.from(deposit))
    }

    @Test
    fun `correct datetime format`() {
        Helper.getDepositsJsonBody(webClient, "2019-10-05T13:00:00+01:00", "2019-10-06T13:00:00+00:00")
                .expectStatus().isOk
    }

    @Test
    fun `incorrect datetime format - no timezone`() {
        Helper.getDepositsJsonBody(webClient, "2019-10-05T13:00:00", "2019-10-06T13:00:00+04:00")
                .expectStatus().isBadRequest
    }

    @Test
    fun `incorrect datetime format - no time`() {
        Helper.getDepositsJsonBody(webClient, "2019-10-05", "2019-10-06T13:00:00+00:00")
                .expectStatus().isBadRequest
    }

    @Test
    fun `incorrect datetime format - no date`() {
        Helper.getDepositsJsonBody(webClient, "1234", "2019-10-06T13:00:00+00:00")
                .expectStatus().isBadRequest
    }

    @Test
    fun `timezone not lost - start date greater than end date`() {
        Helper.getDepositsJsonBody(webClient, "2019-10-05T13:00:00+01:00", "2019-10-05T13:00:00+03:00")
                .expectStatus().isBadRequest
    }
}