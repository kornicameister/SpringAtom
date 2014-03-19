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

package org.agatom.springatom.server.repository.repositories.car;

import org.agatom.springatom.server.model.beans.car.SCarMaster;
import org.agatom.springatom.server.repository.SBasicRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */

@Qualifier(SCarMasterRepository.REPO_NAME)
@RepositoryRestResource(itemResourceRel = SCarMasterRepository.REST_REPO_REL, path = SCarMasterRepository.REST_REPO_PATH)
public interface SCarMasterRepository
		extends SBasicRepository<SCarMaster, Long> {
	String REPO_NAME      = "CarMasterRepository";
	String REST_REPO_REL  = "repo.carmaster";
	String REST_REPO_PATH = "car_master";

	@RestResource(rel = "byBrandAndModel", path = "brandAndModel")
	SCarMaster findByManufacturingDataBrandAndManufacturingDataModel(
			@Param("brand") final String brand,
			@Param("model") final String model
	);

	@RestResource(rel = "byBrand", path = "brand")
	Page<SCarMaster> findByManufacturingDataBrand(
			@Param(value = "brand") final String brand,
			final Pageable pageable
	);

	@RestResource(rel = "byModel", path = "model")
	Page<SCarMaster> findByManufacturingDataModel(
			@Param(value = "model") final String model,
			final Pageable pageable
	);

	@RestResource(rel = "byBrandOrModelContaining", path = "brandOrModel_contains")
	@Query(name = "byBrandOrModelContaining", value = "select cm from SCarMaster as cm where cm.manufacturingData.brand like %:arg% or cm.manufacturingData.model like %:arg%")
	List<SCarMaster> findByManufacturingDataBrandContainingOrManufacturingDataModelContaining(
			@Param("arg") final String searchArgument
	);
}
