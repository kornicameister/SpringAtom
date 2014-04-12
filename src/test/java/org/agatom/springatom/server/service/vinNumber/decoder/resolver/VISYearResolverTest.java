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

package org.agatom.springatom.server.service.vinNumber.decoder.resolver;

import com.google.common.collect.Maps;
import org.agatom.springatom.AbstractSpringTestCase;
import org.agatom.springatom.server.service.vinNumber.model.VinNumber;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * <small>Class is a part of <b>SpringAtom</b> and was created at 12.04.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class VISYearResolverTest
		extends AbstractSpringTestCase {

	protected static Map<String, Integer> MAP_OF_VINS = Maps.newHashMap();

	static {
		MAP_OF_VINS.put("1FAFP42X6YF159627", 2000);
		MAP_OF_VINS.put("JHLRD1763WC043592", 1998);
		MAP_OF_VINS.put("1FTHX26H2VEC43620", 1997);
		MAP_OF_VINS.put("JF1CX3355SH100591", 1995);
		MAP_OF_VINS.put("5N1AT2MV4EC800671", 2014);
	}

	@Autowired
	private VISYearResolver yearResolver = null;

	@Test
	public void testGetYear() throws Exception {
		for (final String vin : MAP_OF_VINS.keySet()) {
			final VinNumber vinNumber = VinNumber.newVinNumber(vin);
			final List<Integer> year = this.yearResolver.getYear(vinNumber.getVis());
			final Integer integer = MAP_OF_VINS.get(vin);
			Assert.assertTrue(String.format("%d not in %s", integer, year), year.contains(integer));
		}
	}
}
