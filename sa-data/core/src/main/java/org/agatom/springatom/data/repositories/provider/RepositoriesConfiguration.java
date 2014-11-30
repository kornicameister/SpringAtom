package org.agatom.springatom.data.repositories.provider;

import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.*;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-09-28</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
@Configuration
public class RepositoriesConfiguration {
    @Autowired
    private ListableBeanFactory beanFactory = null;

    @Bean
    @Role(BeanDefinition.ROLE_SUPPORT)
    @Scope(BeanDefinition.SCOPE_SINGLETON)
    @Description("RepositoryProvider allows to retrieve repositories")
    public RepositoriesHelper repositoriesHelper() {
        return new RepositoriesHelperImpl(this.beanFactory);
    }

}
