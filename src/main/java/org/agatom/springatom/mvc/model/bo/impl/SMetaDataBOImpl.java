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

package org.agatom.springatom.mvc.model.bo.impl;

import org.agatom.springatom.model.beans.meta.SMetaData;
import org.agatom.springatom.mvc.model.bo.SMetaDataBO;
import org.agatom.springatom.mvc.model.dao.SMetaDataDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Service(value = "STypeDataBO")
public class SMetaDataBOImpl implements SMetaDataBO {

    @Autowired
    SMetaDataDAO typeDataDao;

    @Override
    public Iterable findAll(@NotNull final Class<? extends SMetaData> clazz) {
        return this.typeDataDao.findAll(clazz);
    }

    @Override
    public SMetaData getById(@NotNull final Long id, @NotNull final Class<? extends SMetaData> clazz) {
        return this.typeDataDao.findOne(id, clazz);
    }

    @Override
    public SMetaData getByType(@NotNull final String type, @NotNull final Class<? extends SMetaData> clazz) {
        return this.typeDataDao.findByType(type, clazz);
    }
}
