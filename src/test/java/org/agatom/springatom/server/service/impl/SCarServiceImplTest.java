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

package org.agatom.springatom.server.service.impl;

import org.agatom.springatom.AbstractCarTestCase;
import org.agatom.springatom.server.model.beans.car.SCar;
import org.agatom.springatom.server.model.beans.car.SCarMaster;
import org.agatom.springatom.server.service.domain.SCarService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.*;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */

@FixMethodOrder(value = MethodSorters.NAME_ASCENDING)
public class SCarServiceImplTest
        extends AbstractCarTestCase {


    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();
    }

    @Test
    public void test_A_A_NewCars() throws Exception {
        final Map<Long, SCar> map = new HashMap<>();
        final List<SCar> list = new ArrayList<>();

        int it = 0;
        for (final MockCar mockedCar : MOCK_CARS) {
            final String licencePlate = mockedCar.licencePlate;
            final String vinNumber = String.valueOf(mockedCar.vinNumber);
            final Long ownerId = C_1.getId();
            list.add(this.carService
                    .newCar(mockedCar.mockMaster.brand, mockedCar.mockMaster.model, licencePlate, vinNumber, ownerId));
        }

        Assert.assertTrue(list.size() == MOCKED_MASTERS.size());
        for (final SCar sCar : list) {
            Assert.assertNotNull(sCar);
            Assert.assertFalse(sCar.isNew());
            Assert.assertNotNull(sCar.getId());
            map.put(sCar.getId(), sCar);
        }

        SCarServiceImplTest.MOCKED_CARS = map;
    }

    @Test
    public void test_B_B_ValidateMasters() throws Exception {
        final Set<SCarMaster> carMasters = new HashSet<>();
        for (final SCar sCar : MOCKED_CARS.values()) {
            carMasters.add(sCar.getCarMaster());
        }
        Assert.assertTrue(MOCKED_MASTER_DISTINCT.equals((long) carMasters.size()));
        Assert.assertTrue(MOCKED_MASTER_DISTINCT.equals(this.carMasterService.count()));
    }

    @Test
    public void test_C_Count() throws Exception {
        Assert.assertTrue(this.carService.count() > 0);
    }

    @Test
    public void test_D_ChangeOwner() throws Exception {
        final Long carId = MOCKED_CARS.keySet().iterator().next();
        final Long newOwnerId = C_2.getId();
        final Long oldOwnerId = MOCKED_CARS.get(carId).getOwner().getId();
        final Long oldVersion = MOCKED_CARS.get(carId).getVersion();
        final SCar sCar = this.carService.newOwner(carId, newOwnerId);

        Assert.assertNotNull(sCar);
        Assert.assertFalse(sCar.getVersion().equals(oldVersion));
        Assert.assertFalse(newOwnerId.equals(oldOwnerId));
    }

    @Test
    public void test_E_Master_FindByBrand() throws Exception {
        for (final MockMaster mm : MOCKED_MASTERS) {
            final List<SCarMaster> byBrand = this.carMasterService.findByBrand(mm.brand);
            Assert.assertNotNull(byBrand);
            Assert.assertTrue(byBrand.size() > 0);
            for (final SCarMaster sCarMaster : byBrand) {
                Assert.assertNotNull(sCarMaster);
                Assert.assertNotNull(sCarMaster.getId());
                Assert.assertFalse(sCarMaster.isNew());
                Assert.assertTrue(sCarMaster.getManufacturingData().getBrand().equals(mm.brand));
            }
        }
    }

    @Test
    public void test_F_Master_FindByModel() throws Exception {
        for (final MockMaster mm : MOCKED_MASTERS) {
            final List<SCarMaster> byModel = this.carMasterService.findByModel(mm.model);
            Assert.assertNotNull(byModel);
            Assert.assertTrue(byModel.size() > 0);
            for (final SCarMaster sCarMaster : byModel) {
                Assert.assertNotNull(sCarMaster);
                Assert.assertNotNull(sCarMaster.getId());
                Assert.assertFalse(sCarMaster.isNew());
                Assert.assertTrue(sCarMaster.getManufacturingData().getModel().equals(mm.model));
            }
        }
    }

    @Test
    public void test_G_Master_FindOne() throws Exception {
        for (final MockMaster mm : MOCKED_MASTERS) {
            final SCarMaster sCarMaster = this.carMasterService.findOne(mm.brand, mm.model);
            Assert.assertNotNull(sCarMaster);
            Assert.assertNotNull(sCarMaster.getId());
            Assert.assertFalse(sCarMaster.isNew());
            Assert.assertTrue(sCarMaster.getManufacturingData().getModel().equals(mm.model));
            Assert.assertTrue(sCarMaster.getManufacturingData().getBrand().equals(mm.brand));
        }
    }

    @Test
    public void test_H_Master_FindChildren() throws Exception {
        for (final MockMaster mm : MOCKED_MASTERS) {
            final SCarMaster sCarMaster = this.carMasterService.findOne(mm.brand, mm.model);
            final List<SCar> children = this.carMasterService.findChildren(sCarMaster.getId());
            Assert.assertNotNull(children);
            Assert.assertTrue(children.size() > 0);
            for (final SCar child : children) {
                Assert.assertNotNull(child);
                Assert.assertNotNull(child.getId());
                Assert.assertFalse(child.isNew());
                Assert.assertTrue(child.getCarMaster().equals(sCarMaster));
            }
        }
    }

    @Test
    public void test_I_FindByMaster() throws Exception {
        for (final MockMaster mm : MOCKED_MASTERS) {
            final SCarMaster sCarMaster = this.carMasterService.findOne(mm.brand, mm.model);
            final List<SCar> cars = this.carService.findByMaster(sCarMaster.getId());
            Assert.assertNotNull(cars);
            Assert.assertTrue(cars.size() > 0);
            for (final SCar child : cars) {
                Assert.assertNotNull(child);
                Assert.assertNotNull(child.getId());
                Assert.assertFalse(child.isNew());
                Assert.assertTrue(child.getCarMaster().equals(sCarMaster));
            }
        }
    }

    @Test
    public void test_J_FindMaster() throws Exception {
        for (final Long carId : SCarServiceImplTest.MOCKED_CARS.keySet()) {
            final SCarMaster master = this.carService.findMaster(carId);
            Assert.assertNotNull(master);
            Assert.assertNotNull(master.getId());
            Assert.assertFalse(master.isNew());
        }
    }

    @Test
    public void test_K_FindBy() throws Exception {
        final List<SCar> foundCars = new ArrayList<>();
        for (final MockCar mockCar : MOCK_CARS) {
            final SCar car1 = this.carService.findBy(SCarService.SCarAttribute.LICENCE_PLATE, mockCar.licencePlate)
                                             .get(0);
            final SCar car2 = this.carService.findBy(SCarService.SCarAttribute.VIN_NUMBER, mockCar.vinNumber).get(0);
            final List<SCar> cars3 = this.carService.findBy(SCarService.SCarAttribute.OWNER, mockCar.ownerId);

            foundCars.add(car1);
            foundCars.add(car2);
            foundCars.addAll(cars3);

            Assert.assertTrue(car1.equals(car2));
        }
        for (final SCar car : foundCars) {
            Assert.assertNotNull(car);
            Assert.assertNotNull(car.getId());
            Assert.assertFalse(car.isNew());
        }
    }

    @Test
    public void test_Y_DeleteOne() throws Exception {
        final Long randomCarId = (Long) SCarServiceImplTest.MOCKED_CARS.keySet().toArray()[new Random()
                .nextInt(SCarServiceImplTest.MOCKED_CARS.size())];
        this.carService.deleteOne(randomCarId);
        final SCar sCar = this.carService.findOne(randomCarId);
        Assert.assertNull(sCar);
    }

    @Test
    public void test_L_FindOne() throws Exception {
        final Long randomCarId = (Long) SCarServiceImplTest.MOCKED_CARS.keySet().toArray()[new Random()
                .nextInt(SCarServiceImplTest.MOCKED_CARS.size())];
        final SCar car = this.carService.findOne(randomCarId);
        Assert.assertNotNull(car);
        Assert.assertNotNull(car.getId());
        Assert.assertFalse(car.isNew());
        Assert.assertTrue(randomCarId.equals(car.getId()));
    }

    @Test
    public void test_M_FindAll() throws Exception {
        final List<SCar> foundCars = this.carService.findAll();
        Assert.assertNotNull(foundCars);
        Assert.assertTrue(foundCars.size() > 0);
        for (final SCar car : foundCars) {
            Assert.assertNotNull(car);
            Assert.assertNotNull(car.getId());
            Assert.assertFalse(car.isNew());
        }
    }

    @Test
    public void test_Z_DeleteAll() throws Exception {
        this.carService.deleteAll();
        for (final Long carId : SCarServiceImplTest.MOCKED_CARS.keySet()) {
            final SCar sCar = this.carService.findOne(carId);
            Assert.assertNull(sCar);
        }
    }
}
