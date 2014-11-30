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

/**
 * <p>ReportResource interface.</p>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public interface ReportResource {
    /**
     * <p>getJasperPath.</p>
     *
     * @return a {@link String} object.
     */
    String getJasperPath();

    /**
     * <p>getJasperFilename.</p>
     *
     * @return a {@link String} object.
     */
    String getJasperFilename();

    /**
     * <p>getJasperExtension.</p>
     *
     * @return a {@link String} object.
     */
    String getJasperExtension();

    /**
     * <p>getConfigurationPath.</p>
     *
     * @return a {@link String} object.
     */
    String getConfigurationPath();

    /**
     * <p>getConfigurationFilename.</p>
     *
     * @return a {@link String} object.
     */
    String getConfigurationFilename();

    /**
     * <p>getConfigurationExtension.</p>
     *
     * @return a {@link String} object.
     */
    String getConfigurationExtension();
}
