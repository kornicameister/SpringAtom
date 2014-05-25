/**************************************************************************************************
 * This file is part of [SpringAtom] Copyright [kornicameister@gmail.com][2014]                   *
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

package org.agatom.springatom.web.locale.impl;

import com.google.common.collect.Lists;
import org.agatom.springatom.AbstractSpringTestCase;
import org.agatom.springatom.server.model.beans.PersistentContactable;
import org.agatom.springatom.server.model.beans.PersistentObject;
import org.agatom.springatom.server.model.beans.PersistentVersionedObject;
import org.agatom.springatom.server.model.beans.activity.SActivity;
import org.agatom.springatom.server.model.beans.activity.SAssignedActivity;
import org.agatom.springatom.server.model.beans.appointment.QSAppointment;
import org.agatom.springatom.server.model.beans.appointment.SAppointment;
import org.agatom.springatom.server.model.beans.appointment.SAppointmentIssue;
import org.agatom.springatom.server.model.beans.appointment.SAppointmentTask;
import org.agatom.springatom.server.model.beans.calendar.SCalendar;
import org.agatom.springatom.server.model.beans.car.SCar;
import org.agatom.springatom.server.model.beans.car.SCarMaster;
import org.agatom.springatom.server.model.beans.car.embeddable.SCarMasterManufacturingData;
import org.agatom.springatom.server.model.beans.contact.SAbstractContact;
import org.agatom.springatom.server.model.beans.issue.SIssue;
import org.agatom.springatom.server.model.beans.notification.SAbstractNotification;
import org.agatom.springatom.server.model.beans.person.SPerson;
import org.agatom.springatom.server.model.beans.person.SPersonContact;
import org.agatom.springatom.server.model.beans.report.SReport;
import org.agatom.springatom.server.model.beans.report.SReportBooleanSetting;
import org.agatom.springatom.server.model.beans.report.SReportNumberSetting;
import org.agatom.springatom.server.model.beans.report.SReportStringSetting;
import org.agatom.springatom.server.model.beans.user.embeddable.SUserCredentials;
import org.agatom.springatom.server.model.beans.user.notification.SUserNotification;
import org.agatom.springatom.web.locale.SMessageSource;
import org.agatom.springatom.web.locale.beans.LocalizedClassAttribute;
import org.agatom.springatom.web.locale.beans.LocalizedClassModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.AbstractAuditable;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.util.ClassUtils;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * <small>Class is a part of <b>SpringAtom</b> and was created at 30.03.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class SMessageSourceImplTest
		extends AbstractSpringTestCase {

	private List<Class<?>> classList     = Lists.newArrayList();
	@Autowired
	private SMessageSource messageSource = null;

	@Before
	public void setUp() throws Exception {
		this.classList.add(SAppointment.class);
		this.classList.add(SAssignedActivity.class);
		this.classList.add(SAssignedActivity.class);
		this.classList.add(SActivity.class);
		this.classList.add(PersistentObject.class);
		this.classList.add(AbstractPersistable.class);
		this.classList.add(PersistentVersionedObject.class);
		this.classList.add(AbstractAuditable.class);
		this.classList.add(SAppointmentIssue.class);
		this.classList.add(SIssue.class);
		this.classList.add(SAppointmentTask.class);
		this.classList.add(SCalendar.class);
		this.classList.add(SCar.class);
		this.classList.add(SCarMaster.class);
		this.classList.add(SCarMasterManufacturingData.class);
		this.classList.add(SAbstractContact.class);
		this.classList.add(SAbstractNotification.class);
		this.classList.add(SPerson.class);
		this.classList.add(PersistentContactable.class);
		this.classList.add(SPersonContact.class);
		this.classList.add(SReport.class);
		this.classList.add(SReportBooleanSetting.class);
		this.classList.add(SReportNumberSetting.class);
		this.classList.add(SReportStringSetting.class);
		this.classList.add(SUserNotification.class);
		this.classList.add(SUserCredentials.class);
	}

	@Test
	public void testGetMessageLocalePL() throws Exception {
		for (final Class<?> clazz : this.classList) {
			final LocalizedClassModel<?> pl_pl = this.messageSource.getLocalizedClassModel(clazz, Locale.forLanguageTag("pl"));
			System.out.println(pl_pl);
			final Set<LocalizedClassAttribute> attributes = pl_pl.getAttributes();

			if (attributes == null) {
				continue;
			}

			final List<LocalizedClassAttribute> notFound = Lists.newLinkedList();

			for (LocalizedClassAttribute attribute : attributes) {
				if (!attribute.isFound()) {
					notFound.add(attribute);
				}
			}

			Assert.assertTrue(String.format("[%s] No localized values for attributes = %s", ClassUtils.getShortName(clazz), ObjectUtils.nullSafeToString(notFound)), notFound.isEmpty());
		}
	}

	@Test
	public void testGetMessageLocaleEN() throws Exception {
		for (final Class<?> clazz : this.classList) {
			final LocalizedClassModel<?> pl_pl = this.messageSource.getLocalizedClassModel(clazz, Locale.ENGLISH);
			System.out.println(pl_pl);
			final Set<LocalizedClassAttribute> attributes = pl_pl.getAttributes();

			if (attributes == null) {
				continue;
			}

			final List<LocalizedClassAttribute> notFound = Lists.newLinkedList();

			for (LocalizedClassAttribute attribute : attributes) {
				if (!attribute.isFound()) {
					notFound.add(attribute);
				}
			}

			Assert.assertTrue(String.format("[%s] No localized values for attributes = %s", ClassUtils.getShortName(clazz), ObjectUtils.nullSafeToString(notFound)), notFound.isEmpty());
		}
	}


	@Test
	public void testGetAttributes() throws Exception {
		final String[] attributes = new String[]{
				QSAppointment.sAppointment.begin.getMetadata().getName(),
				QSAppointment.sAppointment.end.getMetadata().getName(),
				QSAppointment.sAppointment.tasks.getMetadata().getName(),
				QSAppointment.sAppointment.beginTs.getMetadata().getName(),
				QSAppointment.sAppointment.endTs.getMetadata().getName(),
				QSAppointment.sAppointment.assigned.getMetadata().getName(),
				QSAppointment.sAppointment.car.getMetadata().getName()
		};
		for (final String attrName : attributes) {
			final LocalizedClassAttribute pl_pl = this.messageSource.getLocalizedClassAttribute(SAppointment.class, attrName, Locale.forLanguageTag("pl"));
			System.out.println(pl_pl);
			Assert.assertTrue(pl_pl.isFound());
		}
	}

}
