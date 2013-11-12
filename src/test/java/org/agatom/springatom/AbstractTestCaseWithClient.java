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


import org.agatom.springatom.server.model.beans.person.SPerson;
import org.agatom.springatom.server.model.beans.user.SUser;
import org.agatom.springatom.server.service.domain.SPersonService;
import org.agatom.springatom.server.service.domain.SUserService;
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
    protected static SUser C_1;
    protected static SUser C_2;
    @Autowired
    protected        SUserService   userService;
    @Autowired
    protected        SPersonService personService;

    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();
        if (AbstractTestCaseWithClient.C_1 != null && AbstractTestCaseWithClient.C_2 != null) {
            return;
        }
        {
            SPerson person = new SPerson();
            person.setFirstName("Tomasz");
            person.setLastName("Trębski");
            person.setPrimaryMail("kornicameister@gmail.com");

            SUser client = new SUser();
            client.setPerson(person);
            client.setUserName("kornicameister");
            client.setPassword("a");

            SUser newClient = this.userService.save(client);

            Assert.assertNotNull("newClient is null", newClient);
            Assert.assertNotNull("newClient#id is null", newClient.getId());
            AbstractCarTestCase.C_1 = newClient;
        }
        {
            SPerson person = new SPerson();
            person.setFirstName("Maja");
            person.setLastName("Staszczyk");
            person.setPrimaryMail("m2311007@gmail.com");

            SUser client = new SUser();
            client.setPerson(person);
            client.setUserName("m2311007");
            client.setPassword("b");

            SUser newClient = this.userService.save(client);

            Assert.assertNotNull("newClient is null", newClient);
            Assert.assertNotNull("newClient#id is null", newClient.getId());
            AbstractCarTestCase.C_2 = newClient;
        }
    }
}
