package org.agatom.springatom.data.hades.repo.repositories.car;

import org.agatom.springatom.data.hades.model.car.NCar;
import org.agatom.springatom.data.hades.repo.NRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource(
        itemResourceRel = NCarRepository.REST_REPO_REL,
        path = NCarRepository.REST_REPO_PATH
)
public interface NCarRepository
        extends NRepository<NCar> {
    /** Constant <code>REST_REPO_PATH="car"</code> */
    String REST_REPO_PATH = "car";
    /** Constant <code>REST_REPO_REL="rest.car"</code> */
    String REST_REPO_REL  = "rest.car";

    /**
     * <p>findByLicencePlate.</p>
     *
     * @param licencePlate a {@link String} object.
     *
     * @return a {@link org.agatom.springatom.data.hades.model.car.NCar} object.
     */
    @RestResource(rel = "byLicencePlate", path = "lp_equal")
    NCar findByLicencePlate(
            @Param(value = "lp") final String licencePlate
    );

    /**
     * <p>findByLicencePlateStartingWith.</p>
     *
     * @param licencePlate a {@link String} object.
     * @param pageable     a {@link org.springframework.data.domain.Pageable} object.
     *
     * @return a {@link org.springframework.data.domain.Page} object.
     */
    @RestResource(rel = "byLicencePlateStarting", path = "lp_starts")
    Page<NCar> findByLicencePlateStartingWith(
            @Param(value = "lp") final String licencePlate,
            final Pageable pageable
    );

    /**
     * <p>findByLicencePlateEndingWith.</p>
     *
     * @param licencePlate a {@link String} object.
     * @param pageable     a {@link org.springframework.data.domain.Pageable} object.
     *
     * @return a {@link org.springframework.data.domain.Page} object.
     */
    @RestResource(rel = "byLicencePlateEnding", path = "lp_ends")
    Page<NCar> findByLicencePlateEndingWith(
            @Param(value = "lp") final String licencePlate,
            final Pageable pageable
    );

    /**
     * <p>findByLicencePlateContaining.</p>
     *
     * @param licencePlate a {@link String} object.
     * @param pageable     a {@link org.springframework.data.domain.Pageable} object.
     *
     * @return a {@link org.springframework.data.domain.Page} object.
     */
    @RestResource(rel = "byLicencePlateContaining", path = "lp_contains")
    Page<NCar> findByLicencePlateContaining(
            @Param(value = "lp") final String licencePlate,
            final Pageable pageable
    );

    /**
     * <p>findByCarMasterManufacturingDataBrand.</p>
     *
     * @param brand    a {@link String} object.
     * @param pageable a {@link org.springframework.data.domain.Pageable} object.
     *
     * @return a {@link org.springframework.data.domain.Page} object.
     */
    @RestResource(rel = "byBrand", path = "brand")
    Page<NCar> findByCarMasterManufacturingDataBrand(
            @Param(value = "brand") final String brand,
            final Pageable pageable
    );

    /**
     * <p>findByCarMasterManufacturingDataModel.</p>
     *
     * @param model    a {@link String} object.
     * @param pageable a {@link org.springframework.data.domain.Pageable} object.
     *
     * @return a {@link org.springframework.data.domain.Page} object.
     */
    @RestResource(rel = "byModel", path = "model")
    Page<NCar> findByCarMasterManufacturingDataModel(
            @Param(value = "model") final String model,
            final Pageable pageable
    );

    /**
     * <p>findByCarMasterManufacturingDataBrandAndCarMasterManufacturingDataModel.</p>
     *
     * @param brand    a {@link String} object.
     * @param model    a {@link String} object.
     * @param pageable a {@link org.springframework.data.domain.Pageable} object.
     *
     * @return a {@link org.springframework.data.domain.Page} object.
     */
    @RestResource(rel = "byBrandAndModel", path = "brandAndModel")
    Page<NCar> findByCarMasterManufacturingDataBrandAndCarMasterManufacturingDataModel(
            @Param(value = "brand") final String brand,
            @Param(value = "model") final String model,
            final Pageable pageable
    );

    /**
     * <p>findByOwnerPersonLastNameContaining.</p>
     *
     * @param owner    a {@link String} object.
     * @param pageable a {@link org.springframework.data.domain.Pageable} object.
     *
     * @return a {@link org.springframework.data.domain.Page} object.
     */
    @RestResource(rel = "byOwnerLastName", path = "ownerLastName")
    Page<NCar> findByOwnerCredentialsUsernameContaining(@Param(value = "owner") final String owner, final Pageable pageable);

    /**
     * <p>findByVinNumber.</p>
     *
     * @param vinNumber a {@link String} object.
     *
     * @return a {@link org.agatom.springatom.data.hades.model.car.NCar} object.
     */
    @RestResource(rel = "byVinNumber", path = "vin")
    NCar findByVinNumber(@Param(value = "vin") final String vinNumber);

    /**
     * <p>findByVinNumberContaining.</p>
     *
     * @param vinNumber a {@link String} object.
     * @param pageable  a {@link org.springframework.data.domain.Pageable} object.
     *
     * @return a {@link org.springframework.data.domain.Page} object.
     */
    @RestResource(rel = "byVinNumberContaining", path = "vin_contains")
    Page<NCar> findByVinNumberContaining(@Param(value = "vin") final String vinNumber, Pageable pageable);
}
