package org.agatom.springatom.data.event;

import org.agatom.springatom.data.link.ObjectToObjectLink;
import org.springframework.util.ClassUtils;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-10-15</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
public abstract class PersistenceLinkEvent
        extends PersistenceEvent {
    private static final long serialVersionUID = -9071648572128698903L;
    private final Object roleB;

    public PersistenceLinkEvent(final ObjectToObjectLink<?, ?> link) {
        this(link.getRoleA(), link.getRoleB());
    }

    public PersistenceLinkEvent(Object source, final Object linked) {
        super(source);
        this.roleB = linked;
    }

    public Object getRoleB() {
        return this.roleB;
    }

    public Object getRoleA() {
        return this.getSource();
    }

    public Class<?> getRoleAType() {
        return super.getObjectType();
    }

    public Class<?> getRoleBType() {
        return ClassUtils.getUserClass(this.roleB);
    }
}
