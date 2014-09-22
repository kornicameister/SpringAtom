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

package org.agatom.springatom.web.wizards.core;

import com.google.common.base.Throwables;
import org.agatom.springatom.server.model.oid.SOidService;
import org.agatom.springatom.web.locale.SMessageSource;
import org.agatom.springatom.web.wizards.StepHelper;
import org.agatom.springatom.web.wizards.Wizard;
import org.agatom.springatom.web.wizards.WizardProcessor;
import org.agatom.springatom.web.wizards.data.result.FeedbackMessage;
import org.agatom.springatom.web.wizards.data.result.WizardDebugDataKeys;
import org.agatom.springatom.web.wizards.data.result.WizardResult;
import org.agatom.springatom.web.wizards.validation.ValidationService;
import org.agatom.springatom.web.wizards.validation.model.ValidationBean;
import org.apache.log4j.Logger;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.binding.message.Message;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.style.StylerUtils;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.*;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.annotation.PostConstruct;
import javax.naming.ConfigurationException;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * {@code AbstractWizardProcessor} combines overall logic to access wizard properties as well
 * execute binding and validation process
 *
 * <small>Class is a part of <b>SpringAtom</b> and was created at 2014-08-18</small>
 *
 * @author trebskit
 * @version 0.0.5
 * @since 0.0.1
 */
abstract class AbstractWizardProcessor {
    private static final Logger                      LOGGER             = Logger.getLogger(AbstractWizardProcessor.class);
    @Autowired
    @Qualifier(value = "springAtomConversionService")
    protected            FormattingConversionService conversionService  = null;
    @Autowired
    protected            LocalValidatorFactoryBean   delegatedValidator = null;
    @Autowired
    protected            SMessageSource              messageSource      = null;
    protected            StepHelperDelegate          stepHelperDelegate = null;
    protected            Validator                   localValidator     = null;
    @Autowired
    protected SOidService oidService = null;
    @Autowired
    private              ValidationService           validationService  = null;

    /**
     * Executes actual binding. Provider {@link org.springframework.validation.DataBinder} is used to verify
     * if {@link org.springframework.validation.DataBinder#getTarget()} is valid in context of raw field to actual field mapping.
     * For instance {@code params.get(anAttribute)} must have corresponding property in {@code binder.getTargetObject()}.
     * Type mismatches are resolved via supplied {@link java.beans.PropertyEditorSupport} instances available through {@code binder} and
     * registered before this method executes
     *
     * @param binder binder to use, must be initialized prior this method calling
     * @param step   current step, may be null if {@code binder} binds entire wizard
     * @param params supplied params to be set in {@link org.springframework.validation.DataBinder#getTarget()}
     * @param locale current locale
     *
     * @return local {@link org.agatom.springatom.web.wizards.data.result.WizardResult}
     *
     * @throws Exception if any
     */
    protected WizardResult bind(final DataBinder binder, final String step, final ModelMap params, final Locale locale) throws Exception {

        final WizardResult localResult = new WizardResult().setStepId(step).setWizardId(this.getWizardID());
        final long startTime = System.nanoTime();

        try {
            this.doBind(binder, params);
        } catch (Exception exp) {
            LOGGER.error("Binding error detected", exp);
            localResult.addError(exp);
        }

        this.addErrorsToResultIfAny(binder, locale, localResult);

        final long endTime = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime);

        localResult.addDebugData(WizardDebugDataKeys.BINDING_TIME, endTime);
        localResult.addDebugData(WizardDebugDataKeys.IS_STEP_BINDING, StringUtils.hasText(step));
        localResult.addDebugData(WizardDebugDataKeys.DATA_SIZE, params.size());

        if ((this.isValidationEnabled() || this.isValidationEnabledForStep(step))) {
            // Do not validate if bindingErrors
            this.doValidate(localResult, binder, step, params, locale);
        }

        return localResult;
    }

    protected String getWizardID() {
        final String value = this.getWizardAnnotation().value();
        if (!StringUtils.hasText(value)) {
            return StringUtils.uncapitalize(ClassUtils.getShortName(this.getClass()));
        }
        return value;
    }

    private void doBind(final DataBinder binder, Map<String, Object> params) throws Exception {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("Binding allowed request parameters in %s to form object with name '%s', pre-bind formObject toString = %s", params, binder.getObjectName(), binder.getTarget()));
            if (binder.getAllowedFields() != null && binder.getAllowedFields().length > 0) {
                LOGGER.debug(String.format("(Allowed fields are %s)", StylerUtils.style(binder.getAllowedFields())));
            } else {
                LOGGER.debug("(Any field is allowed)");
            }
        }
        binder.bind(new MutablePropertyValues(params));
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("Binding completed for form object with name '%s', post-bind formObject toString = %s", binder.getObjectName(), binder.getTarget()));
            LOGGER.debug(String.format("There are [%d] errors, details: %s", binder.getBindingResult().getErrorCount(), binder.getBindingResult().getAllErrors()));
        }
    }

    /**
     * If {@link org.springframework.validation.DataBinder#getBindingResult()}} contains any errors then this method
     * will update {@code localResult} with :
     * <ol>
     * <li>{@link org.springframework.validation.ObjectError} to {@link org.agatom.springatom.web.wizards.data.result.WizardResult#addBindingError(org.springframework.validation.ObjectError)}</li>
     * <li>{@link org.agatom.springatom.web.wizards.data.result.FeedbackMessage} to {@link org.agatom.springatom.web.wizards.data.result.WizardResult#addFeedbackMessage(org.agatom.springatom.web.wizards.data.result.FeedbackMessage)}</li>
     * </ol>
     *
     * @param binder      current binder
     * @param locale      current locale
     * @param localResult current result
     *
     * @see org.agatom.springatom.web.wizards.data.result.FeedbackMessage#newError()
     * @see org.agatom.springatom.web.wizards.data.result.FeedbackType
     * @see org.springframework.validation.Errors
     * @see #getBindErrorFM(org.springframework.validation.ObjectError, java.util.Locale)
     */
    private void addErrorsToResultIfAny(final DataBinder binder, final Locale locale, final WizardResult localResult) {
        final Errors errors = binder.getBindingResult();
        if (errors.hasErrors()) {
            final Object contextObject = binder.getTarget();
            LOGGER.warn(String.format("Found %d binding bindingResult for context object=%s", errors.getErrorCount(), contextObject));
            for (final ObjectError objectError : errors.getAllErrors()) {
                localResult.addFeedbackMessage(this.getBindErrorFM(objectError, locale));
                localResult.addBindingError(objectError);
            }
        }
    }

    /**
     * Returns true only if {@link org.agatom.springatom.web.wizards.Wizard#validate()} is true
     *
     * @return true if validation is enabled for entire wizard
     */
    protected boolean isValidationEnabled() {
        return this.getWizardAnnotation().validate();
    }

    /**
     * Method will return true if
     * <ol>
     * <li>{@code step} has text</li>
     * <li>particular {@link org.agatom.springatom.web.wizards.StepHelper} returns true for validation question</li>
     * </ol>
     *
     * @param step current step, may be null if validating entire wizard submission
     *
     * @return true if step validation enabled
     *
     * @see org.springframework.util.StringUtils#hasText(String)
     * @see org.agatom.springatom.web.wizards.core.StepHelperDelegate#isValidationEnabled(String)
     * @see org.agatom.springatom.web.wizards.StepHelper#isValidationEnabled()
     */
    private boolean isValidationEnabledForStep(String step) {
        final boolean isStepSubmission = StringUtils.hasText(step);
        if (!isStepSubmission) {
            LOGGER.trace(String.format("step is not defined, therefore it may be a wizard submission, getting last step pointer"));
            step = this.stepHelperDelegate.getLastStep();
        }
        final boolean enabled = this.stepHelperDelegate.isValidationEnabled(step);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("Validation for step=%s as %s step is %s", step, (isStepSubmission ? "not last" : "last"), (enabled ? "enabled" : "disabled")));
        }
        return enabled;
    }

    @SuppressWarnings("UnusedAssignment")
    private void doValidate(final WizardResult result, final DataBinder binder, String step, final Map<String, Object> formData, final Locale locale) throws Exception {

        final Object target = binder.getTarget();

        if (!StringUtils.hasText(step)) {
            // Wizard submission, because step is null
            step = this.stepHelperDelegate.getLastStep();
            if (!this.stepHelperDelegate.isValidationEnabled(step)) {
                // reset the step again
                step = null;
            }
        }

        try {
            final BindingResult bindingResult = binder.getBindingResult();
            boolean alreadyValidate = false;

            if (this.localValidator != null) {
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug(String.format("Validating via Validator instance=%s", this.localValidator));
                }
                this.validationService.validate(this.localValidator, bindingResult, result);
                alreadyValidate = true;
            }

            if (!alreadyValidate) {
                final ValidationBean bean = new ValidationBean();

                bean.setPartialResult(result);
                bean.setStepId(step);
                bean.setCommandBean(bindingResult.getTarget());
                bean.setCommandBeanName(this.getContextObjectName());
                bean.setFormData(formData);
                bean.setBindingModel(formData);

                if (this.validationService.canValidate(bean)) {
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug(String.format("Validating via validation service for validationBean=%s", bean));
                    }
                    this.validationService.validate(bean);
                    alreadyValidate = true;
                }
            }

            /* Will validate only if not yet validated it is not step submission and wizard is allowed to validate
             * This a last opportunity to validate however unlike validation via
             * - localValidator
             * - validationService
             * this validation will be run only if
             * - not yet validated
             * - current (or last) step has validation flag set
             * - entire wizard has validation flag set
             */
            if (!alreadyValidate && this.isValidationEnabledForStep(step) && this.isValidationEnabled()) {
                LOGGER.debug(String.format("Not yet validated (tried localValidator and via validationService), assuming that is wizard submission due to step===null, validating through binder"));
                final Validator validator = binder.getValidator();
                if (validator != null) {

                    final long startTime = System.nanoTime();
                    validator.validate(target, bindingResult);
                    final long endTime = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime);

                    result.addDebugData(WizardDebugDataKeys.VALIDATION_TIME, endTime);
                    result.addDebugData(WizardDebugDataKeys.VALIDATOR, ClassUtils.getShortName(validator.getClass()));

                }
            }

            if (LOGGER.isDebugEnabled()) {
                final Set<Message> messages = result.getValidationMessages();
                final short count = (short) (messages == null ? 0 : messages.size());
                LOGGER.debug(String.format("Validation completed, found %d validation errors", count));
            }
        } catch (Exception exp) {
            // Catch any validation exception and add it as an error
            LOGGER.error("Validation failed either via [localValidator,validationService,binder#validator", exp);
            result.addError(exp);
            result.addFeedbackMessage(
                    FeedbackMessage.newError()
                            .setTitle(this.messageSource.getMessage("sa.wiz.validationError.title", locale))
                            .setMessage(this.messageSource.getMessage("sa.wiz.validationError.msg", new Object[]{target.toString()}, locale))
            );
        }

    }

    private Wizard getWizardAnnotation() {
        return AnnotationUtils.findAnnotation(this.getClass(), Wizard.class);
    }

    /**
     * Creates {@link org.agatom.springatom.web.wizards.data.result.FeedbackMessage#newError()} message out of {@link org.springframework.validation.ObjectError}.
     * Runs through {@link org.springframework.validation.ObjectError#getCodes()}} and loops until it locates a valid message (i.e. localized one). In first place
     * it tries to resolve such message out of {@link org.springframework.validation.ObjectError#getCode()}. If both attempts fails {@link org.springframework.validation.ObjectError#getDefaultMessage()}
     * is used to create feedback
     *
     * @param objectError current object error
     * @param locale      current locale
     *
     * @return new feedback message
     */
    private FeedbackMessage getBindErrorFM(final ObjectError objectError, final Locale locale) {
        final FeedbackMessage message = FeedbackMessage.newError();
        final String[] codes = objectError.getCodes();
        boolean found = false;
        String msg = this.messageSource.getMessage(objectError.getCode(), locale);

        if (msg.equals(objectError.getCode())) {
            for (final String code : codes) {
                msg = this.messageSource.getMessage(code, locale);
                if (!msg.equals(code)) {
                    msg = this.messageSource.getMessage(code, objectError.getArguments(), locale);
                    found = true;
                }
                if (found) {
                    break;
                }
            }
        } else {
            found = true;
        }

        if (!found) {
            msg = objectError.getDefaultMessage();
        }

        message.setMessage(msg);

        return message;
    }

    /**
     * By default this method returns {@link org.agatom.springatom.web.wizards.WizardProcessor#DEFAULT_FORM_OBJECT_NAME}.
     * To specify different name override this method.
     *
     * @return context object name
     */
    protected String getContextObjectName() {
        return WizardProcessor.DEFAULT_FORM_OBJECT_NAME;
    }

    /**
     * Creates <b>global binder</b>. The initialization of such binder (ran through {@link #initializeGlobalBinder(org.springframework.validation.DataBinder)})
     * should be equivalent to initializing binder for each step at the time.
     *
     * @param contextObject context object
     *
     * @return initialized global binder
     *
     * @throws Exception if any
     * @see #initializeGlobalBinder(org.springframework.validation.DataBinder)
     */
    protected final DataBinder createGlobalBinder(final Object contextObject) throws Exception {
        final DataBinder binder = this.createBinder(contextObject, this.getContextObjectName());
        this.initializeGlobalBinder(binder);
        return binder;
    }

    /**
     * Creates {@link org.springframework.validation.DataBinder} without paying attention on {@link org.springframework.validation.DataBinder#setAllowedFields(String...)} or
     * {@link org.springframework.validation.DataBinder#setRequiredFields(String...)}
     *
     * @param contextObject     context object
     * @param contextObjectName context object name
     *
     * @return the binder
     */
    private DataBinder createBinder(final Object contextObject, final String contextObjectName) {
        LOGGER.debug(String.format("createGlobalBinder(contextObject=%s,contextObjectName=%s)", contextObject, contextObjectName));

        Assert.notNull(contextObject, "contextObject must not be null");
        Assert.notNull(contextObjectName, "contextObjectName must not be null");

        final DataBinder binder = new WizardDataBinder(contextObject, contextObjectName);

        binder.setIgnoreUnknownFields(true);
        binder.setAutoGrowNestedPaths(true);
        binder.setConversionService(this.conversionService);
        binder.setValidator(this.delegatedValidator);
        binder.setMessageCodesResolver(new DefaultMessageCodesResolver());

        return binder;
    }

    /**
     * Purpose of this method is to initialize raw {@link org.springframework.validation.DataBinder} as created
     * via {@link #createGlobalBinder(Object)}
     *
     * @param binder raw binder
     */
    protected void initializeGlobalBinder(final DataBinder binder) {
        this.stepHelperDelegate.initializeGlobalBinder(binder);
    }

    /**
     * Creates {@code step} specific binder. It is different then calling {@link #initializeGlobalBinder(org.springframework.validation.DataBinder)}
     * because abilities of such binder are limited, in context of required / allowed fields and {@link java.beans.PropertyEditorSupport}, to the actual step
     *
     * @param contextObject context object
     * @param step          active step
     *
     * @return initialized step binder
     *
     * @throws Exception if any
     * @see #initializeStepBinder(org.springframework.validation.DataBinder, String)
     */
    protected final DataBinder createStepBinder(final Object contextObject, final String step) throws Exception {
        Assert.notNull(step, "step can not null");
        Assert.notNull(contextObject, "contextObject can not null");
        final DataBinder binder = this.createBinder(contextObject, this.getContextObjectName());
        this.initializeStepBinder(binder, step);
        return binder;
    }

    /**
     * Allows to initialize step specific binder as created via {@link #createStepBinder(Object, String)}
     *
     * @param binder raw binder
     * @param step   active step
     */
    protected void initializeStepBinder(final DataBinder binder, final String step) {
        this.stepHelperDelegate.initializeBinder(step, binder);
    }

    protected ModelMap initializeStep(final String step, final Locale locale) throws Exception {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("initializeStep(step=%s,locale=%s)", step, locale));
        }
        return this.stepHelperDelegate.initialize(step, locale);
    }

    @PostConstruct
    private void loadStepHelperDelegate() {
        LOGGER.trace("loadStepHelperDelegate()");
        final StepHelper[] helpers = this.getStepHelpers();
        Assert.notNull(helpers, "Helpers cannot be null");
        try {
            this.stepHelperDelegate = StepHelperDelegate.newStepHelperDelegate(helpers);
        } catch (ConfigurationException e) {
            LOGGER.fatal("Failed to initialized stepHelperDelegate, configuration failed", e);
            throw new BeanInitializationException(e.getMessage(), Throwables.getRootCause(e));
        }
    }

    /**
     * Must be implemented by subclasses in order to make
     * some method to be automatically routed to the appropriate {@link org.agatom.springatom.web.wizards.StepHelper}
     * via {@link org.agatom.springatom.web.wizards.core.StepHelperDelegate}
     *
     * @return array of step helpers
     */
    protected abstract StepHelper[] getStepHelpers();

    /**
     * If {@link #localValidator} is enabled therefore it is possible to run validation
     * through it instead of via {@link org.agatom.springatom.web.wizards.validation.ValidationService}.
     * Notice that setting validators is not inclusively, therefore only one validation
     * applies at the moment. If {@link #localValidator} is set, validation will be run only through it
     *
     * @param localValidator local validator
     */
    public void setLocalValidator(final Validator localValidator) {
        this.localValidator = localValidator;
    }
}
