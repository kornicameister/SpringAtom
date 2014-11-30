package org.agatom.springatom.cmp.locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.*;
import org.springframework.core.io.FileSystemResourceLoader;

import java.io.IOException;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-09-26</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
@Configuration
public class MessageSourceConfiguration {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageSourceConfiguration.class);

    @Primary
    @Bean(name = "messageSource")
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    @Description("SMessageSource is customized MessageSource containing lot of methods to work with classes localization")
    public SMessageSource messageSource() throws IOException {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("messageSource() bean creating...");
        }
        final SMessageSourceImpl messageSource = new SMessageSourceImpl();
        messageSource.setCacheSeconds(360);
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setUseCodeAsDefaultMessage(true);
        messageSource.setBasenames(this.getBasenames());
        messageSource.setFallbackToSystemLocale(true);
        messageSource.setResourceLoader(new FileSystemResourceLoader());
        return messageSource;
    }


    public String[] getBasenames() {
        return new String[]{
                "classpath:org/agatom/springatom/locale/actions",
                "classpath:org/agatom/springatom/locale/domainObjects",
                "classpath:org/agatom/springatom/locale/enums",
                "classpath:org/agatom/springatom/locale/format",
                "classpath:org/agatom/springatom/locale/messages",
                "classpath:org/agatom/springatom/locale/wizards",
                "classpath:org/agatom/springatom/locale/ui",
                "classpath:org/agatom/springatom/locale/notifications"
        };
    }
}
