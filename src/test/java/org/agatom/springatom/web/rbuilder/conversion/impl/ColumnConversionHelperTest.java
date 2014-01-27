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

package org.agatom.springatom.web.rbuilder.conversion.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.agatom.springatom.AbstractSpringTestCase;
import org.agatom.springatom.server.model.beans.appointment.SAppointment;
import org.agatom.springatom.server.model.beans.appointment.SAppointmentTask;
import org.agatom.springatom.web.rbuilder.bean.RBuilderColumn;
import org.agatom.springatom.web.rbuilder.conversion.ColumnTypeConversionBranch;
import org.agatom.springatom.web.rbuilder.conversion.ConversionHelper;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class ColumnConversionHelperTest
        extends AbstractSpringTestCase {

    @Autowired
    private ConversionHelper<RBuilderColumn>              conversionHelper;
    private Set<Class<?>>                                 types;
    private List<RBuilderColumn>                          columns;
    private HashMap<Class<?>, ColumnTypeConversionBranch> expectedColumnTypes;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        this.initColumnsForTest();
        this.initExpectedTypes();
        this.initColumns();
    }

    private void initColumns() {
        this.columns = Lists.newLinkedList();
        for (final Class<?> clazz : this.types) {
            this.columns.add(new RBuilderColumn().setColumnClass(clazz));
        }
    }

    private void initExpectedTypes() {
        this.expectedColumnTypes = Maps.newHashMap();
        this.expectedColumnTypes.put(DateTime.class, ColumnTypeConversionBranch.DATE);
        this.expectedColumnTypes.put(String.class, ColumnTypeConversionBranch.BASIC);
        this.expectedColumnTypes.put(Long.class, ColumnTypeConversionBranch.BASIC);
        this.expectedColumnTypes.put(Integer.class, ColumnTypeConversionBranch.BASIC);
        this.expectedColumnTypes.put(Double.class, ColumnTypeConversionBranch.BASIC);
        this.expectedColumnTypes.put(Enum.class, ColumnTypeConversionBranch.BASIC);
        this.expectedColumnTypes.put(SAppointment.class, ColumnTypeConversionBranch.ENTITY);
        this.expectedColumnTypes.put(new ArrayList<SAppointmentTask>().getClass(), ColumnTypeConversionBranch.COLLECTION);
    }

    private void initColumnsForTest() {
        this.types = Sets.newHashSet();
        this.types.add(DateTime.class);
        this.types.add(String.class);
        this.types.add(Long.class);
        this.types.add(Integer.class);
        this.types.add(Double.class);
        this.types.add(Enum.class);
        this.types.add(SAppointment.class);
        this.types.add(new ArrayList<SAppointmentTask>().getClass());
    }

    @Test
    public void testGetConvertiblePairs() throws Exception {
        for (final RBuilderColumn column : this.columns) {
            {
                System.out.println("Before 1 ::" + column.getColumnClass());
                this.conversionHelper.getConvertiblePairs(column);
            }
            {
                System.out.println("Before 2 :: " + column.getColumnClass());
                this.conversionHelper.getConvertiblePairs(column);
            }
            {
                System.out.println("Before 3 :: " + column.getColumnClass());
                this.conversionHelper.getConvertiblePairs(column);
            }
            System.out.print("After" + column.getColumnClass());
        }
    }

    @Test
    public void testGetPossibleColumnType() throws Exception {
        for (final Class<?> clazz : this.types) {
            final boolean condition = this.conversionHelper.getPossibleColumnType(clazz).equals(this.expectedColumnTypes.get(clazz));
            Assert.assertTrue(String.format("%s is not %s", clazz, this.expectedColumnTypes.get(clazz)), condition);
        }
    }
}
