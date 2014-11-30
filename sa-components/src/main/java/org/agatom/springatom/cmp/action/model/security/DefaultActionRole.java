package org.agatom.springatom.cmp.action.model.security;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.google.common.collect.Lists;

import java.util.Collection;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-11-17</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
public class DefaultActionRole
        implements ActionRole {
    private Collection<String> roles = null;

    @JsonUnwrapped
    public Collection<String> getRoles() {
        return this.roles;
    }

    public DefaultActionRole setRoles(final Collection<String> role) {
        this.roles = role;
        return this;
    }

    public DefaultActionRole appendRole(final String role) {
        if (this.roles == null) {
            this.roles = Lists.newArrayList();
        }
        this.roles.add(role);
        return this;
    }
}
