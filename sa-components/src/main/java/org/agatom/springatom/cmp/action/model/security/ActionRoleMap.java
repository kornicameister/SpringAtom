package org.agatom.springatom.cmp.action.model.security;

import java.util.Map;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-11-17</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
public class ActionRoleMap
        implements ActionRole {
    private Map<Connector, DefaultActionRole> roles = null;

    public Map<Connector, DefaultActionRole> getRoles() {
        return this.roles;
    }

    public ActionRoleMap setRoles(final Map<Connector, DefaultActionRole> roles) {
        this.roles = roles;
        return this;
    }

    public static enum Connector {
        AND,
        OR,
        ANY,
        ONE,
        ALL
    }
}
