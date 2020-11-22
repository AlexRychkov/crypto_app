package com.crypto.app.validator

import java.math.BigDecimal

object AmountValidator {
    fun validate(amount: BigDecimal) {
        if (amount == BigDecimal.ZERO) {
            throw IllegalArgumentException("Amount should be positive")
        }
    }
}