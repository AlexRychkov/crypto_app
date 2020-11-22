package com.crypto.app.history.dto

import java.math.BigDecimal

data class DepositRequest(val datetime: String, val amount: BigDecimal)

data class DepositResponse(val datetime: String, val amount: BigDecimal)