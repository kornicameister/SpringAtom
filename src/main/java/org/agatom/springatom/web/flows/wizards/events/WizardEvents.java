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

package org.agatom.springatom.web.flows.wizards.events;

import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Nullable;
import java.util.List;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public final class WizardEvents {

    private static final String       NEXT_EVENT     = "next";
    private static final String       PREVIOUS_EVENT = "previous";
    private static final String       CANCEL_EVENT   = "cancel";
    private static final String       FINISH_EVENT   = "finish";
    private static final List<String> EVENTS         = Lists.newArrayList(NEXT_EVENT, PREVIOUS_EVENT, CANCEL_EVENT, FINISH_EVENT);

    public boolean isWizardEvent(final String eventId) {
        if (!StringUtils.hasText(eventId)) {
            return false;
        }
        final String anotherString = StringUtils.trimAllWhitespace(eventId);
        return FluentIterable
                .from(EVENTS)
                .filter(new Predicate<String>() {
                    @Override
                    public boolean apply(@Nullable final String input) {
                        assert input != null;
                        return input.equalsIgnoreCase(anotherString);
                    }
                })
                .first()
                .isPresent();
    }

    @Bean(name = "WizardNextEvent")
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public WizardEvent getNext() {
        return new WizardEvent().init(NEXT_EVENT);
    }

    @Bean(name = "WizardPreviousEvent")
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public WizardEvent getPrevious() {
        return new WizardEvent().init(PREVIOUS_EVENT);
    }

    @Bean(name = "WizardCancelEvent")
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public WizardEvent getCancel() {
        return new WizardEvent().init(CANCEL_EVENT);
    }

    @Bean(name = "WizardFinishEvent")
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public WizardEvent getFinish() {
        return new WizardEvent().init(FINISH_EVENT);
    }
}
