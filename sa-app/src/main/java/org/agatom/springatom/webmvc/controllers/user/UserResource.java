package org.agatom.springatom.webmvc.controllers.user;

import org.agatom.springatom.core.web.DataResource;
import org.springframework.hateoas.Link;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-11-18</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
class UserResource
        extends DataResource<Object> {
    private static final long serialVersionUID = -8724103741592690303L;

    public UserResource(final Object content, final Link... links) {
        super(content, links);
    }

}
