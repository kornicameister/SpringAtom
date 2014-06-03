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

import org.agatom.springatom.server.model.beans.car.SCar;
import org.agatom.springatom.server.repository.SRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

/**
 * <p>SCarRepository interface.</p>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Qualifier(SCarRepository.REPO_NAME)
@RepositoryRestResource(
		itemResourceRel = SCarRepository.REST_REPO_REL,
		path = SCarRepository.REST_REPO_PATH
)
public interface SCarRepository
		extends SRepository<SCar, Long, Integer> {
	/** Constant <code>REST_REPO_PATH="car"</code> */
	String REST_REPO_PATH = "car";
	/** Constant <code>REST_REPO_REL="rest.car"</code> */
	String REST_REPO_REL  = "rest.car";
	/** Constant <code>REPO_NAME="CarRepository"</code> */
	String REPO_NAME      = "CarRepository";

	/**
	 * <p>findByLicencePlate.</p>
	 *
	 * @param licencePlate a {@link java.lang.String} object.
	 *
	 * @return a {@link org.agatom.springatom.server.model.beans.car.SCar} object.
	 */
	@RestResource(rel = "byLicencePlate", path = "lp_equal")
	SCar findByLicencePlate(
			@Param(value = "lp") final String licencePlate
	);

	/**
	 * <p>findByLicencePlateStartingWith.</p>
	 *
	 * @param licencePlate a {@link java.lang.String} object.
	 * @param pageable     a {@link org.springframework.data.domain.Pageable} object.
	 *
	 * @return a {@link org.springframework.data.domain.Page} object.
	 */
	@RestResource(rel = "byLicencePlateStarting", path = "lp_starts")
	Page<SCar> findByLicencePlateStartingWith(
			@Param(value = "lp") final String licencePlate,
			final Pageable pageable
	);

	/**
	 * <p>findByLicencePlateEndingWith.</p>
	 *
	 * @param licencePlate a {@link java.lang.String} object.
	 * @param pageable     a {@link org.springframework.data.domain.Pageable} object.
	 *
	 * @return a {@link org.springframework.data.domain.Page} object.
	 */
	@RestResource(rel = "byLicencePlateEnding", path = "lp_ends")
	Page<SCar> findByLicencePlateEndingWith(
			@Param(value = "lp") final String licencePlate,
			final Pageable pageable
	);

	/**
	 * <p>findByLicencePlateContaining.</p>
	 *
	 * @param licencePlate a {@link java.lang.String} object.
	 * @param pageable     a {@link org.springframework.data.domain.Pageable} object.
	 *
	 * @return a {@link org.springframework.data.domain.Page} object.
	 */
	@RestResource(rel = "byLicencePlateContaining", path = "lp_contains")
	Page<SCar> findByLicencePlateContaining(
			@Param(value = "lp") final String licencePlate,
			final Pageable pageable
	);

	/**
	 * <p>findByCarMasterManufacturingDataBrand.</p>
	 *
	 * @param brand    a {@link java.lang.String} object.
	 * @param pageable a {@link org.springframework.data.domain.Pageable} object.
	 *
	 * @return a {@link org.springframework.data.domain.Page} object.
	 */
	@RestResource(rel = "byBrand", path = "brand")
	Page<SCar> findByCarMasterManufacturingDataBrand(
			@Param(value = "brand") final String brand,
			final Pageable pageable
	);

	/**
	 * <p>findByCarMasterManufacturingDataModel.</p>
	 *
	 * @param model    a {@link java.lang.String} object.
	 * @param pageable a {@link org.springframework.data.domain.Pageable} object.
	 *
	 * @return a {@link org.springframework.data.domain.Page} object.
	 */
	@RestResource(rel = "byModel", path = "model")
	Page<SCar> findByCarMasterManufacturingDataModel(
			@Param(value = "model") final String model,
			final Pageable pageable
	);

	/**
	 * <p>findByCarMasterManufacturingDataBrandAndCarMasterManufacturingDataModel.</p>
	 *
	 * @param brand    a {@link java.lang.String} object.
	 * @param model    a {@link java.lang.String} object.
	 * @param pageable a {@link org.springframework.data.domain.Pageable} object.
	 *
	 * @return a {@link org.springframework.data.domain.Page} object.
	 */
	@RestResource(rel = "byBrandAndModel", path = "brandAndModel")
	Page<SCar> findByCarMasterManufacturingDataBrandAndCarMasterManufacturingDataModel(
			@Param(value = "brand") final String brand,
			@Param(value = "model") final String model,
			final Pageable pageable
	);

	/**
	 * <p>findByOwnerPersonLastNameContaining.</p>
	 *
	 * @param lastName a {@link java.lang.String} object.
	 * @param pageable a {@link org.springframework.data.domain.Pageable} object.
	 *
	 * @return a {@link org.springframework.data.domain.Page} object.
	 */
	@RestResource(rel = "byOwnerLastName", path = "ownerLastName")
	Page<SCar> findByOwnerPersonLastNameContaining(
			@Param(value = "ownerLastName") final String lastName,
			final Pageable pageable
	);

	/**
	 * <p>findByVinNumber.</p>
	 *
	 * @param vinNumber a {@link java.lang.String} object.
	 *
	 * @return a {@link org.agatom.springatom.server.model.beans.car.SCar} object.
	 */
	@RestResource(rel = "byVinNumber", path = "vin")
	SCar findByVinNumber(
			@Param(value = "vin") final String vinNumber
	);

	/**
	 * <p>findByVinNumberContaining.</p>
	 *
	 * @param vinNumber a {@link java.lang.String} object.
	 * @param pageable  a {@link org.springframework.data.domain.Pageable} object.
	 *
	 * @return a {@link org.springframework.data.domain.Page} object.
	 */
	@RestResource(rel = "byVinNumberContaining", path = "vin_contains")
	Page<SCar> findByVinNumberContaining(
			@Param(value = "vin") final String vinNumber,
			final Pageable pageable
	);
}
