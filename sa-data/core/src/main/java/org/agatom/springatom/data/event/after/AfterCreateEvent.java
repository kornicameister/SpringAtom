package org.agatom.springatom.data.event.after;

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
public class AfterCreateEvent
        extends PersistenceEvent {
    private static final long serialVersionUID = -402392578415331104L;

    public AfterCreateEvent(final Object source) {
        super(source);
    }
}
