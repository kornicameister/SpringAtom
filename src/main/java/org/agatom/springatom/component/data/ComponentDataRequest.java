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

package org.agatom.springatom.component.data;

import com.google.common.base.Objects;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class ComponentDataRequest {
    private final ModelMap   values;
    private final WebRequest webRequest;

    public ComponentDataRequest(final ModelMap modelMap, final WebRequest webRequest) {
        this.values = modelMap;
        this.webRequest = webRequest;
    }

    public ModelMap getValues() {
        return values;
    }

    public WebRequest getWebRequest() {
        return webRequest;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                      .addValue(values)
                      .addValue(webRequest)
                      .toString();
    }

    public Long getLong(final String key) {
        return Long.parseLong(String.valueOf(this.values.get(key)));
    }
}
