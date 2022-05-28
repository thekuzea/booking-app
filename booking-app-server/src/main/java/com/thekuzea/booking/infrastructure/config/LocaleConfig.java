package com.thekuzea.booking.infrastructure.config;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.function.Supplier;
import javax.validation.Validator;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.thekuzea.booking.support.util.FileUtil;

@Configuration
public class LocaleConfig {

    @Bean
    public Supplier<Locale> localeSupplier() {
        return () -> Locale.ENGLISH;
    }

    @Bean
    public MessageSource alertMessageSource() throws IOException {
        final String resourcePath = "messages/alerts";
        final List<String> fileList = FileUtil.getMessagePropertiesFileWithinClasspath(resourcePath);

        final ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setDefaultLocale(localeSupplier().get());
        fileList.forEach(messageSource::addBasenames);

        return messageSource;
    }

    @Bean
    public Validator validator() {
        final ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages/validation");
        messageSource.setDefaultLocale(localeSupplier().get());

        final LocalValidatorFactoryBean factory = new LocalValidatorFactoryBean();
        factory.setValidationMessageSource(messageSource);

        return factory;
    }
}
