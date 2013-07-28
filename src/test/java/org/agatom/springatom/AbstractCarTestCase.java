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

package org.agatom.springatom;

import org.agatom.springatom.model.beans.car.SCar;
import org.agatom.springatom.model.beans.person.client.SClient;
import org.agatom.springatom.model.beans.person.embeddable.SPersonalInformation;
import org.agatom.springatom.mvc.model.service.SCarMasterService;
import org.agatom.springatom.mvc.model.service.SCarService;
import org.agatom.springatom.mvc.model.service.SClientService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
abstract public class AbstractCarTestCase extends AbstractSpringTestCase {

    protected static Long            MOCKED_MASTER_DISTINCT = null;
    protected static Map<Long, SCar> CARS                   = null;
    protected static SClient           C_1;
    protected static List<MockMaster>  MOCKED_MASTERS;
    protected static SClient           C_2;
    @Autowired
    protected        SCarService       carService;
    @Autowired
    protected        SClientService    clientService;
    @Autowired
    protected        SCarMasterService carMasterService;

    @BeforeClass
    public static void mockMasterMap() {
        MOCKED_MASTERS = new ArrayList<>();

        MOCKED_MASTERS.add(new MockMaster("Fiat", "Panda"));
        MOCKED_MASTERS.add(new MockMaster("Fiat", "126p"));
        MOCKED_MASTERS.add(new MockMaster("Jeep", "Commander"));
        MOCKED_MASTERS.add(new MockMaster("Fiat", "Panda"));
        MOCKED_MASTERS.add(new MockMaster("Jeep", "Commander"));

        MOCKED_MASTER_DISTINCT = 3l;
    }

    @Before
    public void test_0_createOwner() {
        if (AbstractCarTestCase.C_1 != null && AbstractCarTestCase.C_2 != null) {
            return;
        }
        {
            SPersonalInformation personalInformation = new SPersonalInformation();
            personalInformation.setFirstName("Tomasz");
            personalInformation.setLastName("Trębski");

            SClient client = new SClient();
            client.setInformation(personalInformation);
            client.setEmail("kornicameister@gmail.com");

            SClient newClient = this.clientService.save(client);

            Assert.assertNotNull("newClient is null", newClient);
            Assert.assertNotNull("newClient#id is null", newClient.getId());
            AbstractCarTestCase.C_1 = newClient;
        }
        {
            SPersonalInformation personalInformation = new SPersonalInformation();
            personalInformation.setFirstName("Maja");
            personalInformation.setLastName("Staszczyk");

            SClient client = new SClient();
            client.setInformation(personalInformation);
            client.setEmail("m2311007@gmail.com");

            SClient newClient = this.clientService.save(client);

            Assert.assertNotNull("newClient is null", newClient);
            Assert.assertNotNull("newClient#id is null", newClient.getId());
            AbstractCarTestCase.C_2 = newClient;
        }
    }

    protected static class MockMaster {
        public final String brand;
        public final String model;

        public MockMaster(final String brand, final String model) {
            this.brand = brand;
            this.model = model;
        }
    }
}
