package org.agatom.springatom.data.loader.mgr;

import org.springframework.scheduling.annotation.Async;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-10-11</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
public interface DataLoaderManager {
    @Async
    void doLoad();
}
