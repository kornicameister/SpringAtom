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

package org.agatom.springatom.mvc.model.service;

import org.agatom.springatom.jpa.SCarRepository;
import org.agatom.springatom.model.beans.car.SCar;
import org.agatom.springatom.model.beans.car.SCarMaster;
import org.agatom.springatom.model.beans.links.SCarClientLink;

import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */

public interface SCarService extends Service<SCar, Long, SCarRepository> {
    SCarMaster findByMaster(@NotNull final String brand,
                            @NotNull final String model);

    SCarMaster findByMaster(@NotNull final Long carId);

    SCar saveWithMaster(@NotNull final String brand,
                        @NotNull final String model,
                        @NotNull final String licencePlate,
                        @NotNull final String vinNumber);

    SCar changeOwner(@NotNull final Long idCar,
                     @NotNull final Long idClient);

    Set<SCarClientLink> findOwnershipHistory(@NotNull final Long idCar);
}
