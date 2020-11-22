package com.crypto.app.validator

import java.time.ZonedDateTime

object DatetimeValidator {
    fun validate(startDatetime: ZonedDateTime, endDatetime: ZonedDateTime) {
        if (startDatetime.isAfter(endDatetime) || endDatetime.isEqual(startDatetime)) {
            throw IllegalArgumentException("Date startDatetime should be before endDatetime")
        }
    }
}