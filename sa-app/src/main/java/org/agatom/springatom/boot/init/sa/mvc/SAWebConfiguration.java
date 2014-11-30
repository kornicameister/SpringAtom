package org.agatom.springatom.boot.init.sa.mvc;

import com.google.common.collect.Lists;
import org.agatom.springatom.cmp.locale.MessageSourceConfiguration;
import org.agatom.springatom.cmp.wizards.WizardsConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleKeyGenerator;
import org.springframework.cache.support.CompositeCacheManager;
import org.springframework.context.annotation.*;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import java.util.Properties;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-09-25</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
@Configuration
@Import(value = {
        MessageSourceConfiguration.class,
        WizardsConfiguration.class
})
@EnableAsync
//@EnableLoadTimeWeaving
@EnableAspectJAutoProxy(proxyTargetClass = true)
@ComponentScan(value = "org.agatom.springatom.web")
public class SAWebConfiguration {

    @Autowired
    @Qualifier(value = "applicationProperties")
    private Properties applicationProperties = null;

    @Bean
    public LocaleResolver localeResolver() {
        final CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();
        cookieLocaleResolver.setDefaultLocale(StringUtils.parseLocaleString(this.applicationProperties.getProperty("sa.locale.defaultLocale")));
        return cookieLocaleResolver;
    }

    @Configuration
    @EnableCaching(mode = AdviceMode.ASPECTJ, order = 2)
    public static class CachingConfiguration
            implements CachingConfigurer {

        @Override
        public CacheManager cacheManager() {
            return this.compositeCacheManager();
        }

        @Bean(name = "webCacheManager")
        public CompositeCacheManager compositeCacheManager() {
            final CompositeCacheManager manager = new CompositeCacheManager();
            manager.setCacheManagers(Lists.<CacheManager>newArrayList(
                    this.guavaCacheManager()
            ));
            manager.setFallbackToNoOpCache(true);
            return manager;
        }

        @Bean(name = "guavaCacheManager")
        public GuavaCacheManager guavaCacheManager() {
            final GuavaCacheManager manager = new GuavaCacheManager();
            manager.setAllowNullValues(false);
            return manager;
        }

        @Override
        public KeyGenerator keyGenerator() {
            return new SimpleKeyGenerator();
        }
    }
}
