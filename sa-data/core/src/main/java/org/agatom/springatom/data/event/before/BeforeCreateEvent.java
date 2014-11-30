package org.agatom.springatom.data.event.before;

import org.agatom.springatom.data.event.PersistenceEvent;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-10-15</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
public class BeforeCreateEvent
        extends PersistenceEvent {
    private static final long serialVersionUID = -402392578415331104L;

    public BeforeCreateEvent(final Object source) {
        super(source);
    }
}
