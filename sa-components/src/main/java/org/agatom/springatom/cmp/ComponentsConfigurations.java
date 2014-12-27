package org.agatom.springatom.cmp;

import org.agatom.springatom.cmp.action.ActionsConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

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
@Import(
        value = {
                ActionsConfiguration.class
        }
)
@ComponentScan(
        basePackages = {
                "org.agatom.springatom.cmp.component"
        }
)
public class ComponentsConfigurations {
}
