package com.thekuzea.booking.support.alert

import java.util.function.Supplier

import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.logging.LogLevel
import org.springframework.context.MessageSource
import org.springframework.stereotype.Service

@Slf4j
@Service
class AlertService {

    @Autowired
    MessageSource alertMessageSource

    @Autowired
    Supplier<Locale> localeSupplier

    void throwException(final AlertCode alertCode,
                        final Class<? extends Exception> clazz,
                        final String... params) {

        final String message = assembleMessageByCode(alertCode, params)
        throw clazz.getDeclaredConstructor(String.class).newInstance(message)
    }

    void logMessageByCode(final AlertCode alertCode,
                          final LogLevel logLevel,
                          final String... params) {

        final String message = assembleMessageByCode(alertCode, params)
        logMessageByLogLevel(logLevel, message)
    }

    void logAndThrowException(final AlertCode alertCode,
                              final Class<? extends Exception> clazz,
                              final LogLevel logLevel,
                              final String... params) {

        logMessageByCode(alertCode, logLevel, params)
        throwException(alertCode, clazz, params)
    }

    void logAndRethrowException(final AlertCode alertCode,
                                final Exception exception,
                                final LogLevel logLevel,
                                final String... params) {

        logMessageByCode(alertCode, logLevel, params)
        throw exception
    }

    private String assembleMessageByCode(final AlertCode alertCode, final String... params) {
        alertMessageSource.getMessage(alertCode.name(), params, localeSupplier.get())
    }

    private static void logMessageByLogLevel(final LogLevel logLevel, final String message) {
        switch (logLevel) {
            case LogLevel.ERROR: log.error(message); break
            case LogLevel.WARN: log.warn(message); break
            case LogLevel.INFO: log.info(message); break
        }
    }
}
