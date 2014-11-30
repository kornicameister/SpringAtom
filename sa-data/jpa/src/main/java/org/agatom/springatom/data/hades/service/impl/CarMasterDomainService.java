package org.agatom.springatom.data.hades.service.impl;

import org.agatom.springatom.data.constraints.BrandOrModel;
import org.agatom.springatom.data.hades.model.car.NCar;
import org.agatom.springatom.data.hades.model.car.NCarMaster;
import org.agatom.springatom.data.hades.service.NCarMasterService;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-09-29</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
@Service
class CarMasterDomainService
        extends AbstractDomainService<NCarMaster>
        implements NCarMasterService {
    @Override
    public List<NCarMaster> findByBrand(@BrandOrModel final String... brand) {
        return null;
    }

    @Override
    public List<NCarMaster> findByModel(@BrandOrModel final String... model) {
        return null;
    }

    @Override
    public NCarMaster findOne(@BrandOrModel final String brand, @BrandOrModel final String model) {
        return null;
    }

    @Override
    public List<NCar> findChildren(@NotNull final Long... masterIds) {
        return null;
    }

    @Override
    public Collection<String> getAllBrands() {
        return null;
    }

    @Override
    public Collection<String> getAllModels() {
        return null;
    }
}
