package org.agatom.springatom.data.vin;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-09-29</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
@Configuration
@ComponentScan(
        basePackageClasses = VinNumberConfiguration.class
)
public class VinNumberConfiguration {
}
