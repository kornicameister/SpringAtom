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

package org.agatom.springatom.webmvc.flows.tags;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.agatom.springatom.web.locale.SMessageSource;
import org.agatom.springatom.webmvc.flows.wizard.events.WizardDynamicEvent;
import org.agatom.springatom.webmvc.flows.wizard.events.WizardEvent;
import org.agatom.springatom.webmvc.flows.wizard.events.WizardEvents;
import org.json.JSONArray;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.util.ClassUtils;
import org.springframework.webflow.core.collection.MutableAttributeMap;
import org.springframework.webflow.definition.FlowDefinition;
import org.springframework.webflow.engine.Flow;
import org.springframework.webflow.engine.Transition;
import org.springframework.webflow.engine.TransitionSet;
import org.springframework.webflow.engine.TransitionableState;
import org.springframework.webflow.engine.support.DefaultTransitionCriteria;
import org.springframework.webflow.execution.RequestContextHolder;

import java.util.List;
import java.util.Locale;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class DynamicTransitionsTag
        extends PossibleTransitionsTag {

    private Flow flow;

    public void setFlow(final FlowDefinition flowDefinition) {
        Preconditions.checkArgument(ClassUtils
                .isAssignable(Flow.class, flowDefinition.getClass()), "Flow must be an instance of org.springframework.webflow.engine.Flow");
        this.flow = (Flow) flowDefinition;
    }

    @Override
    protected int doStartTagInternal() throws Exception {
        if (this.state instanceof TransitionableState) {

            final Locale locale = LocaleContextHolder.getLocale();
            final WizardEvents wizardEvents = (WizardEvents) this.getRequestContext().getWebApplicationContext().getBean("wizardEvents");
            final SMessageSource messageSource = this.getRequestContext().getWebApplicationContext().getBean(SMessageSource.class);

            final MutableAttributeMap<Object> holder = RequestContextHolder.getRequestContext().getFlowScope();

            final TransitionableState transitionableState = (TransitionableState) this.state;
            final TransitionSet set = transitionableState.getTransitionSet();

            final List<WizardDynamicEvent> css = Lists.newArrayListWithExpectedSize(set.size());

            for (final Transition ts : set) {
                final DefaultTransitionCriteria criteria = (DefaultTransitionCriteria) ts.getMatchingCriteria();
                final String criteriaAsString = criteria.toString();
                if (!wizardEvents.isWizardEvent(criteriaAsString)) {
                    css.add(this.entitle(
                            new WizardDynamicEvent(new WizardEvent().init(criteriaAsString))
                                    .setWizardId(holder.getString("wizardID"))
                                    .setStateId(this.state.getId()),
                            messageSource,
                            locale
                    ));
                }
            }

            this.pageContext.setAttribute(this.var, new JSONArray(css).toString());
        }
        return EVAL_BODY_INCLUDE;
    }

    private WizardDynamicEvent entitle(final WizardDynamicEvent wdad, final SMessageSource messageSource, final Locale locale) {
        return wdad.setLabelName(messageSource.getMessage(wdad, locale));
    }
}
