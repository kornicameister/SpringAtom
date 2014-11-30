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

package org.agatom.springatom.data.oid.creators;

import org.agatom.springatom.data.oid.SOid;
import org.agatom.springatom.data.oid.annotation.OidProvider;
import org.agatom.springatom.data.repositories.SRepository;
import org.agatom.springatom.data.repositories.provider.RepositoriesHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Persistable;
import org.springframework.util.ClassUtils;

import java.lang.ref.WeakReference;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom</b> and was created at 2014-09-18</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
@OidProvider(prefix = PersistableSOidCreator.PERSISTABLE_TYPE_PREFIX)
class PersistableSOidCreator
        extends DefaultSOidCreator<Persistable<?>> {
    protected static final String             PERSISTABLE_TYPE_PREFIX = "P";
    @Autowired
    private                RepositoriesHelper repositoriesHelper      = null;

    @Override
    @SuppressWarnings("unchecked")
    public <S extends Persistable<?>> SOid fromObject(final S from) throws Exception {
        return new PersistableOid()
                .setReference(new WeakReference<Persistable<?>>(from))
                .setObjectClass((Class<Persistable<?>>) ClassUtils.getUserClass(from))
                .setObjectId((Long) from.getId())
                .setObjectPrefix(PERSISTABLE_TYPE_PREFIX);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected SOid getOidObject(final String oidPrefix, final Class<?> oidClass, final String oidId) {
        final SRepository<?> repositoryFor = (SRepository<?>) this.repositoriesHelper.getRepositoryFor(oidClass);
        final Object one = repositoryFor.findOne(Long.valueOf(oidId));
        return new PersistableOid()
                .setReference(new WeakReference<Persistable<?>>((Persistable<?>) one))
                .setObjectPrefix(oidPrefix)
                .setObjectClass((Class<Persistable<?>>) oidClass)
                .setObjectId(Long.valueOf(oidId));
    }

    @Override
    public String getTypePrefix() {
        return PERSISTABLE_TYPE_PREFIX;
    }

    @Override
    protected Object getTypeId(final Persistable<?> from) {
        return from.getId();
    }


}
