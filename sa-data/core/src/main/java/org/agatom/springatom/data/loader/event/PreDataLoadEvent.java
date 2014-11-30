package org.agatom.springatom.data.loader.event;

import org.agatom.springatom.data.event.before.BeforeCreateEvent;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-10-15</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
public class PreDataLoadEvent
        extends BeforeCreateEvent {
    private static final long serialVersionUID = -5614627089517185377L;
    private final String loader;

    public PreDataLoadEvent(final String loader, final Object source) {
        super(source);
        this.loader = loader;
    }

    public String getLoader() {
        return this.loader;
    }
}
