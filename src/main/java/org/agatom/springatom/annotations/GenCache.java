package org.agatom.springatom.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */

@Target(value = ElementType.TYPE)
@Retention(value = RetentionPolicy.SOURCE)
public @interface GenCache {
    String daoName();
}
