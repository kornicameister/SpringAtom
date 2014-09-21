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

package org.agatom.springatom.web.component.infopages.elements;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Objects;
import org.agatom.springatom.core.util.Localized;
import org.agatom.springatom.web.component.core.EmbeddableComponent;
import org.agatom.springatom.web.component.core.elements.DefaultComponent;
import org.agatom.springatom.web.component.infopages.elements.meta.AttributeDisplayAs;

import javax.annotation.Nonnull;

/**
 * {@code InfoPageAttributeComponent} describes single <b>attribute</b> being part of {@link org.agatom.springatom.web.component.infopages.elements.InfoPagePanelComponent}
 *
 * @author kornicameister
 * @version 0.0.2
 * @since 0.0.1
 */
public class InfoPageAttributeComponent
        extends DefaultComponent
        implements Localized, EmbeddableComponent {
    private static final long               serialVersionUID = -6698659446456215525L;
    protected            int                position         = -1;
    protected            String             path             = null;
    protected            AttributeDisplayAs displayAs        = null;
    protected            String             messageKey       = null;

    /**
     * <p>Getter for the field <code>path</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getPath() {
        return path;
    }

    /**
     * <p>Setter for the field <code>path</code>.</p>
     *
     * @param path a {@link java.lang.String} object.
     *
     * @return a {@link org.agatom.springatom.web.component.infopages.elements.InfoPageAttributeComponent} object.
     */
    public InfoPageAttributeComponent setPath(final String path) {
        this.path = path;
        return this;
    }

    /**
     * <p>Getter for the field <code>displayAs</code>.</p>
     *
     * @return a {@link org.agatom.springatom.web.component.infopages.elements.meta.AttributeDisplayAs} object.
     */
    public AttributeDisplayAs getDisplayAs() {
        return displayAs;
    }

    /**
     * <p>Setter for the field <code>displayAs</code>.</p>
     *
     * @param displayAs a {@link org.agatom.springatom.web.component.infopages.elements.meta.AttributeDisplayAs} object.
     *
     * @return a {@link org.agatom.springatom.web.component.infopages.elements.InfoPageAttributeComponent} object.
     */
    public InfoPageAttributeComponent setDisplayAs(final AttributeDisplayAs displayAs) {
        this.displayAs = displayAs;
        return this;
    }

    /**
     * <p>isValueAttribute.</p>
     *
     * @return a boolean.
     */
    @JsonIgnore
    public boolean isValueAttribute() {
        return this.displayAs.equals(AttributeDisplayAs.VALUE_ATTRIBUTE);
    }

    /**
     * <p>isInfoPageAttribute.</p>
     *
     * @return a boolean.
     */
    @JsonIgnore
    public boolean isInfoPageAttribute() {
        return this.displayAs.equals(AttributeDisplayAs.INFOPAGE_ATTRIBUTE);
    }

    /**
     * <p>isEmbeddedAttribute.</p>
     *
     * @return a boolean.
     */
    @JsonIgnore
    public boolean isEmbeddedAttribute() {
        return this.displayAs.equals(AttributeDisplayAs.EMBEDDED_ATTRIBUTE);
    }

    /**
     * <p>isTableAttribute.</p>
     *
     * @return a boolean.
     */
    @JsonIgnore
    public boolean isTableAttribute() {
        return this.displayAs.equals(AttributeDisplayAs.TABLE_ATTRIBUTE);
    }

    /**
     * <p>isLinkAttribute.</p>
     *
     * @return a boolean.
     */
    @JsonIgnore
    public boolean isLinkAttribute() {
        return this.displayAs.equals(AttributeDisplayAs.LINK_ATTRIBUTE);
    }

    /** {@inheritDoc} */
    @Override
    public String getMessageKey() {
        return this.messageKey;
    }

    /**
     * <p>Setter for the field <code>messageKey</code>.</p>
     *
     * @param messageKey a {@link java.lang.String} object.
     */
    public void setMessageKey(final String messageKey) {
        this.messageKey = messageKey;
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

    /** {@inheritDoc} */
    @Override
    public int hashCode() {
        return Objects.hashCode(position, path, displayAs, messageKey, label);
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        InfoPageAttributeComponent that = (InfoPageAttributeComponent) o;

        return Objects.equal(this.position, that.position) &&
                Objects.equal(this.path, that.path) &&
                Objects.equal(this.displayAs, that.displayAs) &&
                Objects.equal(this.messageKey, that.messageKey) &&
                Objects.equal(this.label, that.label);
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .addValue(position)
                .addValue(path)
                .addValue(displayAs)
                .addValue(messageKey)
                .addValue(label)
                .toString();
    }
}
