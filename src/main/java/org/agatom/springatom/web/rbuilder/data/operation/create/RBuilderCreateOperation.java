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

package org.agatom.springatom.web.rbuilder.data.operation.create;

import org.agatom.springatom.server.model.types.report.Report;
import org.agatom.springatom.web.rbuilder.ReportConfiguration;
import org.agatom.springatom.web.rbuilder.data.exception.ReportCreateOperationException;
import org.agatom.springatom.web.rbuilder.data.operation.RBuilderOperation;

import javax.validation.constraints.NotNull;

/**
 * {@link RBuilderCreateOperation} is interface defining contract
 * of the particular operation designed to persist new instance of {@link org.agatom.springatom.server.model.types.report.Report}
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */

public interface RBuilderCreateOperation
        extends RBuilderOperation {
    @NotNull
    Report createReport(final ReportConfiguration reportConfiguration) throws ReportCreateOperationException;

    boolean canCreate(final ReportConfiguration reportConfiguration);
}