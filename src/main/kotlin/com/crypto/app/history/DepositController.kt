package com.crypto.app.history

import com.crypto.app.history.dto.DepositRequest
import com.crypto.app.history.dto.HistoryRequest
import com.crypto.app.history.dto.HistoryResponse
import com.crypto.app.util.AmountUtil.toPrettyString
import com.crypto.app.util.DatetimeUtil
import com.crypto.app.util.DatetimeUtil.toUtcString
import com.crypto.app.validator.AmountValidator
import com.crypto.app.validator.DatetimeValidator
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component
class DepositController(
        private val depositService: IDepositService
) : IDepositController {

    override fun history(historyRequest: HistoryRequest): Flux<HistoryResponse> {
        val start = DatetimeUtil.convert(historyRequest.startDatetime)
        val end = DatetimeUtil.convert(historyRequest.endDatetime)
        DatetimeValidator.validate(start, end)
        return depositService.history(start, end).map { HistoryResponse(it.datetime.toUtcString(), it.amount.toPrettyString()) }
    }

    override fun add(depositRequest: DepositRequest): Mono<Void> {
        val datetime = DatetimeUtil.convert(depositRequest.datetime)
        AmountValidator.validate(depositRequest.amount)
        return depositService.add(datetime, depositRequest.amount).then()
    }
}