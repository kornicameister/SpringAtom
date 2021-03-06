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

package org.agatom.springatom.data.types.report;

import org.springframework.binding.collection.MapAdaptable;

import javax.annotation.Nonnull;
import java.io.Serializable;
import java.util.Set;

/**
 * <p>Report interface.</p>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public interface Report
        extends MapAdaptable<String, Serializable> {
    /**
     * <p>getResource.</p>
     *
     * @return a {@link org.agatom.springatom.data.types.report.ReportResource} object.
     */
    ReportResource getResource();

    /**
     * <p>getDescription.</p>
     *
     * @return a {@link String} object.
     */
    String getDescription();

    /**
     * <p>getTitle.</p>
     *
     * @return a {@link String} object.
     */
    String getTitle();

    /**
     * <p>getSubtitle.</p>
     *
     * @return a {@link String} object.
     */
    String getSubtitle();

    /**
     * <p>getSettings.</p>
     *
     * @return a {@link java.util.Set} object.
     */
    Set<ReportSetting<?>> getSettings();

    /**
     * <p>getSetting.</p>
     *
     * @param key a {@link String} object.
     *
     * @return a {@link java.io.Serializable} object.
     */
    Serializable getSetting(String key);

    /**
     * <p>hasSetting.</p>
     *
     * @param key a {@link String} object.
     *
     * @return a boolean.
     */
    boolean hasSetting(@Nonnull String key);

    /**
     * <p>hasSettings.</p>
     *
     * @return a boolean.
     */
    boolean hasSettings();

    /**
     * <p>getReportMaster.</p>
     *
     * @return a {@link org.agatom.springatom.data.types.report.Report} object.
     */
    Report getReportMaster();

    /**
     * <p>getSubReports.</p>
     *
     * @return a {@link java.util.Set} object.
     */
    Set<Report> getSubReports();

    /**
     * <p>isDynamic.</p>
     *
     * @return a boolean.
     */
    boolean isDynamic();

    /**
     * <p>getReportedClass.</p>
     *
     * @return a {@link Class} object.
     */
    Class<?> getReportedClass();
}
