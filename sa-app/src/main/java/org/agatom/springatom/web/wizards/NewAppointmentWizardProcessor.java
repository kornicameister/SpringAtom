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

package org.agatom.springatom.web.wizards;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.agatom.springatom.cmp.component.ComponentCompilationException;
import org.agatom.springatom.cmp.component.select.SelectComponent;
import org.agatom.springatom.cmp.component.select.factory.SelectComponentFactory;
import org.agatom.springatom.cmp.locale.SMessageSource;
import org.agatom.springatom.cmp.wizards.StepHelper;
import org.agatom.springatom.cmp.wizards.Wizard;
import org.agatom.springatom.cmp.wizards.core.AbstractStepHelper;
import org.agatom.springatom.cmp.wizards.core.CreateObjectWizardProcessor;
import org.agatom.springatom.cmp.wizards.data.WizardDescriptor;
import org.agatom.springatom.cmp.wizards.data.WizardStepDescriptor;
import org.agatom.springatom.cmp.wizards.data.result.FeedbackMessage;
import org.agatom.springatom.cmp.wizards.data.result.WizardResult;
import org.agatom.springatom.data.hades.model.appointment.NAppointment;
import org.agatom.springatom.data.hades.model.appointment.NAppointmentTask;
import org.agatom.springatom.data.hades.model.appointment.QNAppointment;
import org.agatom.springatom.data.hades.model.appointment.QNAppointmentTask;
import org.agatom.springatom.data.hades.model.car.NCar;
import org.agatom.springatom.data.hades.model.user.NUser;
import org.agatom.springatom.data.hades.service.NAppointmentService;
import org.agatom.springatom.data.hades.service.NCarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.PropertyValuesEditor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.DataBinder;

import javax.annotation.Nullable;
import java.util.*;

/**
 * <small>Class is a part of <b>SpringAtom</b> and was created at 2014-08-17</small>
 *
 * @author trebskit
 * @version 0.0.2
 * @since 0.0.1
 */
@Wizard(value = NewAppointmentWizardProcessor.WIZARD_ID, validate = true)
class NewAppointmentWizardProcessor
        extends CreateObjectWizardProcessor<NAppointment> {
    protected static final String                 WIZARD_ID              = "newAppointment";
    private static final   Logger                 LOGGER                 = LoggerFactory.getLogger(NewAppointmentWizardProcessor.class);
    private static final   String                 FORM_OBJECT_NAME       = "appointment";
    private static final   String                 CARS                   = "cars";
    private static final   String                 ASSIGNEES              = "assignees";
    private static final   String                 REPORTERS              = "reporters";
    private static final   String                 APPOINTMENT_TASK_TYPES = "APPOINTMENT_TASK_TYPES";
    private static final   Comparator<Long>       KEY_ORDER              = new Comparator<Long>() {
        @Override
        public int compare(final Long o1, final Long o2) {
            return o1.compareTo(o2);
        }
    };
    private final          StepsDefinitionHolder  steps                  = new StepsDefinitionHolder();
    @Autowired
    private                SMessageSource         messageSource          = null;
    @Autowired
    private                NAppointmentService    appointmentService     = null;
    @Autowired
    private                NCarService            carService             = null;
    @Autowired
    private                SelectComponentFactory selectComponentFactory = null;

    @Override
    public String getContextObjectName() {
        return FORM_OBJECT_NAME;
    }

    @Override
    protected StepHelper[] getStepHelpers() {
        return new StepHelper[]{
                this.steps.DEFINITION,
                this.steps.TASKS,
                this.steps.COMMENT
        };
    }

    @Override
    protected WizardDescriptor initializeWizard(final Locale locale) {
        LOGGER.debug(String.format("initializeWizard(locale=%s)", locale));

        final WizardDescriptor descriptor = new WizardDescriptor();

        descriptor.setLabel(this.messageSource.getMessage("wizard.NewAppointmentWizard.title", locale));
        descriptor.addStep(this.steps.DEFINITION.getStepDescriptor(locale));
        descriptor.addStep(this.steps.TASKS.getStepDescriptor(locale));
        descriptor.addStep(this.steps.COMMENT.getStepDescriptor(locale));

        return descriptor;
    }

    @Override
    protected WizardResult submitWizard(NAppointment contextObject, final ModelMap stepData, final Locale locale) throws Exception {
        final WizardResult result = new WizardResult()
                .setWizardId("newAppointment");

        contextObject = this.setComment(contextObject, stepData);
        contextObject = this.appointmentService.save(contextObject);

        result.setOid(this.getOID(contextObject));
        result.addFeedbackMessage(
                FeedbackMessage
                        .newInfo()
                        .setMessage(
                                this.messageSource.getMessage(
                                        "sa.msg.objectCreated",
                                        new Object[]{this.messageSource.getMessage("NAppointment", locale)},
                                        locale
                                )
                        )
        );

        return result;
    }

    private NAppointment setComment(final NAppointment contextObject, final Map<String, Object> stepData) {
        final String comment = (String) stepData.get(getPropertyName(QNAppointment.nAppointment.comment));
        return StringUtils.hasText(comment) ? (NAppointment) contextObject.setComment(comment) : contextObject;
    }

    /**
     * Lists of steps for the appointment
     */
    private class StepsDefinitionHolder {
        final StepHelper TASKS = new AbstractStepHelper("tasks") {

            private final Logger logger = LoggerFactory.getLogger(this.getClass());

            @Override
            public WizardStepDescriptor getStepDescriptor(final Locale locale) {
                return (WizardStepDescriptor) super.getStepDescriptor(locale)
                        .setRequired(true)
                        .addLabel("tasks", messageSource.getMessage("NAppointment.tasks", locale))
                        .addLabel("tasks.task", messageSource.getMessage("NAppointment.task.task", locale))
                        .addLabel("tasks.type", messageSource.getMessage("NAppointment.task.type", locale))
                        .setLabel(messageSource.getMessage("wizard.NewAppointmentWizard.step2.desc", locale));
            }

            @Override
            public ModelMap initialize(final Locale locale) throws ComponentCompilationException {
                final ModelMap map = new ModelMap();
                map.addAttribute("taskTypes", this.getTaskTypes(locale));
                return map;
            }

            @Override
            public void initializeBinder(final DataBinder binder) {

                final String propertyName = getPropertyName(QNAppointment.nAppointment.tasks);

                binder.registerCustomEditor(Collection.class, propertyName, new PropertyValuesEditor() {
                    @Override
                    public Object getValue() {

                        final List<?> tasks = (List<?>) super.getValue();
                        final Collection<NAppointmentTask> appointmentTasks = Lists.newArrayListWithExpectedSize(tasks.size());

                        logger.debug(String.format("setTasks(rawTasks=%s)", tasks));

                        if (!CollectionUtils.isEmpty(tasks)) {
                            Map<?, ?> rawTaskMap;
                            for (final Object rawTask : tasks) {
                                rawTaskMap = (Map<?, ?>) rawTask;
                                final Object task = rawTaskMap.get(getPropertyName(QNAppointmentTask.nAppointmentTask.task));
                                final Object type = rawTaskMap.get(getPropertyName(QNAppointmentTask.nAppointmentTask.type));
                                if (task != null && type != null) {
                                    appointmentTasks.add(new NAppointmentTask().setTask((String) task).setType(null));
                                }
                            }
                        }

                        return appointmentTasks;
                    }
                });

                binder.setRequiredFields(propertyName);
                binder.setAllowedFields(propertyName);
            }

            private SelectComponent<String, String> getTaskTypes(final Locale locale) throws ComponentCompilationException {
                return selectComponentFactory.fromEnumeration(APPOINTMENT_TASK_TYPES, locale);
            }
        };

        final StepHelper COMMENT = new AbstractStepHelper("comment", true) {

            @Override
            public WizardStepDescriptor getStepDescriptor(final Locale locale) {
                return (WizardStepDescriptor) super.getStepDescriptor(locale)
                        .setRequired(true)
                        .addLabel("comment", messageSource.getMessage("sactivity.comment", locale))
                        .setLabel(messageSource.getMessage("wizard.NewAppointmentWizard.step3.desc", locale));
            }

            @Override
            public void initializeBinder(final DataBinder binder) {
                binder.setAllowedFields(getPropertyName(QNAppointment.nAppointment.comment));
            }

        };

        final StepHelper DEFINITION = new AbstractStepHelper("definition") {
            final Function<NUser, String> keyFunctionUser = new Function<NUser, String>() {
                @Nullable
                @Override
                public String apply(@Nullable final NUser input) {
                    assert input != null;
                    return input.getIdentity();
                }
            };
            final Function<NUser, Long> valueFunctionUser = new Function<NUser, Long>() {
                @Nullable
                @Override
                public Long apply(@Nullable final NUser input) {
                    assert input != null;
                    return input.getId();
                }
            };
            final Function<NCar, Long> keyFunctionCar = new Function<NCar, Long>() {
                @Nullable
                @Override
                public Long apply(@Nullable final NCar input) {
                    assert input != null;
                    return input.getId();
                }
            };
            final Function<NCar, String> valueFunctionCar = new Function<NCar, String>() {
                @Nullable
                @Override
                public String apply(@Nullable final NCar input) {
                    assert input != null;
                    return input.getLicencePlate();
                }
            };
            final String step = "definition";

            @Override
            public boolean isValidationEnabled() {
                return true;
            }

            @Override
            public WizardStepDescriptor getStepDescriptor(final Locale locale) {
                return (WizardStepDescriptor) new WizardStepDescriptor()
                        .setStep(this.step)
                        .setRequired(true)
                        .addLabel("begin", messageSource.getMessage("NAppointment.begin", locale))
                        .addLabel("end", messageSource.getMessage("NAppointment.end", locale))
                        .addLabel("reporter", messageSource.getMessage("NAppointment.reporter", locale))
                        .addLabel("assignee", messageSource.getMessage("NAppointment.assignee", locale))
                        .addLabel("car", messageSource.getMessage("NAppointment.car", locale))
                        .setLabel(messageSource.getMessage("wizard.NewAppointmentWizard.step1.desc", locale));
            }

            @Override
            public ModelMap initialize(final Locale locale) throws Exception {
                final ModelMap map = new ModelMap();

                map.addAttribute("dateFormat", messageSource.getMessage("data.format.value", locale));
                map.addAttribute("timeFormat", messageSource.getMessage("date.format.hours", locale));

                map.addAttribute(CARS, this.getCars());
                map.addAttribute(ASSIGNEES, this.getUsers(appointmentService.findAssignees()));
                map.addAttribute(REPORTERS, this.getUsers(appointmentService.findReporters()));

                return map;
            }

            private SelectComponent<Long, String> getCars() throws Exception {
                return selectComponentFactory
                        .<Long, String, NCar>newSelectComponent()
                        .from(carService.findAll())
                        .usingValueFunction(keyFunctionCar)
                        .usingLabelFunction(valueFunctionCar)
                        .withValueOrder(KEY_ORDER)
                        .get();
            }

            private SelectComponent<Long, String> getUsers(Collection<NUser> users) throws Exception {
                return selectComponentFactory
                        .<Long, String, NUser>newSelectComponent()
                        .from(users)
                        .usingLabelFunction(this.keyFunctionUser)
                        .usingValueFunction(this.valueFunctionUser)
                        .withValueOrder(KEY_ORDER)
                        .get();
            }


            @Override
            public void initializeBinder(final DataBinder binder) {
                final String[] fields = this.getRequiredFields();
                binder.setRequiredFields(fields);
                binder.setAllowedFields(fields);
            }

            private String[] getRequiredFields() {
                final QNAppointment appointment = QNAppointment.nAppointment;
                return new String[]{
                        "beginDate", "endDate", "beginTime", "endTime",
                        getPropertyName(appointment.assignee),
                        getPropertyName(appointment.reporter),
                        getPropertyName(appointment.car),
                };
            }
        };

    }

}
