package com.crypto.app.history

import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import java.time.ZonedDateTime

@Repository
interface IDepositRepository : ReactiveCrudRepository<Deposit, Long> {
    @Query("""
        select datet as datetime, amount 
        from (
            select date_trunc('hour', datetime) as datet, sum(amount::real) as amount
            from deposit
            where datetime >= :startDatetime and datetime <= :endDatetime
            group by datet
        ) as subquery
    """)
    fun findAllByDatetimeBetweenAndGroupByHour(startDatetime: ZonedDateTime, endDatetime: ZonedDateTime): Flux<Deposit>
}