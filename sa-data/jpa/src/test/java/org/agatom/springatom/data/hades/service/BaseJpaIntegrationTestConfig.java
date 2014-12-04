package org.agatom.springatom.data.hades.service;

import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.http.converter.json.Jackson2ObjectMapperFactoryBean;

import java.io.FileNotFoundException;
import java.text.MessageFormat;
import java.util.Locale;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-12-02</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
@Configuration
class BaseJpaIntegrationTestConfig {

    @Bean(name = "applicationProperties")
    public PropertiesFactoryBean applicationProperties() throws FileNotFoundException {
        final PropertiesFactoryBean bean = new PropertiesFactoryBean();
        bean.setSingleton(true);
        return bean;
    }

    @Bean
    @Primary
    public Jackson2ObjectMapperFactoryBean objectMapper() {
        return new Jackson2ObjectMapperFactoryBean();
    }

    @Bean
    @Primary
    public MessageSource messageSource() {
        return new AbstractMessageSource() {

            @Override
            protected String resolveCodeWithoutArguments(final String code, final Locale locale) {
                return code;
            }

            @Override
            protected MessageFormat resolveCode(final String code, final Locale locale) {
                return new MessageFormat(code, locale);
            }
        };
    }

}
