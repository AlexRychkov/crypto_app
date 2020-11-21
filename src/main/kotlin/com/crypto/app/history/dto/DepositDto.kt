package com.crypto.app.history.dto

import java.math.BigDecimal
import java.time.LocalDateTime

data class DepositRequest(val datetime: LocalDateTime, val amount: BigDecimal)