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
}
