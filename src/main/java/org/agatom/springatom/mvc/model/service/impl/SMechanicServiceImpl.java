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
import org.agatom.springatom.jpa.SMechanicRepository;
import org.agatom.springatom.model.beans.person.mechanic.SMechanic;
import org.agatom.springatom.mvc.model.service.SMechanicService;
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
public class SMechanicServiceImpl
        implements SMechanicService {

    @Autowired
    SMechanicRepository mechanicService;

    @Override
    public SMechanic findOne(@NotNull final Long id) {
        return this.mechanicService.findOne(id);
    }

    @Override
    public List<SMechanic> findAll() {
        return this.mechanicService.findAll();
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = IllegalArgumentException.class)
    public SMechanic save(@NotNull final SMechanic persistable) {
        Preconditions.checkArgument(persistable != null, "SMechanic must not be null");
        return this.mechanicService.save(persistable);
    }

    @Override
    public Long count() {
        return this.mechanicService.count();
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = IllegalArgumentException.class)
    public void deleteOne(@NotNull final Long pk) {
        Preconditions.checkArgument(pk != null, "SMechanic#pk must not be null");
        this.mechanicService.delete(pk);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteAll() {
        this.mechanicService.deleteAll();
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(final Long id) {
        this.mechanicService.delete(id);
    }
}
