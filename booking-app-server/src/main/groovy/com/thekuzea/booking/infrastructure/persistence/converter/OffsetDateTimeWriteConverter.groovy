package com.thekuzea.booking.infrastructure.persistence.converter

import java.time.OffsetDateTime

import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.WritingConverter

@WritingConverter
class OffsetDateTimeWriteConverter implements Converter<OffsetDateTime, Date> {

    @Override
    Date convert(OffsetDateTime offsetDateTime) {
        Date.from(offsetDateTime.toInstant())
    }
}
