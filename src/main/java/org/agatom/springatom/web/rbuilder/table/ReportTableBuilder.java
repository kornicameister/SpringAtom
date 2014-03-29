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

package org.agatom.springatom.web.rbuilder.table;

import com.mysema.query.types.Predicate;
import org.agatom.springatom.server.model.beans.report.QSReport;
import org.agatom.springatom.server.model.beans.report.SReport;
import org.agatom.springatom.web.action.model.actions.AjaxAction;
import org.agatom.springatom.web.action.model.actions.PopupAction;
import org.agatom.springatom.web.component.builders.annotation.ComponentBuilds;
import org.agatom.springatom.web.component.builders.annotation.EntityBased;
import org.agatom.springatom.web.component.builders.table.TableComponentBuilder;
import org.agatom.springatom.web.component.builders.table.exception.DynamicColumnResolutionException;
import org.agatom.springatom.web.component.elements.table.DandelionTableComponent;
import org.agatom.springatom.webmvc.controllers.rbuilder.ReportBuilderController;
import org.apache.log4j.Logger;
import org.springframework.hateoas.Link;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@EntityBased(entity = SReport.class)
@ComponentBuilds(
		id = ReportTableBuilder.BUILDER_ID,
		builds = SReport.class,
		produces = ComponentBuilds.Produces.TABLE_COMPONENT
)
public class ReportTableBuilder
		extends TableComponentBuilder<DandelionTableComponent, SReport> {

	private static final   long   serialVersionUID = -4745941707048690321L;
	private static final   String TABLE_ID         = String.format("%s%s", "table", StringUtils.uncapitalize(SReport.ENTITY_NAME));
	private static final   Logger LOGGER           = Logger.getLogger(ReportTableBuilder.class);
	protected static final String BUILDER_ID       = "reportTableBuilder";

	@Override
	protected DandelionTableComponent buildDefinition() {
		final DandelionTableComponent component = this.helper.newDandelionTable(TABLE_ID, BUILDER_ID);
		this.helper.newTableColumn(component, "id", "persistentobject.id");
		this.helper.newTableColumn(component, "title", "sreport.title");
		this.helper.newTableColumn(component, "subtitle", "sreport.subtitle");
		this.helper.newTableColumn(component, "description", "sreport.description").setSortable(false);
		this.helper.newTableColumn(component, "generate-action", "reports.actions.generate")
				.setRenderFunctionName("renderTableAction")
				.setSortable(false)
				.setFilterable(false);
		this.helper.newTableColumn(component, "delete-action", "reports.actions.delete")
				.setRenderFunctionName("renderTableAction")
				.setSortable(false)
				.setFilterable(false);
		return component;
	}

	@Override
	protected Object handleDynamicColumn(final SReport object, final String path) throws DynamicColumnResolutionException {
		Object retValue = super.handleDynamicColumn(object, path);
		if (retValue != null) {
			return retValue;
		}
		switch (path) {
			case "generate-action": {
				try {
					final Link link = linkTo(ReportBuilderController.class).slash(object.getId()).withSelfRel();
					retValue = new PopupAction().setType(RequestMethod.POST)
							.setUrl(link.getHref());
				} catch (Exception exception) {
					final String message = String.format("Error in creating link over path=%s", path);
					this.logger.error(message, exception);
					throw new DynamicColumnResolutionException(message, exception);
				}
			}
			break;
			case "delete-action": {
				try {
					final Link link = linkTo(ReportBuilderController.class).slash(object.getId()).withSelfRel();
					retValue = new AjaxAction().setType(RequestMethod.DELETE)
							.setUrl(link.getHref());
				} catch (Exception exception) {
					final String message = String.format("Error in creating link over path=%s", path);
					this.logger.error(message, exception);
					throw new DynamicColumnResolutionException(message, exception);
				}
			}
		}
		return retValue;
	}

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}

	@Override
	protected Predicate getPredicate(final Long reportId, final Class<?> contextClass) {
		if (!ClassUtils.isAssignable(SReport.class, contextClass)) {
			return null;
		}
		return QSReport.sReport.id.eq(reportId);
	}
}
