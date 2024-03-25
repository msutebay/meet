package org.ssb.meet.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

/**
 * AppConfig class is a configuration class that defines beans for ModelMapper and MessageSource.
 *
 * ModelMapper bean is responsible for mapping objects of one type to objects of another type.
 *
 * MessageSource bean is responsible for resolving messages from resource bundles for internationalization.
 * It is configured to use the 'messages' resource bundle with UTF-8 encoding and fallback to the system locale.
 *
 */
@Configuration
public class AppConfig {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames("messages");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setFallbackToSystemLocale(true);
        return messageSource;
    }
}
