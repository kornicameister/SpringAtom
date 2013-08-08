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
import org.agatom.springatom.model.beans.person.client.SClient;
import org.agatom.springatom.model.beans.person.client.SClientProblemReport;
import org.agatom.springatom.model.beans.person.mechanic.SMechanic;
import org.agatom.springatom.model.beans.util.SIssueReporter;
import org.agatom.springatom.model.types.meta.SMetaDataEnum;
import org.agatom.springatom.mvc.model.exceptions.SEntityDoesNotExists;
import org.agatom.springatom.mvc.model.service.SClientService;
import org.agatom.springatom.mvc.model.service.SMechanicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Tomasz
 * @version 0.0.1
 * @since 0.0.1
 */
@Service
@Transactional(readOnly = true, isolation = Isolation.SERIALIZABLE, propagation = Propagation.SUPPORTS)
public class SClientServiceImpl
        extends SPersonServiceImpl<SClient, SClientRepository>
        implements SClientService {
    @Autowired
    SMechanicService mechanicService;
    private SClientRepository repository;

    @Override
    @Autowired
    public void autoWireRepository(final SClientRepository repo) {
        super.autoWireRepository(repo);
        this.repository = repo;
    }

    @Override
    @Transactional(rollbackFor = SEntityDoesNotExists.class)
    public SClientProblemReport newProblemReport(
            final String problem,
            final long clientPk,
            final long mechanicPk,
            final SMetaDataEnum metaType) throws
            SEntityDoesNotExists {

        // load objects
        final SClient client = this.repository.findOne(clientPk);
        final SMechanic mechanic = this.mechanicService.findOne(mechanicPk);
        final SClientProblemReportType problemReportType = (SClientProblemReportType) this.metaDataService
                .findByType(metaType);

        if (client == null) {
            throw new SEntityDoesNotExists(SClient.class, clientPk);
        }
        if (mechanic == null) {
            throw new SEntityDoesNotExists(SMechanic.class, mechanicPk);
        }
        if (problemReportType == null) {
            throw new SEntityDoesNotExists(SClientProblemReportType.class, metaType);
        }

        SClientProblemReport problemReport = new SClientProblemReport();

        problemReport.setClient(client);
        problemReport.setReporter(new SIssueReporter(mechanic));
        problemReport.setProblem(problem);
        problemReport.setMetaInformation(problemReportType);

        return this.clientProblemReportService.save(problemReport);
    }

}
