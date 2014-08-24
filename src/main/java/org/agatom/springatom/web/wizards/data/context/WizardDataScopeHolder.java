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

package org.agatom.springatom.web.wizards.data.context;

import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Maps;
import org.apache.log4j.Logger;
import org.springframework.binding.collection.MapAdaptable;
import org.springframework.ui.ModelMap;
import org.springframework.util.ClassUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Nullable;
import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

/**
 * {@code WizardDataScopeHolder} is a class designed to contain {@link org.agatom.springatom.web.wizards.data.context.DataScope}
 * data for result messaging system of {@link org.agatom.springatom.web.wizards.WizardProcessor}
 *
 * <small>Class is a part of <b>SpringAtom</b> and was created at 2014-08-22</small>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
public final class WizardDataScopeHolder
        implements Serializable, MapAdaptable<String, Object> {
    private static final long                     serialVersionUID = 3492872742060913573L;
    private static final DataScope[]              DATA_SCOPES      = DataScope.values();
    private static final Logger                   LOGGER           = Logger.getLogger(WizardDataScopeHolder.class);
    private final        Map<DataScope, ModelMap> data             = Maps.newHashMapWithExpectedSize(DATA_SCOPES.length);

    public WizardDataScopeHolder addWizardData(final Object data) {
        return this.addData(DataScope.WIZARD, null, data);
    }

    @SuppressWarnings("unchecked")
    private WizardDataScopeHolder addData(final DataScope scope, final String key, final Object data) {
        LOGGER.trace(String.format("addData(scope=%s,key=%s,data=%s)", scope, key, ObjectUtils.getDisplayString(data)));
        if (data == null) {
            LOGGER.trace(String.format("Cannot add data, because it is null"));
            return this;
        } else if (ClassUtils.isAssignableValue(Collection.class, data) && ((Collection<?>) data).isEmpty()) {
            LOGGER.trace(String.format("Data is an empty collection, wont be added"));
            return this;
        } else if (ClassUtils.isAssignableValue(Map.class, data) && ((Map<?, ?>) data).isEmpty()) {
            LOGGER.trace(String.format("Data is an empty collection, wont be added"));
            return this;
        }
        ModelMap localHolder = this.data.get(scope);
        if (localHolder == null) {
            localHolder = new ModelMap();
            this.data.put(scope, localHolder);
        }

        final boolean isMap = ClassUtils.isAssignableValue(Map.class, data);
        final boolean isCollection = ClassUtils.isAssignableValue(Collection.class, data);
        final boolean keyHasText = StringUtils.hasText(key);

        if (isMap && !keyHasText) {
            localHolder.addAllAttributes((Map<String, ?>) data);
        } else if (isCollection && !keyHasText) {
            localHolder.addAllAttributes((Collection<?>) data);
        } else if (!keyHasText) {
            localHolder.addAttribute(data);
        } else {
            localHolder.addAttribute(key, data);
        }

        return this;
    }

    public WizardDataScopeHolder addFormData(final Object data) {
        return this.addData(DataScope.FORM, null, data);
    }

    public WizardDataScopeHolder addFormDataData(final Object data) {
        return this.addData(DataScope.FORM_DATA, null, data);
    }

    public WizardDataScopeHolder addStepData(final Object data) {
        return this.addData(DataScope.STEP, null, data);
    }

    public WizardDataScopeHolder addWizardData(final String key, final Object data) {
        return this.addData(DataScope.WIZARD, key, data);
    }

    public WizardDataScopeHolder addFormData(final String key, final Object data) {
        return this.addData(DataScope.FORM, key, data);
    }

    public WizardDataScopeHolder addFormDataData(final String key, final Object data) {
        return this.addData(DataScope.FORM_DATA, key, data);
    }

    public WizardDataScopeHolder addStepData(final String key, final Object data) {
        return this.addData(DataScope.STEP, key, data);
    }

    public Map<DataScope, ModelMap> getData() {
        return data;
    }

    @Override
    public Map<String, Object> asMap() {
        return FluentIterable.from(this.data.keySet())
                .transform(new Function<DataScope, String>() {
                    @Nullable
                    @Override
                    public String apply(@Nullable final DataScope input) {
                        assert input != null;
                        return input.name().toLowerCase().replaceAll("_", "");
                    }
                })
                .toMap(new Function<String, Object>() {
                    @Nullable
                    @Override
                    public Object apply(@Nullable final String input) {
                        assert input != null;
                        return data.get(DataScope.valueOf(input.toUpperCase()));
                    }
                });
    }

    public WizardDataScopeHolder merge(final WizardDataScopeHolder data) {
        this.data.putAll(data.data);
        return this;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(data);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WizardDataScopeHolder that = (WizardDataScopeHolder) o;

        return Objects.equal(this.data, that.data);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("data", data)
                .toString();
    }
}
