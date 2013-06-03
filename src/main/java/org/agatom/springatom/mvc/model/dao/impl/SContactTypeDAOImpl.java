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

import org.agatom.springatom.model.beans.Persistable;
import org.agatom.springatom.model.beans.meta.SContactType;
import org.agatom.springatom.mvc.model.dao.SContactTypeDAO;
import org.agatom.springatom.mvc.model.dao.SMetaDataDAO;
import org.agatom.springatom.mvc.model.dao.abstracts.DefaultDAO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Repository(value = "SContactTypeDAO")
public class SContactTypeDAOImpl extends DefaultDAO<SContactType, Long>
        implements SContactTypeDAO {
    private static final Logger LOGGER = Logger.getLogger(SContactTypeDAOImpl.class);

    private static final String TYPE_FIELD = "type";

    @Autowired
    SMetaDataDAO sMetaDataDAO;

    @Override
    protected Class<? extends Persistable> getTargetClazz() {
        return SContactType.class;
    }

    @Override
    public SContactType findByType(final ContactType type) {
        Object object = this.sMetaDataDAO.findByType(type.toString(), SContactType.class);
        if (object == null) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(String
                        .format("%s returned null by natural id, requesting by query", SMetaDataDAO.class.getName()));
            }
            object = this.getSession()
                    .createQuery("from SContactType sct where sct.type=:type")
                    .setSerializable(TYPE_FIELD, type.toString())
                    .list()
                    .get(0);

        }
        return (SContactType) object;
    }
}
