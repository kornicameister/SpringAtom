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

package org.agatom.springatom.webmvc.data;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Sets;
import org.agatom.springatom.web.beans.WebBean;
import org.springframework.ui.ModelMap;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.Set;

/**
 * <p>DataBean class.</p>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class DataBean
		implements WebBean,
		Iterable<String> {
	private static final String             BEAN_ID          = "restDataBean";
	private static final long               serialVersionUID = -8852958687534065708L;
	private              Set<DataBeanValue> values           = Sets.newHashSet();

	/**
	 * <p>toModelMap.</p>
	 *
	 * @return a {@link org.springframework.ui.ModelMap} object.
	 */
	public ModelMap toModelMap() {
		final ModelMap modelMap = new ModelMap();
		for (final DataBeanValue beanValue : this.getValues()) {
			modelMap.addAttribute(beanValue.getKey(), beanValue.getValue());
		}
		return modelMap;
	}

	/**
	 * <p>Getter for the field <code>values</code>.</p>
	 *
	 * @return a {@link java.util.Set} object.
	 */
	public Set<DataBeanValue> getValues() {
		return this.values;
	}

	/**
	 * <p>Setter for the field <code>values</code>.</p>
	 *
	 * @param values a {@link java.util.Set} object.
	 */
	public void setValues(final Set<DataBeanValue> values) {
		this.values = values;
	}

	/** {@inheritDoc} */
	@Override
	public String getBeanId() {
		return BEAN_ID;
	}

	/** {@inheritDoc} */
	@Override
	public Iterator<String> iterator() {
		return this.keySet().iterator();
	}

	/**
	 * <p>keySet.</p>
	 *
	 * @return a {@link java.util.Set} object.
	 */
	@Nonnull
	public Set<String> keySet() {
		final Set<String> keys = Sets.newHashSet();
		for (final DataBeanValue value : this.values) {
			keys.add(value.getKey());
		}
		return keys;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return Objects.toStringHelper(this)
				.addValue(values)
				.toString();
	}

	/**
	 * <p>size.</p>
	 *
	 * @return a int.
	 */
	public int size() {
		return this.values.size();
	}

	/**
	 * <p>isEmpty.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isEmpty() {
		return this.values.size() == 0;
	}

	/**
	 * <p>containsKey.</p>
	 *
	 * @param key a {@link java.lang.Object} object.
	 *
	 * @return a boolean.
	 */
	public boolean containsKey(final Object key) {
		return key instanceof String && this.keySet().contains(key);
	}

	/**
	 * <p>containsValue.</p>
	 *
	 * @param value a {@link java.lang.Object} object.
	 *
	 * @return a boolean.
	 */
	public boolean containsValue(final Object value) {
		return value instanceof DataBeanValue && this.values.contains(value);
	}

	/**
	 * <p>put.</p>
	 *
	 * @param value a {@link org.agatom.springatom.webmvc.data.DataBeanValue} object.
	 *
	 * @return a {@link org.agatom.springatom.webmvc.data.DataBeanValue} object.
	 */
	public DataBeanValue put(final DataBeanValue value) {
		if (this.values.add(value)) {
			return value;
		}
		return null;
	}

	/**
	 * <p>remove.</p>
	 *
	 * @param key a {@link java.lang.Object} object.
	 *
	 * @return a {@link org.agatom.springatom.webmvc.data.DataBeanValue} object.
	 */
	public DataBeanValue remove(final Object key) {
		DataBeanValue value = this.get(key);
		if (value != null && this.values.remove(value)) {
			return value;
		}
		return null;
	}

	/**
	 * <p>get.</p>
	 *
	 * @param key a {@link java.lang.Object} object.
	 *
	 * @return a {@link org.agatom.springatom.webmvc.data.DataBeanValue} object.
	 */
	public DataBeanValue get(final Object key) {
		if (key instanceof String) {
			final Optional<DataBeanValue> match = FluentIterable
					.from(this.values)
					.firstMatch(new Predicate<DataBeanValue>() {
						@Override
						public boolean apply(@Nullable final DataBeanValue input) {
							assert input != null;
							return input.getKey().equals(key);
						}
					});
			if (match.isPresent()) {
				return match.get();
			}
		}
		return null;
	}

	/**
	 * <p>clear.</p>
	 */
	public void clear() {
		this.values.clear();
	}
}
