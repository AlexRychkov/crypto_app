package com.crypto.app.history

import com.crypto.app.history.dto.DepositRequest
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import java.time.LocalDateTime

object Helper {
    val API = "/api/deposit"

    fun getDeposits(webClient: WebTestClient, startDatetime: String, endDatetime: String): WebTestClient.ResponseSpec {
        return webClient.get()
                .uri { uriBuilder ->
                    uriBuilder.path(API)
                            .queryParam("startDatetime", startDatetime)
                            .queryParam("endDatetime", endDatetime)
                            .build()
                }
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
    }

    fun getDeposits(webClient: WebTestClient, startDatetime: LocalDateTime, endDatetime: LocalDateTime): WebTestClient.ResponseSpec {
        return webClient.get()
                .uri { uriBuilder ->
                    uriBuilder.path(API)
                            .queryParam("startDatetime", startDatetime)
                            .queryParam("endDatetime", endDatetime)
                            .build()
                }
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
    }

    fun createDeposit(webClient: WebTestClient, depositRequest: DepositRequest): WebTestClient.ResponseSpec {
        return webClient.post()
                .uri(API)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(depositRequest)
                .exchange()
    }

    fun createDeposits(webClient: WebTestClient, depositRequests: List<DepositRequest>) {
        depositRequests.forEach {
            createDeposit(webClient, it)
        }
    }
}