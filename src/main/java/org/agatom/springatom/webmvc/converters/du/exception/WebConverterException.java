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

package org.agatom.springatom.webmvc.converters.du.exception;

/**
 * <small>Class is a part of <b>SpringAtom</b> and was created at 31.05.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class WebConverterException
		extends RuntimeException {
	private static final long   serialVersionUID = -1049736042004152500L;
	private              String conversionKey    = null;
	private              Object conversionValue  = null;

	/**
	 * <p>Constructor for WebConverterException.</p>
	 *
	 * @param message a {@link java.lang.String} object.
	 */
	public WebConverterException(final String message) {
		super(message);
	}

	/**
	 * <p>Constructor for WebConverterException.</p>
	 *
	 * @param message a {@link java.lang.String} object.
	 * @param cause   a {@link java.lang.Throwable} object.
	 */
	public WebConverterException(final String message, final Throwable cause) {
		super(message, cause);
	}

	/**
	 * <p>Getter for the field <code>conversionKey</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getConversionKey() {
		return conversionKey;
	}

	/**
	 * <p>Setter for the field <code>conversionKey</code>.</p>
	 *
	 * @param conversionKey a {@link java.lang.String} object.
	 *
	 * @return a {@link org.agatom.springatom.webmvc.converters.du.exception.WebConverterException} object.
	 */
	public WebConverterException setConversionKey(final String conversionKey) {
		this.conversionKey = conversionKey;
		return this;
	}

	/**
	 * <p>Getter for the field <code>conversionValue</code>.</p>
	 *
	 * @return a {@link java.lang.Object} object.
	 */
	public Object getConversionValue() {
		return conversionValue;
	}

	/**
	 * <p>Setter for the field <code>conversionValue</code>.</p>
	 *
	 * @param conversionValue a {@link java.lang.Object} object.
	 *
	 * @return a {@link org.agatom.springatom.webmvc.converters.du.exception.WebConverterException} object.
	 */
	public WebConverterException setConversionValue(final Object conversionValue) {
		this.conversionValue = conversionValue;
		return this;
	}
}
