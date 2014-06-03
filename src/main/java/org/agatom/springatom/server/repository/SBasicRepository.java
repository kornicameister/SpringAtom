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

package org.agatom.springatom.server.repository;

import com.mysema.query.jpa.JPQLQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import java.io.Serializable;

/**
 * {@code SBasicRepository} is the foundamental interface for the repositories.
 * By extending {@link org.springframework.data.jpa.repository.JpaRepository} and {@link org.springframework.data.querydsl.QueryDslPredicateExecutor} it allows to query the database
 * with the help of <b>QueryDSL [{@link org.springframework.data.jpa.repository.support.Querydsl}]</b>.
 * Also it boosts already available functionality with new one.
 *
 * @author kornicameister
 * @version 0.0.2
 * @since 0.0.1
 */
public interface SBasicRepository<T, ID extends Serializable>
		extends JpaRepository<T, ID>,
		QueryDslPredicateExecutor<T> {

	/**
	 * Method to detach, if necessary, the object from the session.
	 *
	 * @param t object to be detached
	 *
	 * @return detached object
	 */
	T detach(T t);

	/**
	 * Returns plain {@link com.mysema.query.jpa.JPQLQuery} without any target and {@link com.mysema.query.types.Predicate} embedded
	 *
	 * @return the query
	 */
	JPQLQuery createCustomQuery();

	/**
	 * Custom operators to be used when constructing the queries
	 */
	public static enum Operators {
		BEFORE,
		AFTER,
		EQ
	}
}
