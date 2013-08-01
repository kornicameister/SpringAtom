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

package org.agatom.springatom.model.beans.notification;

import org.agatom.springatom.model.types.notification.STargetedNotification;
import org.springframework.data.domain.Persistable;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

/**
 * @author kornicamaister
 * @version 0.0.1
 * @since 0.0.1
 */

@MappedSuperclass
abstract class SBasicTargetedNotification<SN_T extends Persistable<Long>>
        extends SBasicNotification
        implements STargetedNotification<SN_T, Long> {

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "target", updatable = false)
    private SN_T client;

    @Override
    public SN_T getTarget() {
        return this.client;
    }

    @Override
    public STargetedNotification<SN_T, Long> setTarget(final SN_T target) {
        this.client = target;
        return this;
    }
}
