package org.agatom.springatom.mvc.model.dao.impl;

import org.agatom.springatom.model.meta.*;
import org.agatom.springatom.mvc.model.bo.STypeDataBo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        "file:src/main/webapp/WEB-INF/mvc-dispatcher-servlet.xml")
public class STypeDataDaoImplTest extends ReflectionTestUtils {
    @Autowired
    STypeDataBo typeDataBo;
    private List<Class<? extends STypeData>> clazzList;

    @Before
    public void setUp() throws Exception {
        List<Class<? extends STypeData>> classList = new ArrayList<>();

        classList.add(SNotificationType.class);
        classList.add(SContactDataType.class);
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
            Set<STypeData> dataSet = typeDataBo.getAll(clazz);
            Assert.assertNotSame(String.format("Data set is empty for %s", clazz.getSimpleName()), 0, dataSet.size());
            for (STypeData typeData : dataSet) {
                Assert.assertNotNull("TypeData is null", typeData);
            }
        }
    }

    @Test
    public void testGetById() throws Exception {
        STypeData typeData = typeDataBo.getById((long) 3, SNotificationType.class);
        Assert.assertNotNull("TypeData is null", typeData);
    }

    @Test
    public void testGetByType() throws Exception {
        STypeData typeData = typeDataBo.getByType("app_done", SNotificationType.class);
        Assert.assertNotNull("TypeData is null", typeData);
    }
}
