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

package org.agatom.springatom.web.rbuilder.data.service;

import net.sf.jasperreports.engine.JasperReport;
import org.agatom.springatom.server.model.types.report.Report;
import org.agatom.springatom.web.rbuilder.bean.RBuilderColumn;
import org.agatom.springatom.web.rbuilder.bean.RBuilderEntity;
import org.agatom.springatom.web.rbuilder.data.exception.ReportGenerationException;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * <p>JasperBuilderService interface.</p>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Validated
public interface JasperBuilderService {
	// TODO remove dependency for rbuilder.ban.RBuilderEntity -> replace with something more general

	/**
	 * <p>generateReport.</p>
	 *
	 * @param report a {@link org.agatom.springatom.server.model.types.report.Report} object.
	 * @param entity a {@link org.agatom.springatom.web.rbuilder.bean.RBuilderEntity} object.
	 *
	 * @return a {@link net.sf.jasperreports.engine.JasperReport} object.
	 *
	 * @throws org.agatom.springatom.web.rbuilder.data.exception.ReportGenerationException if any.
	 */
	JasperReport generateReport(@NotNull final Report report, @NotNull final RBuilderEntity entity) throws ReportGenerationException;

	/**
	 * <p>getGroupByColumns.</p>
	 *
	 * @param columns a {@link java.util.Set} object.
	 *
	 * @return a {@link java.util.Set} object.
	 */
	Set<RBuilderColumn> getGroupByColumns(@NotNull final Set<RBuilderColumn> columns);
}
