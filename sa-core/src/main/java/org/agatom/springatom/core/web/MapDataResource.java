package org.agatom.springatom.core.web;

import org.springframework.hateoas.Link;

import java.util.Map;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-11-20</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
public class MapDataResource<K, V>
        extends DataResource<Map<K, V>> {
    private static final long serialVersionUID = -3460926416892058689L;

    public MapDataResource(final Map<K, V> content, final Link... links) {
        super(content, links);
    }

    public MapDataResource(final Map<K, V> content, final Iterable<Link> links) {
        super(content, links);
    }

    @Override
    public final long getSize() {
        final Map<K, V> map = this.getContent();
        return map != null ? map.size() : 0;
    }
}
