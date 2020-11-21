package com.crypto.app.history

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest
@AutoConfigureWebTestClient
@RunWith(SpringRunner::class)
@AutoConfigureJsonTesters
class DepositControllerTest {
    @Autowired
    private lateinit var webClient: WebTestClient

    @Test
    fun `correct datetime format`() {
        Helper.getDeposits(webClient, "2019-10-05T13:00:00+00:00", "2019-10-06T13:00:00+00:00")
                .expectStatus().isOk
    }

    @Test
    fun `incorrect datetime format - no timezone`() {
        Helper.getDeposits(webClient, "2019-10-05T13:00:00", "2019-10-06T13:00:00+00:00")
                .expectStatus().isBadRequest
    }

    @Test
    fun `incorrect datetime format - no time`() {
        Helper.getDeposits(webClient, "2019-10-05", "2019-10-06T13:00:00+00:00")
                .expectStatus().isBadRequest
    }

    @Test
    fun `incorrect datetime format - no date`() {
        Helper.getDeposits(webClient, "1234", "2019-10-06T13:00:00+00:00")
                .expectStatus().isBadRequest
    }
}