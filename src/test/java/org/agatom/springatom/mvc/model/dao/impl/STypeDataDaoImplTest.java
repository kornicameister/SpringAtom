package org.agatom.springatom.mvc.model.dao.impl;

import org.agatom.springatom.model.meta.SNotificationType;
import org.agatom.springatom.model.meta.STypeData;
import org.agatom.springatom.mvc.model.dao.STypeDataDao;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        "file:src/main/webapp/WEB-INF/mvc-dispatcher-servlet.xml")
public class STypeDataDaoImplTest extends ReflectionTestUtils {
    @Autowired
    STypeDataDao dao;

    @Test
    public void testNotNull() throws Exception {
        Assert.assertNotNull("Dao is null", dao);
    }

    @Test
    public void testGetAll() throws Exception {
        Set<STypeData> dataSet = dao.getAll(SNotificationType.class);
        Assert.assertNotSame("Data set is empty", 0, dataSet.size());
        for (STypeData typeData : dataSet) {
            Assert.assertNotNull("TypeData is null", typeData);
        }
    }

    @Test
    public void testGetById() throws Exception {
        STypeData typeData = dao.getById((long) 3, SNotificationType.class);
        Assert.assertNotNull("TypeData is null", typeData);
    }

    @Test
    public void testGetByType() throws Exception {
        STypeData typeData = dao.getByType("app_done", SNotificationType.class);
        Assert.assertNotNull("TypeData is null", typeData);
    }
}
