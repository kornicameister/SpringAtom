package org.agatom.springatom.data.loader.condition;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-10-11</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
public class ActiveLoaderCondition
        extends LoaderCondition {
    @Override
    protected boolean isLoaderManagerEnabled(final Boolean loadData) {
        return loadData.equals(true);
    }
}
