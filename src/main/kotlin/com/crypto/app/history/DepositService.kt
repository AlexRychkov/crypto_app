package com.crypto.app.history

import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.math.BigDecimal
import java.time.ZonedDateTime

@Service
class DepositService(
        private val depositRepository: IDepositRepository
) : IDepositService {

    override fun add(datetime: ZonedDateTime, amount: BigDecimal): Mono<Deposit> {
        return depositRepository.save(Deposit(datetime, amount))
    }

    override fun history(startDateTime: ZonedDateTime, endDateTime: ZonedDateTime): Flux<Deposit> {
        return depositRepository.findAllByDatetimeBetweenAndGroupByHour(startDateTime, endDateTime)
    }
}