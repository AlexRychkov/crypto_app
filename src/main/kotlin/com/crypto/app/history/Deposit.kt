package com.crypto.app.history

import org.springframework.data.annotation.Id
import java.math.BigDecimal
import java.time.ZonedDateTime

data class Deposit(val datetime: ZonedDateTime, val amount: BigDecimal, @Id val id: Long? = null)