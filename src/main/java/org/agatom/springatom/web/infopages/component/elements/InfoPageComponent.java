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

package org.agatom.springatom.web.infopages.component.elements;

import com.google.common.base.Objects;
import org.agatom.springatom.web.component.elements.ContentComponent;
import org.agatom.springatom.web.component.elements.ThumbnailComponent;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */

public class InfoPageComponent
        extends ContentComponent<InfoPagePanelComponent> {
    private static final long               serialVersionUID = -1693645505025410828L;
    private              ThumbnailComponent thumbnail        = null;

    public ThumbnailComponent getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(final ThumbnailComponent thumbnail) {
        this.thumbnail = thumbnail;
    }

    public int getPanelsCount() {
        return this.content.size();
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                      .addValue(thumbnail)
                      .addValue(content)
                      .toString();
    }
}
