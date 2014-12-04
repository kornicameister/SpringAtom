package org.agatom.springatom.core.conditions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.io.IOException;
import java.util.Properties;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-10-11</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
abstract public class BootCondition
        implements Condition {
    private static final Logger LOGGER                    = LoggerFactory.getLogger(BootCondition.class);
    private static final String CLASSPATH_BOOT_PROPERTIES = "classpath:boot.properties";

    @Override
    public boolean matches(final ConditionContext context, final AnnotatedTypeMetadata metadata) {
        final Resource resource = this.getResource(context);
        final Properties properties = new Properties();
        try {
            if (resource.exists()) {
                properties.load(resource.getInputStream());
            } else {
                LOGGER.warn(String.format("%s does not exists, fine if testing...", CLASSPATH_BOOT_PROPERTIES));
            }
            return this.matches(properties);
        } catch (IOException e) {
            LOGGER.error("Failed to get boot.properties", e);
            throw new RuntimeException(e);
        }
    }

    private Resource getResource(final ConditionContext context) {
        final ResourceLoader resourceLoader = context.getResourceLoader();
        return resourceLoader.getResource(CLASSPATH_BOOT_PROPERTIES);
    }

    protected abstract boolean matches(final Properties bootProperties);

}
