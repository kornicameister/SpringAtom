package org.agatom.springatom.data.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.util.ClassUtils;

import java.util.Map;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-10-15</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
abstract public class PersistenceEvent
        extends ApplicationEvent {
    private static final long serialVersionUID = -6713368400308610889L;

    public PersistenceEvent(final Object source) {
        super(source);
    }

    public Class<?> getObjectType() {
        return ClassUtils.getUserClass(this.getSource());
    }

    public boolean isMultiObjectEvent() {
        final Object source = this.getSource();
        return ClassUtils.isAssignableValue(Iterable.class, source) || ClassUtils.isAssignableValue(Map.class, source);
    }

}
