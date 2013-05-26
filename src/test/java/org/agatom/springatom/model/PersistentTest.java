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

package org.agatom.springatom.model;

import org.agatom.springatom.model.client.SClient;
import org.agatom.springatom.model.mechanic.SMechanic;
import org.agatom.springatom.model.meta.SAppointmentTaskType;
import org.agatom.springatom.model.meta.SClientProblemReportType;
import org.agatom.springatom.model.meta.SContactType;
import org.agatom.springatom.model.meta.SNotificationType;
import org.agatom.springatom.model.user.SUser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kornicamaister
 * @version 0.0.1
 * @since 0.0.1
 */
public class PersistentTest {
    private List<Class<? extends Persistable>> clazzList;

    @Before
    public void setUp() throws Exception {
        List<Class<? extends Persistable>> classList = new ArrayList<>();

        classList.add(SNotificationType.class);
        classList.add(SContactType.class);
        classList.add(SAppointmentTaskType.class);
        classList.add(SClientProblemReportType.class);
        classList.add(SUser.class);
        classList.add(SNotificationType.class);
        classList.add(SMechanic.class);
        classList.add(SClient.class);

        this.clazzList = classList;

    }

    @Test
    public void testGetEntityName() throws Exception {
        for (Class aClass : this.clazzList) {
            Persistable persistable = (Persistable) aClass.newInstance();
            Assert.assertEquals(aClass.getSimpleName(), persistable.getEntityName());
        }
    }

    @Test
    public void testIdColumnName() throws Exception {
        for (Class aClass : this.clazzList) {
            Persistable persistable = (Persistable) aClass.newInstance();
            System.out.println(String.format("%s = %s", aClass.getSimpleName(), persistable.getIdColumnName()));
//            Assert.assertEquals("id" + aClass.getSimpleName(), persistable.getIdColumnName());
        }
    }
}
