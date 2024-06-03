package com.duzon.lulu.bean;

import com.duzon.common.message.LogMessageSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
public class loggerConfig {

    @Value("${spring.logMessage.test}")
    private String logMessage;

    @Bean
    public LogMessageSource logMessageSource() {
        LogMessageSource logMessageSource = new LogMessageSource();
        logMessageSource.setReloadableResourceBundleMessageSource(reloadableResourceBundleMessageSource());
        return logMessageSource;
    }

    public ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames(logMessage);
        messageSource.setCacheSeconds(60);
        return messageSource;
    }
}