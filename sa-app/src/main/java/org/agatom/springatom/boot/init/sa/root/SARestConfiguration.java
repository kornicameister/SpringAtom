package org.agatom.springatom.boot.init.sa.root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

import java.util.Properties;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-10-01</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
@Configuration
public class SARestConfiguration {

    @Autowired
    @Qualifier("applicationProperties")
    private Properties properties = null;

    @Bean
    public RepositoryRestConfiguration repositoryRestConfiguration() {
        final RepositoryRestConfiguration configuration = new RepositoryRestConfiguration();
        configuration.setReturnBodyOnCreate(Boolean.parseBoolean(this.properties.getProperty("rest.returnBodyOnCreate")));
        configuration.setReturnBodyOnUpdate(Boolean.parseBoolean(this.properties.getProperty("rest.returnBodyOnUpdate")));
        configuration.setMaxPageSize(Integer.parseInt(this.properties.getProperty("rest.maxPageSize")));
        configuration.setLimitParamName(this.properties.getProperty("rest.limitParamName"));
        configuration.setPageParamName(this.properties.getProperty("rest.pageParamName"));
        configuration.setSortParamName(this.properties.getProperty("rest.sortParamName"));
        configuration.setDefaultPageSize(Integer.parseInt(this.properties.getProperty("rest.defaultPageSize")));
        return configuration;
    }
}
