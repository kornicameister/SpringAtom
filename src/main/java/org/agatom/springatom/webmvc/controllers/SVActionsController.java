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

package org.agatom.springatom.webmvc.controllers;

import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableSet;
import javassist.NotFoundException;
import org.agatom.springatom.web.action.model.Action;
import org.agatom.springatom.web.action.model.ActionModel;
import org.agatom.springatom.web.action.reader.ActionsModelReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * <small>Class is a part of <b>SpringAtom</b> and was created at 15.07.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Controller
@RequestMapping(value = "/cmp/actions")
@Description(value = "Data controller for ActionModels")
public class SVActionsController {

	@Autowired
	private ActionsModelReader actionsModelReader = null;

	@ResponseBody
	@RequestMapping(
			value = "/model/{*.+actionModel}",
			method = RequestMethod.GET,
			produces = {MediaType.APPLICATION_JSON_VALUE}
	)
	public ActionModel onActionModelRequest(@PathVariable("*.+actionModel") final String actionModel) throws NotFoundException {
		final ActionModel model = this.actionsModelReader.getActionModel(actionModel);
		final ImmutableSet<Action> filter = FluentIterable.from(model.getContent()).filter(new Predicate<Action>() {
			@Override
			public boolean apply(final Action input) {
				return true;
			}
		}).toSet();
		model.setContent(filter);
		return model;
	}

	@RequestMapping(
			value = "/model/jsp/{*.+actionModel}",
			method = RequestMethod.GET
	)
	public ModelAndView onActionModelRequestViaView(@PathVariable("*.+actionModel") final String actionModel) {
		return new ModelAndView("ui.nav.Navigator", new ModelMap("actionModel", actionModel));
	}

}
