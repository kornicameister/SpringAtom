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

package org.agatom.springatom.web.component.infopages.provider;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.agatom.springatom.AbstractSpringTestCase;
import org.agatom.springatom.server.model.beans.appointment.SAppointment;
import org.agatom.springatom.web.component.infopages.provider.structure.InfoPage;
import org.agatom.springatom.web.component.infopages.provider.structure.InfoPageAttribute;
import org.agatom.springatom.web.component.infopages.provider.structure.InfoPagePanel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Persistable;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.Set;

/**
 * <small>Class is a part of <b>SpringAtom</b> and was created at 17.05.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JSONInfoPageProviderServiceTest
		extends AbstractSpringTestCase {

	@Autowired
	private InfoPageProviderService                      service      = null;
	private Map<Class<? extends Persistable<?>>, String> filePathTest = Maps.newHashMap();

	@Before
	public void setUp() throws Exception {
		this.filePathTest.put(SAppointment.class, "org/agatom/springatom/server/model/beans/appointment/sappointment.json");
	}

	@Test
	public void test_0_GetFilePath() throws Exception {
		for (final Class<? extends Persistable<?>> clazz : this.filePathTest.keySet()) {
			final String filePath = this.service.getFilePath(clazz);
			Assert.assertNotNull("filePath null...", filePath);
			Assert.assertFalse("filePath empty...", filePath.isEmpty());
			Assert.assertTrue("filePath wrong...", filePath.contains(this.filePathTest.get(clazz)));
		}
	}

	@Test
	public void test_1_GetInfoPage() throws Exception {
		for (final Class<? extends Persistable<?>> clazz : this.filePathTest.keySet()) {
			final InfoPage page = this.service.getInfoPage(clazz);
			Assert.assertNotNull("page is null", page);
			Assert.assertNotNull("page should have content set", page.getContent());
			Assert.assertEquals("page should have tree panels defined", page.getSize(), 3);
		}
	}

	@Test
	public void test_3_TestPanelsAttributes() throws Exception {
		for (final Class<? extends Persistable<?>> clazz : this.filePathTest.keySet()) {
			final InfoPage page = this.service.getInfoPage(clazz);
			for (final InfoPagePanel panel : page) {
				Assert.assertTrue(StringUtils.hasText(panel.getId()));
				Assert.assertNotNull(panel.getTitle());
				int lastPos = -1;
				Set<InfoPageAttribute> attributes = panel.getAttributes();
				Assert.assertNotNull("Panel must have attributes", attributes != null && !attributes.isEmpty());
				attributes = Sets.newTreeSet(attributes);
				for (final InfoPageAttribute attribute : attributes) {
					Assert.assertTrue(StringUtils.hasText(attribute.getPath()));
					Assert.assertTrue(attribute.getPosition() > (lastPos++));
				}
			}

		}
	}
}
