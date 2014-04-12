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
import com.neovisionaries.i18n.CountryCode;
import org.agatom.springatom.AbstractSpringTestCase;
import org.agatom.springatom.server.service.vinNumber.model.VinNumber;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * <small>Class is a part of <b>SpringAtom</b> and was created at 12.04.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class WMIManufacturedInResolverTest
		extends AbstractSpringTestCase {

	protected static Map<String, CountryCode> MAP_OF_VINS = Maps.newHashMap();

	@Autowired
	private WMIManufacturedInResolver resolver = null;

	static {
		MAP_OF_VINS.put("1FAFP42X6YF159627", CountryCode.US);
		MAP_OF_VINS.put("JHLRD1763WC043592", CountryCode.JP);
		MAP_OF_VINS.put("1FTHX26H2VEC43620", CountryCode.US);
		MAP_OF_VINS.put("JF1CX3355SH100591", CountryCode.JP);
		MAP_OF_VINS.put("KNAFE122655081495", CountryCode.KR);
		MAP_OF_VINS.put("1FTDF0826VKA27897", CountryCode.US);
		MAP_OF_VINS.put("WBANW13578CZ80903", CountryCode.DE);
		MAP_OF_VINS.put("2C4GP54L54R520735", CountryCode.CA);
		MAP_OF_VINS.put("YV1MC67228J049381", CountryCode.SE);
		MAP_OF_VINS.put("5N1AT2MV4EC800671", CountryCode.US);
	}

	@Test
	public void testGetCountryCode() throws Exception {
		for (final String vin : MAP_OF_VINS.keySet()) {
			final VinNumber vinNumber = VinNumber.newVinNumber(vin);
			final CountryCode countryCode = this.resolver.getCountryCode(vinNumber.getWmi());
			Assert.assertEquals(String.format("%s => Country codes don't match", vin), MAP_OF_VINS.get(vin), countryCode);
		}
	}
}
