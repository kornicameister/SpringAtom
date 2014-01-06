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

package org.agatom.springatom.web.action;

import org.springframework.hateoas.Identifiable;
import org.springframework.hateoas.Link;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */

public class LinkAction
        implements Identifiable<String> {
    protected String url   = null;
    protected String label = null;

    public LinkAction setLabel(final String label) {
        this.label = label;
        return this;
    }

    public String getLabel() {
        return label;
    }

    public LinkAction setUrl(final String url) {
        this.url = url;
        return this;
    }

    public LinkAction setUrl(final Link link) {
        return this.setUrl(link.getHref());
    }

    public String getUrl() {
        return url;
    }

    public String getMode() {
        return StringUtils.uncapitalize(ClassUtils.getShortName(this.getClass()));
    }

    @Override
    public String getId() {
        return String.format("%s-%d", ClassUtils.getShortName(this.getClass()), Math.abs(this.hashCode()));
    }
}
