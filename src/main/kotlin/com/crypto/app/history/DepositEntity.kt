package com.crypto.app.history

import java.math.BigDecimal
import java.time.LocalDateTime

data class DepositEntity(val datetime: LocalDateTime, val amount: BigDecimal)