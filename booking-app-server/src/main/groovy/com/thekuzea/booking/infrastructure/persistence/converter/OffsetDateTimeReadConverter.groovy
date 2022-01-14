package com.thekuzea.booking.infrastructure.persistence.converter

import java.time.ZoneOffset
import java.time.OffsetDateTime

import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter

@ReadingConverter
class OffsetDateTimeReadConverter implements Converter<Date, OffsetDateTime> {

    @Override
    OffsetDateTime convert(Date date) {
        date.toInstant().atOffset(ZoneOffset.UTC)
    }
}
