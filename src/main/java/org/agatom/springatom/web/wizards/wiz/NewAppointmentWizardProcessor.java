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

package org.agatom.springatom.web.wizards.wiz;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.mysema.query.types.Path;
import org.agatom.springatom.server.model.beans.appointment.QSAppointment;
import org.agatom.springatom.server.model.beans.appointment.QSAppointmentTask;
import org.agatom.springatom.server.model.beans.appointment.SAppointment;
import org.agatom.springatom.server.model.beans.appointment.SAppointmentTask;
import org.agatom.springatom.server.model.beans.car.SCar;
import org.agatom.springatom.server.model.beans.enumeration.SEnumeration;
import org.agatom.springatom.server.model.beans.enumeration.SEnumerationEntry;
import org.agatom.springatom.server.model.beans.user.SUser;
import org.agatom.springatom.server.service.domain.SAppointmentService;
import org.agatom.springatom.server.service.domain.SCarService;
import org.agatom.springatom.web.component.ComponentCompilationException;
import org.agatom.springatom.web.component.select.SelectComponent;
import org.agatom.springatom.web.component.select.factory.SelectComponentFactory;
import org.agatom.springatom.web.locale.SMessageSource;
import org.agatom.springatom.web.wizards.Wizard;
import org.agatom.springatom.web.wizards.core.AbstractWizardProcessor;
import org.agatom.springatom.web.wizards.data.WizardDescriptor;
import org.agatom.springatom.web.wizards.data.WizardStepDescriptor;
import org.agatom.springatom.web.wizards.data.result.FeedbackMessage;
import org.agatom.springatom.web.wizards.data.result.WizardResult;
import org.apache.log4j.Logger;
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
 * @version 0.0.1
 * @since 0.0.1
 */
@Wizard(NewAppointmentWizardProcessor.WIZARD_ID)
class NewAppointmentWizardProcessor
        extends AbstractWizardProcessor<SAppointment> {
    protected static final String                 WIZARD_ID              = "newAppointment";
    private static final   Logger                 LOGGER                 = Logger.getLogger(NewAppointmentWizardProcessor.class);
    private static final   String                 FORM_OBJECT_NAME       = "appointment";
    private static final   String[]               REQUIRED_FIELDS        = NewAppointmentWizardProcessor.getRequiredFields();
    private static final   String[]               ALLOWED_FIELDS         = NewAppointmentWizardProcessor.getAllowedFields();
    private static final   String                 CARS                   = "cars";
    private static final   String                 ASSIGNEES              = "assignees";
    private static final   String                 REPORTERS              = "reporters";
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
    private                SAppointmentService    appointmentService     = null;
    @Autowired
    private                SCarService            carService             = null;
    @Autowired
    private                SelectComponentFactory selectComponentFactory = null;

    /**
     * Returns array of allowed fields for {@link org.springframework.validation.DataBinder}
     *
     * @return allowed fields
     */
    private static String[] getAllowedFields() {
        final QSAppointment appointment = QSAppointment.sAppointment;
        final List<String> required = Lists.newArrayList(getRequiredFields());
        required.add(getPropertyName(appointment.assigned));
        return required.toArray(new String[required.size()]);
    }

    /**
     * Returns array of required fields for {@link org.springframework.validation.DataBinder}
     *
     * @return required fields
     */
    private static String[] getRequiredFields() {
        final QSAppointment appointment = QSAppointment.sAppointment;
        return new String[]{
                "beginDate", "endDate", "beginTime", "endTime",
                getPropertyName(appointment.assignee),
                getPropertyName(appointment.reporter),
                getPropertyName(appointment.car),
        };
    }

    private static String getPropertyName(final Path<?> path) {
        return path.getMetadata().getName();
    }

    @Override
    protected WizardResult submitWizard(SAppointment contextObject, final Map<String, Object> stepData, final Locale locale) throws Exception {
        final WizardResult result = new WizardResult()
                .setWizardId("newAppointment");

        contextObject = this.setTasks(contextObject, stepData);
        contextObject = this.setComment(contextObject, stepData);
        contextObject = this.appointmentService.save(contextObject);

        result.setOid(this.getOID(contextObject));
        result.addFeedbackMessage(
                FeedbackMessage
                        .newInfo()
                        .setMessage(
                                this.messageSource.getMessage(
                                        "sa.msg.objectCreated",
                                        new Object[]{this.messageSource.getMessage("sappointment", locale)},
                                        locale
                                )
                        )
        );


        return result;
    }

    /**
     * Sets {@link org.agatom.springatom.server.model.beans.appointment.SAppointment#tasks} in {@code contextObject}.
     * It is done locally after binding process in order to keep {@link org.springframework.format.support.FormattingConversionService} unpolluted with very
     * specific converters.
     *
     * @param contextObject raw context object
     * @param stepData      step data to get tasks from
     *
     * @return updated context object
     *
     * @see #submit(java.util.Map, java.util.Locale)
     */
    private SAppointment setTasks(final SAppointment contextObject, final Map<String, Object> stepData) {
        LOGGER.debug(String.format("setTasks(contextObject=%s)", contextObject));
        final List<?> tasks = (List<?>) stepData.get(getPropertyName(QSAppointment.sAppointment.tasks));
        if (!CollectionUtils.isEmpty(tasks)) {
            final Collection<SAppointmentTask> appointmentTasks = Lists.newArrayListWithExpectedSize(tasks.size());
            Map<?, ?> rawTaskMap;
            for (final Object rawTask : tasks) {
                rawTaskMap = (Map<?, ?>) rawTask;
                final Object task = rawTaskMap.get(getPropertyName(QSAppointmentTask.sAppointmentTask.task));
                final Object type = rawTaskMap.get(getPropertyName(QSAppointmentTask.sAppointmentTask.type));
                if (task != null && type != null) {
                    appointmentTasks.add(new SAppointmentTask().setTask((String) task).setType((String) type));
                }
            }
            contextObject.setTasks(appointmentTasks);
        }
        return contextObject;
    }

    private SAppointment setComment(final SAppointment contextObject, final Map<String, Object> stepData) {
        final String comment = (String) stepData.get(getPropertyName(QSAppointment.sAppointment.comment));
        return StringUtils.hasText(comment) ? (SAppointment) contextObject.setComment(comment) : contextObject;
    }

    @Override
    public String getContextObjectName() {
        return FORM_OBJECT_NAME;
    }

    @Override
    protected WizardDescriptor getDescriptor(final Locale locale) {
        LOGGER.debug(String.format("getDescriptor(locale=%s)", locale));

        final WizardDescriptor descriptor = new WizardDescriptor();

        descriptor.setLabel(this.messageSource.getMessage("sappointment", locale));
        descriptor.addStep(this.steps.DEFINITION.getDescriptor(locale));
        descriptor.addStep(this.steps.TASKS.getDescriptor(locale));
        descriptor.addStep(this.steps.COMMENT.getDescriptor(locale));

        return descriptor;
    }

    @Override
    protected DataBinder createBinder(final Object contextObject, final String contextObjectName) {
        final DataBinder binder = super.createBinder(contextObject, contextObjectName);
        binder.setRequiredFields(REQUIRED_FIELDS);
        binder.setAllowedFields(ALLOWED_FIELDS);
        return binder;
    }

    @Override
    public WizardResult initializeStep(final String step, final Locale locale) {
        LOGGER.debug(String.format("initializeStep(step=%s, locale=%s)", step, locale));
        final ModelMap modelMap = new ModelMap();

        final WizardResult result = new WizardResult()
                .setStepId(step)
                .setWizardId(WIZARD_ID);
        try {
            switch (step) {
                case "definition":
                    modelMap.putAll(this.steps.DEFINITION.init(locale));
                    break;
                case "tasks":
                    modelMap.putAll(this.steps.TASKS.init(locale));
                    break;
            }
        } catch (Exception exp) {
            result.addError(exp);
            result.addFeedbackMessage(FeedbackMessage.newError().setMessage(exp.getLocalizedMessage()));
            return result;
        }

        return result.addStepData(modelMap);
    }

    /**
     * Lists of steps for the appointment
     */
    private class StepsDefinitionHolder {
        final StepHelper TASKS      = new StepHelper() {
            final String step = "tasks";

            @Override
            public WizardStepDescriptor getDescriptor(final Locale locale) {
                return (WizardStepDescriptor) new WizardStepDescriptor()
                        .setStep(this.step)
                        .setRequired(true)
                        .addLabel("tasks", messageSource.getMessage("sappointment.tasks", locale))
                        .addLabel("tasks.task", messageSource.getMessage("sappointment.task.task", locale))
                        .addLabel("tasks.type", messageSource.getMessage("sappointment.task.type", locale))
                        .setLabel(messageSource.getMessage("wizard.NewAppointmentWizard.step2.desc", locale));
            }

            @Override
            public ModelMap init(final Locale locale) throws ComponentCompilationException {
                final ModelMap map = new ModelMap();
                map.addAttribute("taskTypes", this.getTaskTypes(locale));
                return map;
            }

            private SelectComponent<String, String> getTaskTypes(final Locale locale) throws ComponentCompilationException {
                final SEnumeration appointmentTypes = appointmentService.getAppointmentTypes();
                return selectComponentFactory
                        .<String, String, SEnumerationEntry>newSelectComponent()
                        .from(appointmentTypes.getEntries())
                        .usingValueFunction(new Function<SEnumerationEntry, String>() {
                            @Nullable
                            @Override
                            public String apply(@Nullable final SEnumerationEntry input) {
                                assert input != null;
                                return input.getKey();
                            }
                        })
                        .usingLabelFunction(new Function<SEnumerationEntry, String>() {
                            @Nullable
                            @Override
                            public String apply(@Nullable final SEnumerationEntry input) {
                                assert input != null;
                                return messageSource.getMessage(input.getKey(), locale);
                            }
                        })
                        .get();

            }
        };
        final StepHelper COMMENT    = new StepHelper() {
            final String step = "comment";

            @Override
            public WizardStepDescriptor getDescriptor(final Locale locale) {
                return (WizardStepDescriptor) new WizardStepDescriptor()
                        .setStep(this.step)
                        .setRequired(true)
                        .addLabel("comment", messageSource.getMessage("sactivity.comment", locale))
                        .setLabel(messageSource.getMessage("wizard.NewAppointmentWizard.step3.desc", locale));
            }

            @Override
            public ModelMap init(final Locale locale) {
                return null;
            }
        };
        final StepHelper DEFINITION = new StepHelper() {
            final Function<SUser, String> keyFunctionUser = new Function<SUser, String>() {
                @Nullable
                @Override
                public String apply(@Nullable final SUser input) {
                    assert input != null;
                    return input.getIdentity();
                }
            };
            final Function<SUser, Long> valueFunctionUser = new Function<SUser, Long>() {
                @Nullable
                @Override
                public Long apply(@Nullable final SUser input) {
                    assert input != null;
                    return input.getId();
                }
            };
            final Function<SCar, Long> keyFunctionCar = new Function<SCar, Long>() {
                @Nullable
                @Override
                public Long apply(@Nullable final SCar input) {
                    assert input != null;
                    return input.getId();
                }
            };
            final Function<SCar, String> valueFunctionCar = new Function<SCar, String>() {
                @Nullable
                @Override
                public String apply(@Nullable final SCar input) {
                    assert input != null;
                    return input.getLicencePlate();
                }
            };
            final String step = "definition";

            @Override
            public WizardStepDescriptor getDescriptor(final Locale locale) {
                return (WizardStepDescriptor) new WizardStepDescriptor()
                        .setStep(this.step)
                        .setRequired(true)
                        .addLabel("begin", messageSource.getMessage("sappointment.begin", locale))
                        .addLabel("end", messageSource.getMessage("sappointment.end", locale))
                        .addLabel("reporter", messageSource.getMessage("sappointment.reporter", locale))
                        .addLabel("assignee", messageSource.getMessage("sappointment.assignee", locale))
                        .addLabel("car", messageSource.getMessage("sappointment.car", locale))
                        .setLabel(messageSource.getMessage("wizard.NewAppointmentWizard.step1.desc", locale));
            }

            @Override
            public ModelMap init(final Locale locale) throws Exception {
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
                        .<Long, String, SCar>newSelectComponent()
                        .from(carService.findAll())
                        .usingValueFunction(keyFunctionCar)
                        .usingLabelFunction(valueFunctionCar)
                        .withValueOrder(KEY_ORDER)
                        .get();
            }

            private SelectComponent<Long, String> getUsers(Collection<SUser> users) throws Exception {
                return selectComponentFactory
                        .<Long, String, SUser>newSelectComponent()
                        .from(users)
                        .usingLabelFunction(this.keyFunctionUser)
                        .usingValueFunction(this.valueFunctionUser)
                        .withValueOrder(KEY_ORDER)
                        .get();
            }
        };


    }


}
