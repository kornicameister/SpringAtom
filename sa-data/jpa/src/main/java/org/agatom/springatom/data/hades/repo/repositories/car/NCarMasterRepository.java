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

package org.agatom.springatom.data.hades.repo.repositories.car;

import org.agatom.springatom.data.hades.model.car.NCarMaster;
import org.agatom.springatom.data.hades.repo.NRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource(itemResourceRel = NCarMasterRepository.REST_REPO_REL, path = NCarMasterRepository.REST_REPO_PATH)
public interface NCarMasterRepository
        extends NRepository<NCarMaster> {
    /** Constant <code>REST_REPO_REL="repo.carmaster"</code> */
    String REST_REPO_REL  = "repo.carmaster";
    /** Constant <code>REST_REPO_PATH="car_master"</code> */
    String REST_REPO_PATH = "car_master";

    @RestResource(rel = "byBrandAndModel", path = "brandAndModel")
    NCarMaster findByManufacturingDataBrandAndManufacturingDataModel(
            @Param("brand") final String brand,
            @Param("model") final String model
    );

    @RestResource(rel = "byBrand", path = "brand")
    Page<NCarMaster> findByManufacturingDataBrand(
            @Param(value = "brand") final String brand,
            final Pageable pageable
    );

    @RestResource(rel = "byModel", path = "model")
    Page<NCarMaster> findByManufacturingDataModel(
            @Param(value = "model") final String model,
            final Pageable pageable
    );
}
