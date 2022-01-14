package com.thekuzea.booking.infrastructure.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.convert.converter.Converter
import org.springframework.data.mongodb.core.convert.MongoCustomConversions
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

import com.thekuzea.booking.infrastructure.persistence.converter.OffsetDateTimeReadConverter
import com.thekuzea.booking.infrastructure.persistence.converter.OffsetDateTimeWriteConverter

@Configuration
@EnableMongoRepositories(basePackages = "com.thekuzea.booking.infrastructure.persistence")
class PersistenceConfig {

    @Bean
    MongoCustomConversions mongoCustomConversions() {
        final List<Converter<?, ?>> converterList = new ArrayList<Converter<?, ?>>()
        converterList.add(new OffsetDateTimeReadConverter())
        converterList.add(new OffsetDateTimeWriteConverter())

        new MongoCustomConversions(converterList)
    }
}
