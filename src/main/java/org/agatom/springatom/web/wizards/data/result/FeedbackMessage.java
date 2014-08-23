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

package org.agatom.springatom.web.wizards.data.result;

import com.google.common.base.Objects;

import java.io.Serializable;

/**
 * {@code FeedbackMessage} wraps up a message that is sent back to the client. Severity or importance
 * of the message is defined via {@link org.agatom.springatom.web.wizards.data.result.FeedbackType} which
 * allows client to react upon changes
 * <small>Class is a part of <b>SpringAtom</b> and was created at 2014-08-23</small>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
public class FeedbackMessage
        implements Serializable {
    private static final long         serialVersionUID = -2354212878234471998L;
    /**
     * Message to display
     */
    private              String       message          = null;
    /**
     * <b>Optional</b> title of the message
     */
    private              String       title            = null;
    /**
     * {@link org.agatom.springatom.web.wizards.data.result.FeedbackType}, type of the message
     */
    private              FeedbackType type             = null;

    /**
     * Creates new {@link org.agatom.springatom.web.wizards.data.result.FeedbackType#SUCCESS} message
     *
     * @return message
     */
    public static FeedbackMessage newSuccess() {
        return new FeedbackMessage().setType(FeedbackType.SUCCESS);
    }

    /**
     * Creates new {@link org.agatom.springatom.web.wizards.data.result.FeedbackType#CONFIRMATION} message
     *
     * @return message
     */
    public static FeedbackMessage newConfirmation() {
        return new FeedbackMessage().setType(FeedbackType.CONFIRMATION);
    }

    /**
     * Creates new {@link org.agatom.springatom.web.wizards.data.result.FeedbackType#FAILURE} message
     *
     * @return message
     */
    public static FeedbackMessage newFailure() {
        return new FeedbackMessage().setType(FeedbackType.FAILURE);
    }

    /**
     * Creates new {@link org.agatom.springatom.web.wizards.data.result.FeedbackType#ERROR} message
     *
     * @return message
     */
    public static FeedbackMessage newError() {
        return new FeedbackMessage().setType(FeedbackType.ERROR);
    }

    /**
     * Creates new {@link org.agatom.springatom.web.wizards.data.result.FeedbackType#INFO} message
     *
     * @return message
     */
    public static FeedbackMessage newInfo() {
        return new FeedbackMessage().setType(FeedbackType.INFO);
    }

    /**
     * Creates new {@link org.agatom.springatom.web.wizards.data.result.FeedbackType#WARNING} message
     *
     * @return message
     */
    public static FeedbackMessage newWarning() {
        return new FeedbackMessage().setType(FeedbackType.WARNING);
    }

    public String getTitle() {
        return title;
    }

    public FeedbackMessage setTitle(final String title) {
        this.title = title;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public FeedbackMessage setMessage(final String message) {
        this.message = message;
        return this;
    }

    public FeedbackType getType() {
        return type;
    }

    public FeedbackMessage setType(final FeedbackType type) {
        this.type = type;
        return this;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(message, title, type);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FeedbackMessage that = (FeedbackMessage) o;

        return Objects.equal(this.message, that.message) &&
                Objects.equal(this.title, that.title) &&
                Objects.equal(this.type, that.type);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("message", message)
                .add("title", title)
                .add("type", type)
                .toString();
    }
}
