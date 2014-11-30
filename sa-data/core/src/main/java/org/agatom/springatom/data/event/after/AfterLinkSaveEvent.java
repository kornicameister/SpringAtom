package org.agatom.springatom.data.event.after;

import org.agatom.springatom.data.event.PersistenceLinkEvent;
import org.agatom.springatom.data.link.ObjectToObjectLink;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-10-15</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
public class AfterLinkSaveEvent
        extends PersistenceLinkEvent {
    private static final long serialVersionUID = 1228789747613253335L;

    public AfterLinkSaveEvent(final ObjectToObjectLink<?, ?> link) {
        super(link);
    }

    public AfterLinkSaveEvent(final Object source, Object target) {
        super(source, target);
    }

}
