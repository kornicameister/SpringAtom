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

package org.agatom.springatom.server.service.impl;

import org.agatom.springatom.AbstractSpringTestCase;
import org.agatom.springatom.server.model.beans.user.SUser;
import org.agatom.springatom.server.service.domain.SUserService;
import org.joda.time.DateTime;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Tomasz
 * @version 0.0.1
 * @since 0.0.1
 */

@FixMethodOrder(value = MethodSorters.NAME_ASCENDING)
public class SClientServiceTest
        extends AbstractSpringTestCase {
    protected static final DateTime PAST_TIME   = DateTime.now().minusMonths(1);
    protected static final DateTime FUTURE_TIME = DateTime.now().plusMonths(1);
    protected static       SUser    client      = null;
    protected static       SUser    mechanic    = null;
    @Autowired
    SUserService clientBO;
    @Autowired
    SUserService mechanicBO;

    @Test
    public void test_A_DeleteAll() throws Exception {
//        this.clientBO.deleteAll();
//        this.mechanicBO.deleteAll();
//
//        List<SClient> clients = this.clientBO.findAll();
//        List<SMechanic> mechanics = this.mechanicBO.findAll();
//        Assert.assertTrue("Clients not removed", clients.size() == 0);
//        Assert.assertTrue("Mechanics not removed", mechanics.size() == 0);
    }

    @Test
    public void test_B_CreateClient() throws Exception {
//        final SClient newClient = this.clientBO.save(
//                (SClient) new SClient()
//                        .setInformation(
//                                new SPersonalInformation()
//                                        .setFirstName("Tomasz")
//                                        .setLastName("Trębski")
//                        )
//                        .setPrimaryMail("kornicameister@gmail.com")
//        );
//
//        Assert.assertNotNull("newClient is null", newClient);
//        Assert.assertNotNull("newClient#id is null", newClient.getId());
//        SClientServiceTest.client = newClient;
    }

    @Test
    public void test_C_FindClient() throws Exception {
//        SClient sClient = this.clientBO.findOne(SClientServiceTest.client.getId());
//        Assert.assertNotNull("Client did not persisted", sClient);
    }

    @Test
    public void test_D_AddContactData() throws Exception {
//        final SContact mail = this.clientBO.newEmail("tomasz.trebski@gmail.com", SClientServiceTest.client.getId());
//        final SContact cellPhone = this.clientBO.newCellPhone("724-470-830", SClientServiceTest.client.getId());
//        final SContact fax = this.clientBO.newFax("888-66-33", SClientServiceTest.client.getId());
//        final SContact phone = this.clientBO.newPhone("838-12-54", SClientServiceTest.client.getId());
//
//        Assert.assertNotNull("mail is null", mail);
//        Assert.assertNotNull("cellPhone is null", cellPhone);
//        Assert.assertNotNull("fax is null", fax);
//        Assert.assertNotNull("phone is null", phone);
//
//        final List<SContact> clientContacts = this.clientBO.findAllContacts(SClientServiceTest.client.getId());
//        Assert.assertNotNull("clientContacts are null", clientContacts);
//        Assert.assertTrue("clientContacts are empty", clientContacts.size() > 0);
    }

    @Test
    public void test_F_CountClients() throws Exception {
//        Long clients = this.clientBO.count();
//        Assert.assertEquals("Not 1 web found", (Object) 1l, clients);
    }

    @Test
    public void test_G_createSampleWorker() throws Exception {
//        SPersonalInformation personalInformation = new SPersonalInformation();
//        personalInformation.setFirstName("Maja");
//        personalInformation.setLastName("Staszczyk");
//
//        SMechanic sMechanic = new SMechanic();
//        sMechanic.setInformation(personalInformation);
//        sMechanic.setPrimaryMail("m2311007@gmail.com");
//
//        SMechanic newMechanic = this.mechanicBO.save(sMechanic);
//
//
//        Assert.assertNotNull("newMechanic is null", newMechanic);
//        Assert.assertNotNull("newMechanic#id is null", newMechanic.getId());
//        SClientServiceTest.mechanic = newMechanic;
    }

    @Test
    public void test_H_AddProblemReports() throws Exception {
//        List<SMetaDataEnum> sMetaDataList = new ArrayList<>();
//
//        sMetaDataList.add(SMetaDataEnum.SCPR_FAKE_ID);
//        sMetaDataList.add(SMetaDataEnum.SCPR_DEBTS);
//        sMetaDataList.add(SMetaDataEnum.SCPR_MISSED_APP);
//        sMetaDataList.add(SMetaDataEnum.SCPR_NO_PAYMENT);
//
//        Random seed = new Random();
//
//        for (int i = 0 ; i < 20 ; i++) {
//            this.clientBO.newProblemReport(
//                    String.format("problem %d", i),
//                    SClientServiceTest.client.getId(),
//                    SClientServiceTest.mechanic.getId(),
//                    sMetaDataList.get(seed.nextInt(sMetaDataList.size()))
//            );
//        }
    }

    @Test
    public void test_I_ListReports() throws Exception {
//        Iterable<SClientProblemReport> problemReports = this.clientProblemReportBO.findAll();
//        Assert.assertNotNull("problemReports is null", problemReports);
//
//
//        List<SClientProblemReport> clientProblemReports = new ArrayList<>();
//        for (Object scpr : problemReports) {
//            clientProblemReports.add((SClientProblemReport) scpr);
//        }
//
//        Assert.assertTrue("problemReport is empty", clientProblemReports.size() > 0);
//
//        for (SClientProblemReport problemReport : problemReports) {
//            Assert.assertNotNull("ProblemReport is null", problemReport);
//        }
    }

    @Test
    public void test_J_findByAttributes() throws Exception {
//        List<SClient> testTest = new ArrayList<>();
//        Iterable testIt;
//        //by first name
//        testIt = this.clientBO.findByFirstName("Tomasz");
//        Assert.assertNotNull("findByFirstName is null", testTest);
//        for (Object scpr : testIt) {
//            testTest.add((SClient) scpr);
//        }
//        Assert.assertTrue("findByFirstName is empty", testTest.size() > 0);
//
//        //by last name
//        testIt = this.clientBO.findByLastName("Trębski");
//        Assert.assertNotNull("findByLastName is null", testIt);
//        for (Object scpr : testIt) {
//            testTest.add((SClient) scpr);
//        }
//        Assert.assertTrue("findByLastName is empty", testTest.size() > 0);
//
//        //by email
//        SClient sClient = this.clientBO.findByEmail("kornicameister@gmail.com");
//        Assert.assertNotNull("findByEmail is null", sClient);
    }

    @Test
    public void test_K_findAllRevisions() throws Exception {
//        final Revisions<Integer, SClient> revisions = this.clientBO.findAllRevisions(SClientServiceTest.client.getId());
//        Assert.assertNotNull("Revision are null", revisions);
//        Assert.assertTrue("There are no revisions", revisions.getContent().size() > 0);
//        System.out.println("All revision:");
//        for (final Revision<Integer, SClient> revision : revisions) {
//            System.out.println(revision);
//        }
    }

    @Test
    public void test_L_findFirstRevision() throws Exception {
//        final Revision<Integer, SClient> first = this.clientBO.findFirstRevision(SClientServiceTest.client.getId());
//        Assert.assertNotNull("Revision is null", first);
//        System.out.println(String.format("FirstRev=%s", first));
    }

    @Test
    public void test_M_findLastRevision() throws Exception {
//        final Revision<Integer, SClient> last = this.clientBO.findLatestRevision(SClientServiceTest.client.getId());
//        Assert.assertNotNull("Revision is null", last);
//        System.out.println(String.format("LastRev=%s", last));
    }

    @Test
    public void test_N_findRevisionPast() throws Exception {
//        final Revisions<Integer, SClient> modifiedBefore = this.clientBO.findModifiedBefore(SClientServiceTest.client
//                                                                                                              .getId(), PAST_TIME);
//        Assert.assertTrue(String.format("Before %s are not empty", PAST_TIME), modifiedBefore.getContent().isEmpty());
//
//        final Revisions<Integer, SClient> modifiedAfter = this.clientBO.findModifiedAfter(SClientServiceTest.client
//                                                                                                            .getId(), PAST_TIME);
//        Assert.assertTrue(String.format("Before %s are not empty", PAST_TIME), !modifiedAfter.getContent().isEmpty());
    }

    @Test
    public void test_O_findRevisionFuture() throws Exception {
//        final Revisions<Integer, SClient> modifiedBefore = this.clientBO.findModifiedBefore(SClientServiceTest.client
//                                                                                                              .getId(), FUTURE_TIME);
//        Assert.assertTrue(String.format("After %s are not empty", FUTURE_TIME), !modifiedBefore.getContent().isEmpty());
//
//        final Revisions<Integer, SClient> modifiedAfter = this.clientBO.findModifiedAfter(SClientServiceTest.client
//                                                                                                            .getId(), FUTURE_TIME);
//        Assert.assertTrue(String.format("After %s are not empty", FUTURE_TIME), modifiedAfter.getContent().isEmpty());
    }

    @Test
    public void test_U_countRevisions() throws Exception {
//        final Long revision = this.clientBO.countRevisions(SClientServiceTest.client.getId());
//        Assert.assertTrue(revision > 0);
    }

    @Test
    public void test_Y_DeleteClient() throws Exception {
//        this.clientBO.deleteOne(SClientServiceTest.client.getId());
    }

    @Test
    public void test_Z_DeleteMechanic() throws Exception {
//        this.mechanicBO.deleteOne(SClientServiceTest.mechanic.getId());
    }

}
