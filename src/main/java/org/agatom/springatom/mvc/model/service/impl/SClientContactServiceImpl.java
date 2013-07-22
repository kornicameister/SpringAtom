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

import org.agatom.springatom.jpa.SClientContactRepository;
import org.agatom.springatom.model.beans.person.client.QSClientContact;
import org.agatom.springatom.model.beans.person.client.SClientContact;
import org.agatom.springatom.mvc.model.service.SClientContactService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class SClientContactServiceImpl
        extends DefaultService<SClientContact, Long, SClientContactRepository>
        implements SClientContactService {

    SClientContactRepository clientContactRepository;

    @Override
    public List<SClientContact> findByClient(final Long idClient) {
        return (List<SClientContact>) this.clientContactRepository
                .findAll(QSClientContact.sClientContact.client.id.eq(idClient));
    }

    @Override
    @Autowired
    public void autoWireRepository(final SClientContactRepository repo) {
        this.clientContactRepository = repo;
        this.jpaRepository = repo;
    }
}
