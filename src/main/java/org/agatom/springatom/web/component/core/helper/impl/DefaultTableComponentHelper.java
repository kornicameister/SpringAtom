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

package org.agatom.springatom.web.component.core.helper.impl;

import org.agatom.springatom.web.component.core.elements.table.DandelionTableComponent;
import org.agatom.springatom.web.component.core.elements.table.TableColumnComponent;
import org.agatom.springatom.web.component.core.elements.table.TableComponent;
import org.agatom.springatom.web.component.core.helper.TableComponentHelper;
import org.agatom.springatom.web.component.infopages.link.InfoPageLinkHelper;
import org.agatom.springatom.webmvc.controllers.components.SVComponentsDataController;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Role;
import org.springframework.context.annotation.Scope;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Component
@Scope(BeanDefinition.SCOPE_SINGLETON)
@Role(BeanDefinition.ROLE_SUPPORT)
class DefaultTableComponentHelper
		extends DefaultComponentHelper
		implements TableComponentHelper {

	private static final Logger             LOGGER             = Logger.getLogger(DefaultTableComponentHelper.class);
	@Autowired
	private              InfoPageLinkHelper infoPageLinkHelper = null;

	@Override
	public Link getInfoPageLink(String path, Long id) {
		try {
			return this.infoPageLinkHelper.getInfoPageLink(path, id);
		} catch (Exception linkRetrievalException) {
			LOGGER.trace(String.format("Could not have resolved link to InfoPage[path=%s,id=%d] using %s", path, id, ControllerLinkBuilder.class),
					linkRetrievalException
			);
		}
		return new Link(String.format("/app/ip/%s/%d", path, id)).withRel(String.format("infopage.%s", path));
	}

	@Override
	public DandelionTableComponent newDandelionTable(final String tableId, final String builderId) {
		return (DandelionTableComponent) new DandelionTableComponent()
				.setUrl(this.getTableLink(tableId, builderId))
				.setTableId(tableId);
	}

	@Override
	public Link getTableLink(final String tableId, final String builderId) {
		try {
			return linkTo(methodOn(SVComponentsDataController.class).onTableDataRequest(null, null)).withRel(tableId);
		} catch (Exception linkRetrievalException) {
			LOGGER.trace(
					String.format("Could not have resolved link to TableBuilder[builderId=%s,tableId=%s] using %s",
							builderId,
							tableId,
							ControllerLinkBuilder.class
					)
			);
		}
		return new Link(String.format("/app/tableBuilder/data/%s", builderId)).withRel(tableId);
	}

	@Override
	public TableColumnComponent newTableColumn(final TableComponent cmp, final String path, final String rbKey) {
		final TableColumnComponent column = new TableColumnComponent();
		this.initColumn(cmp, path, rbKey, column);
		return column;
	}

	private void initColumn(final TableComponent cmp, final String path, final String rbKey, final TableColumnComponent column) {
		column.setProperty(path);
		column.setTitleKey(rbKey);
		column.setTitle(this.entitleFromMessageKey(column));
		cmp.addContent(column);
	}

}
