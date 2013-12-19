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

package org.agatom.springatom.component.elements;

import org.agatom.springatom.component.EmbeddableComponent;
import org.agatom.springatom.component.meta.LayoutType;
import org.agatom.springatom.component.meta.PanelType;

import javax.annotation.Nonnull;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class PanelComponent<T extends EmbeddableComponent>
        extends ContentComponent<T>
        implements EmbeddableComponent {
    protected int        position = -1;
    protected PanelType  type     = null;
    protected LayoutType layout   = null;

    @Override
    public int getPosition() {
        return position;
    }

    @Override
    public void setPosition(final int position) {
        this.position = position;
    }

    public PanelType getType() {
        return type;
    }

    public PanelComponent setType(final PanelType type) {
        this.type = type;
        return this;
    }

    public LayoutType getLayout() {
        if (this.layout == null) {
            this.layout = LayoutType.VERTICAL;
        }
        return layout;
    }

    public PanelComponent setLayout(final LayoutType layout) {
        this.layout = layout;
        return this;
    }

    @Override
    public int compareTo(final @Nonnull EmbeddableComponent panel) {
        return Integer.compare(this.position, panel.getPosition());
    }
}
