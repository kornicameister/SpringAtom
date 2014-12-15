package org.agatom.springatom.core.om;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.converter.json.Jackson2ObjectMapperFactoryBean;

import java.util.List;

/**
 * {@code ObjectMapperConfiguration} defines and initialized {@link com.fasterxml.jackson.databind.ObjectMapper}
 * for the application transforming JSON-POJO operations
 *
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-12-14</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
@Configuration
public class ObjectMapperConfiguration {

    @Autowired
    private MessageSource messageSource = null;

    @Bean
    @Primary
    @Scope(BeanDefinition.SCOPE_SINGLETON)
    public Jackson2ObjectMapperFactoryBean objectMapper() {
        final Jackson2ObjectMapperFactoryBean bean = new Jackson2ObjectMapperFactoryBean();
        bean.setFailOnEmptyBeans(false);
        bean.setAutoDetectGettersSetters(true);
        bean.setAutoDetectFields(true);
        bean.setIndentOutput(false);
        bean.setSimpleDateFormat(this.messageSource.getMessage("date.format.date.long", null, "YYYY-MM-dd", LocaleContextHolder.getLocale()));
        bean.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        bean.setFeaturesToDisable(this.getDisabledFeatures());
        bean.setFeaturesToEnable(this.getEnabledFeatures());
        bean.setModules(this.getModules());
        return bean;
    }

    private SerializationFeature[] getDisabledFeatures() {
        return new SerializationFeature[]{
                SerializationFeature.WRITE_NULL_MAP_VALUES
        };
    }

    private SerializationFeature[] getEnabledFeatures() {
        return new SerializationFeature[]{
                SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,
                SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS,
                SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS
        };
    }

    private List<Module> getModules() {
        return Lists.newArrayList(
                new JodaModule(),
                new GuavaModule()
        );
    }

}
