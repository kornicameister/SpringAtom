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

package org.agatom.springatom.web.locale.beans;

import com.google.common.base.Objects;
import org.agatom.springatom.web.beans.WebBean;

import java.util.Locale;

/**
 * {@code SLocale} is {@link org.agatom.springatom.web.beans.WebBean} that carries information about the
 * localization.
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class SLocale
		implements WebBean {
	private static final long   serialVersionUID = -1965000113537795587L;
	private static final String BEAN_ID          = "sLocale";
	private String  tag;
	private String  language;
	private String  country;
	private boolean isSet;

	/**
	 * <p>fromLocale.</p>
	 *
	 * @param locale a {@link java.util.Locale} object.
	 *
	 * @return a {@link org.agatom.springatom.web.locale.beans.SLocale} object.
	 */
	public static SLocale fromLocale(final Locale locale) {
		return new SLocale()
				.setTag(locale.toLanguageTag())
				.setCountry(locale.getCountry())
				.setLanguage(locale.getLanguage());
	}

	/**
	 * <p>toLocale.</p>
	 *
	 * @param sLocale a {@link org.agatom.springatom.web.locale.beans.SLocale} object.
	 *
	 * @return a {@link java.util.Locale} object.
	 */
	public static Locale toLocale(final SLocale sLocale) {
		return Locale.forLanguageTag(sLocale.tag);
	}

	/**
	 * <p>Setter for the field <code>isSet</code>.</p>
	 *
	 * @param isSet a boolean.
	 *
	 * @return a {@link org.agatom.springatom.web.locale.beans.SLocale} object.
	 */
	public SLocale setIsSet(final boolean isSet) {
		this.isSet = isSet;
		return this;
	}

	/**
	 * <p>isSet.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isSet() {
		return isSet;
	}

	/**
	 * <p>Getter for the field <code>tag</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getTag() {
		return tag;
	}

	/**
	 * <p>Setter for the field <code>tag</code>.</p>
	 *
	 * @param tag a {@link java.lang.String} object.
	 *
	 * @return a {@link org.agatom.springatom.web.locale.beans.SLocale} object.
	 */
	public SLocale setTag(final String tag) {
		this.tag = tag.replace("-", "_");
		return this;
	}

	/**
	 * <p>Getter for the field <code>language</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * <p>Setter for the field <code>language</code>.</p>
	 *
	 * @param language a {@link java.lang.String} object.
	 *
	 * @return a {@link org.agatom.springatom.web.locale.beans.SLocale} object.
	 */
	public SLocale setLanguage(final String language) {
		this.language = language;
		return this;
	}

	/**
	 * <p>Getter for the field <code>country</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * <p>Setter for the field <code>country</code>.</p>
	 *
	 * @param country a {@link java.lang.String} object.
	 *
	 * @return a {@link org.agatom.springatom.web.locale.beans.SLocale} object.
	 */
	public SLocale setCountry(final String country) {
		this.country = country;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public int hashCode() {
		return Objects.hashCode(tag, language, country, isSet);
	}

	/** {@inheritDoc} */
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		SLocale that = (SLocale) o;

		return Objects.equal(this.tag, that.tag) &&
				Objects.equal(this.language, that.language) &&
				Objects.equal(this.country, that.country) &&
				Objects.equal(this.isSet, that.isSet);
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return Objects.toStringHelper(this)
				.addValue(tag)
				.addValue(language)
				.addValue(country)
				.addValue(isSet)
				.toString();
	}

	/** {@inheritDoc} */
	@Override
	public String getBeanId() {
		return BEAN_ID;
	}
}
