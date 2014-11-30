package org.agatom.springatom.data.hades.ds;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.util.ResourceUtils;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-10-10</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
@Configuration
@EnableCaching(mode = AdviceMode.ASPECTJ, order = 1)
public class EhCacheConfiguration {

    @Bean
    public EhCacheCacheManager ehCacheCacheManager() throws Exception {
        final EhCacheCacheManager manager = new EhCacheCacheManager();
        manager.setCacheManager(this.ehCacheManagerFactoryBean().getObject());
        manager.setTransactionAware(true);
        return manager;
    }

    @Bean
    public EhCacheManagerFactoryBean ehCacheManagerFactoryBean() throws Exception {
        final EhCacheManagerFactoryBean bean = new EhCacheManagerFactoryBean();
        bean.setShared(true);
        bean.setConfigLocation(
                new FileSystemResource(ResourceUtils.getFile("classpath:org/agatom/springatom/ehcache/ehcache.xml"))
        );
        return bean;
    }

}
