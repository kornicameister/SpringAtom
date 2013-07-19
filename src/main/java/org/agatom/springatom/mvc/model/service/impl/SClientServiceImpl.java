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

import com.google.common.base.Preconditions;
import org.agatom.springatom.jpa.*;
import org.agatom.springatom.model.beans.meta.QSMetaData;
import org.agatom.springatom.model.beans.meta.SClientProblemReportType;
import org.agatom.springatom.model.beans.meta.SContactType;
import org.agatom.springatom.model.beans.meta.SMetaData;
import org.agatom.springatom.model.beans.person.client.*;
import org.agatom.springatom.model.beans.person.mechanic.SMechanic;
import org.agatom.springatom.model.beans.person.user.embeddable.SPersonalInformation;
import org.agatom.springatom.model.beans.util.SIssueReporter;
import org.agatom.springatom.mvc.model.exceptions.EntityDoesNotExists;
import org.agatom.springatom.mvc.model.service.SClientService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Tomasz
 * @version 0.0.1
 * @since 0.0.1
 */
@Service
@Transactional(readOnly = true, isolation = Isolation.SERIALIZABLE, propagation = Propagation.SUPPORTS)
public class SClientServiceImpl implements SClientService {
    @Resource
    SClientRepository              sClientRepository;
    @Resource
    SMechanicRepository            sMechanicRepository;
    @Resource
    SClientProblemReportRepository sClientProblemReportRepository;
    @Resource
    SMetaDataRepository            sMetaDataRepository;
    @Resource
    SClientContactRepository       sClientContactRepository;

    @Override
    @Transactional(rollbackFor = EntityDoesNotExists.class)
    public SClient disable(@NotNull final Long pk) throws EntityDoesNotExists {
        final SClient client = this.sClientRepository.findOne(pk);
        if (client == null) {
            throw new EntityDoesNotExists(SClient.class, pk);
        }
        client.setDisabled(true);
        return this.sClientRepository.saveAndFlush(client);
    }

    @Override
    @Transactional(rollbackFor = EntityDoesNotExists.class)
    public SClient enable(@NotNull final Long pk) throws EntityDoesNotExists {
        final SClient client = this.sClientRepository.findOne(pk);
        if (client == null) {
            throw new EntityDoesNotExists(SClient.class, pk);
        }
        client.setDisabled(false);
        return this.sClientRepository.saveAndFlush(client);
    }

    @Override
    @Transactional(rollbackFor = EntityDoesNotExists.class)
    public SClientProblemReport newProblemReport(@NotNull final String problem,
                                                 @NotNull final Long clientPk,
                                                 @NotNull final Long mechanicPk,
                                                 @NotNull final SMetaDataRepository.MetaType metaType) throws
            EntityDoesNotExists {

        // load objects
        final SClient client = this.sClientRepository.findOne(clientPk);
        final SMechanic mechanic = this.sMechanicRepository.findOne(mechanicPk);
        final SClientProblemReportType problemReportType = (SClientProblemReportType) this.sMetaDataRepository
                .findOne(QSMetaData.sMetaData.type.eq(metaType.getType()));

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

        return this.sClientProblemReportRepository.save(problemReport);
    }

    @Override
    @Transactional(rollbackFor = EntityDoesNotExists.class)
    public SClientContact newPhone(@NotNull final String contact,
                                   @NotNull final Long client) throws EntityDoesNotExists {
        return this.newContactData(contact, client, SMetaDataRepository.MetaType.SCT_PHONE);
    }

    @Override
    @Transactional(rollbackFor = EntityDoesNotExists.class)
    public SClientContact newContactData(@NotNull final String contact,
                                         @NotNull final Long clientPk,
                                         @NotNull final SMetaDataRepository.MetaType type) throws EntityDoesNotExists {
        final SClient client = this.sClientRepository.findOne(clientPk);
        final SMetaData metaData = this.sMetaDataRepository.findOne(QSMetaData.sMetaData.type.eq(type.getType()));
        if (client == null) {
            throw new EntityDoesNotExists(SClient.class, clientPk);
        }
        if (metaData == null) {
            throw new EntityDoesNotExists(SContactType.class, type);
        }
        final SClientContact clientContact = new SClientContact();
        clientContact.setClient(client);
        clientContact.setContact(contact);
        clientContact.setMetaInformation(metaData);
        return this.sClientContactRepository.save(clientContact);
    }

    @Override
    @Transactional(rollbackFor = EntityDoesNotExists.class)
    public SClientContact newEmail(@NotNull final String contact,
                                   @NotNull final Long client) throws EntityDoesNotExists {
        return this.newContactData(contact, client, SMetaDataRepository.MetaType.SCT_MAIL);
    }

    @Override
    @Transactional(rollbackFor = EntityDoesNotExists.class)
    public SClientContact newCellPhone(@NotNull final String contact,
                                       @NotNull final Long client) throws EntityDoesNotExists {
        return this.newContactData(contact, client, SMetaDataRepository.MetaType.SCT_CELL_PHONE);
    }

    @Override
    @Transactional(rollbackFor = EntityDoesNotExists.class)
    public SClientContact newFax(@NotNull final String contact,
                                 @NotNull final Long client) throws EntityDoesNotExists {
        return this.newContactData(contact, client, SMetaDataRepository.MetaType.SCT_FAX);
    }

    @Override
    public List<SClientContact> findAllContacts(final Long idClient) {
        return (List<SClientContact>) this.sClientContactRepository
                .findAll(QSClientContact.sClientContact.client.id.eq(idClient));
    }

    @Override
    public List<SClient> findByPersonalInformation(@NotNull final SPersonalInformation information) {
        return (List<SClient>) this.sClientRepository.findAll(QSClient.sClient.information.eq(information));
    }

    @Override
    public List<SClient> findByFirstName(@NotNull final String firstName) {
        return (List<SClient>) this.sClientRepository.findAll(QSClient.sClient.information.firstName.eq(firstName));
    }

    @Override
    public List<SClient> findByLastName(@NotNull final String lastName) {
        return (List<SClient>) this.sClientRepository.findAll(QSClient.sClient.information.lastName.eq(lastName));
    }

    @Override
    public SClient findByEmail(@NotNull final String email) {
        return this.sClientRepository.findOne(QSClient.sClient.email.eq(email));
    }

    @Override
    public List<SClient> findByStatus(@NotNull final Boolean enabled) {
        return (List<SClient>) this.sClientRepository.findAll(QSClient.sClient.disabled.eq(enabled));
    }

    @Override
    public SClient findOne(@NotNull final Long id) {
        return this.sClientRepository.findOne(id);
    }

    @Override
    public List<SClient> findAll() {
        return this.sClientRepository.findAll();
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = IllegalArgumentException.class)
    public SClient save(@NotNull final SClient persistable) {
        Preconditions.checkArgument(persistable != null, "SClient must not be null");
        return this.sClientRepository.save(persistable);
    }

    @Override
    public Long count() {
        return this.sClientRepository.count();
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = IllegalArgumentException.class)
    public void deleteOne(@NotNull final Long pk) {
        Preconditions.checkArgument(pk != null, "SClient#pk must not be null");
        this.sClientRepository.delete(pk);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteAll() {
        this.sClientRepository.deleteAll();
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(final Long id) {
        this.sClientRepository.delete(id);
    }

}
