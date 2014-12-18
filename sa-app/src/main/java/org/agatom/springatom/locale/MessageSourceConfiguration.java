package org.agatom.springatom.locale;

import org.agatom.springatom.core.locale.ms.AbstractMessageSourceConfiguration;
import org.springframework.context.annotation.Configuration;

/**
 * {@code MessageSourceConfiguration} extends from abstract {@link org.agatom.springatom.core.locale.ms.AbstractMessageSourceConfiguration}
 * Defines set of resource bundles loaded for application module
 *
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-12-17</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
@Configuration
public class MessageSourceConfiguration
        extends AbstractMessageSourceConfiguration {

    @Override
    protected String[] getBasenames() {
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
