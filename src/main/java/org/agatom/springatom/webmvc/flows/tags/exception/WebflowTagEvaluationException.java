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

package org.agatom.springatom.webmvc.flows.tags.exception;

import javax.servlet.jsp.JspTagException;

/**
 * {@code WebflowTagEvaluationException} is a subclass of {@link javax.servlet.jsp.JspTagException}
 * marking an evaluation error in any SWF tags.
 *
 * @author kornicameister
 * @version 0.0.1
 * @see org.agatom.springatom.webmvc.flows.tags.AllStatesOfWebflow
 * @see org.agatom.springatom.webmvc.flows.tags.DynamicTransitionsTag
 * @see org.agatom.springatom.webmvc.flows.tags.PossibleTransitionsTag
 * @see org.agatom.springatom.webmvc.flows.tags.StateIdTag
 * @see org.agatom.springatom.webmvc.flows.tags.TransitionResolverTag
 * @since 0.0.1
 */
public class WebflowTagEvaluationException
        extends JspTagException {

    public WebflowTagEvaluationException(final String message) {
        super(message);
    }

    public WebflowTagEvaluationException(final Throwable cause) {
        this("Webflow tag threw exception...", cause);
    }

    public WebflowTagEvaluationException(final String message, final Throwable cause) {
        super(message, cause);
    }
}