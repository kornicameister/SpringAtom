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

package org.agatom.springatom.web.infopages.link;

import com.google.common.base.Objects;
import com.google.common.collect.Maps;
import org.agatom.springatom.AbstractSpringTestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;

import java.util.Map;
import java.util.Set;

public class InfoPageLinkHelperImplTest
		extends AbstractSpringTestCase {

	private Map<InternalKey, String> uris       = Maps.newHashMap();
	@Autowired
	private InfoPageLinkHelper       linkHelper = null;

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();
		uris.put(new InternalKey("appointment", 1), "/app/cmp/ip/appointment/1");
		uris.put(new InternalKey("car", 1), "/app/cmp/ip/car/1");
		uris.put(new InternalKey("car-master", 1), "/app/cmp/ip/car-master/1");
		uris.put(new InternalKey("brand-model", 1), "/app/cmp/ip/brand-model/1");
	}

	@Test
	public void testGetInfoPageLink() throws Exception {
		final Set<InternalKey> internalKeys = this.uris.keySet();
		for (InternalKey internalKey : internalKeys) {
			final Link infoPageLink = this.linkHelper.getInfoPageLink(internalKey.path, internalKey.id);
			Assert.assertEquals(this.uris.get(internalKey), infoPageLink.getHref());
		}
	}

	/**
	 * Small utility class wrapping both path and id for infopage link
	 */
	private class InternalKey {
		private final String path;
		private final Long   id;

		private InternalKey(final String path, final int id) {
			this.path = path;
			this.id = (long) id;
		}

		@Override
		public int hashCode() {
			return Objects.hashCode(path, id);
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;

			InternalKey that = (InternalKey) o;

			return Objects.equal(this.path, that.path) &&
					Objects.equal(this.id, that.id);
		}
	}
}