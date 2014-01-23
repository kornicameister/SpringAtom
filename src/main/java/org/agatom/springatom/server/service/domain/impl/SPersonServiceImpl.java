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

package org.agatom.springatom.server.service.domain.impl;

import com.google.common.collect.ImmutableList;
import org.agatom.springatom.server.model.beans.person.QSPerson;
import org.agatom.springatom.server.model.beans.person.SPerson;
import org.agatom.springatom.server.model.beans.person.SPersonContact;
import org.agatom.springatom.server.model.types.contact.SContact;
import org.agatom.springatom.server.service.domain.SPersonContactService;
import org.agatom.springatom.server.service.domain.SPersonService;
import org.agatom.springatom.server.service.support.exceptions.EntityDoesNotExistsServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Tomasz
 * @version 0.0.1
 * @since 0.0.1
 */

@Service(value = SPersonServiceImpl.SERVICE_NAME)
@Transactional(readOnly = true, isolation = Isolation.SERIALIZABLE, propagation = Propagation.SUPPORTS)
@SuppressWarnings("unchecked")
public class SPersonServiceImpl
        extends SServiceImpl<SPerson, Long, Integer>
        implements SPersonService {
    public static final String                SERVICE_NAME         = "SPersonService";
    @Autowired
    private             SPersonContactService personContactService = null;

    @Override
    @Transactional(readOnly = false,
            isolation = Isolation.SERIALIZABLE,
            propagation = Propagation.SUPPORTS,
            rollbackFor = EntityDoesNotExistsServiceException.class)
    public SContact<SPerson> newContactData(final String value, final long clientPk,
                                            final SContact clientContact) throws EntityDoesNotExistsServiceException {
        final SPerson client = this.revisionRepository.findOne(clientPk);

        if (client == null) {
            throw new EntityDoesNotExistsServiceException(SPerson.class, clientPk);
        }

        final SContact<SPerson> contact = new SPersonContact();

        contact.setContact(value);
        contact.setAssigned(client);
        contact.setType(clientContact.getType());

        return this.personContactService.save((SPersonContact) contact);
    }

    @Override
    @CacheEvict(value = "clients", key = "#idClient", beforeInvocation = true)
    public List<SPersonContact> findAllContacts(final Long idClient) {
        return ImmutableList.copyOf(this.personContactService.findByAssigned(idClient));
    }

    @Override
    @CacheEvict(value = "clients", key = "#firstName", beforeInvocation = true)
    public List<SPerson> findByFirstName(final String firstName) {
        return ImmutableList.copyOf(this.revisionRepository.findAll(QSPerson.sPerson.firstName.eq(firstName)));
    }

    @Override
    @CacheEvict(value = "clients", key = "#lastName", beforeInvocation = true)
    public List<SPerson> findByLastName(final String lastName) {
        return ImmutableList.copyOf(this.revisionRepository.findAll(QSPerson.sPerson.lastName.eq(lastName)));
    }

    @Override
    @CacheEvict(value = "clients", key = "#email", beforeInvocation = true)
    public SPerson findByEmail(final String email) {
        return this.revisionRepository.findOne(QSPerson.sPerson.primaryMail.eq(email));
    }

}
