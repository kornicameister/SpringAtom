/**************************************************************************************************
 * This file is part of [SpringAtom] Copyright [kornicameister@gmail.com][2014]                   *
 *                                                                                                *
 * [SpringAtom] is free software: you can redistribute it and/or modify                           *
 * it under the terms of the GNU General Public License as published by                           *
 * the Free Software Foundation, either version 3 of the License, or                              *
 * (at your option) any later version.                                                            *
 *                                                                                                *
 * [SpringAtom] is distributed in the hope that it will be useful,                                *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of                                 *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the                                  *
 * GNU General Public License for more details.                                                   *
 *                                                                                                *
 * You should have received a copy of the GNU General Public License                              *
 * along with [SpringAtom].  If not, see <http://www.gnu.org/licenses/gpl.html>.                  *
 **************************************************************************************************/

package org.agatom.springatom.web.component.core.context;

import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;
import org.springframework.data.domain.Persistable;
import org.springframework.util.ClassUtils;

import javax.annotation.Nonnull;
import java.io.Serializable;
import java.lang.ref.WeakReference;

/**
 * {@code ComponentContext} wraps the context given page/component works on. Such context
 * is described mainly by {@link org.springframework.data.domain.Persistable} instance.
 * Contains:
 * <ol>
 * <li>{@link #id}</li>
 * <li>{@link #revision}</li>
 * <li>{@link #version}</li>
 * <li>{@link #domain}</li>
 * <li>{@link #object}, in fact {@link java.lang.ref.WeakReference}</li>
 * </ol>
 *
 * <small>Class is a part of <b>SpringAtom</b> and was created at 12.06.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class ComponentContext
        implements Serializable, Comparable<ComponentContext> {
    private static final long                       serialVersionUID = 5867417867216280653L;
    private              Long                       id               = null;
    private              Long                       revision         = null;
    private              Long                       version          = null;
    private              Class<?>                   domain           = null;
    private              WeakReference<Persistable> object           = null;

    public ComponentContext() {
        super();
    }

    /**
     * Creates new context for the {@code persistable} object
     *
     * @param persistable persistable to create context
     */
    public ComponentContext(final Persistable<?> persistable) {
        this(persistable.getId(), ClassUtils.getUserClass(persistable.getClass()));
        this.object = new WeakReference<Persistable>(persistable);
    }

    private ComponentContext(final Serializable id, final Class<?> domain) {
        this.id = (Long) id;
        this.domain = ClassUtils.getUserClass(domain);
    }

    public Long getId() {
        return id;
    }

    public ComponentContext setId(final Long id) {
        this.id = id;
        return this;
    }

    public Long getRevision() {
        return revision;
    }

    public ComponentContext setRevision(final Long revision) {
        this.revision = revision;
        return this;
    }

    public Long getVersion() {
        return version;
    }

    public ComponentContext setVersion(final Long version) {
        this.version = version;
        return this;
    }

    public Class<?> getDomain() {
        return domain;
    }

    public ComponentContext setDomain(final Class<? extends Persistable> domain) {
        this.domain = domain;
        return this;
    }

    public WeakReference<Persistable> getObject() {
        return object;
    }

    public ComponentContext setObject(final WeakReference<Persistable> object) {
        this.object = object;
        return this;
    }

    public boolean isValid() {
        return this.object != null;
    }

    @Override
    public int compareTo(@Nonnull final ComponentContext cc) {
        return ComparisonChain.start()
                .compare(this.domain.getName(), cc.domain.getName())
                .compare(this.id, cc.id)
                .compare(this.revision, cc.revision)
                .compare(this.version, cc.version)
                .result();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, revision, version, domain, object);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ComponentContext that = (ComponentContext) o;

        return Objects.equal(this.id, that.id) &&
                Objects.equal(this.revision, that.revision) &&
                Objects.equal(this.version, that.version) &&
                Objects.equal(this.domain, that.domain) &&
                Objects.equal(this.object, that.object);
    }
}
