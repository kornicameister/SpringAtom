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

import org.agatom.springatom.model.beans.client.SClient;
import org.agatom.springatom.model.beans.client.SClientContact;
import org.agatom.springatom.model.beans.client.SClientProblemReport;
import org.agatom.springatom.model.beans.mechanic.SMechanic;
import org.agatom.springatom.model.beans.meta.SClientProblemReportType;
import org.agatom.springatom.model.beans.meta.SContactType;
import org.agatom.springatom.model.beans.util.SIssueReporter;
import org.agatom.springatom.mvc.model.bo.SClientBO;
import org.agatom.springatom.mvc.model.dao.*;
import org.agatom.springatom.mvc.model.exceptions.EntityDoesNotExists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * @author Tomasz
 * @version 0.0.1
 * @since 0.0.1
 */
@Service(value = "SClientBO")
public class SClientBOImpl implements SClientBO {

    @Autowired
    SClientDAO sClientDAO;

    @Autowired
    SMechanicDAO sMechanicDAO;

    @Autowired
    SClientProblemReportDAO sClientProblemReportDAO;

    @Autowired
    SMetaDataDAO sMetaDataDAO;

    @Autowired
    SClientContactDAO sClientContactDAO;

    @Override
    public SClient disable(@NotNull final Long pk) throws EntityDoesNotExists {
        final SClient client = this.sClientDAO.findOne(pk);
        if (client == null) {
            throw new EntityDoesNotExists(SClient.class, pk);
        }
        return this.sClientDAO.setDisabled(client, true);
    }

    @Override
    public SClient enable(@NotNull final Long pk) throws EntityDoesNotExists {
        final SClient client = this.sClientDAO.findOne(pk);
        if (client == null) {
            throw new EntityDoesNotExists(SClient.class, pk);
        }
        return this.sClientDAO.setDisabled(client, false);
    }

    @Override
    public SClientProblemReport newProblemReport(@NotNull final String problem,
                                                 @NotNull final Long clientPk,
                                                 @NotNull final Long mechanicPk,
                                                 @NotNull final SMetaDataDAO.MetaType metaType) throws
            EntityDoesNotExists {

        // load objects
        final SClient client = this.sClientDAO.findOne(clientPk);
        final SMechanic mechanic = this.sMechanicDAO.findOne(mechanicPk);
        final SClientProblemReportType problemReportType = (SClientProblemReportType) this.sMetaDataDAO
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
        problemReport.setType(problemReportType);

        return this.sClientProblemReportDAO.save(problemReport);
    }

    @Override
    public SClientContact newPhone(@NotNull final String contact,
                                   @NotNull final Long client) throws EntityDoesNotExists {
        final SContactType contactType = (SContactType) this.sMetaDataDAO
                .findByType(SMetaDataDAO.MetaType.SCT_PHONE);
        return this.newContactData(contact, client, contactType);
    }

    private SClientContact newContactData(@NotNull final String contact,
                                          @NotNull final Long clientPk,
                                          @NotNull final SContactType type) throws EntityDoesNotExists {
        final SClient client = this.sClientDAO.findOne(clientPk);
        if (client == null) {
            throw new EntityDoesNotExists(SClient.class, clientPk);
        }
        final SClientContact clientContact = new SClientContact();
        clientContact.setClient(client);
        clientContact.setContact(contact);
        clientContact.setType(type);
        return this.sClientContactDAO.save(clientContact);
    }

    @Override
    public SClientContact newEmail(@NotNull final String contact,
                                   @NotNull final Long client) throws EntityDoesNotExists {
        final SContactType contactType = (SContactType) this.sMetaDataDAO
                .findByType(SMetaDataDAO.MetaType.SCT_MAIL);
        return this.newContactData(contact, client, contactType);
    }

    @Override
    public SClientContact newCellPhone(@NotNull final String contact,
                                       @NotNull final Long client) throws EntityDoesNotExists {
        final SContactType contactType = (SContactType) this.sMetaDataDAO
                .findByType(SMetaDataDAO.MetaType.SCT_CELL_PHONE);
        return this.newContactData(contact, client, contactType);
    }

    @Override
    public SClientContact newFax(@NotNull final String contact,
                                 @NotNull final Long client) throws EntityDoesNotExists {
        final SContactType contactType = (SContactType) this.sMetaDataDAO
                .findByType(SMetaDataDAO.MetaType.SCT_FAX);
        return this.newContactData(contact, client, contactType);
    }

    @Override
    public Set<SClientContact> findAllContacts(final Long idClient) {
        return this.sClientContactDAO.findByClient(idClient);
    }

    @Override
    public SClient findOne(@NotNull final Long id) {
        return this.sClientDAO.findOne(id);
    }

    @Override
    public Iterable<SClient> findAll() {
        return this.sClientDAO.findAll();
    }

    @Override
    public SClient save(@NotNull final SClient persistable) {
        return this.sClientDAO.save(persistable);
    }

    @Override
    public Long count() {
        return this.sClientDAO.count();
    }

    @Override
    public void deleteOne(@NotNull final Long pk) {
        this.sClientDAO.delete(pk);
    }

    @Override
    public void deleteAll() {
        this.sClientDAO.deleteAll();
    }

}
