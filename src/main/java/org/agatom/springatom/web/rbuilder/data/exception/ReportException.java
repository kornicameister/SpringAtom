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

import org.agatom.springatom.server.service.support.exceptions.ServiceException;
import org.springframework.data.domain.Persistable;

import java.io.Serializable;

/**
 * <p>Abstract ReportException class.</p>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
abstract public class ReportException
		extends ServiceException {
	private static final long serialVersionUID = 6553574310591708853L;

	/**
	 * <p>Constructor for ReportException.</p>
	 *
	 * @param message a {@link java.lang.String} object.
	 */
	public ReportException(final String message) {
		super(message);
	}

	/**
	 * <p>Constructor for ReportException.</p>
	 *
	 * @param message a {@link java.lang.String} object.
	 * @param cause   a {@link java.lang.Throwable} object.
	 */
	public ReportException(final String message, final Throwable cause) {
		super(message, cause);
	}

	/**
	 * <p>Constructor for ReportException.</p>
	 *
	 * @param target  a {@link java.lang.Class} object.
	 * @param message a {@link java.lang.String} object.
	 * @param <T>     a T object.
	 * @param <ID>    a ID object.
	 */
	public <T extends Persistable<ID>, ID extends Serializable> ReportException(final Class<T> target, final String message) {
		super(target, message);
	}

	/**
	 * <p>Constructor for ReportException.</p>
	 *
	 * @param target  a {@link java.lang.Class} object.
	 * @param message a {@link java.lang.String} object.
	 * @param cause   a {@link java.lang.Throwable} object.
	 * @param <T>     a T object.
	 * @param <ID>    a ID object.
	 */
	public <T extends Persistable<ID>, ID extends Serializable> ReportException(final Class<T> target, final String message, final Throwable cause) {
		super(target, message, cause);
	}

	/**
	 * <p>Constructor for ReportException.</p>
	 *
	 * @param target a {@link java.lang.Class} object.
	 * @param cause  a {@link java.lang.Throwable} object.
	 * @param <T>    a T object.
	 * @param <ID>   a ID object.
	 */
	public <T extends Persistable<ID>, ID extends Serializable> ReportException(final Class<T> target, final Throwable cause) {
		super(target, cause);
	}

	/**
	 * <p>Constructor for ReportException.</p>
	 *
	 * @param target             a {@link java.lang.Class} object.
	 * @param message            a {@link java.lang.String} object.
	 * @param cause              a {@link java.lang.Throwable} object.
	 * @param enableSuppression  a boolean.
	 * @param writableStackTrace a boolean.
	 * @param <T>                a T object.
	 * @param <ID>               a ID object.
	 */
	public <T extends Persistable<ID>, ID extends Serializable> ReportException(final Class<T> target, final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
		super(target, message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * <p>Constructor for ReportException.</p>
	 *
	 * @param cause a {@link java.lang.Throwable} object.
	 */
	public ReportException(final Throwable cause) {
		super(cause);
	}
}
