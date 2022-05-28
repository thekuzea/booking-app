package com.thekuzea.booking.support.util;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DateTimeUtil {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

    public static String convertOffsetDateTimeToString(final OffsetDateTime offsetDateTime) {
        return FORMATTER.format(offsetDateTime.truncatedTo(ChronoUnit.SECONDS));
    }

    public static OffsetDateTime convertStringToOffsetDateTime(final String dateTime) {
        return OffsetDateTime.parse(dateTime, FORMATTER).truncatedTo(ChronoUnit.SECONDS);
    }
}
