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

import com.mysema.query.types.Path;
import com.mysema.query.types.expr.BooleanExpression;
import com.mysema.query.types.path.NumberPath;
import com.mysema.query.types.path.StringPath;
import org.agatom.springatom.jpa.repositories.SCarRepository;
import org.agatom.springatom.model.beans.car.QSCar;
import org.agatom.springatom.model.beans.car.SCar;
import org.agatom.springatom.model.beans.car.SCarMaster;
import org.agatom.springatom.mvc.model.exceptions.SEntityDoesNotExists;
import org.agatom.springatom.mvc.model.exceptions.SUnambiguousResultException;
import org.agatom.springatom.mvc.model.service.base.SService;
import org.agatom.springatom.mvc.model.service.constraints.BrandOrModel;
import org.agatom.springatom.mvc.model.service.constraints.LicencePlatePL;
import org.agatom.springatom.mvc.model.service.constraints.VinNumber;
import org.agatom.springatom.mvc.model.service.impl.SCarServiceImpl;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */

public interface SCarService
        extends SService<SCar, Long, Integer, SCarRepository> {
    @NotNull
    List<SCar> findByMaster(
            @BrandOrModel
            final String brand,
            @BrandOrModel
            final String model);

    @NotNull
    List<SCar> findByMaster(
            @NotNull
            @NotEmpty
            final Long... masterId);

    @NotNull
    SCarMaster findMaster(final long carId);

    @NotNull
    List<SCar> findBy(
            @NotNull
            final SCarAttribute attribute,
            @NotNull
            final Object value) throws
            SUnambiguousResultException;

    @NotNull
    SCar newCar(
            @BrandOrModel
            final String brand,
            @BrandOrModel
            final String model,
            @LicencePlatePL
            final String licencePlate,
            @VinNumber
            final String vinNumber,
            final long ownerId) throws SEntityDoesNotExists;

    @NotNull
    SCar newOwner(
            final long idCar,
            final long idClient) throws SEntityDoesNotExists, SCarServiceImpl.InvalidOwnerException;

    public static enum SCarAttribute {
        LICENCE_PLATE(QSCar.sCar.licencePlate),
        OWNER(QSCar.sCar.owner.id),
        VIN_NUMBER(QSCar.sCar.vinNumber);
        private final Path expression;

        SCarAttribute(final Path expression) {
            this.expression = expression;
        }

        @SuppressWarnings("unchecked")
        public BooleanExpression eq(final Object value) {
            if (value instanceof String) {
                final StringPath path = (StringPath) this.expression;
                return path.eq((String) value);
            } else if (value instanceof Long) {
                final NumberPath<Long> path = (NumberPath<Long>) this.expression;
                return path.eq((Long) value);
            }
            return null;
        }
    }
}
