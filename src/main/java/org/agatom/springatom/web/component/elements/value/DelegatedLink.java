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

package org.agatom.springatom.web.component.elements.value;

import org.agatom.springatom.web.component.ComponentValue;
import org.springframework.hateoas.Link;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class DelegatedLink
        implements ComponentValue {
    private final Link   link;
    private       String label;

    public DelegatedLink(final Link link) {
        this.link = link;
    }

    public String getLabel() {
        return label;
    }

    public DelegatedLink withLabel(final String label) {
        this.label = label;
        return this;
    }

    public String getHref() {
        return link.getHref();
    }

    public String getRel() {
        return link.getRel();
    }

    public DelegatedLink withRel(final String rel) {
        link.withRel(rel);
        return this;
    }

}
