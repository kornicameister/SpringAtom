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

import org.agatom.springatom.web.wizards.WizardProcessor;
import org.agatom.springatom.web.wizards.data.WizardDescriptor;
import org.apache.log4j.Logger;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.core.GenericTypeResolver;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Errors;

import java.util.Locale;
import java.util.Map;

/**
 * <small>Class is a part of <b>SpringAtom</b> and was created at 2014-08-17</small>
 *
 * @param <T> params object {@link org.agatom.springatom.web.wizards.WizardProcessor} submits upon finish
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
abstract public class AbstractWizardProcessor<T>
        extends AbstractWizardHandler
        implements WizardProcessor<T> {
    private static final Logger LOGGER = Logger.getLogger(AbstractWizardProcessor.class);

    public AbstractWizardProcessor() {
        super();
    }

    @Override
    public final WizardDescriptor initialize(final Locale locale) {
        LOGGER.debug(String.format("initialize(locale=%s)", locale));
        return this.getDescriptor(locale);
    }

    @Override
    public T submit(final Map<String, Object> stepData) throws Exception {
        LOGGER.debug(String.format("submit(stepData=%s)", stepData));

        T contextObject = this.getContextObject();
        final DataBinder binder = this.createBinder(contextObject);

        binder.bind(new MutablePropertyValues(stepData));
        contextObject = this.submitWizard(contextObject, stepData, binder.getBindingResult());

        binder.close();

        return contextObject;
    }

    private DataBinder createBinder(final T contextObject) throws Exception {
        return this.createBinder(contextObject, this.getContextObjectName());
    }

    protected String getContextObjectName() {
        return DataBinder.DEFAULT_OBJECT_NAME;
    }

    @SuppressWarnings("unchecked")
    protected T getContextObject() throws Exception {
        return (T) GenericTypeResolver.resolveTypeArgument(getClass(), AbstractWizardProcessor.class).newInstance();
    }

    protected abstract T submitWizard(final T contextObject, final Map<String, Object> stepData, final Errors errors) throws Exception;

    /**
     * Returns {@link org.agatom.springatom.web.wizards.data.WizardDescriptor} that contain full definition
     * of a step.
     *
     * @param locale current locale
     *
     * @return the descriptor
     */
    protected abstract WizardDescriptor getDescriptor(final Locale locale);

}
