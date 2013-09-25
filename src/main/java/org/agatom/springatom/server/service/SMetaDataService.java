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

package org.agatom.springatom.server.service;

import org.agatom.springatom.server.model.beans.meta.SMetaData;
import org.agatom.springatom.server.model.types.meta.SMetaDataEnum;
import org.agatom.springatom.server.model.types.meta.SMetaDataHolder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public interface SMetaDataService {
    @NotNull List<SMetaData> findAll(
            @Valid
            @NotNull
            final Class<? extends SMetaData> clazz);

    @NotNull SMetaData findByType(
            @Valid
            @NotNull
            final SMetaDataEnum type);

    /**
     * Returns list of equivalent
     *
     * @param metaDataCapable
     *
     * @return
     */
    @NotNull List<SMetaData> findEquivalences(
            @Valid
            @NotNull
            final SMetaDataHolder metaDataCapable);

    @NotNull SMetaData findByType(
            @Valid
            @NotNull
            SMetaDataHolder dataCapable);
}