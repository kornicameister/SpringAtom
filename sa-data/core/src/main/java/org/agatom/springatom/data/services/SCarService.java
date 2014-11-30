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

package org.agatom.springatom.data.services;

import org.agatom.springatom.core.RegexpPatterns;
import org.agatom.springatom.data.constraints.BrandOrModel;
import org.agatom.springatom.data.constraints.LicencePlatePL;
import org.agatom.springatom.data.types.car.Car;
import org.agatom.springatom.data.types.car.CarMaster;
import org.agatom.springatom.data.types.car.CarOwnership;
import org.agatom.springatom.data.types.user.User;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.domain.Persistable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Collection;
import java.util.List;

/**
 * <p>SCarService interface.</p>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public interface SCarService<T extends Car & Persistable<Long>, U extends User>
        extends SDomainService<T> {

    /** {@inheritDoc} */
    @Override
    T save(final T persistable);

    /**
     * <p>findByMaster.</p>
     *
     * @param brand a {@link String} object.
     * @param model a {@link String} object.
     *
     * @return a {@link java.util.List} object.
     */
    @NotNull
    List<T> findByMaster(
            @BrandOrModel
            final String brand,
            @BrandOrModel
            final String model);

    /**
     * <p>findByMaster.</p>
     *
     * @param masterId a {@link Long} object.
     *
     * @return a {@link java.util.List} object.
     */
    @NotNull
    Iterable<T> findByMaster(
            @NotNull
            @NotEmpty
            final Long... masterId);

    /**
     * <p>findMaster.</p>
     *
     * @param carId a long.
     *
     * @return a {@link org.agatom.springatom.data.types.car.CarMaster} object.
     */
    @NotNull
    <S extends T> CarMaster<S> findMaster(@Min(value = 1) final long carId);

    /**
     * <p>newCar.</p>
     *
     * @param brand        a {@link String} object.
     * @param model        a {@link String} object.
     * @param licencePlate a {@link String} object.
     * @param vinNumber    a {@link String} object.
     * @param ownerId      a long.
     *
     * @return a {@link org.agatom.springatom.data.types.car.Car} object.
     *
     * @throws java.lang.Exception if any.
     */
    @NotNull
    T newCar(
            @Pattern(regexp = RegexpPatterns.BIG_FIRST_LETTER_PATTERN, message = "Brand or service must starts with the capitalized letter")
            final String brand,
            @Pattern(regexp = RegexpPatterns.BIG_FIRST_LETTER_PATTERN, message = "Brand or service must starts with the capitalized letter")
            final String model,
            @LicencePlatePL
            final String licencePlate,
            final String vinNumber,
            final long ownerId) throws Exception;

    @NotNull
    T changeOwner(final long idCar, final long idClient, final String licencePlate) throws Exception;

    @NotNull
    T changeOwner(final T car, final User user, final String licencePlate) throws Exception;

    @NotNull
    Collection<CarOwnership> getOwnersips();

    @NotNull
    Collection<U> getOwners();

    @NotNull
    Collection<String> getFuelTypes();

}
