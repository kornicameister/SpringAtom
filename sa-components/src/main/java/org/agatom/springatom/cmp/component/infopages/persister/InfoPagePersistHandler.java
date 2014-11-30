package org.agatom.springatom.cmp.component.infopages.persister;

import org.agatom.springatom.cmp.component.infopages.provider.structure.InfoPage;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-10-15</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
public interface InfoPagePersistHandler {
    void saveToPersistentStorage(final InfoPage infoPage);
}
