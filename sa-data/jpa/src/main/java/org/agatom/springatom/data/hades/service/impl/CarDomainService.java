package org.agatom.springatom.data.hades.service.impl;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import org.agatom.springatom.data.constraints.BrandOrModel;
import org.agatom.springatom.data.hades.model.car.NCar;
import org.agatom.springatom.data.hades.model.car.NCarMaster;
import org.agatom.springatom.data.hades.model.user.NUser;
import org.agatom.springatom.data.hades.repo.repositories.car.NCarMasterRepository;
import org.agatom.springatom.data.hades.repo.repositories.user.NUserRepository;
import org.agatom.springatom.data.hades.service.NCarService;
import org.agatom.springatom.data.types.car.CarOwnership;
import org.agatom.springatom.data.types.user.User;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-09-28</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
@Service
class CarDomainService
        extends AbstractDomainService<NCar>
        implements NCarService {

    @Autowired
    private NCarMasterRepository carMasterRepository = null;
    @Autowired
    private NUserRepository      userRepository      = null;

    @Override
    public List<NCar> findByMaster(@BrandOrModel final String brand, @BrandOrModel final String model) {
        return null;
    }

    @Override
    public Iterable<NCar> findByMaster(@NotNull @NotEmpty final Long... masterId) {
        final List<NCarMaster> all = this.carMasterRepository.findAll(Lists.newArrayList(masterId));
        return FluentIterable.from(all).transformAndConcat(new Function<NCarMaster, Iterable<? extends NCar>>() {
            @Nullable
            @Override
            public Iterable<? extends NCar> apply(final NCarMaster input) {
                return input.getChildren();
            }
        });
    }

    @Override
    @SuppressWarnings("unchecked")
    public NCarMaster findMaster(@Min(value = 1) final long carId) {
        return this.carMasterRepository.findOne(carId);
    }

    @Override
    public NCar newCar(final String brand, final String model, final String licencePlate, final String vinNumber, final long ownerId) throws Exception {
        return null;
    }

    @Override
    public NCar changeOwner(final long idCar, final long idClient, final String licencePlate) throws Exception {
        return this.changeOwner(
                this.repository.findOne(idCar),
                this.userRepository.findOne(idClient),
                licencePlate
        );
    }

    @Override
    public NCar changeOwner(final NCar car, final User user, final String licencePlate) throws Exception {
        final User owner = car.getOwner();
        return null;
    }

    @Override
    public Collection<CarOwnership> getOwnersips() {
        return null;
    }

    @Override
    public Collection<NUser> getOwners() {
        return null;
    }

    @Override
    public Collection<String> getFuelTypes() {
        return null;
    }

}
