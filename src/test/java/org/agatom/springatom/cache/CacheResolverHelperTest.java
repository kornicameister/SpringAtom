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

package org.agatom.springatom.cache;

import org.agatom.springatom.AbstractSpringTestCase;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */

@FixMethodOrder
public class CacheResolverHelperTest extends AbstractSpringTestCase {

    @Autowired
    private CacheResolverHelper cacheResolverHelper;

    @Test
    public void test_1_NewInstance() throws Exception {
        Assert.assertNotNull("CacheResolverHelper is null", this.cacheResolverHelper);
    }

    @Test
    public void test_2_GetCachesForName() throws Exception {
        String[] names = {
                "org.agatom.springatom.jpa.impl.SMetaDataRepository",
                "org.agatom.springatom.jpa.impl.SMechanicRepository"
        };
        for (String name : names) {
            Assert.assertNotNull(String.format("No cache for %s", name), this.cacheResolverHelper.getCachesForName(name)
                    .getDao());
        }
    }
}
