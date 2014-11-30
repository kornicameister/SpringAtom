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

package org.agatom.springatom.webmvc.converters.du.converters;

import org.agatom.springatom.data.types.PersistentVersionedBean;
import org.agatom.springatom.webmvc.converters.du.annotation.WebConverter;
import org.springframework.data.domain.Persistable;

/**
 * <small>Class is a part of <b>SpringAtom</b> and was created at 02.06.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@WebConverter(key = "idVersion")
class PrimaryVersionKeyWebConverter
        extends PrimaryKeyWebConverter {

    /**
     * Returns combined {@link org.springframework.data.domain.Persistable#getId()} and {@link PersistentVersionedBean#getVersion()}
     */
    @Override
    protected String getPrimaryKeyValue(final Object value, final Persistable<?> persistable) {
        return String.format("%s [%s]", persistable.getId(), ((PersistentVersionedBean) persistable).getVersion());
    }

}
