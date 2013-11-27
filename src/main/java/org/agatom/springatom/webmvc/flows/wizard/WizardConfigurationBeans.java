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

package org.agatom.springatom.webmvc.flows.wizard;

import org.agatom.springatom.webmvc.flows.wizard.events.WizardEvents;
import org.agatom.springatom.webmvc.flows.wizard.support.WizardRequiresStepsHolder;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Configuration(value = "WizardConfiguration")
public class WizardConfigurationBeans {

    @Bean(name = "WizardEvents", autowire = Autowire.BY_TYPE)
    public WizardEvents getEvents() {
        return new WizardEvents();
    }

    @Bean
    public WizardRequiresStepsHolder getRequiredStepsHolder() {
        return new WizardRequiresStepsHolder();
    }
}
