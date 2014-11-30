package org.agatom.springatom.data.support.notification;

import org.joda.time.DateTime;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-11-18</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
public class UserNotification {
    private Long                id;
    private String              message;
    private DateTime            sent;
    private NotificationSubject subject;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public DateTime getSent() {
        return sent;
    }

    public void setSent(final DateTime sent) {
        this.sent = sent;
    }

    public NotificationSubject getSubject() {
        return subject;
    }

    public void setSubject(final NotificationSubject subject) {
        this.subject = subject;
    }
}
