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

import org.agatom.springatom.jpa.repositories.SClientProblemReportRepository;
import org.agatom.springatom.model.beans.person.client.SClientProblemReport;
import org.agatom.springatom.mvc.model.service.SClientProblemReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */

@Service
@Transactional(readOnly = true, isolation = Isolation.SERIALIZABLE, propagation = Propagation.SUPPORTS)
public class SClientProblemReportServiceImpl
        extends SServiceImpl<SClientProblemReport, Long, Integer, SClientProblemReportRepository>
        implements SClientProblemReportService {

    SClientProblemReportRepository repository;

    @Override
    @Autowired(required = true)
    public void autoWireRepository(final SClientProblemReportRepository repo) {
        this.repository = repo;
        this.jpaRepository = (JpaRepository) repo;
    }
}
