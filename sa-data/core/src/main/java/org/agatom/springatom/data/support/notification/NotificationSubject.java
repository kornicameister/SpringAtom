package org.agatom.springatom.data.support.notification;

import org.agatom.springatom.core.data.Labeled;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-11-19</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
public class NotificationSubject
        implements Labeled {
    private String label   = null;
    private Object subject = null;

    @Override
    public String getLabel() {
        return label;
    }

    public NotificationSubject setLabel(final String label) {
        this.label = label;
        return this;
    }

    public Object getSubject() {
        return subject;
    }

    public NotificationSubject setSubject(final Object subject) {
        this.subject = subject;
        return this;
    }
}
