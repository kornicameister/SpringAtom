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

package org.agatom.springatom.server.service.vinNumber.model;

import com.google.common.collect.Maps;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

/**
 * <small>Class is a part of <b>SpringAtom</b> and was created at 12.04.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class VinNumberTest {

	protected static Map<String, ExpectedResult> MAP_OF_VINS = Maps.newHashMap();

	static {
		MAP_OF_VINS.put("1FAFP42X6YF159627", ExpectedResult.newExpectedResult("1FA", "FP42X6", "YF159627"));
		MAP_OF_VINS.put("JHLRD1763WC043592", ExpectedResult.newExpectedResult("JHL", "RD1763", "WC043592"));
		MAP_OF_VINS.put("1FTHX26H2VEC43620", ExpectedResult.newExpectedResult("1FT", "HX26H2", "VEC43620"));
		MAP_OF_VINS.put("JF1CX3355SH100591", ExpectedResult.newExpectedResult("JF1", "CX3355", "SH100591"));
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGetWmi() throws Exception {
		for (final String vin : MAP_OF_VINS.keySet()) {
			final VinNumber vinNumber = VinNumber.newVinNumber(vin);
			Assert.assertEquals("WMI different", MAP_OF_VINS.get(vin).wmi, vinNumber.getWmi());
		}
	}

	@Test
	public void testGetVds() throws Exception {
		for (final String vin : MAP_OF_VINS.keySet()) {
			final VinNumber vinNumber = VinNumber.newVinNumber(vin);
			Assert.assertEquals("VDS different", MAP_OF_VINS.get(vin).vds, vinNumber.getVds());
		}
	}

	@Test
	public void testGetVis() throws Exception {
		for (final String vin : MAP_OF_VINS.keySet()) {
			final VinNumber vinNumber = VinNumber.newVinNumber(vin);
			Assert.assertEquals("VIS different", MAP_OF_VINS.get(vin).vis, vinNumber.getVis());
		}
	}

	@Test
	public void testGetElement() throws Exception {
		for (final String vin : MAP_OF_VINS.keySet()) {
			final VinNumber vinNumber = VinNumber.newVinNumber(vin);
			Assert.assertEquals("WMI different", MAP_OF_VINS.get(vin).wmi, vinNumber.getElement(VinNumberElement.WMI));
			Assert.assertEquals("VDS different", MAP_OF_VINS.get(vin).vds, vinNumber.getElement(VinNumberElement.VDS));
			Assert.assertEquals("VDS different", MAP_OF_VINS.get(vin).vis, vinNumber.getElement(VinNumberElement.VIS));
		}
	}

	private static class ExpectedResult {
		String wmi;
		String vds;
		String vis;

		static ExpectedResult newExpectedResult(final String wmi, final String vds, final String vis) {
			final ExpectedResult res = new ExpectedResult();
			res.wmi = wmi;
			res.vds = vds;
			res.vis = vis;
			return res;
		}
	}
}
