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

package org.agatom.springatom.mvc.model.bo.impl;

import org.agatom.springatom.AbstractSpringTestCase;
import org.agatom.springatom.model.beans.client.SClient;
import org.agatom.springatom.model.beans.client.SClientContact;
import org.agatom.springatom.model.beans.client.SClientProblemReport;
import org.agatom.springatom.model.beans.mechanic.SMechanic;
import org.agatom.springatom.model.beans.util.SPersonalInformation;
import org.agatom.springatom.mvc.model.bo.SClientBO;
import org.agatom.springatom.mvc.model.bo.SClientProblemReportBO;
import org.agatom.springatom.mvc.model.bo.SMechanicBO;
import org.agatom.springatom.mvc.model.bo.SMetaDataBO;
import org.agatom.springatom.mvc.model.dao.SMetaDataDAO;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * @author Tomasz
 * @version 0.0.1
 * @since 0.0.1
 */

@FixMethodOrder(value = MethodSorters.NAME_ASCENDING)
public class SClientBOTest extends AbstractSpringTestCase {
    protected static SClient client = null;

    protected static SMechanic mechanic = null;

    @Autowired
    SClientBO clientBO;

    @Autowired
    SMetaDataBO metaDataBO;

    @Autowired
    SMechanicBO mechanicBO;

    @Autowired
    SClientProblemReportBO clientProblemReportBO;

    @Test
    public void test_A_DeleteAll() throws Exception {
        this.clientBO.deleteAll();
        this.mechanicBO.deleteAll();

        Set<SClient> clients = (Set<SClient>) this.clientBO.findAll();
        Set<SMechanic> mechanics = (Set<SMechanic>) this.mechanicBO.findAll();
        Assert.assertTrue("Clients not removed", clients.size() == 0);
        Assert.assertTrue("Mechanics not removed", mechanics.size() == 0);
    }

    @Test
    public void test_B_CreateClient() throws Exception {
        SPersonalInformation personalInformation = new SPersonalInformation();
        personalInformation.setFirstName("Tomasz");
        personalInformation.setLastName("Trębski");

        SClient client = new SClient();
        client.setInformation(personalInformation);
        client.setEmail("kornicameister@gmail.com");

        SClient newClient = this.clientBO.save(client);

        Assert.assertNotNull("newClient is null", newClient);
        Assert.assertNotNull("newClient#id is null", newClient.getId());
        SClientBOTest.client = newClient;
    }

    @Test
    public void test_C_FindClient() throws Exception {
        SClient sClient = this.clientBO.findOne(SClientBOTest.client.getId());
        Assert.assertNotNull("Client did not persisted", sClient);
    }

    @Test
    public void test_D_AddContactData() throws Exception {
        SClientContact mail = this.clientBO.newEmail("tomasz.trebski@gmail.com", SClientBOTest.client.getId());
        SClientContact cellPhone = this.clientBO.newCellPhone("724-470-830", SClientBOTest.client.getId());
        SClientContact fax = this.clientBO.newFax("888-66-33", SClientBOTest.client.getId());
        SClientContact phone = this.clientBO.newPhone("838-12-54", SClientBOTest.client.getId());

        Assert.assertNotNull("mail is null", mail);
        Assert.assertNotNull("cellPhone is null", cellPhone);
        Assert.assertNotNull("fax is null", fax);
        Assert.assertNotNull("phone is null", phone);

        Set<SClientContact> clientContacts = this.clientBO.findAllContacts(SClientBOTest.client.getId());
        Assert.assertNotNull("clientContacts are null", clientContacts);
        Assert.assertTrue("clientContacts are empty", clientContacts.size() > 0);
    }

    @Test
    public void test_E_ChangeStatusOfClient() throws Exception {
        SClient sClient = this.clientBO.disable(SClientBOTest.client.getId());
        Assert.assertFalse("newClient#oldClient are equal", SClientBOTest.client.getDisabled()
                .equals(sClient.getDisabled()));
        SClientBOTest.client = sClient;
        Assert.assertTrue("newClient#disabled did not change", SClientBOTest.client.getDisabled());
    }

    @Test
    public void test_F_CountClients() throws Exception {
        Long clients = this.clientBO.count();
        Assert.assertEquals("Not 1 client found", (Object) 1l, clients);
    }

    @Test
    public void test_G_createSampleWorker() throws Exception {
        SPersonalInformation personalInformation = new SPersonalInformation();
        personalInformation.setFirstName("Maja");
        personalInformation.setLastName("Staszczyk");

        SMechanic sMechanic = new SMechanic();
        sMechanic.setInformation(personalInformation);
        sMechanic.setEmail("m2311007@gmail.com");

        SMechanic newMechanic = this.mechanicBO.save(sMechanic);


        Assert.assertNotNull("newMechanic is null", newMechanic);
        Assert.assertNotNull("newMechanic#id is null", newMechanic.getId());
        SClientBOTest.mechanic = newMechanic;
    }

    @Test
    public void test_H_AddProblemReports() throws Exception {
        List<SMetaDataDAO.MetaType> sMetaDataList = new ArrayList<>();

        sMetaDataList.add(SMetaDataDAO.MetaType.SCPR_FAKE_ID);
        sMetaDataList.add(SMetaDataDAO.MetaType.SCPR_DEBTS);
        sMetaDataList.add(SMetaDataDAO.MetaType.SCPR_MISSED_APP);
        sMetaDataList.add(SMetaDataDAO.MetaType.SCPR_NO_PAYMENT);

        Random seed = new Random();

        for (int i = 0 ; i < 20 ; i++) {
            this.clientBO.newProblemReport(
                    String.format("problem %d", i),
                    SClientBOTest.client.getId(),
                    SClientBOTest.mechanic.getId(),
                    sMetaDataList.get(seed.nextInt(sMetaDataList.size()))
            );
        }
    }

    @Test
    public void test_I_ListReports() throws Exception {
        Iterable<SClientProblemReport> problemReports = this.clientProblemReportBO.findAll();
        Assert.assertNotNull("problemReports is null", problemReports);


        List<SClientProblemReport> clientProblemReports = new ArrayList<>();
        for (Object scpr : problemReports) {
            clientProblemReports.add((SClientProblemReport) scpr);
        }

        Assert.assertTrue("problemReport is empty", clientProblemReports.size() > 0);

        for (SClientProblemReport problemReport : problemReports) {
            Assert.assertNotNull("ProblemReport is null", problemReport);
        }
    }

    @Test
    public void test_J_DeleteClient() throws Exception {
        this.clientBO.deleteOne(SClientBOTest.client.getId());
    }

    @Test
    public void test_K_DeleteMechanic() throws Exception {
        this.mechanicBO.deleteOne(SClientBOTest.mechanic.getId());
    }

}
