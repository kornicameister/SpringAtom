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

import com.google.common.collect.Maps;
import org.agatom.springatom.webmvc.core.SVDefaultController;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * <small>Class is a part of <b>SpringAtom</b> and was created at 14.06.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Controller(SVInfoController.CTRL_NAME)
@RequestMapping(value = "/info")
public class SVInfoController
		extends SVDefaultController {
	protected static final String CTRL_NAME = "infoController";

	public SVInfoController() {
		super(CTRL_NAME);
	}

	@ResponseBody
	@RequestMapping(
			value = "/meta",
			method = RequestMethod.POST,
			produces = {MediaType.APPLICATION_JSON_VALUE}
	)
	public Object onInfoRequest() {
		final Map<String, Object> info = Maps.newHashMap();
		info.put("name", "SA");
		info.put("longName", "SpringAtom");
		info.put("version", "1.0 Alpha");
		return info;
	}

}
