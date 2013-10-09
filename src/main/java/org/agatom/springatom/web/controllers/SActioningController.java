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

package org.agatom.springatom.web.controllers;

import org.agatom.springatom.web.actions.SActionRepository;
import org.agatom.springatom.web.actions.beans.SActionDefinition;
import org.agatom.springatom.web.actions.beans.SActions;
import org.agatom.springatom.web.actions.controllers.SActionAwareController;
import org.agatom.springatom.web.actions.exception.SNoSuchActionDefinition;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.servlet.ServletException;

/**
 * {@code SActioningController} is the base class for the controller configured via specialized actions xml.
 * Each XML defines a general component for which it defines model and further the actions of that model.
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public abstract class SActioningController
        extends SDefaultController
        implements SActionAwareController {

    private SActionRepository actionsRepository;
    private String            componentKey;

    protected SActioningController(final String controllerName) {
        super(controllerName);
    }

    @PostConstruct
    private void init() {
        this.componentKey = this.getComponentKey();
    }

    protected abstract String getComponentKey();

    @Override
    @Autowired
    public void setActionsRepository(final SActionRepository repository) {
        this.actionsRepository = repository;
    }

    protected SActions getActions() throws SActioningControllerException {
        try {
            return this.actionsRepository.getActionsForComponent(this.componentKey);
        } catch (SNoSuchActionDefinition sNoSuchActionDefinition) {
            throw new SActioningControllerException(sNoSuchActionDefinition);
        }
    }

    protected SActionDefinition getAction(String actionPath) throws SActioningControllerException {
        final SActions actions = this.getActions();
        if (!actionPath.startsWith(this.componentKey)) {
            if (actionPath.charAt(0) != '.') {
                actionPath = String.format("%s.%s", this.componentKey, actionPath);
            } else {
                actionPath = String.format("%s%s", this.componentKey, actionPath);
            }
        }
        return actions.getDefinitionForPath(actionPath);
    }

    protected class SActioningControllerException
            extends ServletException {
        public SActioningControllerException(final Throwable cause) {
            super(cause);
        }
    }
}
