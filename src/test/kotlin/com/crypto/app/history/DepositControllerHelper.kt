package com.crypto.app.history

import com.crypto.app.history.dto.DepositRequest
import com.crypto.app.history.dto.HistoryRequest
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient

object Helper {
    val API = "/api/deposit"

    fun getDepositsJsonBody(webClient: WebTestClient, startDatetime: String, endDatetime: String): WebTestClient.ResponseSpec {
        return webClient.method(HttpMethod.GET)
                .uri(API)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{ \"startDatetime\": \"$startDatetime\", \"endDatetime\": \"$endDatetime\" }")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
    }

    fun getDeposits(webClient: WebTestClient, startDatetime: String, endDatetime: String): WebTestClient.ResponseSpec {
        return webClient.method(HttpMethod.GET)
                .uri(API)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(HistoryRequest(startDatetime, endDatetime))
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