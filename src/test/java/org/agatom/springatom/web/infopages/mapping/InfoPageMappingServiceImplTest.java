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

package org.agatom.springatom.web.infopages.mapping;

import com.google.common.collect.Maps;
import org.agatom.springatom.AbstractSpringTestCase;
import org.agatom.springatom.server.model.beans.appointment.SAppointment;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Persistable;

import java.util.Map;

/**
 * <small>Class is a part of <b>SpringAtom</b> and was created at 18.05.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class InfoPageMappingServiceImplTest
		extends AbstractSpringTestCase {
	private Map<String, Hit>       relToClassMap = Maps.newHashMap();
	@Autowired
	private InfoPageMappingService service       = null;

	@Before
	public void setUp() throws Exception {
		this.relToClassMap.put("appointment", new Hit(true, SAppointment.class));
	}

	@Test
	public void testHasInfoPageRel() throws Exception {
		for (final String key : this.relToClassMap.keySet()) {

			final boolean hasPage = this.service.hasInfoPage(key);
			final Hit hit = this.relToClassMap.get(key);

			Assert.assertSame(hit.hasPage, hasPage);
		}
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testHasInfoPageClass() throws Exception {
		for (final String key : this.relToClassMap.keySet()) {

			final Hit hit = this.relToClassMap.get(key);

			final boolean hasPage = this.service.hasInfoPage((Class<? extends Persistable<?>>) hit.pageClazz);

			Assert.assertSame(hit.hasPage, hasPage);
		}
	}

	private class Hit {
		boolean  hasPage   = false;
		Class<?> pageClazz = null;

		private Hit(final boolean hasPage, final Class<?> pageClazz) {
			this.hasPage = hasPage;
			this.pageClazz = pageClazz;
		}
	}
}
