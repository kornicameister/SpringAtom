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
import org.agatom.springatom.cmp.component.core.EmbeddableComponent;

import javax.annotation.Nonnull;

/**
 * <p>PanelComponent class.</p>
 *
 * @author kornicameister
 * @version 0.0.2
 * @since 0.0.1
 */
public class PanelComponent<T extends EmbeddableComponent>
        extends ContentComponent<T>
        implements EmbeddableComponent {
    private static final long   serialVersionUID = 8998087479297251535L;
    protected            int    position         = -1;
    protected            String layout           = null;

    /**
     * <p>Getter for the field <code>layout</code>.</p>
     *
     * @return a {@link String} object.
     */
    public String getLayout() {
        return this.layout;
    }

    /**
     * <p>Setter for the field <code>layout</code>.</p>
     *
     * @param layout a {@link String} object.
     *
     * @return a {@link org.agatom.springatom.web.component.core.elements.PanelComponent} object.
     */
    public PanelComponent<T> setLayout(final String layout) {
        this.layout = layout;
        return this;
    }

    /** {@inheritDoc} */
    @Override
    @JsonIgnore
    public int getPosition() {
        return position;
    }

    /** {@inheritDoc} */
    @Override
    public void setPosition(final int position) {
        this.position = position;
    }

    /** {@inheritDoc} */
    @Override
    public int compareTo(final @Nonnull EmbeddableComponent panel) {
        return Integer.compare(this.position, panel.getPosition());
    }
}
