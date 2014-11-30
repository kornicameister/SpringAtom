package org.agatom.springatom.data.event.before;

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
public class BeforeLinkSaveEvent
        extends PersistenceLinkEvent {
    private static final long serialVersionUID = 1228789747613253335L;

    public BeforeLinkSaveEvent(final ObjectToObjectLink<?, ?> link) {
        super(link);
    }

    public BeforeLinkSaveEvent(final Object source, Object target) {
        super(source, target);
    }

}
