package com.thekuzea.booking.support.alert;

import java.util.Locale;
import java.util.function.Supplier;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.logging.LogLevel;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlertService {

    private final MessageSource alertMessageSource;

    private final Supplier<Locale> localeSupplier;

    public String logAlertByCode(final AlertCode alertCode, final LogLevel logLevel, final String... params) {
        final String message = assembleMessageByCode(alertCode, params);
        logMessageByLogLevel(logLevel, message);

        return message;
    }

    private String assembleMessageByCode(final AlertCode alertCode, final String... params) {
        return alertMessageSource.getMessage(alertCode.name(), params, localeSupplier.get());
    }

    private static void logMessageByLogLevel(final LogLevel logLevel, final String message) {
        switch (logLevel) {
            case ERROR:
                log.error(message);
                break;
            case WARN:
                log.warn(message);
                break;
            case INFO:
                log.info(message);
                break;
            default:
                log.error("Non existing log level provided");
        }
    }
}
