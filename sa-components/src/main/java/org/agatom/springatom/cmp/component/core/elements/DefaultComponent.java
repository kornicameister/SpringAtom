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

package org.agatom.springatom.cmp.component.core.elements;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Maps;
import org.agatom.springatom.cmp.component.core.Component;

import javax.annotation.Nonnull;
import java.util.Map;

/**
 * <p>Abstract DefaultComponent class.</p>
 *
 * @author kornicameister
 * @version 0.0.2
 * @since 0.0.1
 */
abstract public class DefaultComponent
        implements Component {
    private static final long                serialVersionUID  = -2772317139576112812L;
    protected            String              label             = null;
    protected            Map<String, Object> dynamicProperties = null;

    /** {@inheritDoc} */
    @Override
    public String getLabel() {
        return this.label;
    }

    /** {@inheritDoc} */
    @Override
    public Component setLabel(final String label) {
        this.label = label;
        return this;
    }

    /**
     * <p>getDynamicProperty.</p>
     *
     * @param key a {@link String} object.
     *
     * @return a {@link Object} object.
     */
    public Object getDynamicProperty(@Nonnull final String key) {
        return this.getDynamicProperties().get(key);
    }

    /**
     * <p>Getter for the field <code>dynamicProperties</code>.</p>
     *
     * @return a {@link java.util.Map} object.
     */
    @JsonIgnore
    public Map<String, Object> getDynamicProperties() {
        if (this.dynamicProperties == null) {
            this.dynamicProperties = Maps.newHashMap();
        }
        return this.dynamicProperties;
    }

    /**
     * <p>addDynamicProperty.</p>
     *
     * @param key   a {@link String} object.
     * @param value a {@link Object} object.
     *
     * @return a {@link Object} object.
     */
    public Object addDynamicProperty(final String key, final Object value) {
        return this.getDynamicProperties().put(key, value);
    }
}
