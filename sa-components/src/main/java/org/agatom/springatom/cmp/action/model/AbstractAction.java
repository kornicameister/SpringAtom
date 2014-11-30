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

package org.agatom.springatom.cmp.action.model;

import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;
import org.agatom.springatom.cmp.action.model.security.ActionSecurityCheck;
import org.agatom.springatom.cmp.component.core.elements.DefaultComponent;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Nonnull;

/**
 * <small>Class is a part of <b>SpringAtom</b> and was created at 15.07.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class AbstractAction
        extends DefaultComponent
        implements Action {
    private static final long                serialVersionUID = -1186129891931343039L;
    private static final ActionSecurityCheck NO_SECURITY      = new ActionSecurityCheck().setAuthenticated(false);
    protected            String              name             = null;
    private              ActionSecurityCheck security         = NO_SECURITY;
    private              short               order            = -1;
    private              CSSRule             css              = null;

    public String getName() {
        return name;
    }

    public AbstractAction setName(final String name) {
        this.name = name;
        return this;
    }

    @Override
    public short getOrder() {
        return order;
    }

    public AbstractAction setOrder(final short order) {
        this.order = order;
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public String getMode() {
        return StringUtils.uncapitalize(ClassUtils.getShortName(this.getClass()));
    }

    @Override
    public ActionSecurityCheck getSecurity() {
        return security;
    }

    public AbstractAction setSecurity(final ActionSecurityCheck security) {
        this.security = security;
        return this;
    }

    @Override
    public CSSRule getCss() {
        return css;
    }

    public AbstractAction setCss(final CSSRule css) {
        this.css = css;
        return this;
    }

    public AbstractAction setIconClass(final String iconClass) {
        this.requireCSSRule().setIconClass(iconClass);
        return this;
    }

    private CSSRule requireCSSRule() {
        if (this.css == null) {
            this.css = new CSSRule();
        }
        return this.css;
    }

    public AbstractAction setBtnClass(final String btnClass) {
        this.requireCSSRule().setBtnClass(btnClass);
        return this;
    }

    public AbstractAction disableSecurity() {
        this.security = new ActionSecurityCheck().setAuthenticated(false);
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public String getId() {
        return String.format("%s-%d", ClassUtils.getShortName(this.getClass()), Math.abs(this.hashCode()));
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(security, css, label, dynamicProperties);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractAction that = (AbstractAction) o;

        return Objects.equal(this.security, that.security) &&
                Objects.equal(this.css, that.css) &&
                Objects.equal(this.label, that.label) &&
                Objects.equal(this.dynamicProperties, that.dynamicProperties);
    }

    @Override
    public int compareTo(@Nonnull final Action o) {
        return ComparisonChain.start().compare(this.order, o.getOrder()).result();
    }
}
