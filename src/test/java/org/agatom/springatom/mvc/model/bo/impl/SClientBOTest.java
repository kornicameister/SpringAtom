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
import org.agatom.springatom.model.beans.meta.SClientProblemReportType;
import org.agatom.springatom.model.beans.meta.SMetaData;
import org.agatom.springatom.model.beans.util.SPersonalInformation;
import org.agatom.springatom.mvc.model.bo.SClientBO;
import org.agatom.springatom.mvc.model.bo.SClientProblemReportBO;
import org.agatom.springatom.mvc.model.bo.SMechanicBO;
import org.agatom.springatom.mvc.model.bo.SMetaDataBO;
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
    SClientBO clientService;
    @Autowired
    SMetaDataBO metaDataService;
    @Autowired
    SMechanicBO mechanicService;
    @Autowired
    SClientProblemReportBO clientProblemReportService;

    @Test
    public void test_A_DeleteAll() throws Exception {
        this.clientService.deleteAll();
        this.mechanicService.deleteAll();

        Set<SClient> clients = (Set<SClient>) this.clientService.findAll();
        Set<SMechanic> mechanics = (Set<SMechanic>) this.mechanicService.findAll();
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

        SClient newClient = this.clientService.save(client);

        Assert.assertNotNull("newClient is null", newClient);
        Assert.assertNotNull("newClient#id is null", newClient.getId());
        SClientBOTest.client = newClient;
    }

    @Test
    public void test_C_FindClient() throws Exception {
        SClient sClient = this.clientService.findOne(SClientBOTest.client.getId());
        Assert.assertNotNull("Client did not persisted", sClient);
    }

    @Test
    public void test_D_AddContactData() throws Exception {
        SClientContact mail = this.clientService.newEmail("tomasz.trebski@gmail.com", SClientBOTest.client);
        SClientContact cellPhone = this.clientService.newCellPhone("724-470-830", SClientBOTest.client);
        SClientContact fax = this.clientService.newFax("888-66-33", SClientBOTest.client);
        SClientContact phone = this.clientService.newPhone("838-12-54", SClientBOTest.client);

        Assert.assertNotNull("mail is null", mail);
        Assert.assertNotNull("cellPhone is null", cellPhone);
        Assert.assertNotNull("fax is null", fax);
        Assert.assertNotNull("phone is null", phone);

        Set<SClientContact> clientContacts = this.clientService.findAllContacts(SClientBOTest.client.getId());
        Assert.assertNotNull("clientContacts are null", clientContacts);
        Assert.assertTrue("clientContacts are empty", clientContacts.size() > 0);
    }

    @Test
    public void test_E_ChangeStatusOfClient() throws Exception {
        SClient sClient = this.clientService.disable(SClientBOTest.client);
        Assert.assertTrue("newClient#oldClient are equal", SClientBOTest.client.equals(sClient));
        SClientBOTest.client = sClient;
        Assert.assertTrue("newClient#disabled did not change", SClientBOTest.client.getDisabled());
    }

    @Test
    public void test_F_CountClients() throws Exception {
        Long clients = this.clientService.count();
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

        SMechanic newMechanic = this.mechanicService.save(sMechanic);


        Assert.assertNotNull("newMechanic is null", newMechanic);
        Assert.assertNotNull("newMechanic#id is null", newMechanic.getId());
        SClientBOTest.mechanic = newMechanic;
    }

    @Test
    public void test_H_AddProblemReports() throws Exception {
        SClientProblemReport problemReport;
        Iterable metaDatas = this.metaDataService.findAll(SClientProblemReportType.class);
        List<SMetaData> sMetaDataList = new ArrayList<>();
        for (Object sMetaData : metaDatas) {
            sMetaDataList.add((SMetaData) sMetaData);
        }
        Random seed = new Random();

        for (int i = 0 ; i < 10 ; i++) {
            problemReport = new SClientProblemReport();
            problemReport.setClient(SClientBOTest.client);
            problemReport.setType((SClientProblemReportType) sMetaDataList.get(seed.nextInt(sMetaDataList.size())));
            problemReport.setProblem(String.format("problem %d", i));
            this.clientService.newProblemReport(
                    String.format("problem %d", i),
                    SClientBOTest.client,
                    SClientBOTest.mechanic,
                    (SClientProblemReportType) sMetaDataList.get(seed.nextInt(sMetaDataList.size()))
            );
        }
        System.out.println();
    }

    @Test
    public void test_I_ListReports() throws Exception {
        Iterable<SClientProblemReport> problemReports = this.clientProblemReportService.findAll();
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
        this.clientService.deleteOne(SClientBOTest.client);
    }

    @Test
    public void test_K_DeleteMechanic() throws Exception {
        this.mechanicService.deleteOne(SClientBOTest.mechanic);
    }

}
