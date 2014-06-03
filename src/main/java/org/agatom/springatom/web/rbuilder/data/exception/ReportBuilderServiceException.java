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

package org.agatom.springatom.web.rbuilder.data.exception;

import org.springframework.data.domain.Persistable;

import java.io.Serializable;

/**
 * <p>ReportBuilderServiceException class.</p>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class ReportBuilderServiceException
		extends ReportException {

	private static final long serialVersionUID = -5282239784661658192L;

	/**
	 * <p>Constructor for ReportBuilderServiceException.</p>
	 *
	 * @param message a {@link java.lang.String} object.
	 * @param cause   a {@link java.lang.Throwable} object.
	 */
	public ReportBuilderServiceException(final String message, final Throwable cause) {
		super(message, cause);
	}

	/**
	 * <p>Constructor for ReportBuilderServiceException.</p>
	 *
	 * @param cause a {@link java.lang.Throwable} object.
	 */
	public ReportBuilderServiceException(final Throwable cause) {
		super(cause);
	}

	/**
	 * <p>Constructor for ReportBuilderServiceException.</p>
	 *
	 * @param message a {@link java.lang.String} object.
	 */
	public ReportBuilderServiceException(final String message) {
		super(message);
	}

	/**
	 * <p>Constructor for ReportBuilderServiceException.</p>
	 *
	 * @param target  a {@link java.lang.Class} object.
	 * @param message a {@link java.lang.String} object.
	 * @param <T>     a T object.
	 * @param <ID>    a ID object.
	 */
	public <T extends Persistable<ID>, ID extends Serializable> ReportBuilderServiceException(final Class<T> target, final String message) {
		super(target, message);
	}
}
