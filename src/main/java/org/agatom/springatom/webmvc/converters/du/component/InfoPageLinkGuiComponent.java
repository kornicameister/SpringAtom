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

import com.google.common.base.Objects;
import com.google.common.collect.Maps;
import org.agatom.springatom.server.model.oid.SOid;
import org.agatom.springatom.web.component.core.context.ComponentContext;
import org.springframework.hateoas.Link;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom</b> and was created at 2014-09-22</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
public class InfoPageLinkGuiComponent
        extends AbstractGuiComponent<HashMap<String, Object>> {
    private static final long             serialVersionUID = 7511182780977578085L;
    private              String           rel              = null;
    private              ComponentContext context          = null;
    private              String           label            = null;
    private              Link             href             = null;
    private              SOid             oid              = null;

    public InfoPageLinkGuiComponent setOid(final SOid oid) {
        this.oid = oid;
        return this;
    }

    public InfoPageLinkGuiComponent setRel(final String rel) {
        this.rel = rel;
        return this;
    }

    public InfoPageLinkGuiComponent setContext(final ComponentContext context) {
        this.context = context;
        return this;
    }

    @Override
    public RenderAs getRenderAs() {
        return RenderAs.LINK;
    }


    public String getLabel() {
        return label;
    }

    public InfoPageLinkGuiComponent setLabel(final String label) {
        this.label = label;
        return this;
    }

    public InfoPageLinkGuiComponent setHref(final Link href) {
        this.href = href;
        return this;
    }

    @Override
    public HashMap<String, Object> getValue() {
        final Map<String, Object> map = Maps.newHashMap();

        map.put("rel", this.rel);
        map.put("oid", this.oid.getOid());
        map.put("context", this.context);
        map.put("href", this.href);
        map.put("label", this.label);

        return (HashMap<String, Object>) map;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(rel, context, label, href);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InfoPageLinkGuiComponent that = (InfoPageLinkGuiComponent) o;

        return Objects.equal(this.rel, that.rel) &&
                Objects.equal(this.context, that.context) &&
                Objects.equal(this.label, that.label) &&
                Objects.equal(this.href, that.href);
    }
}
