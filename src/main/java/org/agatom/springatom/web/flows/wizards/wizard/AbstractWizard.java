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

package org.agatom.springatom.web.flows.wizards.wizard;

import com.google.common.base.Objects;
import org.agatom.springatom.server.model.beans.user.SUser;
import org.agatom.springatom.web.beans.WebBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.webflow.execution.RequestContext;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
abstract public class AbstractWizard
        implements WebBean,
                   InitializingBean {
    private static final String DEFAULT_TITLE    = "NewWizard";
    private static final long   serialVersionUID = -1224480743925951112L;
    protected String title;
    protected SUser  createdBy;

    public String getTitle() {
        return title;
    }

    public SUser getCreatedBy() {
        return createdBy;
    }

    public void init(final RequestContext context) {
        this.title = context.getRequestParameters().get("title", AbstractWizard.DEFAULT_TITLE);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                      .addValue(title)
                      .addValue(createdBy)
                      .toString();
    }
}
