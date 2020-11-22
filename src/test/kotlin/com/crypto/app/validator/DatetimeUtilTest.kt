package com.crypto.app.validator

import com.crypto.app.util.DatetimeUtil
import com.crypto.app.util.DatetimeUtil.toPrettyString
import com.crypto.app.util.DatetimeUtil.toUtcString
import io.kotest.matchers.shouldBe
import org.junit.Test
import java.time.format.DateTimeParseException

class DatetimeUtilTest {
    @Test
    fun `correct datetime`() {
        val datetime = DatetimeUtil.convert("2020-01-03T12:13:11+03:00")
        datetime.year shouldBe 2020
        datetime.month.value shouldBe 1
        datetime.dayOfMonth shouldBe 3
        datetime.hour shouldBe 12
        datetime.minute shouldBe 13
        datetime.second shouldBe 11

        println(datetime.toString())
    }

    @Test(expected = DateTimeParseException::class)
    fun `incorrect datetime - no date`() {
        DatetimeUtil.convert("12:00:01+03:00")
    }

    @Test(expected = DateTimeParseException::class)
    fun `incorrect datetime - no seconds`() {
        DatetimeUtil.convert("2020-01-03T12:00+03:00")
    }

    @Test(expected = DateTimeParseException::class)
    fun `incorrect datetime - no time`() {
        DatetimeUtil.convert("2020-01-03T+03:00")
    }

    @Test(expected = DateTimeParseException::class)
    fun `incorrect datetime - just number`() {
        DatetimeUtil.convert("21414")
    }

    @Test(expected = DateTimeParseException::class)
    fun `incorrect datetime - no T between date and time`() {
        DatetimeUtil.convert("2020-01-0312:00:01+03:00")
    }

    @Test(expected = DateTimeParseException::class)
    fun `incorrect datetime - no time zone`() {
        DatetimeUtil.convert("2020-01-0312:00:01")
    }

    @Test
    fun `Moscow tz to utc`() {
        DatetimeUtil.convert("2020-01-03T12:13:11+03:00").toUtcString() shouldBe "2020-01-03T09:13:11+00:00"
    }

    @Test
    fun `to utc contains minutes`() {
        DatetimeUtil.convert("2020-01-03T06:00:00+00:00").toUtcString() shouldBe "2020-01-03T06:00:00+00:00"
    }

    @Test
    fun `to pretty string not ignore zero seconds`() {
        DatetimeUtil.convert("2020-01-03T06:00:00+03:00").toPrettyString() shouldBe "2020-01-03T06:00:00+03:00"
    }
}