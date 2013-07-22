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

package org.agatom.springatom.model.beans.links;

import org.agatom.springatom.model.beans.PersistentObject;
import org.agatom.springatom.model.beans.car.SCar;
import org.agatom.springatom.model.beans.person.client.SClient;

import javax.persistence.*;

/**
 * @author kornicamaister
 * @version 0.0.1
 * @since 0.0.1
 */
@Entity(name = "SCarClientLink")
@Table(name = "SCarClientLink")
@AttributeOverride(
        name = "id",
        column = @Column(
                name = "idSCarClientLink",
                updatable = false,
                nullable = false)
)
public class SCarClientLink extends PersistentObject<Long> {

    @ManyToOne(optional = false)
    @JoinColumn(name = "client", referencedColumnName = "idSClient", updatable = false)
    private SClient client;
    @ManyToOne(optional = false)
    @JoinColumn(name = "car", referencedColumnName = "idScar")
    private SCar    car;

    public SClient getClient() {
        return client;
    }

    public void setClient(final SClient client) {
        this.client = client;
    }

    public SCar getCar() {
        return car;
    }

    public void setCar(final SCar car) {
        this.car = car;
    }
}
