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

package org.agatom.springatom.cmp.component.table.meta;

import org.agatom.springatom.cmp.component.core.context.ComponentContext;
import org.springframework.hateoas.Link;

import java.io.Serializable;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom</b> and was created at 2014-09-21</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
public class TableRenderInformation
        implements Serializable {
    private static final long             serialVersionUID = -4822100087343529154L;
    private              String           builderId        = null;
    private              Link             url              = null;
    private              ComponentContext context          = null;
    private              String           oid              = null;

    public String getBuilderId() {
        return builderId;
    }

    public TableRenderInformation setBuilderId(final String builderId) {
        this.builderId = builderId;
        return this;
    }

    public Link getUrl() {
        return url;
    }

    public TableRenderInformation setUrl(final Link url) {
        this.url = url;
        return this;
    }

    public ComponentContext getContext() {
        return context;
    }

    public TableRenderInformation setContext(final ComponentContext context) {
        this.context = context;
        return this;
    }

    public String getOid() {
        return oid;
    }

    public TableRenderInformation setOid(final String oid) {
        this.oid = oid;
        return this;
    }
}
