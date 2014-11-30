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
public class BeforeSaveEvent
        extends PersistenceEvent {
    private static final long serialVersionUID = 5463469646632465284L;

    public BeforeSaveEvent(final Object source) {
        super(source);
    }
}
