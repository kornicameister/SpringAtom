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
import org.agatom.springatom.model.beans.client.SClientContact;
import org.agatom.springatom.mvc.model.dao.SClientContactDAO;
import org.agatom.springatom.mvc.model.dao.abstracts.DefaultDAO;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Repository(value = "SClientContactDAO")
public class SClientContactDAOImpl extends DefaultDAO<SClientContact, Long>
        implements SClientContactDAO {
    private static final String FROM_CLIENT_CONTACT_BY_CLIENT_ID = "fromClientContactByClientId";

    private static final String CLIENT_ID                        = "clientId";

    @Override
    public Set<SClientContact> findByClient(final Long id) {
        if (!this.isQueryPossible(String.format(FIND_METHOD, String.format("byClient=%d", id)))) {
            return null;
        }
        List<?> data = this.getSession()
                .getNamedQuery(FROM_CLIENT_CONTACT_BY_CLIENT_ID)
                .setLong(CLIENT_ID, id)
                .list();
        Set<SClientContact> clientContacts = new HashSet<>();
        for (Object object : data) {
            if (object instanceof SClientContact) {
                clientContacts.add((SClientContact) object);
            }
        }
        return clientContacts;
    }

    @Override
    protected Class<? extends Persistable> getTargetClazz() {
        return SClientContact.class;
    }
}
