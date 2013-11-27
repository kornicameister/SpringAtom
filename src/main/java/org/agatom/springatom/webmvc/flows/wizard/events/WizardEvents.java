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

package org.agatom.springatom.webmvc.flows.wizard.events;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public final class WizardEvents {

    @Bean(name = "WizardNextEvent")
    public WizardEvent getNext() {
        return new WizardEvent().init("next");
    }

    @Bean(name = "WizardPreviousEvent")
    public WizardEvent getPrevious() {
        return new WizardEvent().init("previous");
    }

    @Bean(name = "WizardCancelEvent")
    public WizardEvent getCancel() {
        return new WizardEvent().init("cancel");
    }

    @Bean(name = "WizardFinishEvent")
    public WizardEvent getFinish() {
        return new WizardEvent().init("finish");
    }
}
