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

package org.agatom.springatom.mvc.model.service.impl;

import org.agatom.springatom.jpa.repositories.SMetaDataRepository;
import org.agatom.springatom.model.beans.meta.QSMetaData;
import org.agatom.springatom.model.beans.meta.SMetaData;
import org.agatom.springatom.model.types.meta.SMetaDataEnum;
import org.agatom.springatom.model.types.meta.SMetaDataHolder;
import org.agatom.springatom.mvc.model.service.SMetaDataService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Service
@Transactional(readOnly = true, isolation = Isolation.SERIALIZABLE, propagation = Propagation.SUPPORTS)
public class SMetaDataServiceImpl
        implements SMetaDataService {

    private static final Logger LOGGER = Logger.getLogger(SMetaDataServiceImpl.class);
    @Autowired
    SMetaDataRepository repository;

    @Override
    @Cacheable(value = "typedata")
    public List<SMetaData> findAll(final Class<? extends SMetaData> clazz) {
        return (List<SMetaData>) this.repository.findAll(QSMetaData.sMetaData.instanceOf(clazz));
    }

    @Override
    @Cacheable(value = "typedata")
    public SMetaData findByType(final SMetaDataEnum type) {
        return this.repository.findOne(QSMetaData.sMetaData.type.eq(type));
    }

    @Override
    @Cacheable(value = "typedata", key = "metaDataCapable.getMetaInformation().getType()")
    public List<SMetaData> findEquivalences(final SMetaDataHolder metaDataCapable) {
        final SMetaData sMetaData = (SMetaData) metaDataCapable.getMetaInformation();
        return (List<SMetaData>) this.repository.findAll(QSMetaData.sMetaData.instanceOf(sMetaData.getClass()));
    }

    @Override
    @Cacheable(value = "typedata", key = "metaDataCapable.getMetaInformation().getType()")
    public SMetaData findByType(final SMetaDataHolder dataCapable) {
        final SMetaData sMetaData = (SMetaData) dataCapable.getMetaInformation();
        if (sMetaData != null) {
            return this.repository.findOne(QSMetaData.sMetaData.eq(sMetaData));
        }

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("%s is null for %s", SMetaData.class.getSimpleName(), dataCapable));
        }

        return null;
    }
}
