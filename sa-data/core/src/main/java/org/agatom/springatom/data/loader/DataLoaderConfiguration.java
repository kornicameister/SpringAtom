package org.agatom.springatom.data.loader;

import org.agatom.springatom.data.loader.condition.ActiveLoaderCondition;
import org.agatom.springatom.data.loader.condition.NoopLoaderCondition;
import org.agatom.springatom.data.loader.mgr.DataLoaderManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-10-11</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
@Lazy(false)
@Configuration
public class DataLoaderConfiguration {

    @Bean
    @Conditional(ActiveLoaderCondition.class)
    public DataLoaderManager activeDataLoaderManager() {
        return new ActiveLoaderManager();
    }

    @Bean
    @Conditional(NoopLoaderCondition.class)
    public DataLoaderManager noopDataLoaderManager() {
        return new NoopLoaderManager();
    }

}
