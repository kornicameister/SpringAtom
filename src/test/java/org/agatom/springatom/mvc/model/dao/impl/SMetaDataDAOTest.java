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

package org.agatom.springatom.mvc.model.dao.impl;

import org.agatom.springatom.AbstractSpringTestCase;
import org.agatom.springatom.model.meta.*;
import org.agatom.springatom.mvc.model.bo.SMetaDataBO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class SMetaDataDAOTest extends AbstractSpringTestCase {
    @Autowired
    SMetaDataBO typeDataBo;

    private List<Class<? extends SMetaData>> clazzList;

    @Before
    public void setUp() throws Exception {
        List<Class<? extends SMetaData>> classList = new ArrayList<>();

        classList.add(SNotificationType.class);
        classList.add(SContactType.class);
        classList.add(SAppointmentTaskType.class);
        classList.add(SClientProblemReportType.class);

        this.clazzList = classList;
    }

    @Test
    public void testNotNull() throws Exception {
        Assert.assertNotNull("Dao is null", typeDataBo);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testGetAll() throws Exception {
        for (Class clazz : this.clazzList) {
            Set<SMetaData> dataSet = (Set<SMetaData>) typeDataBo.findAll(clazz);
            Assert.assertNotSame(String.format("Data set is empty for %s", clazz.getSimpleName()), 0, dataSet.size());
            for (SMetaData typeData : dataSet) {
                Assert.assertNotNull("TypeData is null", typeData);
            }
        }
    }

    @Test
    public void testGetById() throws Exception {
        Assert.assertNotNull("TypeData is null", this.typeDataBo.getById((long) 13, SNotificationType.class));
        Assert.assertNotNull("TypeData is null", this.typeDataBo.getById((long) 14, SNotificationType.class));
        Assert.assertNotNull("TypeData is null", this.typeDataBo.getById((long) 5, SClientProblemReportType.class));
        Assert.assertNotNull("TypeData is null", this.typeDataBo.getById((long) 13, SNotificationType.class));
        Assert.assertNotNull("TypeData is null", this.typeDataBo.getById((long) 8, SClientProblemReportType.class));
        Assert.assertNotNull("TypeData is null", this.typeDataBo.getById((long) 5, SClientProblemReportType.class));
    }

    @Test
    public void testGetByType() throws Exception {
        Assert.assertNotNull("TypeData is null", this.typeDataBo.getByType("app_done", SNotificationType.class));
        Assert.assertNotNull("TypeData is null", this.typeDataBo.getByType("fake_id", SClientProblemReportType.class));
        Assert.assertNotNull("TypeData is null", this.typeDataBo.getByType("app_done", SNotificationType.class));
        Assert.assertNotNull("TypeData is null", this.typeDataBo.getByType("fake_id", SClientProblemReportType.class));
        Assert.assertNotNull("TypeData is null", this.typeDataBo.getByType("fake_id", SClientProblemReportType.class));
        Assert.assertNotNull("TypeData is null", this.typeDataBo.getByType("missed_app", SClientProblemReportType.class));
    }
}
