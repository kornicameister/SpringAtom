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

package org.agatom.springatom.server.service.domain;

import org.agatom.springatom.server.repository.exceptions.EntityInRevisionDoesNotExists;
import org.joda.time.DateTime;
import org.springframework.data.domain.Persistable;
import org.springframework.data.history.Revision;
import org.springframework.data.history.Revisions;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * {@code SBasicService} is the interface for the services layers in {@code SpringAtom}.
 * Defines commonly used functionality of <b>CRUD</b> operations.
 *
 * @author kornicameister
 * @version 0.0.2
 * @since 0.0.1
 */
public interface SService<T extends Persistable<ID>, ID extends Serializable, N extends Number & Comparable<N>>
		extends SBasicService<T, ID> {

	/**
	 * <p>findFirstRevision.</p>
	 *
	 * @param id a ID object.
	 *
	 * @return a {@link org.springframework.data.history.Revision} object.
	 */
	Revision<N, T> findFirstRevision(
			@NotNull
			final ID id);

	/**
	 * <p>findAllRevisions.</p>
	 *
	 * @param id a ID object.
	 *
	 * @return a {@link org.springframework.data.history.Revisions} object.
	 */
	Revisions<N, T> findAllRevisions(
			@NotNull
			final ID id);

	/**
	 * <p>findLatestRevision.</p>
	 *
	 * @param id a ID object.
	 *
	 * @return a {@link org.springframework.data.history.Revision} object.
	 */
	Revision<N, T> findLatestRevision(
			@NotNull
			final ID id);

	/**
	 * <p>countRevisions.</p>
	 *
	 * @param id a ID object.
	 *
	 * @return a long.
	 */
	long countRevisions(
			@NotNull
			final ID id);

	/**
	 * <p>findModifiedBefore.</p>
	 *
	 * @param id       a ID object.
	 * @param dateTime a {@link org.joda.time.DateTime} object.
	 *
	 * @return a {@link org.springframework.data.history.Revisions} object.
	 *
	 * @throws org.agatom.springatom.server.repository.exceptions.EntityInRevisionDoesNotExists if any.
	 */
	Revisions<N, T> findModifiedBefore(
			@NotNull
			final ID id,
			@NotNull
			final DateTime dateTime) throws EntityInRevisionDoesNotExists;

	/**
	 * <p>findModifiedAfter.</p>
	 *
	 * @param id       a ID object.
	 * @param dateTime a {@link org.joda.time.DateTime} object.
	 *
	 * @return a {@link org.springframework.data.history.Revisions} object.
	 *
	 * @throws org.agatom.springatom.server.repository.exceptions.EntityInRevisionDoesNotExists if any.
	 */
	Revisions<N, T> findModifiedAfter(
			@NotNull
			final ID id,
			@NotNull
			final DateTime dateTime) throws EntityInRevisionDoesNotExists;

	/**
	 * <p>findModifiedAt.</p>
	 *
	 * @param id       a ID object.
	 * @param dateTime a {@link org.joda.time.DateTime} object.
	 *
	 * @return a {@link org.springframework.data.history.Revisions} object.
	 *
	 * @throws org.agatom.springatom.server.repository.exceptions.EntityInRevisionDoesNotExists if any.
	 */
	Revisions<N, T> findModifiedAt(
			@NotNull
			final ID id,
			@NotNull
			final DateTime dateTime) throws EntityInRevisionDoesNotExists;

}
