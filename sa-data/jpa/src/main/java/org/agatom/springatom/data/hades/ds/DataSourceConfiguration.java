package org.agatom.springatom.data.hades.ds;

import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.IConnectionCustomizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-10-08</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
@Configuration
@PropertySource(value = "classpath:org/agatom/springatom/data/hades/database.properties")
public class DataSourceConfiguration {
    private static final Logger      LOGGER      = LoggerFactory.getLogger(DataSourceConfiguration.class);
    @Autowired
    private              Environment environment = null;

    @Bean
    public HikariDataSource dataSource() throws Exception {
        final HikariDataSource dataSource = new HikariDataSource();
        try {
            this.setConnectionProperties(dataSource);
            this.setPoolProperties(dataSource);
            this.setCustomizations(dataSource);
            dataSource.setAutoCommit(Boolean.parseBoolean(this.environment.getProperty("db.hibernate.connection.autocommit")));
        } catch (Exception exp) {
            LOGGER.error("Failed to initialized dataSource", exp);
            throw new BeanCreationException("Failed to initialized dataSource", exp);
        }
        return dataSource;
    }

    private void setConnectionProperties(final HikariDataSource dataSource) {
        dataSource.setUsername(this.environment.getProperty("db.connection.username"));
        dataSource.setPassword(this.environment.getProperty("db.connection.password"));
        dataSource.setJdbcUrl(this.environment.getProperty("db.connection.url"));
        dataSource.setDriverClassName(this.environment.getProperty("db.connection.driverClassName"));
        dataSource.setConnectionInitSql(this.environment.getProperty("db.connection.initSql"));
        dataSource.setConnectionTestQuery(this.environment.getProperty("db.connection.initSql"));
    }

    private void setPoolProperties(final HikariDataSource dataSource) throws SQLException {
        dataSource.setLoginTimeout(Integer.parseInt(this.environment.getProperty("db.connection.pool.loginTimeout")));
        dataSource.setConnectionTimeout(Integer.parseInt(this.environment.getProperty("db.connection.pool.connectionTimeout")));
        dataSource.setIdleTimeout(Integer.parseInt(this.environment.getProperty("db.connection.pool.maxIdleTime")));
        dataSource.setMaximumPoolSize(Integer.parseInt(this.environment.getProperty("db.connection.pool.maxPoolSize")));
    }

    private void setCustomizations(final HikariDataSource dataSource) {
//        dataSource.setMetricsTrackerClassName();
        dataSource.setConnectionCustomizer(this.getConnectionCustomizer());
    }

    private IConnectionCustomizer getConnectionCustomizer() {
        return new IConnectionCustomizer() {
            @Override
            public void customize(final Connection connection) throws SQLException {
                if (LOGGER.isTraceEnabled()) {
                    LOGGER.trace(String.format("Customizing connection = %s", connection));
                }
            }
        };
    }

}
