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

import org.agatom.springatom.server.model.beans.person.client.SClient;
import org.agatom.springatom.server.model.beans.person.embeddable.SPersonalInformation;
import org.agatom.springatom.server.service.SClientService;
import org.junit.Assert;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
abstract public class AbstractTestCaseWithClient
        extends AbstractSpringTestCase {
    protected static SClient        C_1;
    protected static SClient        C_2;
    @Autowired
    protected        SClientService clientService;

    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();
        if (AbstractTestCaseWithClient.C_1 != null && AbstractTestCaseWithClient.C_2 != null) {
            return;
        }
        {
            SPersonalInformation personalInformation = new SPersonalInformation();
            personalInformation.setFirstName("Tomasz");
            personalInformation.setLastName("Trębski");

            SClient client = new SClient();
            client.setInformation(personalInformation);
            client.setPrimaryMail("kornicameister@gmail.com");

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
            client.setPrimaryMail("m2311007@gmail.com");

            SClient newClient = this.clientService.save(client);

            Assert.assertNotNull("newClient is null", newClient);
            Assert.assertNotNull("newClient#id is null", newClient.getId());
            AbstractCarTestCase.C_2 = newClient;
        }
    }
}
