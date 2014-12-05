package org.agatom.springatom.boot.init.sa.mvc;

import com.google.common.collect.Lists;
import org.agatom.springatom.cmp.locale.MessageSourceConfiguration;
import org.agatom.springatom.cmp.wizards.WizardsConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.cache.interceptor.*;
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
            extends CachingConfigurerSupport {

        @Override
        public CacheManager cacheManager() {
            return this.compositeCacheManager();
        }

        @Override
        public CacheResolver cacheResolver() {
            return new SimpleCacheResolver(this.cacheManager());
        }

        @Override
        public KeyGenerator keyGenerator() {
            return new SimpleKeyGenerator();
        }

        @Override
        public CacheErrorHandler errorHandler() {
            return new SimpleCacheErrorHandler() {
                private final Logger logger = LoggerFactory.getLogger(this.getClass());

                @Override
                public void handleCacheGetError(final RuntimeException exception, final Cache cache, final Object key) {
                    logger.error(String.format("CacheGetError :: In cache %s for key %s, exception was thrown", cache.getName(), key), exception);
                }

                @Override
                public void handleCachePutError(final RuntimeException exception, final Cache cache, final Object key, final Object value) {
                    logger.error(String.format("CachePutError :: In cache %s for key %s, exception was thrown", cache.getName(), key), exception);
                }

                @Override
                public void handleCacheEvictError(final RuntimeException exception, final Cache cache, final Object key) {
                    logger.error(String.format("CacheEvictError :: In cache %s for key %s, exception was thrown", cache.getName(), key), exception);
                }

                @Override
                public void handleCacheClearError(final RuntimeException exception, final Cache cache) {
                    logger.error(String.format("CacheCleanError :: In cache %s, exception was thrown", cache.getName()), exception);
                }
            };
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
    }
}
