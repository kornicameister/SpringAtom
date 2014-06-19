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

package org.agatom.springatom.webmvc.converters.du.component;

import com.google.common.collect.Lists;
import org.agatom.springatom.web.component.core.elements.DefaultComponent;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.List;

/**
 * <small>Class is a part of <b>SpringAtom</b> and was created at 17.06.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
abstract public class AbstractGuiComponent<T extends Serializable>
		extends DefaultComponent
		implements GuiComponent {
	private static final long         serialVersionUID = -2742680241932145098L;
	protected            String       name             = null;
	protected            Object       rawValue         = null;
	protected            T            value            = null;
	protected            String       tooltip          = null;
	protected            boolean      multiValued      = false;    // true if there is more than one value
	private              List<String> styles           = null;

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public boolean isMultiValued() {
		return multiValued;
	}

	public void setMultiValued(final boolean multiValued) {
		this.multiValued = multiValued;
	}

	public String getTooltip() {
		return tooltip;
	}

	public void setTooltip(final String tooltip) {
		this.tooltip = tooltip;
	}

	public T getValue() {
		return value;
	}

	public void setValue(final T value) {
		this.value = value;
	}

	@Override
	public Object getRawValue() {
		return this.rawValue;
	}

	public void setRawValue(final Object rawValue) {
		this.rawValue = rawValue;
	}

	public List<String> getStyles() {
		return styles;
	}

	public void setStyles(final List<String> styles) {
		this.styles = styles;
	}

	public void addStyle(final String style) {
		if (!StringUtils.hasText(style)) {
			return;
		}
		if (this.styles == null) {
			this.styles = Lists.newArrayList();
		}
		this.styles.add(style);
	}

	/**
	 * To be reimplemented by subclass. By default returns short and decapitalized class name
	 *
	 * @return xtype for ExtJS
	 */
	public String getType() {
		return StringUtils.uncapitalize(ClassUtils.getShortName(this.getClass()));
	}
}
