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

import org.agatom.springatom.data.constraints.BrandOrModel;
import org.agatom.springatom.data.types.car.Car;
import org.agatom.springatom.data.types.car.CarMaster;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Persistable;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;

/**
 * <p>SCarMasterService interface.</p>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public interface SCarMasterService<T extends CarMaster<P> & Persistable<Long>, P extends Car>
        extends SDomainService<T> {

    /**
     * <p>findByBrand.</p>
     *
     * @param brand a {@link String} object.
     *
     * @return a {@link java.util.List} object.
     */
    List<T> findByBrand(
            @BrandOrModel
            final String... brand);

    /**
     * <p>findByModel.</p>
     *
     * @param model a {@link String} object.
     *
     * @return a {@link java.util.List} object.
     */
    List<T> findByModel(
            @BrandOrModel
            final String... model);

    /**
     * <p>findOne.</p>
     *
     * @param brand a {@link String} object.
     * @param model a {@link String} object.
     *
     * @return a {@link org.agatom.springatom.data.types.car.CarMaster} object.
     */
    T findOne(
            @BrandOrModel
            final String brand,
            @BrandOrModel
            final String model);

    /**
     * <p>findChildren.</p>
     *
     * @param masterIds a {@link Long} object.
     *
     * @return a {@link java.util.List} object.
     */
    List<P> findChildren(
            @NotNull
            final Long... masterIds);

    /**
     * Returns collection of all available brands
     *
     * @return all brands
     */
    @Cacheable("scarmaster.brands")
    Collection<String> getAllBrands();

    /**
     * Returns collection of all available models
     *
     * @return all models
     */
    @Cacheable("scarmaster.models")
    Collection<String> getAllModels();
}
