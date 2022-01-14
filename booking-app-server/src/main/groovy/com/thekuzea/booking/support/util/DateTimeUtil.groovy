package com.thekuzea.booking.support.util

import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

final class DateTimeUtil {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_OFFSET_DATE_TIME

    private DateTimeUtil() {
    }

    static String convertOffsetDateTimeToString(final OffsetDateTime offsetDateTime) {
        FORMATTER.format(offsetDateTime.truncatedTo(ChronoUnit.SECONDS))
    }

    static OffsetDateTime convertStringToOffsetDateTime(final String dateTime) {
        OffsetDateTime.parse(dateTime, FORMATTER).truncatedTo(ChronoUnit.SECONDS)
    }
}
