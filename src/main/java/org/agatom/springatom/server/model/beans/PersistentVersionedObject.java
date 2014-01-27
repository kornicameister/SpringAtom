/**************************************************************************************************
 * This file is part of [SpringAtom] Copyright [kornicameister@gmail.com][2013]                   *
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

package org.agatom.springatom.server.model.beans;

import org.agatom.springatom.core.identifier.BeanIdentifier;
import org.agatom.springatom.core.identifier.BeanVersionIdentifier;
import org.agatom.springatom.server.model.beans.user.SUser;
import org.agatom.springatom.server.model.types.PersistentVersionedBean;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.jpa.domain.AbstractAuditable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.ClassUtils;

import javax.annotation.Nonnull;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.validation.constraints.Min;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@MappedSuperclass
@EntityListeners(value = AuditingEntityListener.class)
abstract public class PersistentVersionedObject
        extends AbstractAuditable<SUser, Long>
        implements PersistentVersionedBean<Long> {
    private static final long serialVersionUID = -3113664043161581649L;
    @Version
    private Long version;

    public PersistentVersionedObject() {
        super();
    }

    @Override
    @Transient
    public String getMessageKey() {
        return ClassUtils.getShortName(this.getClass()).toLowerCase(LocaleContextHolder.getLocale());
    }

    @Override
    @Transient
    public String asString() {
        return String.format("%s=%s[%d]", ClassUtils.getShortName(this.getClass()), this.getId(), this.version);
    }

    @Override
    public Long getVersion() {
        return this.version;
    }

    @Override
    public void setVersion(@Nonnull @Min(value = 0) final Long version) {
        this.version = version;
    }

    @Override
    public BeanIdentifier<Long> getIdentifier() {
        return BeanVersionIdentifier.newIdentifier(this.getClass(), this.getId() == null ? Long.valueOf(-1l) : this.getId(), this.version);
    }
}
