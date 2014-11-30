package org.agatom.springatom.data.loader;

import org.agatom.springatom.data.loader.mgr.DataLoaderManager;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-10-11</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
class NoopLoaderManager
        implements DataLoaderManager {
    @Override
    public void doLoad() {
        // do nothing
    }
}
