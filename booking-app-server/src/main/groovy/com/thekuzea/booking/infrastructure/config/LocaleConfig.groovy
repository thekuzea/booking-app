package com.thekuzea.booking.infrastructure.config

import java.util.function.Supplier
import javax.validation.Validator

import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ReloadableResourceBundleMessageSource
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean

import com.thekuzea.booking.support.util.FileUtil

@Configuration
class LocaleConfig {

    @Bean
    Supplier<Locale> localeSupplier() {
        return { Locale.ENGLISH }
    }

    @Bean
    MessageSource alertMessageSource() {
        final String resourcePath = "messages/alerts"
        final List<String> fileList = FileUtil.getMessagePropertiesFileWithinClasspath(resourcePath)

        final ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource()
        messageSource.setDefaultLocale(localeSupplier().get())
        fileList.forEach { messageSource.addBasenames(it) }

        messageSource
    }

    @Bean
    Validator validator() {
        final ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource()
        messageSource.setBasename("classpath:messages/validation")
        messageSource.setDefaultLocale(localeSupplier().get())

        final LocalValidatorFactoryBean factory = new LocalValidatorFactoryBean()
        factory.setValidationMessageSource(messageSource)

        factory
    }
}
