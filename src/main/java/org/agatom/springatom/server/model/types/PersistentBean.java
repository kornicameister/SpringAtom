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

package org.agatom.springatom.server.model.types;

import org.agatom.springatom.core.identifier.Identified;
import org.agatom.springatom.core.util.Localized;
import org.agatom.springatom.core.util.StringAdaptable;

import java.io.Serializable;

/**
 * {@code PersistentBean} is common interface for all beans in <b>SpringAtom</b>.
 * The main of goal for it is to provide general functionality applicable for the beans.
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public interface PersistentBean<PK extends Serializable>
        extends StringAdaptable,
                Localized,
                Identified<PK> {

}
