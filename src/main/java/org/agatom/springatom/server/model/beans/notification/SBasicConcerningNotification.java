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

package org.agatom.springatom.server.model.beans.notification;

import org.agatom.springatom.server.model.types.notification.SConcerningNotification;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author kornicamaister
 * @version 0.0.1
 * @since 0.0.1
 */

@MappedSuperclass
abstract class SBasicConcerningNotification<SN_C extends Persistable<Long>, SN_T extends Persistable<Long>>
        extends SBasicTargetedNotification<SN_T>
        implements SConcerningNotification<SN_C, SN_T, Long> {
    private static final long serialVersionUID = 8637694587051421693L;
    @NotEmpty
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "SNotificiationConcerns", joinColumns = @JoinColumn(name = "fkNotification"))
    private Set<SN_C> concerns;

    @Override
    public Set<SN_C> getConcerning() {
        return this.concerns;
    }

    @Override
    public SConcerningNotification<SN_C, SN_T, Long> setConcerning(final Collection<SN_C> concerning) {
        if (this.concerns == null) {
            this.concerns = new HashSet<>();
        }
        this.concerns.addAll(concerning);
        return this;
    }
}
