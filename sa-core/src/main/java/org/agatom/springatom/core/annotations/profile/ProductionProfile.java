package org.agatom.springatom.core.annotations.profile;

import org.springframework.context.annotation.Profile;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * {@code DevProfile} marks annotated class as candidate for autowiring only in {@code profile} profile
 *
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-12-03</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Profile(ProductionProfile.PROFILE_NAME)
public @interface ProductionProfile {
    String PROFILE_NAME = "sa_profile_production";
}
