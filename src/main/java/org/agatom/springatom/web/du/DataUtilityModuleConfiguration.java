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

package org.agatom.springatom.web.du;

import org.agatom.springatom.core.module.AbstractModuleConfiguration;
import org.agatom.springatom.web.component.config.ComponentBuilderModuleConfiguration;
import org.agatom.springatom.web.du.annotation.DataUtility;
import org.agatom.springatom.web.du.utility.appointment.AppointmentTaskListGenericConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Configuration(value = DataUtilityModuleConfiguration.MODULE_NAME)
@Description(value = "DataUtilityModuleConfiguration provides beans instances for easing with resolving and using DataUtilities as enhanced Converters")
@ComponentScan(
        nameGenerator = ComponentBuilderModuleConfiguration.NameGen.class,
        basePackages = {
                "org.agatom.springatom"
        },
        useDefaultFilters = false,
        includeFilters = {
                @ComponentScan.Filter(value = DataUtility.class, type = FilterType.ANNOTATION)
        }
)
@DependsOn(value = "springAtomConversionService")
public class DataUtilityModuleConfiguration
        extends AbstractModuleConfiguration {
    protected static final String MODULE_NAME = "duModuleConfiguration";

    @Autowired
    private FormattingConversionService conversionService;

    @PostConstruct
    private void init() {
        Assert.notNull(this.conversionService);
        this.conversionService.addConverter(new AppointmentTaskListGenericConverter().setConversionService(this.conversionService));
    }

}
