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

package org.agatom.springatom.mvc.model.service.impl;

import com.mysema.query.types.expr.BooleanExpression;
import org.agatom.springatom.jpa.repositories.SCarMasterRepository;
import org.agatom.springatom.jpa.repositories.SCarRepository;
import org.agatom.springatom.jpa.repositories.SClientRepository;
import org.agatom.springatom.model.beans.car.QSCar;
import org.agatom.springatom.model.beans.car.QSCarMaster;
import org.agatom.springatom.model.beans.car.SCar;
import org.agatom.springatom.model.beans.car.SCarMaster;
import org.agatom.springatom.model.beans.car.embeddable.QSCarMasterManufacturingData;
import org.agatom.springatom.model.beans.car.embeddable.SCarMasterManufacturingData;
import org.agatom.springatom.model.beans.person.client.SClient;
import org.agatom.springatom.mvc.model.exceptions.SEntityDoesNotExists;
import org.agatom.springatom.mvc.model.exceptions.SException;
import org.agatom.springatom.mvc.model.exceptions.SUnambiguousResultException;
import org.agatom.springatom.mvc.model.service.SCarService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */

@Service
@Transactional(readOnly = true, isolation = Isolation.SERIALIZABLE, propagation = Propagation.SUPPORTS)
public class SCarServiceImpl
        extends SServiceImpl<SCar, Long, Integer, SCarRepository>
        implements SCarService {
    private static final Logger LOGGER = Logger.getLogger(SCarServiceImpl.class);
    @Autowired
    SCarMasterRepository masterService;
    @Autowired
    SClientRepository    clientRepository;
    private SCarRepository repository;

    @Override
    @Autowired
    public void autoWireRepository(final SCarRepository repo) {
        super.autoWireRepository(repo);
        this.repository = repo;
    }

    @Override
    @CacheEvict(value = "cars", allEntries = true, beforeInvocation = true)
    public List<SCar> findByMaster(@NotNull final String brand, @NotNull final String model) {
        return (List<SCar>) this.repository.findAll(QSCar.sCar.carMaster.eq(this.getMaster(brand, model)));
    }

    private SCarMaster getMaster(@NotNull final String brand, @NotNull final String model) {
        final QSCarMasterManufacturingData manufacturingData = QSCarMaster.sCarMaster.manufacturingData;
        final BooleanExpression brandEq = manufacturingData.brand.eq(brand);
        final BooleanExpression modelEq = manufacturingData.model.eq(model);
        return this.masterService.findOne(brandEq.and(modelEq));
    }

    @Override
    @CacheEvict(value = "cars", allEntries = true, beforeInvocation = true)
    public List<SCar> findByMaster(@NotNull final Long... masterId) {
        return (List<SCar>) this.repository.findAll(QSCar.sCar.carMaster.id.in(masterId));
    }

    @Override
    @Cacheable(value = "cars", key = "#carId")
    public SCarMaster findMaster(@NotNull final Long carId) {
        return this.masterService.findOne(QSCarMaster.sCarMaster.children.any().id.eq(carId));
    }

    @Override
    @Cacheable(value = "cars", key = "#attribute.toString()")
    @Transactional(
            readOnly = true,
            isolation = Isolation.SERIALIZABLE,
            propagation = Propagation.SUPPORTS,
            rollbackFor = SUnambiguousResultException.class
    )
    public List<SCar> findBy(@NotNull final SCarAttribute attribute, @NotNull final Object value) throws
            SUnambiguousResultException {
        final List<SCar> cars = (List<SCar>) this.repository.findAll(attribute.eq(value));
        switch (attribute) {
            case LICENCE_PLATE:
            case VIN_NUMBER:
                if (cars.size() > 1) {
                    throw new SUnambiguousResultException(SCar.class, attribute, value, 1, cars.size());
                }
                break;
            case OWNER:
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug(String.format("More than one row has been returned for attribute=%s", attribute));
                }
                break;
        }
        return cars;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = SEntityDoesNotExists.class) //TODO figure out possible exceptions
    public SCar newCar(@NotNull final String brand,
                       @NotNull final String model,
                       @NotNull final String licencePlate,
                       @NotNull final String vinNumber,
                       @NotNull final Long ownerId) throws SEntityDoesNotExists {

        final SClient owner = this.getOwner(ownerId);
        final SCarMaster sCarMaster = this.getOrPersistMaster(brand, model);

        if (owner == null) {
            throw new SEntityDoesNotExists(SClient.class, ownerId);
        }
        if (sCarMaster == null) {
            throw new SEntityDoesNotExists(SCarMaster.class, new String[]{brand, model});
        }
        final SCar sCar = this.persistCar(licencePlate, vinNumber, sCarMaster, owner);
        LOGGER.info(String.format("Created SCar >> %s", sCar));
        return sCar;
    }

    private SCar persistCar(final String licencePlate,
                            final String vinNumber,
                            final SCarMaster sCarMaster,
                            final SClient owner) {
        SCar sCar = new SCar();
        sCar.setCarMaster(sCarMaster);
        sCar.setLicencePlate(licencePlate);
        sCar.setVinNumber(vinNumber);
        sCar.setOwner(owner);
        sCar = this.repository.saveAndFlush(sCar);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("SCar >>> %s", sCar));
        }
        return sCar;
    }

    private SCarMaster getOrPersistMaster(final String brand, final String model) {
        SCarMaster sCarMaster = this.getMaster(brand, model);
        if (sCarMaster == null) {
            LOGGER.warn(String
                    .format("No %s for brand=%s and model=%s", SCarMaster.class.getSimpleName(), brand, model));
            sCarMaster = new SCarMaster();
            sCarMaster.setManufacturingData(new SCarMasterManufacturingData(brand, model));
            sCarMaster = this.masterService.save(sCarMaster);
        }
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace(String.format("SCarMaster >>> %s", sCarMaster));
        }
        return sCarMaster;
    }

    @Override
    @Transactional(readOnly = false,
            rollbackFor = SEntityDoesNotExists.class,
            isolation = Isolation.READ_COMMITTED,
            propagation = Propagation.REQUIRES_NEW)
    @Cacheable(key = "#idCar",
            value = "cars",
            condition = "#idCar.compareTo(1) > 1")
    @CacheEvict(key = "#idClient",
            value = "clients",
            beforeInvocation = true)
    public SCar newOwner(@NotNull final Long idCar,
                         @NotNull final Long idClient) throws
            SEntityDoesNotExists {
        final SCar car = this.findOne(idCar);
        final SClient owner = this.getOwner(idClient);
        if (car == null) {
            throw new SEntityDoesNotExists(SCar.class, idCar);
        }
        if (owner == null) {
            throw new SEntityDoesNotExists(SClient.class, idClient);
        }

        car.setOwner(owner);
        final SCar updatedCar = this.repository.saveAndFlush(car);

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("Changed owner of %s from %s to %s", updatedCar, car.getOwner(), owner));
        }

        return updatedCar;
    }

    private SClient getOwner(final Long ownerId) {
        final SClient owner = this.clientRepository.findOne(ownerId);
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace(String.format("SClient#Owner >>> %s", owner));
        }
        return owner;
    }

    public class InvalidOwnerException extends SException {
        public InvalidOwnerException(final SCar car, final SClient owner) {
            super(SCar.class, String.format("Owner for %s does not differ from current one %s", car, owner));
        }
    }
}
