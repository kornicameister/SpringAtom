package org.agatom.springatom.core.locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.FileSystemResourceLoader;

import javax.annotation.Nonnull;
import java.io.IOException;

/**
 * {@code AbstractMessageSourceConfiguration} defines an abstract foundation for
 * actual message processing. This class is directly responsible for
 * setting common properties of underlying {@link SMessageSource}.
 * Yet allows inheriting classes to provide own set of resource bundle files via {@link #getBasenames()}
 *
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-09-26</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.2
 * @since 0.0.1
 */
public abstract class AbstractMessageSourceConfiguration {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractMessageSourceConfiguration.class);

    @Primary
    @Bean(name = "messageSource")
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    @Description("SMessageSource is customized MessageSource containing lot of methods to work with classes localization")
    public SMessageSource messageSource() throws IOException {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("messageSource() bean creating...");
        }
        final SAMessageSource messageSource = new SAMessageSource();

        messageSource.setConcurrentRefresh(true);
        messageSource.setCacheSeconds(360);
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setUseCodeAsDefaultMessage(true);
        messageSource.setBasenames(this.getBasenames());
        messageSource.setFallbackToSystemLocale(true);
        messageSource.setResourceLoader(new FileSystemResourceLoader());

        this.configureMessageSource(messageSource);

        return messageSource;
    }

    /**
     * Must return a {@link java.lang.String} array containing a basename
     * of all resource bundles ever used by the application
     *
     * @return array of locations of resource bundles
     */
    protected abstract
    @Nonnull
    String[] getBasenames();

    protected void configureMessageSource(final SAMessageSource messageSource) {
        // do nothing
    }
}
