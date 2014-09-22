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

package org.agatom.springatom.web.component.core.data;

import com.google.common.base.MoreObjects;
import org.agatom.springatom.server.model.oid.SOid;
import org.springframework.ui.ModelMap;

/**
 * TODO: add JavaDoc
 *
 * @author kornicameister
 * @version 0.0.6
 * @since 0.0.1
 */
public class ComponentDataRequest {
    private Object   component     = null;
    private SOid     oid           = null;
    private ModelMap attributesMap = null;
    private ModelMap headersMap    = null;
    private ModelMap parametersMap = null;

    public ComponentDataRequest() {
    }

    public Object getComponent() {
        return component;
    }

    public void setComponent(final Object component) {
        this.component = component;
    }

    public SOid getOid() {
        return oid;
    }

    public ComponentDataRequest setOid(final SOid oid) {
        this.oid = oid;
        return this;
    }

    public boolean hasContext() {
        return this.oid != null;
    }


    public ModelMap getAttributesMap() {
        return attributesMap;
    }

    public ComponentDataRequest setAttributesMap(final ModelMap attributesMap) {
        this.attributesMap = attributesMap;
        return this;
    }

    public ModelMap getHeadersMap() {
        return headersMap;
    }

    public ComponentDataRequest setHeadersMap(final ModelMap headersMap) {
        this.headersMap = headersMap;
        return this;
    }

    public ModelMap getParametersMap() {
        return parametersMap;
    }

    public ComponentDataRequest setParametersMap(final ModelMap parametersMap) {
        this.parametersMap = parametersMap;
        return this;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("oid", oid)
                .toString();
    }
}
