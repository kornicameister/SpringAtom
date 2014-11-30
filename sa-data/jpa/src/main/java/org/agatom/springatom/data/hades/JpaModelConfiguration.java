package org.agatom.springatom.data.hades;

import com.google.common.collect.Maps;
import com.zaxxer.hikari.HikariDataSource;
import org.agatom.springatom.data.hades.ds.DataSourceConfiguration;
import org.agatom.springatom.data.hades.ds.EhCacheConfiguration;
import org.agatom.springatom.data.hades.repo.factory.NRepositoriesFactoryBean;
import org.agatom.springatom.data.hades.scheduled.AsyncSchedulingConfiguration;
import org.agatom.springatom.data.loader.DataLoaderConfiguration;
import org.agatom.springatom.data.repositories.provider.RepositoriesConfiguration;
import org.agatom.springatom.data.services.provider.DomainServicesConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewInterceptor;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.persistence.SharedCacheMode;
import javax.persistence.ValidationMode;
import javax.validation.Validator;
import java.util.Map;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-10-08</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
@Lazy(false)
@Import(value = {
        AsyncSchedulingConfiguration.class,
        DataLoaderConfiguration.class,
        DataSourceConfiguration.class,
        EhCacheConfiguration.class,
        RepositoriesConfiguration.class,
        DomainServicesConfiguration.class,
})
@Configuration
@ComponentScan(
        basePackages = {
                "org.agatom.springatom.data.hades.service",
                "org.agatom.springatom.data.hades.service.listener",
                "org.agatom.springatom.data.hades.support"
        }
)
@EnableJpaAuditing(
        modifyOnCreate = true,
        setDates = true,
        auditorAwareRef = "userDomainService"
)
@EnableJpaRepositories(
        basePackages = JpaModelConfiguration.REPOS_BASE_PACKAGE,
        considerNestedRepositories = true,
        repositoryFactoryBeanClass = NRepositoriesFactoryBean.class
)
@EnableTransactionManagement(mode = AdviceMode.ASPECTJ)
@PropertySource(value = "classpath:org/agatom/springatom/data/hades/database.properties")
public class JpaModelConfiguration {
    protected static final String MODEL_BASE_PACKAGE = "org.agatom.springatom.data.hades.model";
    protected static final String REPOS_BASE_PACKAGE = "org.agatom.springatom.data.hades.repo.repositories";

    @Autowired
    private HikariDataSource dataSource  = null;
    @Autowired
    private Environment      environment = null;

    @Bean
    public Validator validator() {
        return new LocalValidatorFactoryBean();
    }

    @Bean
    public JpaTransactionManager transactionManager() {
        final JpaTransactionManager manager = new JpaTransactionManager();
        manager.setEntityManagerFactory(this.entityManagerFactory().getObject());
        return manager;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        final LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();
        bean.setPersistenceUnitName(this.environment.getProperty("db.hibernate.persistenceUnitName"));
        bean.setDataSource(this.dataSource);
        bean.setPackagesToScan(MODEL_BASE_PACKAGE);
        bean.setJpaVendorAdapter(this.hibernateJpaVendorAdapter());
        bean.setSharedCacheMode(SharedCacheMode.ENABLE_SELECTIVE);
        bean.setValidationMode(ValidationMode.AUTO);
        bean.setJpaPropertyMap(this.getJpaProperties());
        return bean;
    }

    @Bean
    public HibernateJpaVendorAdapter hibernateJpaVendorAdapter() {
        final HibernateJpaVendorAdapter vendor = new HibernateJpaVendorAdapter();
        vendor.setDatabase(Database.MYSQL);
        vendor.setDatabasePlatform(this.environment.getProperty("db.hibernate.dialect"));
        vendor.setGenerateDdl(Boolean.parseBoolean(this.environment.getProperty("db.hibernate.generateDDL")));
        vendor.setShowSql(Boolean.parseBoolean(this.environment.getProperty("db.hibernate.show_sql")));
        return vendor;
    }

    private Map<String, ?> getJpaProperties() {
        final Map<String, Object> map = Maps.newHashMap();

        map.put("hibernate.order_inserts", this.environment.getProperty("db.hibernate.order_inserts"));
        map.put("hibernate.order_updates", this.environment.getProperty("db.hibernate.order_updates"));
        map.put("hibernate.connection.useUnicode", this.environment.getProperty("db.hibernate.useUnicode"));
        map.put("hibernate.connection.characterEncoding", this.environment.getProperty("db.hibernate.characterEncoding"));
        map.put("hibernate.connection.charSet", this.environment.getProperty("db.hibernate.characterEncoding"));
        map.put("hibernate.show_sql", this.environment.getProperty("db.hibernate.show_sql"));
        map.put("hibernate.format_sql", this.environment.getProperty("db.hibernate.format_sql"));
        map.put("hibernate.use_sql_comments", this.environment.getProperty("db.hibernate.use_sql_comments"));
        map.put("hibernate.hbm2ddl.auto", this.environment.getProperty("db.hibernate.hbm2ddl.auto"));
        map.put("hibernate.bytecode.use_reflection_optimizer", this.environment.getProperty("db.hibernate.use_reflection_optimizer"));
        map.put("hibernate.transaction.flush_before_completion", this.environment.getProperty("db.hibernate.transaction.flush_before_completion"));
        map.put("hibernate.transaction.auto_close_session", this.environment.getProperty("db.hibernate.transaction.auto_close_session"));
        map.put("hibernate.connection.autocommit", this.environment.getProperty("db.hibernate.connection.autocommit"));
        map.put("org.hibernate.envers.revision_field_name", this.environment.getProperty("db.hibernate.envers.revision_field_name"));
        map.put("org.hibernate.envers.revision_type_field_name", this.environment.getProperty("db.hibernate.envers.revision_type_field_name"));
        map.put("org.hibernate.envers.audit_table_suffix", this.environment.getProperty("db.hibernate.envers.audit_table_suffix"));
        map.put("hibernate.validator.apply_to_ddl", this.environment.getProperty("db.hibernate.validator.apply_to_ddl"));
        map.put("hibernate.validator.autoregister_listeners", this.environment.getProperty("db.hibernate.validator.autoregister_listeners"));
        map.put("org.hibernate.envers.track_entities_changed_in_revision", true);
        map.put("org.hibernate.envers.revision_on_collection_change", false);
        map.put("jadira.usertype.autoRegisterUserTypes", true);
        map.put("hibernate.cache.region.factory_class", "org.hibernate.cache.ehcache.EhCacheRegionFactory");
        map.put("hibernate.cache.use_second_level_cache", true);
        map.put("hibernate.cache.use_minimal_puts", this.environment.getProperty("db.hibernate.cache.use_minimal_puts"));
        map.put("hibernate.cache.use_query_cache", true);
        map.put("hibernate.generate_statistics", true);

        return map;
    }

    @Bean
    public OpenEntityManagerInViewInterceptor openEntityManagerInViewInterceptor() {
        final OpenEntityManagerInViewInterceptor interceptor = new OpenEntityManagerInViewInterceptor();
        interceptor.setEntityManagerFactory(this.entityManagerFactory().getObject());
        return interceptor;
    }

}
