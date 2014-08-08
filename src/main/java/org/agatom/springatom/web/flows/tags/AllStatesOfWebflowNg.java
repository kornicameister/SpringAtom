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

package org.agatom.springatom.web.flows.tags;

import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.FluentIterable;
import org.agatom.springatom.web.flows.wizards.support.WizardRequiresStepsHolder;
import org.agatom.springatom.web.locale.SMessageSource;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.webflow.engine.Flow;
import org.springframework.webflow.execution.RequestContextHolder;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.servlet.jsp.JspWriter;
import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * <small>Class is a part of <b>SpringAtom</b> and was created at 2014-08-07</small>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
public class AllStatesOfWebflowNg
        extends AllStatesOfWebflow {
    private static final long   serialVersionUID = 2427907268721229079L;
    private static final Logger LOGGER           = Logger.getLogger(AllStatesOfWebflowNg.class);
    private              String wizardId         = null;
    private              String var              = null;

    public String getVar() {
        return this.var;
    }

    public void setVar(final String var) {
        this.var = var;
    }

    public String getWizardId() {
        return this.wizardId;
    }

    public void setWizardId(final String wizardId) {
        this.wizardId = wizardId;
    }

    @Override
    protected int doStartTagInternal() throws Exception {
        Preconditions.checkArgument(this.flow != null, "FlowDefinition can not be null");
        Preconditions.checkArgument(ClassUtils.isAssignable(Flow.class, this.flow.getClass()), "FlowDefinition must be an instance of Flow");

        final List<String> states = this.getStates((Flow) this.flow);
        final SMessageSource messageSource = this.getMessageSource();
        final String localWizId = this.wizardId;
        final Locale locale = LocaleContextHolder.getLocale();
        final WizardRequiresStepsHolder requiredSteps = RequestContextHolder.getRequestContext()
                .getFlowScope()
                .get("requiredSteps", WizardRequiresStepsHolder.class);

        LOGGER.debug(String.format("Creating ngModel out of wizardId=%s, locale=%s, flowId=%s", localWizId, locale, this.flow.getId()));

        final Set<NgFlowModel> models = FluentIterable
                .from(states)
                .transform(new Function<String, NgFlowModel>() {

                    private short index = 0;

                    @Nullable
                    @Override
                    public NgFlowModel apply(@Nullable final String input) {
                        if (input == null) {
                            return null;
                        }

                        final NgFlowModel ngFlowModel = new NgFlowModel();

                        ngFlowModel.setIndex(index++);
                        ngFlowModel.setStateId(input);
                        ngFlowModel.setLabel(messageSource.getMessage(String.format("wizard.%s.%s.desc", localWizId, input), locale));
                        ngFlowModel.setRequired(requiredSteps.has(input));

                        return ngFlowModel;
                    }
                })
                .toSortedSet(new Comparator<NgFlowModel>() {
                    @Override
                    public int compare(@Nonnull final NgFlowModel o1, @Nonnull final NgFlowModel o2) {
                        return Short.compare(o1.getIndex(), o2.getIndex());
                    }
                });

        LOGGER.debug(String.format("Created %d ngModels", models.size()));

        if (StringUtils.hasText(this.var)) {
            LOGGER.trace(String.format("var is set, setting ngModel in pageContext, var=%s", this.var));
            this.pageContext.setAttribute(this.var, models);
        } else {
            LOGGER.trace("var is not set, returning ngModel as JSONArray to out");
            final JspWriter out = this.pageContext.getOut();
            out.write(new JSONArray(models).toString());
        }

        return EVAL_BODY_INCLUDE;
    }

    protected SMessageSource getMessageSource() {
        final WebApplicationContext context = this.getRequestContext().getWebApplicationContext();
        return context.getBean(SMessageSource.class);
    }

    private static class NgFlowModel
            implements Serializable {
        private static final long    serialVersionUID = -7334089114056765030L;
        private              short   index            = -1;
        private              boolean required         = false;
        private              String  stateId          = null;
        private              String  label            = null;

        public short getIndex() {
            return index;
        }

        public NgFlowModel setIndex(final short index) {
            this.index = index;
            return this;
        }

        public String getStateId() {
            return stateId;
        }

        public NgFlowModel setStateId(final String stateId) {
            this.stateId = stateId;
            return this;
        }

        public String getLabel() {
            return label;
        }

        public NgFlowModel setLabel(final String label) {
            this.label = label;
            return this;
        }

        public boolean isRequired() {
            return required;
        }

        public NgFlowModel setRequired(final boolean required) {
            this.required = required;
            return this;
        }

        @Override
        public String toString() {
            return Objects.toStringHelper(this)
                    .add("index", index)
                    .add("stateId", stateId)
                    .add("label", label)
                    .toString();
        }
    }
}
