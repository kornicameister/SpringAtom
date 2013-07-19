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
import org.agatom.springatom.jpa.SClientProblemReportRepository;
import org.agatom.springatom.model.beans.person.client.SClientProblemReport;
import org.agatom.springatom.mvc.model.service.SClientProblemReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */

@Service
@Transactional(readOnly = true, isolation = Isolation.SERIALIZABLE, propagation = Propagation.SUPPORTS)
public class SClientProblemReportServiceImpl implements SClientProblemReportService {

    @Autowired
    SClientProblemReportRepository repository;

    @Override
    public SClientProblemReport findOne(@NotNull final Long id) {
        return this.repository.findOne(id);
    }

    @Override
    public List<SClientProblemReport> findAll() {
        return this.repository.findAll();
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = IllegalArgumentException.class)
    public SClientProblemReport save(@NotNull final SClientProblemReport persistable) {
        Preconditions.checkArgument(persistable != null, "SClientProblemReport must not be null");
        return this.repository.saveAndFlush(persistable);
    }

    @Override
    public Long count() {
        return this.repository.count();
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteOne(@NotNull final Long pk) {
        this.repository.delete(pk);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteAll() {
        this.repository.deleteAll();
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(final Long id) {
        this.repository.delete(id);
    }
}
