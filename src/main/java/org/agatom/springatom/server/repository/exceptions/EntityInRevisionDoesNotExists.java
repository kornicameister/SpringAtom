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

package org.agatom.springatom.server.repository.exceptions;

import org.springframework.data.domain.Persistable;

import java.io.Serializable;
import java.util.Arrays;

/**
 * {@code EntityInRevisionDoesNotExists} is thrown when entity in given revision or revisions can not been found.
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class EntityInRevisionDoesNotExists
		extends Exception {
	private static final String MSG              = "No entry exists for %s with revision=%s";
	private static final long   serialVersionUID = -7403813105949944889L;

	/**
	 * <p>Constructor for EntityInRevisionDoesNotExists.</p>
	 *
	 * @param entityClazz a {@link java.lang.Class} object.
	 * @param revision    a {@link java.lang.Object} object.
	 * @param <T>         a T object.
	 * @param <ID>        a ID object.
	 */
	public <T extends Persistable<ID>, ID extends Serializable> EntityInRevisionDoesNotExists(final Class<T> entityClazz, final Object... revision) {
		this(String.format(MSG, entityClazz.getName(), Arrays.toString(revision)));
	}

	/**
	 * <p>Constructor for EntityInRevisionDoesNotExists.</p>
	 *
	 * @param msg a {@link java.lang.String} object.
	 */
	public EntityInRevisionDoesNotExists(final String msg) {
		super(msg);
	}
}
