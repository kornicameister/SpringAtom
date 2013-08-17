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

package org.agatom.springatom.server.service.impl;

import org.agatom.springatom.server.model.beans.meta.SMetaData;
import org.agatom.springatom.server.model.beans.person.QSPerson;
import org.agatom.springatom.server.model.beans.person.SPerson;
import org.agatom.springatom.server.model.beans.person.contact.*;
import org.agatom.springatom.server.model.beans.person.embeddable.SPersonalInformation;
import org.agatom.springatom.server.model.types.contact.SContact;
import org.agatom.springatom.server.model.types.meta.SMetaDataEnum;
import org.agatom.springatom.server.repository.SRepository;
import org.agatom.springatom.server.service.SClientProblemReportService;
import org.agatom.springatom.server.service.SMetaDataService;
import org.agatom.springatom.server.service.SPersonContactService;
import org.agatom.springatom.server.service.SPersonService;
import org.agatom.springatom.server.service.exceptions.SEntityDoesNotExists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Tomasz
 * @version 0.0.1
 * @since 0.0.1
 */
@Transactional(readOnly = true, isolation = Isolation.SERIALIZABLE, propagation = Propagation.SUPPORTS)
@SuppressWarnings("unchecked")
abstract class SPersonServiceImpl<T extends SPerson, R extends JpaRepository>
        extends SServiceImpl<T, Long, Integer, R>
        implements SPersonService<T, R> {
    @Autowired
    SClientProblemReportService clientProblemReportService;
    @Autowired
    SMetaDataService            metaDataService;
    @Autowired
    @Qualifier("SPersonContactService")
    SPersonContactService       personContactService;
    private SRepository revRepo;

    @Override
    public void autoWireRepository(final R repo) {
        super.autoWireRepository(repo);
        this.revRepo = (SRepository) repo;
    }

    @Override
    @Transactional(propagation = Propagation.NEVER)
    public SPersonPhoneContact newPhone(final String contact, final long client) throws SEntityDoesNotExists {
        return (SPersonPhoneContact) this.newContactData(contact, client, SMetaDataEnum.SCT_PHONE);
    }

    @Override
    @Transactional(readOnly = false,
            isolation = Isolation.SERIALIZABLE,
            propagation = Propagation.SUPPORTS,
            rollbackFor = SEntityDoesNotExists.class)
    public SContact newContactData(final String value, final long clientPk,
                                   final SContact clientContact) throws SEntityDoesNotExists {
        final SPerson client = (SPerson) this.revRepo.findOne(clientPk);

        if (client == null) {
            throw new SEntityDoesNotExists(SPerson.class, clientPk);
        }

        final SContact contact = clientContact
                .setAssigned(client)
                .setContact(value);
        return this.personContactService.save((SPersonContact) contact);
    }

    @Override
    @Transactional(propagation = Propagation.NEVER)
    public SPersonEmailContact newEmail(final String contact, final long client) throws SEntityDoesNotExists {
        return (SPersonEmailContact) this.newContactData(contact, client, SMetaDataEnum.SCT_MAIL);
    }

    @Override
    @Transactional(propagation = Propagation.NEVER)
    public SPersonCellPhoneContact newCellPhone(final String contact, final long client) throws SEntityDoesNotExists {
        return (SPersonCellPhoneContact) this.newContactData(contact, client, SMetaDataEnum.SCT_CELL_PHONE);
    }

    @Override
    @Transactional(propagation = Propagation.NEVER)
    public SPersonFaxContact newFax(final String contact, final long client) throws SEntityDoesNotExists {
        return (SPersonFaxContact) this.newContactData(contact, client, SMetaDataEnum.SCT_FAX);
    }

    @Override
    @CacheEvict(value = "clients", key = "#idClient", beforeInvocation = true)
    public <X extends SContact> List<X> findAllContacts(final Long idClient) {
        return (List<X>) this.personContactService.findByAssigned(idClient);
    }

    @Override
    public List<T> findByPersonalInformation(final SPersonalInformation information) {
        return (List<T>) this.revRepo.findAll(QSPerson.sPerson.information.eq(information));
    }

    @Override
    @CacheEvict(value = "clients", key = "#firstName", beforeInvocation = true)
    public List<T> findByFirstName(final String firstName) {
        return (List<T>) this.revRepo.findAll(QSPerson.sPerson.information.firstName.eq(firstName));
    }

    @Override
    @CacheEvict(value = "clients", key = "#lastName", beforeInvocation = true)
    public List<T> findByLastName(final String lastName) {
        return (List<T>) this.revRepo.findAll(QSPerson.sPerson.information.lastName.eq(lastName));
    }

    @Override
    @CacheEvict(value = "clients", key = "#email", beforeInvocation = true)
    public T findByEmail(final String email) {
        return (T) this.revRepo.findOne(QSPerson.sPerson.primaryMail.eq(email));
    }

    private SContact newContactData(final String value, final Long client, final SMetaDataEnum type) throws
            SEntityDoesNotExists {
        final SMetaData metaData = this.metaDataService.findByType(type);
        if (metaData == null) {
            throw new SEntityDoesNotExists(SMetaData.class, type);
        }
        return this.newContactData(
                value,
                client,
                (SContact) SPersonContact
                        .fromType(type)
                        .setMetaInformation(metaData)
        );
    }

}
