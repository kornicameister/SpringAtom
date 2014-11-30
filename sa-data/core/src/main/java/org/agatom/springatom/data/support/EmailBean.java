package org.agatom.springatom.data.support;

import com.google.common.base.MoreObjects;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-10-08</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
public class EmailBean {
    private final String email;

    public EmailBean(final String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String getNickname() {
        return this.email != null ? this.email.split("@")[0] : null;
    }

    public String getDomain() {
        return this.email != null ? this.email.split("@")[1] : null;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("email", email)
                .toString();
    }
}
