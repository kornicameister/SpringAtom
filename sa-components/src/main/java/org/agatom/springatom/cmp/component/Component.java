package org.agatom.springatom.cmp.component;

import java.io.Serializable;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-12-24</small>
 * </p>
 *
 * @author Tomasz
 * @version 0.0.1
 * @since 0.0.1
 */
public class Component
        implements Serializable {
    private static final long   serialVersionUID = -4126461884119300724L;
    protected            String label            = null;

    public String getLabel() {
        return this.label;
    }

    public Component setLabel(final String label) {
        this.label = label;
        return this;
    }

}
