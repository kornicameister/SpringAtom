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
import org.agatom.springatom.web.component.core.builders.annotation.ComponentBuilder;
import org.agatom.springatom.web.component.core.builders.annotation.EntityBased;
import org.agatom.springatom.web.component.core.data.ComponentDataRequest;
import org.agatom.springatom.web.component.infopages.elements.meta.AttributeDisplayAs;
import org.agatom.springatom.web.component.table.TableComponentBuilder;
import org.agatom.springatom.web.component.table.elements.extjs.NgTable;
import org.agatom.springatom.web.component.table.elements.extjs.feature.NgSummaryFeature;
import org.agatom.springatom.web.locale.beans.LocalizedClassModel;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

/**
 * <p>ReportTableBuilder class.</p>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@EntityBased(entity = SReport.class)
@ComponentBuilder(ReportTableBuilder.BUILDER_ID)
public class ReportTableBuilder
		extends TableComponentBuilder<NgTable, SReport> {
	/** Constant <code>BUILDER_ID="reportTableBuilder"</code> */
	protected static final String BUILDER_ID = "reportTableBuilder";
	private static final   String TABLE_ID   = String.format("%s%s", "table", StringUtils.uncapitalize(SReport.ENTITY_NAME));

	/** {@inheritDoc} */
	@Override
	protected NgTable buildDefinition(final ComponentDataRequest dataRequest) {
		final QSReport report = QSReport.sReport;
		final NgTable table = new NgTable(TABLE_ID, BUILDER_ID);
		final LocalizedClassModel<SReport> lModel = this.getLocalizedClassModel();

		table.setBorder(false)
				.addFeature(new NgSummaryFeature().setRemoteRoot(this.getAttributeName(report.createdBy)))
				.setSortableColumns(true)
				.setCollapsible(false)
				.setForceFit(true);

		table.addContent(this.newColumn(report.id, AttributeDisplayAs.VALUE_ATTRIBUTE, lModel));
		table.addContent(this.newColumn(report.title, AttributeDisplayAs.VALUE_ATTRIBUTE, lModel));
		table.addContent(this.newColumn(report.subtitle, AttributeDisplayAs.VALUE_ATTRIBUTE, lModel));
		table.addContent(this.newColumn(report.resource, AttributeDisplayAs.VALUE_ATTRIBUTE, lModel));
		table.addContent(this.newColumn(report.reportedClass, AttributeDisplayAs.VALUE_ATTRIBUTE, lModel));
		table.addContent(this.newColumn(report.createdBy, AttributeDisplayAs.VALUE_ATTRIBUTE, lModel));
		table.addContent(this.newColumn(report.createdDate, AttributeDisplayAs.VALUE_ATTRIBUTE, lModel));

		return table;
	}

	/** {@inheritDoc} */
	@Override
	protected Predicate getPredicate(final Long reportId, final Class<?> contextClass) {
		if (!ClassUtils.isAssignable(SReport.class, contextClass)) {
			return null;
		}
		return QSReport.sReport.id.eq(reportId);
	}
}
