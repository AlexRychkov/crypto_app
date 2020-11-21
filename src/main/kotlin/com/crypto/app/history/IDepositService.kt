package com.crypto.app.history

import reactor.core.publisher.Flux
import java.math.BigDecimal
import java.time.LocalDateTime

interface IDepositService {
    fun add(datetime: LocalDateTime, amount: BigDecimal)

    fun history(startDateTime: LocalDateTime, endDateTime: LocalDateTime): Flux<DepositEntity>
}