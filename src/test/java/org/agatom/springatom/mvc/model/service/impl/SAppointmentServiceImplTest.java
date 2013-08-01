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

package org.agatom.springatom.mvc.model.service.impl;

import org.agatom.springatom.AbstractCarTestCase;
import org.agatom.springatom.model.beans.appointment.SAppointment;
import org.agatom.springatom.model.beans.car.SCar;
import org.agatom.springatom.model.beans.person.embeddable.SPersonalInformation;
import org.agatom.springatom.model.beans.person.mechanic.SMechanic;
import org.agatom.springatom.model.dto.SAppointmentTaskDTO;
import org.agatom.springatom.model.types.meta.SMetaDataEnum;
import org.agatom.springatom.mvc.model.service.SAppointmentService;
import org.agatom.springatom.mvc.model.service.SMechanicService;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.ReadableInterval;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintViolationException;
import java.util.*;

import static org.hamcrest.core.Is.is;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */

@FixMethodOrder(value = MethodSorters.NAME_ASCENDING)
public class SAppointmentServiceImplTest
        extends AbstractCarTestCase {
    public static final  int                        INT        = 10;
    private static final ArrayList<MockAppointment> MOCKED_APP = new ArrayList<>();
    private static SMechanic MECHANIC;
    @Autowired
    SAppointmentService appointmentService;
    @Autowired
    SMechanicService    mechanicService;

    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();
        if (SAppointmentServiceImplTest.MECHANIC == null) {
            SPersonalInformation personalInformation = new SPersonalInformation();
            personalInformation.setFirstName("John");
            personalInformation.setLastName("Doe");

            SMechanic sMechanic = new SMechanic();
            sMechanic.setInformation(personalInformation);
            sMechanic.setPrimaryMail("john.doe@gmail.com");

            SMechanic newMechanic = this.mechanicService.save(sMechanic);


            Assert.assertNotNull("newMechanic is null", newMechanic);
            Assert.assertNotNull("newMechanic#id is null", newMechanic.getId());
            SAppointmentServiceImplTest.MECHANIC = newMechanic;
        }
        if (SAppointmentServiceImplTest.MOCKED_APP.isEmpty()) {
            final Map<Long, SCar> map = new HashMap<>();
            final List<SCar> list = new ArrayList<>();

            int it = 0;
            for (final MockCar mockedCar : MOCK_CARS) {
                final String licencePlate = String.format("E%dKRZ", it++);
                final String vinNumber = String.valueOf(mockedCar.hashCode());
                final Long ownerId = C_1.getId();
                list.add(this.carService
                        .newCar(mockedCar.mockMaster.brand, mockedCar.mockMaster.model, licencePlate, vinNumber, ownerId));
            }

            Assert.assertTrue(list.size() == MOCKED_MASTERS.size());
            for (final SCar sCar : list) {
                Assert.assertNotNull(sCar);
                Assert.assertFalse(sCar.isNew());
                Assert.assertNotNull(sCar.getId());
                map.put(sCar.getId(), sCar);
            }

            SCarServiceImplTest.MOCKED_CARS = map;
        }
        if (SAppointmentServiceImplTest.MOCKED_APP.isEmpty()) {
            for (final Long mockCarId : MOCKED_CARS.keySet()) {
                final List<SAppointmentTaskDTO> tasks = new ArrayList<>(20);
                int it = 1;
                for (int i = 0 ; i < 20 ; i++) {
                    tasks.add(new SAppointmentTaskDTO(
                            SMetaDataEnum.SAT_NORMAL,
                            String.format("66666666666666_Normal_%d", new Random().nextInt(it++))));
                    tasks.add(new SAppointmentTaskDTO(
                            SMetaDataEnum.SAT_OIL_CHANGE,
                            String.format("66666666666666_OilChange_%d", new Random().nextInt(it++))));
                    tasks.add(new SAppointmentTaskDTO(
                            SMetaDataEnum.SAT_REPAIR,
                            String.format("6666666666666666_Repair_%d", new Random().nextInt(it++))));
                }

                MOCKED_APP.add(new MockAppointment(
                        new Interval(DateTime.now(), DateTime.now().plusHours(new Random().nextInt(3))),
                        mockCarId,
                        MECHANIC.getId(),
                        tasks.toArray(new SAppointmentTaskDTO[20])
                ));
            }
        }
    }

    @Test(expected = ConstraintViolationException.class)
    public void test_A_NewAppointmentWithoutTaskExpectingException() throws Exception {
        for (final MockAppointment mockAppointment : MOCKED_APP) {
            this.appointmentService.newAppointment(
                    mockAppointment.interval,
                    mockAppointment.carId,
                    mockAppointment.assigneeId,
                    mockAppointment.assigneeId
            );
        }
    }

    @Test
    public void test_B_NewAppointmentWithTasks() throws Exception {
        final List<SAppointment> tmpApps = new ArrayList<>();
        for (final MockAppointment mockAppointment : MOCKED_APP) {
            final SAppointment appointment = this.appointmentService.newAppointment(
                    mockAppointment.interval,
                    mockAppointment.carId,
                    mockAppointment.assigneeId,
                    mockAppointment.assigneeId,
                    mockAppointment.tasks
            );
            Assert.assertNotNull(appointment);
            Assert.assertFalse(appointment.isNew());
            Assert.assertNotNull(appointment.getId());
            tmpApps.add(appointment);
        }
        Assert.assertThat(tmpApps.size(), is(MOCKED_APP.size()));
    }

    protected class MockAppointment {
        final ReadableInterval      interval;
        final Long                  carId;
        final Long                  assigneeId;
        final SAppointmentTaskDTO[] tasks;

        public MockAppointment(final ReadableInterval interval,
                               final Long carId,
                               final Long assigneeId,
                               final SAppointmentTaskDTO... tasks) {
            this.interval = interval;
            this.carId = carId;
            this.assigneeId = assigneeId;
            this.tasks = tasks;
        }
    }
}
