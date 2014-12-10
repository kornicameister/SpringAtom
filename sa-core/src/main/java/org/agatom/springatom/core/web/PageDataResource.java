package org.agatom.springatom.core.web;

import org.springframework.data.domain.Page;
import org.springframework.hateoas.Link;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-12-09</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
@SuppressWarnings("unchecked")
public class PageDataResource<T>
        extends DataResource<PageDataResource.PageWrapper<T>> {
    private static final long serialVersionUID = 6611819552585374182L;

    public PageDataResource(final Page<T> content, final Link... links) {
        super(new PageWrapper<T>().setPage(content), links);
    }

    @Override
    public long getSize() {
        return this.getContent().getPage().getNumberOfElements();
    }

    protected static class PageWrapper<K> {
        private Page<K> page = null;

        public Page<K> getPage() {
            return page;
        }

        public PageWrapper setPage(final Page<K> collection) {
            this.page = collection;
            return this;
        }
    }
}
