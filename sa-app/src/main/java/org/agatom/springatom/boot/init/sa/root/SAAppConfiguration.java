package org.agatom.springatom.boot.init.sa.root;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.agatom.springatom.boot.security.SecurityConfiguration;
import org.agatom.springatom.cmp.action.ActionsConfiguration;
import org.agatom.springatom.cmp.component.ComponentsConfiguration;
import org.agatom.springatom.cmp.locale.MessageSourceConfiguration;
import org.agatom.springatom.cmp.locale.SMessageSource;
import org.agatom.springatom.data.hades.JpaModelConfiguration;
import org.agatom.springatom.data.loader.mgr.DataLoaderManager;
import org.agatom.springatom.data.oid.OidConfiguration;
import org.agatom.springatom.data.vin.VinNumberConfiguration;
import org.agatom.springatom.data.xml.OXMLConfiguration;
import org.agatom.springatom.webmvc.converters.CollectionToSizeConverter;
import org.agatom.springatom.webmvc.converters.IntervalToStringConverter;
import org.agatom.springatom.webmvc.converters.LongToDateTimeConverter;
import org.agatom.springatom.webmvc.converters.StringToUnixTimestampConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.*;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.format.FormatterRegistrar;
import org.springframework.format.datetime.joda.DateTimeFormatterFactory;
import org.springframework.format.datetime.joda.JodaTimeFormatterRegistrar;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;
import org.springframework.http.converter.json.Jackson2ObjectMapperFactoryBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-09-25</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
@Lazy(value = false)
@Configuration
@Import(value = {
        SecurityConfiguration.class,
        MessageSourceConfiguration.class,
        ComponentsConfiguration.class,
        ActionsConfiguration.class,
        OidConfiguration.class,
        JpaModelConfiguration.class,
        VinNumberConfiguration.class,
        OXMLConfiguration.class
})
@ComponentScan(basePackages = {
        "org.agatom.springatom.boot.ds"
})
@EnableAspectJAutoProxy
public class SAAppConfiguration {

    @Autowired
    private SMessageSource    messageSource     = null;
    @Autowired
    private DataLoaderManager dataLoaderManager = null;

    @PostConstruct
    private void actOnBoot() {
        this.dataLoaderManager.doLoad();
    }

    @Bean(name = "applicationProperties")
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public PropertiesFactoryBean applicationProperties() throws FileNotFoundException {
        final PropertiesFactoryBean bean = new PropertiesFactoryBean();
        bean.setSingleton(true);
        bean.setLocations(new Resource[]{
                new FileSystemResource(ResourceUtils.getFile("classpath:org/agatom/springatom/properties/web.properties")),
                new FileSystemResource(ResourceUtils.getFile("classpath:org/agatom/springatom/properties/domain.properties")),
                new FileSystemResource(ResourceUtils.getFile("classpath:org/agatom/springatom/properties/rest.properties")),
                new FileSystemResource(ResourceUtils.getFile("classpath:org/agatom/springatom/properties/calendar.properties")),
                new FileSystemResource(ResourceUtils.getFile("classpath:org/agatom/springatom/hades/model/hades.properties"))
        });
        bean.setFileEncoding("UTF-8");
        return bean;
    }

    @Primary
    @Bean(name = "conversionService")
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public FormattingConversionServiceFactoryBean conversionService() {
        final FormattingConversionServiceFactoryBean bean = new FormattingConversionServiceFactoryBean();
        bean.setRegisterDefaultFormatters(true);
        bean.setFormatterRegistrars(Sets.<FormatterRegistrar>newHashSet(this.getJodaTimeFormatterRegistrar()));
        bean.setConverters(this.getConverters());
        return bean;
    }

    private JodaTimeFormatterRegistrar getJodaTimeFormatterRegistrar() {
        final Locale locale = LocaleContextHolder.getLocale();
        final JodaTimeFormatterRegistrar registrar = new JodaTimeFormatterRegistrar();

        final DateTimeFormatterFactory jodaDtff = new DateTimeFormatterFactory();
        jodaDtff.setPattern(this.messageSource.getMessage("date.format.date.long", "YYYY-MM-dd", locale));

        final DateTimeFormatterFactory jodaTff = new DateTimeFormatterFactory();
        jodaTff.setPattern(this.messageSource.getMessage("date.format.hours", "HH:mm", locale));

        registrar.setDateFormatter(jodaDtff.createDateTimeFormatter());
        registrar.setTimeFormatter(jodaTff.createDateTimeFormatter());

        return registrar;
    }

    private Set<?> getConverters() {
        final Set<Object> set = Sets.newHashSet();
        set.add(new CollectionToSizeConverter());
        set.add(new IntervalToStringConverter());
        set.add(new LongToDateTimeConverter());
        set.add(new StringToUnixTimestampConverter());
        return set;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Configuration
    static class ObjectMapperConfiguration {

        @Autowired
        private SMessageSource messageSource = null;

        @Bean
        @Primary
        @Scope(BeanDefinition.SCOPE_SINGLETON)
        public Jackson2ObjectMapperFactoryBean objectMapper() {
            final Jackson2ObjectMapperFactoryBean bean = new Jackson2ObjectMapperFactoryBean();
            bean.setFailOnEmptyBeans(false);
            bean.setAutoDetectGettersSetters(true);
            bean.setAutoDetectFields(true);
            bean.setIndentOutput(false);
            bean.setSimpleDateFormat(this.messageSource.getMessage("date.format.date.long", "YYYY-MM-dd", LocaleContextHolder.getLocale()));
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
}
