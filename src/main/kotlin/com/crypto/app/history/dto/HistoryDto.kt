package com.crypto.app.history.dto

import java.math.BigDecimal
import java.time.LocalDateTime

data class HistoryRequest(val startDatetime: LocalDateTime, val endDatetime: LocalDateTime)

data class HistoryResponse(val datetime: LocalDateTime, val amount: BigDecimal)