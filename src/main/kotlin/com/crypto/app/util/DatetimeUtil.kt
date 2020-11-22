package com.crypto.app.util

import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.time.temporal.ChronoField.*

object DatetimeUtil {
    private val formatter = DateTimeFormatterBuilder()
            .parseCaseSensitive()
            .append(DateTimeFormatter.ISO_LOCAL_DATE)
            .appendLiteral('T')
            .appendValue(HOUR_OF_DAY, 2)
            .appendLiteral(':')
            .appendValue(MINUTE_OF_HOUR, 2)
            .appendLiteral(':')
            .appendValue(SECOND_OF_MINUTE, 2)
            .parseStrict()
            .appendOffsetId()
            .parseStrict()
            .toFormatter()
    private val UTC = ZoneId.of("+00:00")

    fun convert(datetime: String): ZonedDateTime {
        return ZonedDateTime.parse(datetime, formatter)
    }

    fun ZonedDateTime.toUtc(): ZonedDateTime {
        return this.withZoneSameInstant(UTC)
    }

    fun ZonedDateTime.toPrettyString(): String {
        return this.format(formatter)
    }

    fun ZonedDateTime.toUtcString(): String {
        return this.toUtc().format(formatter).replace("Z", "+00:00")
    }
}