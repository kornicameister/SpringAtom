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

package org.agatom.springatom.server.repository.impl;

import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPAQuery;
import org.agatom.springatom.server.repository.SBasicRepository;
import org.apache.log4j.Logger;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.QueryDslJpaRepository;

import javax.persistence.EntityManager;
import java.io.Serializable;

/**
 * {@code SBasicRepositoryImpl} implements {@link org.agatom.springatom.server.repository.SBasicRepository}
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class SBasicRepositoryImpl<T, ID extends Serializable>
		extends QueryDslJpaRepository<T, ID>
		implements SBasicRepository<T, ID> {

	private static final Logger LOGGER = Logger.getLogger(SBasicRepositoryImpl.class);
	protected final JpaEntityInformation<T, ID> entityInformation;
	protected final EntityManager               entityManager;

	/**
	 * <p>Constructor for SBasicRepositoryImpl.</p>
	 *
	 * @param entityInformation a {@link org.springframework.data.jpa.repository.support.JpaEntityInformation} object.
	 * @param entityManager     a {@link javax.persistence.EntityManager} object.
	 */
	public SBasicRepositoryImpl(final JpaEntityInformation<T, ID> entityInformation,
	                            final EntityManager entityManager) {
		super(entityInformation, entityManager);
		this.entityInformation = entityInformation;
		this.entityManager = entityManager;
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace(String
					.format("Created %s for arguments=[em=%s,ei=%s]", SBasicRepositoryImpl.class
							.getSimpleName(), entityManager, entityInformation));
		}
	}

	/** {@inheritDoc} */
	@Override
	public T detach(final T t) {
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace(String.format("detach(%s)", t));
		}
		this.entityManager.detach(t);
		return t;
	}

	/** {@inheritDoc} */
	@Override
	public JPQLQuery createCustomQuery() {
		return new JPAQuery(this.entityManager);
	}
}
