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

package org.agatom.springatom.server.model.types.report;

import org.agatom.springatom.server.model.beans.report.setting.SReportSetting;
import org.agatom.springatom.server.model.types.report.entity.ReportEntity;
import org.agatom.springatom.server.model.types.report.resource.ReportResource;

import javax.annotation.Nonnull;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public interface Report
        extends Serializable {
    ReportResource getResource();

    String getDescription();

    String getTitle();

    String getSubtitle();

    List<ReportEntity> getEntities();

    Set<SReportSetting<?>> getSettings();

    Map<String, Serializable> getSettingsAsMap();

    Serializable getSetting(String key);

    boolean hasEntity(Class<?> javaClass);

    boolean hasEntities();

    boolean hasSetting(@Nonnull String key);

    boolean hasSettings();
}
