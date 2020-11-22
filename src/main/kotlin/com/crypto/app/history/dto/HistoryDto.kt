package com.crypto.app.history.dto

import java.math.BigDecimal

data class HistoryRequest(val startDatetime: String, val endDatetime: String)

data class HistoryResponse(val datetime: String, val amount: BigDecimal)