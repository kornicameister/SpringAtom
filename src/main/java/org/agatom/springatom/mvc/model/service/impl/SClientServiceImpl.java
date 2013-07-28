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

import org.agatom.springatom.jpa.repositories.SClientRepository;
import org.agatom.springatom.model.beans.meta.SClientProblemReportType;
import org.agatom.springatom.model.beans.meta.SContactType;
import org.agatom.springatom.model.beans.meta.SMetaData;
import org.agatom.springatom.model.beans.meta.SMetaDataType;
import org.agatom.springatom.model.beans.person.client.QSClient;
import org.agatom.springatom.model.beans.person.client.SClient;
import org.agatom.springatom.model.beans.person.client.SClientContact;
import org.agatom.springatom.model.beans.person.client.SClientProblemReport;
import org.agatom.springatom.model.beans.person.embeddable.SPersonalInformation;
import org.agatom.springatom.model.beans.person.mechanic.SMechanic;
import org.agatom.springatom.model.beans.util.SIssueReporter;
import org.agatom.springatom.mvc.model.exceptions.EntityDoesNotExists;
import org.agatom.springatom.mvc.model.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Tomasz
 * @version 0.0.1
 * @since 0.0.1
 */
@Service
@Transactional(readOnly = true, isolation = Isolation.SERIALIZABLE, propagation = Propagation.SUPPORTS)
public class SClientServiceImpl
        extends SServiceImpl<SClient, Long, Integer, SClientRepository>
        implements SClientService {
    @Autowired
    SMechanicService            mechanicService;
    @Autowired
    SClientProblemReportService clientProblemReportService;
    @Autowired
    SMetaDataService            metaDataService;
    @Autowired
    SClientContactService       clientContactService;
    private SClientRepository repository;

    @Override
    @Autowired(required = true)
    public void autoWireRepository(final SClientRepository repo) {
        super.autoWireRepository(repo);
        this.repository = repo;
    }

    @Override
    @Transactional(rollbackFor = EntityDoesNotExists.class)
    public SClientProblemReport newProblemReport(@NotNull final String problem,
                                                 @NotNull final Long clientPk,
                                                 @NotNull final Long mechanicPk,
                                                 @NotNull final SMetaDataType metaType) throws
            EntityDoesNotExists {

        // load objects
        final SClient client = this.repository.findOne(clientPk);
        final SMechanic mechanic = this.mechanicService.findOne(mechanicPk);
        final SClientProblemReportType problemReportType = (SClientProblemReportType) this.metaDataService
                .findByType(metaType);

        if (client == null) {
            throw new EntityDoesNotExists(SClient.class, clientPk);
        }
        if (mechanic == null) {
            throw new EntityDoesNotExists(SMechanic.class, mechanicPk);
        }
        if (problemReportType == null) {
            throw new EntityDoesNotExists(SClientProblemReportType.class, metaType);
        }

        SClientProblemReport problemReport = new SClientProblemReport();

        problemReport.setClient(client);
        problemReport.setReporter(new SIssueReporter(mechanic));
        problemReport.setProblem(problem);
        problemReport.setMetaInformation(problemReportType);

        return this.clientProblemReportService.save(problemReport);
    }

    @Override
    @Transactional(propagation = Propagation.NEVER)
    public SClientContact newPhone(@NotNull final String contact,
                                   @NotNull final Long client) throws EntityDoesNotExists {
        return this.newContactData(contact, client, SMetaDataType.SCT_PHONE);
    }

    @Override
    @Transactional(readOnly = false,
            isolation = Isolation.SERIALIZABLE,
            propagation = Propagation.SUPPORTS,
            rollbackFor = EntityDoesNotExists.class)
    public SClientContact newContactData(@NotNull final String contact,
                                         @NotNull final Long clientPk,
                                         @NotNull final SMetaDataType type) throws EntityDoesNotExists {
        final SClient client = this.repository.findOne(clientPk);
        final SMetaData metaData = this.metaDataService.findByType(type);
        if (client == null) {
            throw new EntityDoesNotExists(SClient.class, clientPk);
        }
        if (metaData == null) {
            throw new EntityDoesNotExists(SContactType.class, type);
        }
        final SClientContact clientContact = new SClientContact();
        clientContact.setClient(client);
        clientContact.setContact(contact);
        clientContact.setMetaInformation((SContactType) metaData);
        return this.clientContactService.save(clientContact);
    }

    @Override
    @Transactional(propagation = Propagation.NEVER)
    public SClientContact newEmail(@NotNull final String contact,
                                   @NotNull final Long client) throws EntityDoesNotExists {
        return this.newContactData(contact, client, SMetaDataType.SCT_MAIL);
    }

    @Override
    @Transactional(propagation = Propagation.NEVER)
    public SClientContact newCellPhone(@NotNull final String contact,
                                       @NotNull final Long client) throws EntityDoesNotExists {
        return this.newContactData(contact, client, SMetaDataType.SCT_CELL_PHONE);
    }

    @Override
    @Transactional(propagation = Propagation.NEVER)
    public SClientContact newFax(@NotNull final String contact,
                                 @NotNull final Long client) throws EntityDoesNotExists {
        return this.newContactData(contact, client, SMetaDataType.SCT_FAX);
    }

    @Override
    @CacheEvict(value = "clients", key = "#idClient", beforeInvocation = true)
    public List<SClientContact> findAllContacts(final Long idClient) {
        return this.clientContactService.findByClient(idClient);
    }

    @Override
    public List<SClient> findByPersonalInformation(@NotNull final SPersonalInformation information) {
        return (List<SClient>) this.repository.findAll(QSClient.sClient.information.eq(information));
    }

    @Override
    @CacheEvict(value = "clients", key = "#firstName", beforeInvocation = true)
    public List<SClient> findByFirstName(@NotNull final String firstName) {
        return (List<SClient>) this.repository.findAll(QSClient.sClient.information.firstName.eq(firstName));
    }

    @Override
    @CacheEvict(value = "clients", key = "#lastName", beforeInvocation = true)
    public List<SClient> findByLastName(@NotNull final String lastName) {
        return (List<SClient>) this.repository.findAll(QSClient.sClient.information.lastName.eq(lastName));
    }

    @Override
    @CacheEvict(value = "clients", key = "#email", beforeInvocation = true)
    public SClient findByEmail(@NotNull final String email) {
        return this.repository.findOne(QSClient.sClient.email.eq(email));
    }

}
