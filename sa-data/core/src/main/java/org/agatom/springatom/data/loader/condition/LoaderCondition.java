package org.agatom.springatom.data.loader.condition;

import org.agatom.springatom.core.conditions.BootCondition;

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
abstract class LoaderCondition
        extends BootCondition {

    @Override
    protected boolean matches(final Properties bootProperties) {
        return this.isLoaderManagerEnabled(Boolean.valueOf(bootProperties.getProperty("boot.data.load")));
    }

    protected abstract boolean isLoaderManagerEnabled(final Boolean loadData);

}
