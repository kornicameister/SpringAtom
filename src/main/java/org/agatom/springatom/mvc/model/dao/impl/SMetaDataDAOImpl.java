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

package org.agatom.springatom.mvc.model.dao.impl;

import org.agatom.springatom.annotations.GenCache;
import org.agatom.springatom.model.beans.Persistable;
import org.agatom.springatom.model.beans.meta.SMetaData;
import org.agatom.springatom.mvc.model.dao.SMetaDataDAO;
import org.agatom.springatom.mvc.model.dao.abstracts.DefaultDAO;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@GenCache(daoName = "SMetaDataDAO")
@Repository(value = "SMetaDataDAO")
@SuppressWarnings("SpringElInspection")
public class SMetaDataDAOImpl extends DefaultDAO<SMetaData, Long>
        implements SMetaDataDAO {
    private static final String TYPE = "type";
    private static final String CACHE_NAME = "typedata";

    @Override
    @Cacheable(value = CACHE_NAME)
    public Iterable findAll(@NotNull final Class<? extends SMetaData> clazz) {
        this.target = clazz;
        return this.findAll();
    }

    @Override
    @Cacheable(value = CACHE_NAME, key = "#id")
    public SMetaData findOne(@NotNull final Long id, @NotNull final Class<? extends SMetaData> clazz) {
        this.target = clazz;
        return this.findOne(id);
    }

    @Override
    public SMetaData findByType(@NotNull final String type, @NotNull final Class<? extends SMetaData> clazz) {
        this.target = clazz;
        return this.findByNaturalId(TYPE, type);
    }

    @Override
    protected Class<? extends Persistable> getTargetClazz() {
        return SMetaData.class;
    }
}
