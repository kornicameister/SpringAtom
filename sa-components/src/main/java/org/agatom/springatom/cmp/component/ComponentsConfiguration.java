package org.agatom.springatom.cmp.component;

import org.agatom.springatom.cmp.component.infopages.InfoPageModuleConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;

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
@ComponentScan(
        basePackageClasses = ComponentsConfiguration.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ANNOTATION, value = {Configuration.class})
        }
)
@Import(value = {
        InfoPageModuleConfiguration.class
})
public class ComponentsConfiguration {
}
