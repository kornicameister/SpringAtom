package org.agatom.springatom.core.web;

import org.springframework.hateoas.Link;

import java.io.Serializable;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-11-22</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
public class PrimitiveDataSource<T extends Serializable>
        extends DataResource<T> {
    private static final long serialVersionUID = -1647287534282526517L;
    private static final int  SIZE             = 1;

    public PrimitiveDataSource(final T content, final Link... links) {
        super(content, links);
    }

    public PrimitiveDataSource(final T content, final Iterable<Link> links) {
        super(content, links);
    }

    @Override
    public long getSize() {
        return SIZE;
    }
}
