package com.crypto.app.util

import java.math.BigDecimal

object AmountUtil {

    fun BigDecimal.toPrettyString(): String {
        return this.stripTrailingZeros().toPlainString()
    }
}