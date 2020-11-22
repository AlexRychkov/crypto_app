package com.crypto.app.util

import com.crypto.app.util.AmountUtil.toPrettyString
import io.kotest.matchers.shouldBe
import org.junit.Test
import java.math.BigDecimal

class AmountUtilTest {
    @Test
    fun `correct part after decimal for non-zero`() {
        BigDecimal("1.14").toPrettyString() shouldBe "1.14"
    }

    @Test
    fun `correct part after decimal with trailing zeros`() {
        BigDecimal("1.1400").toPrettyString() shouldBe "1.14"
    }

    @Test
    fun `correct part after decimal with only zeros`() {
        BigDecimal("1.000").toPrettyString() shouldBe "1"
    }

    @Test
    fun `negative with trailing zeros`() {
        BigDecimal("-241.000").toPrettyString() shouldBe "-241"
    }

    @Test
    fun `negative with one trailing zero`() {
        BigDecimal("-241.070").toPrettyString() shouldBe "-241.07"
    }
}