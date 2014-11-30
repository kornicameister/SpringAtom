package org.agatom.springatom.core.web;

import org.springframework.hateoas.Link;

import java.util.Collection;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-11-23</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
@SuppressWarnings("unchecked")
public class CollectionDataResource<T>
        extends DataResource<CollectionDataResource.CollectionWrapper<T>> {
    private static final long serialVersionUID = 3677322634123726090L;

    public CollectionDataResource(final Collection<T> content, final Link... links) {
        super(new CollectionWrapper<T>().setCollection(content), links);
    }

    public CollectionDataResource(final Collection<T> content, final Iterable<Link> links) {
        super(new CollectionWrapper<T>().setCollection(content), links);
    }

    @Override
    public long getSize() {
        final CollectionWrapper<T> content = this.getContent();
        return content != null ? content.getCollection().size() : 0;
    }

    protected static class CollectionWrapper<K> {
        private Collection<K> collection = null;

        public Collection<K> getCollection() {
            return collection;
        }

        public CollectionWrapper setCollection(final Collection<K> collection) {
            this.collection = collection;
            return this;
        }
    }
}
