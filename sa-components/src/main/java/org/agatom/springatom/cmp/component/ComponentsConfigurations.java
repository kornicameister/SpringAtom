package org.agatom.springatom.cmp.component;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-12-28</small>
 * </p>
 *
 * @author Tomasz
 * @version 0.0.1
 * @since 0.0.1
 */
@Configuration
@ComponentScan(
    basePackageClasses = ComponentsConfigurations.class,
    excludeFilters = {
        @Filter(type = FilterType.ANNOTATION, value = Configuration.class)
    }
)
public class ComponentsConfigurations {
}
