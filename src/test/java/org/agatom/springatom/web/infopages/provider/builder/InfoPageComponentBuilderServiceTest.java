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

package org.agatom.springatom.web.infopages.provider.builder;

import com.google.common.collect.Lists;
import org.agatom.springatom.AbstractSpringTestCase;
import org.agatom.springatom.server.model.beans.appointment.SAppointment;
import org.agatom.springatom.web.infopages.component.elements.InfoPageComponent;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Persistable;
import org.springframework.test.annotation.Repeat;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * <small>Class is a part of <b>SpringAtom</b> and was created at 25.05.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class InfoPageComponentBuilderServiceTest
		extends AbstractSpringTestCase {
	private List<Class<?>>                  classList      = Lists.newArrayList();
	@Autowired
	private InfoPageComponentBuilderService builderService = null;

	@Before
	public void setUp() throws Exception {
		this.classList.add(SAppointment.class);
	}

	@Test
	@Repeat(10)
	@SuppressWarnings("unchecked")
	public void testBuildInfoPage() throws Exception {
		for (Class<?> aClass : this.classList) {
			final InfoPageComponent cmp = this.builderService.buildInfoPage((Class<? extends Persistable<?>>) aClass);
			Assert.assertNotNull("InfoPageComponent is null", cmp);
			Assert.assertEquals(SAppointment.class, cmp.getDomain());
			Assert.assertEquals("appointment_ip_v_1", cmp.getId());
			Assert.assertFalse("InfoPageComponent should have content (panels) defined", CollectionUtils.isEmpty(cmp.getContent()));
			Assert.assertEquals(3, cmp.getSize());
		}
	}
}
