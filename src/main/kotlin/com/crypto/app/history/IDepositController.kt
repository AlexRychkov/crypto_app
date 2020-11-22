package com.crypto.app.history

import com.crypto.app.history.dto.DepositRequest
import com.crypto.app.history.dto.HistoryRequest
import com.crypto.app.history.dto.HistoryResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/deposit")
interface IDepositController {
    @GetMapping
    fun history(@RequestBody historyRequest: HistoryRequest): Flux<HistoryResponse>

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun add(@RequestBody depositRequest: DepositRequest): Mono<Void>
}