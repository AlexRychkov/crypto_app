package com.crypto.app.history

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.math.BigDecimal
import java.time.ZonedDateTime

interface IDepositService {
    fun add(datetime: ZonedDateTime, amount: BigDecimal): Mono<Deposit>

    fun history(startDateTime: ZonedDateTime, endDateTime: ZonedDateTime): Flux<Deposit>
}