package org.agatom.springatom.boot.init.sa.mvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.agatom.springatom.boot.init.sa.SAWebAppInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewInterceptor;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.List;
import java.util.Properties;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-09-26</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
@EnableWebMvc
@EnableSpringDataWebSupport
@Import(value = SAWebConfiguration.class)
@Configuration
@ComponentScan(basePackages = "org.agatom.springatom.webmvc")
public class SAMvcConfiguration
        extends WebMvcConfigurerAdapter {
    @Autowired
    private ObjectMapper                       objectMapper                       = null;
    @Autowired
    private javax.validation.Validator         validator                          = null;
    @Autowired
    @Qualifier("applicationProperties")
    private Properties                         properties                         = null;
    @Autowired
    private OpenEntityManagerInViewInterceptor openEntityManagerInViewInterceptor = null;

    @Override
    public void configureMessageConverters(final List<HttpMessageConverter<?>> converters) {
        super.configureMessageConverters(converters);
        converters.add(this.jackson2HttpMessageConverter());
    }

    @Override
    public Validator getValidator() {
        return new SpringValidatorAdapter(this.validator);
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        super.addInterceptors(registry);

        // LocaleChangeInterceptor
        final LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        interceptor.setParamName(this.properties.getProperty("sa.locale.requestParam"));

        registry.addInterceptor(interceptor);
        registry.addWebRequestInterceptor(this.openEntityManagerInViewInterceptor);
    }

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        super.addResourceHandlers(registry);
        registry.addResourceHandler("/static/**")
                .addResourceLocations(
                        "/static/",
                        "classpath:/META-INF/web-resources/",
                        "classpath:/META-INF/resources/webjars/"
                )
                .setCachePeriod(10);
    }

    @Override
    public void configureDefaultServletHandling(final DefaultServletHandlerConfigurer configurer) {
        configurer.enable(SAWebAppInitializer.SERVLET_NAME);
    }

    @Bean
    public MappingJackson2HttpMessageConverter jackson2HttpMessageConverter() {
        final MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(this.objectMapper);
        converter.setPrettyPrint(false);
        return converter;
    }

    @Bean
    public InternalResourceViewResolver jspViewResolver() {
        final InternalResourceViewResolver bean = new InternalResourceViewResolver();
        bean.setPrefix("/WEB-INF/views/");
        bean.setSuffix(".jsp");
        return bean;
    }
}
